/**
 * 
 */
package com.luckymiao.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


public class FileUtils {
	private static Logger logger = LogUtils
			.getLogger(FileUtils.class.getName());
	/** 文件缓冲区的长度 */
	private static int buffersize = 1024;

	/**
	 * 构造函数
	 */
	public FileUtils() {
	}

	/**
	 * 取得文件的真实路径
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件所在路径
	 */
	public synchronized static String getCanonicalFileName(String fileName) {
		try {
			File file = new File(fileName);
			return file.getCanonicalPath();// + File.separatorChar +
											// file.getName();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return "";
		}
	}

	/**
	 * 得到文件或者路径的 相对于操作系统的 真实路径
	 * 
	 * @param s
	 * @return
	 */
	public static String getRealPath(String s) {

		try {
			String separator = System.getProperties().getProperty(
					"file.separator");
			if ("/".equalsIgnoreCase(separator)) {
				s = s.replace('\\', '/');
			} else {
				s = s.replace('/', '\\');
			}
		} catch (Exception e) {

		}
		return s;
	}

	/**
	 * 说明：将字符串写入一个文件中(static)
	 * 
	 * @param path
	 *            路径名称
	 * @param filename
	 *            读取的文件模版
	 * @param str
	 *            写入的字符串
	 */
	public static void writeFile(String path, String filename, String str) {
		try {
			File filePath = new File(path);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			File file = new File(path, filename);
			PrintWriter pw = new PrintWriter(new FileOutputStream(file));
			pw.println(str);
			pw.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static boolean writeFile(String filename, InputStream in) {
		FileOutputStream output;
		try {
			output = new FileOutputStream(filename);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return false;
		}
		byte[] buffer = new byte[buffersize];
		int len;
		try {
			while ((len = in.read(buffer, 0, buffersize)) > 0) {
				output.write(buffer, 0, len);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return false;
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
				return false;
			}
		}
	}

	/**
	 * 说明：读取文件内容(static)
	 * 
	 * @param filename
	 *            读取的文件名
	 * @return String 文件的字符串代码
	 */
	public static String readFile(String filename) {
		String return_str = "";
		try {
			FileReader fr = new FileReader(filename);
			LineNumberReader lr = new LineNumberReader(fr, 512);
			while (true) {
				String str = lr.readLine();
				if (str == null)
					break;
				return_str += str + "\n";
			}
			lr.close();
		} catch (FileNotFoundException e) {
			System.out.println("FILENAME:" + filename);
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("IO error");
		}
		return return_str;
	}
	

	/**
	 * 说明:单个文件的拷贝 文件属性在拷贝中丢失
	 * 
	 * @param from
	 *            原文件
	 * @param to
	 *            目标文件
	 * @return boolean true 成功 false 失败
	 */
	public static boolean copyFile(File from, File to) {
		FileInputStream input;
		FileOutputStream output;
		try {
			input = new FileInputStream(from);
			output = new FileOutputStream(to);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return false;
		}
		byte[] buffer = new byte[buffersize];
		int len;
		try {
			while ((len = input.read(buffer, 0, buffersize)) > 0) {
				output.write(buffer, 0, len);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return false;
		} finally {
			try {
				input.close();
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
				return false;
			}
		}
	}

	/**
	 * 说明：将内容写到文件中,如果文件已经存在，则接到后面写
	 * 
	 * @param directory
	 *            路径
	 * @param filename
	 *            文件名
	 * @param content
	 *            内容
	 * @return boolean 写文件成功或失败
	 */
	public boolean contentAppendToFile(String directory, String filename,
			String content) {
		FileOutputStream fos = null;
		boolean flag = true;
		try {
			File objFile = new File(directory, filename);
			if (!objFile.exists()) {
				objFile.createNewFile();
				System.out.println("文件不存在!");
			}
			byte[] b = content.getBytes();
			fos = new FileOutputStream(directory + "/" + filename, true);
			fos.write(b);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
		return flag;
	}

	/**
	 * 删除文件
	 * 
	 * @param filename
	 *            文件名(要包括完整的路径)
	 */
	public static void deleteDir(String filename) {
		boolean isSucc = false;
		try {
			File file = new File(filename);
			if (file.exists()) {
				deleteFile(file);
				isSucc = true;
			}
		} catch (SecurityException e) {
			System.out.println("Delete File Fails:" + e.getMessage());
		} finally {
			if (isSucc) {
				System.out.println("删除成功!");
			} else {
				System.out.println("删除失败!");
			}
		}
	}

	public static void deleteFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteFile(files[i]);
			}
			file.delete();
		} else {
			file.delete();
		}
	}

	/**
	 * 判断文件路径是否存在
	 * 
	 * @param path
	 *            文件路径
	 * @return boolean
	 */
	public static boolean isExists(String path) {
		boolean exists = false;
		File file = new File(path);
		if (file.exists()) {
			exists = true;
		}

		return exists;
	}

	/**
	 * 取得某个文件路径下的所有文件列表
	 * 
	 * @param path
	 *            文件路径
	 * @param filter
	 *            文件过滤器
	 * @return List
	 * @throws Exception
	 */
	public static List getListFiles(String path, FileFilter filter)
			throws Exception {
		List list = new ArrayList();

		File file = new File(path);

		if (!file.exists()) {
			return null;
		}
		if (!file.isDirectory()) {
			list.add(file);
			return list;
		}

		File files[] = file.listFiles(filter);
		for (int i = 0; i < files.length; i++) {
			File temp = files[i];
			if (temp.isFile()) {// 文件,直接添加该文件
				list.add(temp);
				continue;
			}
			List fileList = getListFiles(temp, filter);// 目录,进行查找目录中的文件
			if (fileList != null) {
				list.addAll(fileList);
			}
		}

		return list;
	}

	/**
	 * 取得某个文件下的所有文件列表
	 * 
	 * @param file
	 *            文件
	 * @param filter
	 *            文件过滤器
	 * @return List
	 * @throws Exception
	 */
	public static List getListFiles(File file, FileFilter filter)
			throws Exception {
		if (file == null)
			return null;
		List list = new ArrayList();

		if (!file.isDirectory()) {
			list.add(file);
			return list;
		}
		File files[] = file.listFiles(filter);
		for (int i = 0; i < files.length; i++) {
			File temp = files[i];
			if (temp.isFile()) {// 文件,直接添加该文件
				list.add(temp);
				continue;
			}
			List fileList = getListFiles(temp, filter);// 目录,进行查找目录中的文件
			if (fileList != null) {
				list.addAll(fileList);
			}
		}

		return list;
	}

	public void createFile(String filePathAndName, String fileContent,
			String encoding) throws Exception {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			PrintWriter myFile = new PrintWriter(myFilePath, encoding);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
		} catch (Exception e) {
			throw new Exception("创建文件操作出错" + e.getMessage());
		}
	}
	/**
	 * 读取文件内容为字符串
	 * @param fileName
	 * @param encoding
	 * @return
	 */
	public static String read(String fileName, String encoding) {
		StringBuffer fileContent = new StringBuffer();
		try {
			FileInputStream fis = new FileInputStream(fileName);
			InputStreamReader isr = new InputStreamReader(fis, encoding);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				fileContent.append(line);
				fileContent.append(System.getProperty(" line.separator "));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileContent.toString();
	}
	/**
	 * 将字符串以指定的编码写入指定文件中
	 * @param fileContent
	 * @param fileName
	 * @param encoding
	 */
	public static void write(String fileContent, String fileName,
			String encoding) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos, encoding);
			osw.write(fileContent);
			osw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取文件中的内容，并将内容中的值放入Map中
	 * 
	 * @param fileName
	 * @param encoding
	 * @return
	 */
	public static HashMap readFile(String fileName, String encoding) {
		StringBuffer fileContent = new StringBuffer();
		HashMap map = new HashMap();
		try {
			FileInputStream fis = new FileInputStream(fileName);
			InputStreamReader isr = new InputStreamReader(fis, encoding);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			int i = 0;
			while ((line = br.readLine()) != null) {
				i++;
				map.put(i, line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public static String getExtensionByFileName(String fileName){
		if(fileName == null || "".equals(fileName)){
			return "";
		}
		String extension = fileName.substring(fileName.lastIndexOf("//")+1);
		return extension;
	}
	/**
	 * 主函数
	 * 
	 * @param args
	 *            测试参数
	 */
	public static void main(String[] args) {
		FileUtils fUtils = new FileUtils();
		DateUtils date = new DateUtils();
		// 获得当前日期
		String strdate = date.getCurrDate();
		// 文件路径
		System.out.println(System.getProperty("img.root_path"));
		String path = "c:\\temp\\FQZ_YG_" + strdate + ".txt";
		// 判断文件是否存在
		if (fUtils.isExists(path)) {
			Map s = fUtils.readFile(path, "GBK");
			System.out.println(s);
		} else {
			System.out.println(path + "文件不存在，请确认！");
		}
	}
}
