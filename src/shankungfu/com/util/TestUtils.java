package shankungfu.com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;


/**
 * 测试代码的工具类
 * @author WangQing
 * 2013-8-9
 */
public class TestUtils {
	
	private static String TAG = "TAG";
	
	/**
	 * 可以将这个TAG 改成自己喜欢的 标记。
	 * WangQing
	 * 2013-8-9
	 * void
	 * @param tag  自己想要改成的 TAG 标记
	 */
	public static void SetTAG(String tag) {
		TAG = tag;
	}
	
	/**
	 * 打印Log，打印普通的 绿色Log日志信息
	 * WangQing
	 * 2013-8-9
	 * void
	 * @param message
	 */
	public static void logI(String message) {
		Log.i(TAG, message);
	}
	
	/**
	 * 打印Log的错误的 红色Log日志信息
	 * WangQing
	 * 2013-8-9
	 * void
	 * @param message
	 */
	public static void logE(String message) {
		Log.e(TAG, message);
	}
	
	
	/**
	 * 打印流里面的信息
	 * WangQing
	 * 2013-8-9
	 * String
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static String PrintfStream(InputStream input) throws IOException {
		if(input!=null){
			BufferedReader br = new BufferedReader(new InputStreamReader(input));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		}else{
			return "";
		}
	}
	
}
