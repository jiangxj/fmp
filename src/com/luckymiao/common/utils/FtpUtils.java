/**
 * 
 */
package com.luckymiao.common.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * ����ftp���ļ����ϴ�����ȡ��ɾ��
 * @author yanghl
 */
public class FtpUtils {

	private FTPClient ftpClient;
	private String fileName;
	private String strencoding;
	private int columns;
	private int rowCount;
	private String ip = "127.0.0.1";      // ������IP��ַ
	private String userName = "temp"; 		// �û���
	private String userPwd = "temp";  		// ����
	private int port = 21;                  // �˿ں�
	private String path = "/temp/";         // ��ȡ�ļ��Ĵ��Ŀ¼

	public FtpUtils() {
		this.reSet();
	}

	public void reSet() {
		// �Ե�ǰϵͳʱ��ƴ���ļ���
		fileName = "FQZ_YG_" + getFileName() + ".txt";
		columns = 0;
		rowCount = 0;
		strencoding = "GBK";
		this.connectServer(ip, port, userName, userPwd, path);
	}

	/**
	 * �Ե�ǰϵͳʱ������ļ���
	 * 
	 * @return
	 */
	private String getFileName() {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
		String str = "";
		try {
			str = sdFormat.format(new Date());
		} catch (Exception e) {
			return "";
		}
		if (str.equals("1900-01-01")) {
			str = "";
		}
		return str;
	}

	/**
	 * @param ip
	 * @param port
	 * @param userName
	 * @param userPwd
	 * @param path
	 * @throws SocketException
	 * @throws IOException
	 * function:���ӵ�������
	 */
	public void connectServer(String ip, int port, String userName,
			String userPwd, String path) {
		ftpClient = new FTPClient();
		try {
			// ����
			ftpClient.connect(ip, port);
			// ��¼
			ftpClient.login(userName, userPwd);
			if (path != null && path.length() > 0) {
				// ��ת��ָ��Ŀ¼
				ftpClient.changeWorkingDirectory(path);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws IOException
	 * function:�ر�����
	 */
	public void closeServer() {
		if (ftpClient.isConnected()) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param path
	 * @return function:���ָ��Ŀ¼�������ļ���
	 * @throws IOException
	 */
	public List<String> getFileList(String path) {
		List<String> fileLists = new ArrayList<String>();
		// ���ָ��Ŀ¼�������ļ���
		FTPFile[] ftpFiles = null;
		try {
			ftpFiles = ftpClient.listFiles(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
			FTPFile file = ftpFiles[i];
			if (file.isFile()) {
				fileLists.add(file.getName());
			}
		}
		return fileLists;
	}

	/**
	 * @param fileName
	 * @param sourceFile
	 * @return
	 * @throws IOException
	 * function:�����ļ�
	 */
	public boolean unloadFile(String fileName, String sourceFile) {
		boolean flag = false;
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			flag = ftpClient.retrieveFile(sourceFile, fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * ����һ���ļ���
	 * 
	 * @param fileName
	 * @return
	 */
	public String readFile(String fileName) {
		String result = "";
		InputStream ins = null;
		try {
			ins = ftpClient.retrieveFileStream(fileName);

			// byte []b = new byte[ins.available()];
			// ins.read(b);
			BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
			String inLine = reader.readLine();
			while (inLine != null) {
				result += (inLine + System.getProperty("line.separator"));
				inLine = reader.readLine();
			}
			reader.close();
			if (ins != null) {
				ins.close();
			}

			// ��������һ��getReply()�ѽ�������226��ѵ�. �������ǿ��Խ���������null����
			ftpClient.getReply();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @param fileName
	 * @return function:�ӷ������϶�ȡָ�����ļ�
	 * @throws ParseException
	 * @throws IOException
	 */
	public List readFile() throws ParseException {

		List contentList = new ArrayList();
		InputStream ins = null;
		try {
			// �ӷ������϶�ȡָ�����ļ�
			ins = ftpClient.retrieveFileStream(fileName);

			BufferedReader reader = new BufferedReader(new InputStreamReader(ins, strencoding));
			String inLine = reader.readLine();

			while (inLine != null) {
				// ��������
				if (inLine.length() + 1 > columns){
					columns = inLine.length() + 1;
				}
				inLine = reader.readLine();
				contentList.add(inLine);
				rowCount++;
			}
			reader.close();
			if (ins != null) {
				ins.close();
			}
			// ��������һ��getReply()�ѽ�������226��ѵ�. �������ǿ��Խ���������null����
			ftpClient.getReply();
			System.out.println("�˴�����һ����ȡ[" + contentList.size() + "]����ݼ�¼");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentList;
	}
	
	
	/**
	 * ��ȡ�ļ��е����ݣ����������е�ֵ����Map��
	 * @param fileName ����ļ�·��
	 * @param encoding �ַ�
	 * @return
	 */
     public static  HashMap readFile(String fileName, String encoding) { 
         StringBuffer fileContent  =   new  StringBuffer(); 
         HashMap map = new HashMap();
          try  { 
             FileInputStream fis  =   new  FileInputStream(fileName); 
             InputStreamReader isr  =   new  InputStreamReader(fis, encoding); 
             BufferedReader br  =   new  BufferedReader(isr); 
             String line  =   null ; 
             int i = 0;
             while  ((line  =  br.readLine())  !=   null ) { 
            	 i++;
                 map.put(i,line); 
             } 
         }  catch  (Exception e) { 
             e.printStackTrace(); 
         } 
          return  map; 
     }
	

	/**
	 * @param fileName
	 * function:ɾ���ļ�
	 */
	public void deleteFile(String fileName) {
		try {
			ftpClient.deleteFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ������
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		FtpUtils ftp = new FtpUtils();
		
	}

}
