package xx.love.cc;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtil {

	/**
	 * 得到指定包下面的所有类的Class对象
	 * @param pack
	 * @return
	 */
	public static List<Class<?>> getClasses(Package pack) {
		List<Class<?>> classList = new ArrayList<Class<?>>();
		String packageName = pack.getName();
		String packageDirName = packageName.replace('.', '/');
		Enumeration<URL> urls;
		try {
			urls = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				if (url != null) {
					String protocol = url.getProtocol();//得到协议
					if (protocol.equals("file")) {//文件路径
						String packagePath = url.getPath();
						addClassByFiles(classList, packagePath, packageName);
					} else if (protocol.equals("jar")) {// 如果是jar包文件
						JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
						JarFile jarFile = jarURLConnection.getJarFile();
						Enumeration<JarEntry> jarEntries = jarFile.entries();
						while (jarEntries.hasMoreElements()) {
							JarEntry jarEntry = jarEntries.nextElement();
							String jarEntryName = jarEntry.getName();
							if (jarEntryName.endsWith(".class")) {
								//去掉.class后缀
								String temp = jarEntryName.substring(0, jarEntryName.lastIndexOf("."));
								//重新转为包路径
								String className = temp.replaceAll("/", ".");
								classList.add(Class.forName(className));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classList;
	}
	
	/**
	 * 以文件的形式添加Class
	 * @param classList
	 * @param packagePath
	 * @param packageName
	 */
	private static void addClassByFiles(List<Class<?>> classList, String packagePath, String packageName) {
		try {
			File temp = new File(packagePath);//取得路径下的临时File对象
			File[] files = temp.listFiles(new FileFilter() {
				// 自定义过滤规则 如果包含子目录 或者是以.class结尾的文件(编译好的java类文件)
				public boolean accept(File file) {
					return file.isDirectory() || file.getName().endsWith(".class");
				}
			});
			//遍历files
			if (files != null) {
				for (File file : files) {
					String fileName = file.getName();
					if (file.isFile()) {//是.class文件
						String className = fileName.substring(0, fileName.lastIndexOf("."));
						if(packageName!=null && !packageName.equals("")){
							className = packageName + "." + className;
						}
						classList.add(Class.forName(className));
					} else {//是子目录，需要递归子目录查找
						String subPackagePath = packagePath + "/" + fileName;
						String subPackageName = packageName + "." + fileName;
						addClassByFiles(classList, subPackagePath, subPackageName);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
