package com.pingchebao.android;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * 功能：照相或者选择相册图片的剪切 作者：黄荣星
 */
public class PictureMainActivity extends Activity {

	private static final int TAKE_PICTURE = 0;
	private static final int CHOOSE_PICTURE = 1;
	private static final int CROP = 2;
	private static final int CROP_PICTURE = 3;

	private static final int SCALE = 2;// 照片缩小比例
	private ImageView iv_image = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("Demo作者：@唐韧_Ryan");

		iv_image = (ImageView) this.findViewById(R.id.img);

		this.findViewById(R.id.btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 即拍即显示
				showPicturePicker(PictureMainActivity.this, false);
			}
		});

		this.findViewById(R.id.btn2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 截图后显示
				showPicturePicker(PictureMainActivity.this, true);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("hrx", "resultCode:" + resultCode + " requestCode:" + requestCode);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case TAKE_PICTURE:
				// 将保存在本地的图片取出并缩小后显示在界面上
				Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/image.jpg");
				Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / 1, bitmap.getHeight() / SCALE);
				// 由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
				bitmap.recycle();

				// 将处理过的图片显示在界面上，并保存到本地
				iv_image.setImageBitmap(newBitmap);
				Log.i("hrx", "sd card path:" + Environment.getExternalStorageDirectory().getAbsolutePath());
				ImageTools.savePhotoToSDCard(newBitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), String.valueOf(System.currentTimeMillis()));

				break;

			case CHOOSE_PICTURE:
				ContentResolver resolver = getContentResolver();
				// 照片的原始资源地址
				Uri originalUri = data.getData();
				try {
					// 使用ContentProvider通过URI获取原始图片
					Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
					if (photo != null) {
						// 为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
						Bitmap smallBitmap = ImageTools.zoomBitmap(photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
						// 释放原始图片占用的内存，防止out of memory异常发生
						photo.recycle();

						iv_image.setImageBitmap(smallBitmap);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;

			case CROP:
				Uri uri = null;
				if (data != null) {
					uri = data.getData();
					System.out.println("Data");
				} else {
					System.out.println("File");
					String fileName = getSharedPreferences("temp", Context.MODE_WORLD_WRITEABLE).getString("tempName", "");
					uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), fileName));
				}
				cropImage(uri, 500, 500, CROP_PICTURE);
				break;

			case CROP_PICTURE:
				Bitmap photo = null;
				Uri photoUri = data.getData();
				if (photoUri != null) {
					photo = BitmapFactory.decodeFile(photoUri.getPath());
				}
				if (photo == null) {
					Bundle extra = data.getExtras();
					if (extra != null) {
						photo = (Bitmap) extra.get("data");
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
						photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
					}
				}
				iv_image.setImageBitmap(photo);
				break;
			default:
				break;
			}
		}
	}

	public void showPicturePicker(Context context, boolean isCrop) {
		final boolean crop = isCrop;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("图片来源");
		builder.setNegativeButton("取消", null);
		builder.setItems(new String[] { "拍照", "相册" }, new DialogInterface.OnClickListener() {
			// 类型码
			int REQUEST_CODE;

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case TAKE_PICTURE:
					Uri imageUri = null;
					String fileName = null;
					// 打开照相机
					Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					if (crop) {
						REQUEST_CODE = CROP;
						// 删除上一次截图的临时文件
						SharedPreferences sharedPreferences = getSharedPreferences("temp", Context.MODE_WORLD_WRITEABLE);
						ImageTools.deletePhotoAtPathAndName(Environment.getExternalStorageDirectory().getAbsolutePath(), sharedPreferences.getString("tempName", ""));

						// 保存本次截图临时文件名字
						fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
						Editor editor = sharedPreferences.edit();
						editor.putString("tempName", fileName);
						editor.commit();
					} else {
						REQUEST_CODE = TAKE_PICTURE;
						fileName = "image.jpg";
					}
					imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), fileName));
					// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
					openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					startActivityForResult(openCameraIntent, REQUEST_CODE);
					break;

				case CHOOSE_PICTURE:
					Intent openAlbumIntent = new Intent(Intent.ACTION_PICK);
					openAlbumIntent.setType("image/*");// 相片类型
					if (crop) {
						REQUEST_CODE = CROP;
					} else {
						REQUEST_CODE = CHOOSE_PICTURE;
					}
					openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
					startActivityForResult(openAlbumIntent, REQUEST_CODE);
					break;

				default:
					break;
				}
			}
		});
		builder.create().show();
	}

	// 截取图片 调用系统的截取图片
	public void cropImage(Uri uri, int outputX, int outputY, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, requestCode);
	}

}
