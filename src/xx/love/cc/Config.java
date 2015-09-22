package xx.love.cc;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	
	private static Properties properties;
	
	public static boolean init(String path){
		if(properties == null){
			properties = new Properties();
		}
		try {
			InputStream inStream = new BufferedInputStream(new FileInputStream(path));
			properties.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 获取值
	 * @param key
	 * @return
	 */
	public static int getInt(String key) {
		if (properties == null) {
			return 0;
		}
		String value = properties.getProperty(key);
		return Integer.parseInt(value);
	} 
	
	/**
	 * 获取值
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		if (properties == null) {
			return null;
		}
		return properties.getProperty(key);
	} 
	
	
	
}
