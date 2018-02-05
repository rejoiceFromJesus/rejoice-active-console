/**
 * 系统项目名称
 * com.rejoice.active.console.util
 * Constant.java
 * 
 * 2017年5月11日-上午9:10:39
 *  2017金融街在线公司-版权所有
 *
 */
package com.rejoice.active.console.common.constant;


/**
 *
 * Constant
 * 
 * @author rejoice 948870341@qq.com
 * @date 2017年5月11日 上午9:10:39
 * 
 * @version 1.0.0
 *
 */
public abstract class Constant {

	public static final String PREFIX="rejoice.activeconsole";
	public static final String SUCCESS="success";
	public static final String SESSION_KEY = "loginUser";
	//no auth paths
	public static final String[] PASS_PATHS = new String[]{
		"/page/login.html",
		"/logout","/user/login",
		"/company/active",
		"/user/register",
		"/error"};

}
