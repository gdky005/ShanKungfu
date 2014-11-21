package shankungfu.com.util;

import java.io.File;

import shankungfu.com.MainApplication;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 各种phone信息接口 例如 imei 手机型号，系统 ，分辨率，应用包及包名
 * 
 */
public class PhoneUtils {
	private static final String TAG = "PhoneUtils";

	/**
	 * 获取屏幕的宽和高的工具类 WangQing 2013-8-12 DisplayMetrics
	 * 
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getAppWidthAndHeight(Context context) {
		// 这个可以用于1.5
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		return dm;
	}

	/**
	 * 获取手机的相关信息 wangqing 2013-6-25 String 返回设备的信息
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceInfo(Context context) {
		PackageManager pm = context.getPackageManager();
		PackageInfo pi = null;
		try {
			pi = pm.getPackageInfo(context.getPackageName(),
					PackageManager.GET_ACTIVITIES);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "android";
		}

		String deviceName = Build.MANUFACTURER + "_" + Build.MODEL;

		String phonePixels = MainApplication.screenWidth + "*"
				+ MainApplication.screenHight;
		String OSVersion = Build.VERSION.RELEASE;
		// 用户的版本号
		String VersionInfo = " versionName:" + pi.versionName + " versionCode:"
				+ pi.versionCode;

		return " android设备信息： " + "设备的名称是：" + deviceName + ",设备的版本："
				+ OSVersion + "分辨率是" + phonePixels + ", 用户的程序版本是" + VersionInfo
				+ "。";
	}

	/**
	 * 获取当前手机分辨率
	 * 
	 * @param context
	 *            上下文
	 * @return 手机分辨率
	 */
	public static String getPhoneDisplayMetrics(Context context) {
		String sPoneDisplayMetrics = null;

		DisplayMetrics dm = new DisplayMetrics();
		WindowManager w = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		w.getDefaultDisplay().getMetrics(dm);
		sPoneDisplayMetrics = dm.widthPixels + " * " + dm.heightPixels;
		TestUtils.logI("手机的分辨率是：" + sPoneDisplayMetrics);
		return sPoneDisplayMetrics;

	}

	/**
	 * 根据路径,获取对应分区的总大小/可用大小
	 * 
	 * @param path
	 *            要获取空间大小的路径
	 * @return 手机内置存储空间大小数组,第一个是总大小,第二个是可用大小
	 */
	public static long[] calcSize(String path) {
		if(path==null){
			return null;
		}
		StatFs stat = new StatFs(path);
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		long availableBlocks = stat.getAvailableBlocks();
		long totalSize = totalBlocks * blockSize;
		long availableSize = availableBlocks * blockSize;
		long[] size = new long[]{totalSize,availableSize};
		return size;
	}

	/**
	 * 将long类型的文件大小数值转换为带单位的String
	 * 
	 * @param size
	 *            long类型的文件大小数值,单位:字节
	 * @return 带单位的文件大小String
	 */
	public static String long2String(long size) {
		size = size / (1024 * 1024);
		if (size < 1024) {
			return size + " MB";
		} else if (size % 1024 == 0) {
			return size / 1024 + " GB";
		} else {
			return String.format("%.2f GB", size / 1024f);
		}
	}
	
	/**
	 * 将long类型的文件大小数值转换为带单位的String,精确到Bytes与KB
	 * 
	 * @param size
	 *            long类型的文件大小数值,单位:字节
	 * @return 带单位的文件大小String
	 */
	public static String convertFileSize(long filesize) {

		String strUnit = "Bytes";
		String strAfterComma = "";

		int intDivisor = 1;
		if (filesize >= 1024 * 1024 * 1024) {
			strUnit = "GB";
			intDivisor = 1024 * 1024 * 1024;
		} else if (filesize >= 1024 * 1024) {
			strUnit = "MB";
			intDivisor = 1024 * 1024;
		} else if (filesize >= 1024) {
			strUnit = "KB";
			intDivisor = 1024;
		}

		if (intDivisor == 1)
			return filesize + " " + strUnit;
		strAfterComma = "" + 100 * (filesize % intDivisor) / intDivisor;
		if (strAfterComma == "")
			strAfterComma = ".0";

		return filesize / intDivisor + "." + strAfterComma + " " + strUnit;

	}

	/**
	 * 获取SD卡路径
	 * 
	 * @return
	 */
	public static String getSdCardPath() {
		//这里兼容有问题，赵兵锋  后面调整下！
//		try {
//			FileReader fr = new FileReader("/system/etc/vold.fstab");
//			BufferedReader br = new BufferedReader(fr);
//			while (br.ready()) {
//				String line = br.readLine().trim();
//				if (line.startsWith("dev_mount")) {
//					String[] arrStr = line.split(" ");
//					if (arrStr.length >= 5 && arrStr[0].equals("dev_mount")
//							&& arrStr[1].equals("sdcard")) {
//						br.close();
//						return arrStr[2];
//					}
//				}
//			}
//		} catch (Exception e) {
//			String path = "";
//			Map<String, String> map = System.getenv();
//			if (map.containsKey("SECONDARY_STORAGE")) {
//				path = map.get("SECONDARY_STORAGE").split(":")[0];
//			} else if (map.containsKey("EXTERNAL_STORAGE")) {
//				path = map.get("EXTERNAL_STORAGE");
//			}
//			return path;
//		}
		File file = Environment.getExternalStorageDirectory();
		if(file==null||!file.exists()){
			return null;
		}
		return file.getPath();
	}

	/**
	 * 获取实际上的SD卡空间大小
	 * <br><h1>注意:</h1>获取到的数据单位是字节,可通过同类静态方法long2String(long)来将此数值转换为带单位字符串
	 * 
	 * @return long数组,第一项是空间总大小,第二项是可用大小
	 */
	public static long[] getSdCardTotalSize() {
		return calcSize(getSdCardPath());
	}

	/**
	 * 外部存储设备时候就绪
	 * 
	 * @return true已挂载可用,false不可用
	 */
	public static boolean isExternalMediaMounted() {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return false;
		}
		return true;
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, int dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, int pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
    
    /**
	 * 获取当前是否允许重力自动旋转方向
	 * 
	 * @param context
	 *            上下文
	 * @return true允许,false不允许
	 */
	public static boolean isRotationLocked(Context context) {
		return Settings.System.getInt(context.getContentResolver(),
				Settings.System.ACCELEROMETER_ROTATION, 0) == 1;
	}

	/**
	 * 禁止重力自动旋转方向
	 * @param context 上下文
	 */
	public static void lockAutoRotation(Context context) {
		Settings.System.putInt(context.getContentResolver(),
				Settings.System.ACCELEROMETER_ROTATION, 0);
	}

	/**
	 * 开启重力自动旋转方向
	 * @param context 上下文
	 */
	public static void unlockAutoRotation(Context context) {
		Settings.System.putInt(context.getContentResolver(),
				Settings.System.ACCELEROMETER_ROTATION, 1);
	}
	
	/**
	 * 获取手机顶部状态栏高度
	 * @param activity
	 * @return
	 */
	public static int getStatusBarHeight(Activity activity) {
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		return statusBarHeight;
	}
	
}
