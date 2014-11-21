package shankungfu.com;

import shankungfu.com.util.PhoneUtils;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 程序启动后，初始化数据
 * @author WangQing
 * 2013-8-9
 */
public class MainApplication extends Application{
	
	/** 获取屏幕的宽和高 */
	public static int screenHight = 0;
	public static int screenWidth = 0;
	/** 设备的 IMEI */
	public static String IMEI = "";
	/** 获取全局的上下文 */
	public static Context context;
	
	/** 分享的文本内容,使用完后会置空, 通用的分享内容，可写，可不写  */
	public static String ShareContent = "";
	/** 视频的种类Id ，视频分享专用，可能没有*/
	public static String ShareVideoCategory = "";
	/** 视频的标题 ，视频分享专用*/
	public static String ShareVideoTitle = "";
	/** 视频的url ，视频分享专用*/
	public static String ShareVideoUrl = "";
	/** 设置分享的图片的地址 */
	public static String sharePicPath = "";
	
	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}
	
	

	/**
	 * 初始化一些数据
	 * WangQing
	 * 2013-8-12
	 * void
	 */
	private void init() {
		context = getApplicationContext();
		getDeviceWidthAndHeight();
	}
	
	/**
	 * 获取设备的宽和高
	 * WangQing
	 * 2013-8-12
	 * void
	 */
	private void getDeviceWidthAndHeight() {
		DisplayMetrics dm = PhoneUtils.getAppWidthAndHeight(this);
		screenHight = dm.heightPixels;
		screenWidth = dm.widthPixels;
	}
}
