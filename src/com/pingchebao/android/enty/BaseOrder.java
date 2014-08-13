package com.pingchebao.android.enty;

import java.io.Serializable;

/**
 * 功能：订单父类
 * @ahthor：黄荣星
 * @date:2014年6月3日
 * @version::V1.0
 */
public class BaseOrder implements Serializable {
	/**
	 * serialVersionUID：
	 */
	private static final long serialVersionUID = 1L;
	public String mOrderNo;
	public String mStartAdr;
	public String mEndAdr;
}
