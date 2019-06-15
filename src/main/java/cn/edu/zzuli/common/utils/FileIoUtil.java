package cn.edu.zzuli.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import org.apache.commons.codec.binary.Base64;


public class FileIoUtil {
	
	public static String fileUrl = "/upload/";

	/**
	 * 将文件转成base64 字符串
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(File file) throws Exception {
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
		String base64Str =Base64.encodeBase64String(buffer).replaceAll("\r\n", "").replaceAll("\n", "");
		return base64Str;
	}

	/**
	 * 将输入流转成base64 字符串
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(InputStream input) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = 0;
		byte[] b = new byte[1024];
		while ((len = input.read(b, 0, b.length)) != -1) {
			baos.write(b, 0, len);
		}
		byte[] buffer = baos.toByteArray();
		input.read(buffer);
		input.close();
		String base64Str =Base64.encodeBase64String(buffer).replaceAll("\r\n", "").replaceAll("\n", "");
		return base64Str;
	}

	/**
	 * 将base64字符解码保存文件
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */
	public static void decoderBase64File(String base64Code, String targetPath) throws Exception {
		byte[] buffer = Base64.decodeBase64(base64Code);
		FileOutputStream out = new FileOutputStream(targetPath);
		out.write(buffer);
		out.close();
	}

	/**
	 * 将对象写入文件
	 * 
	 * @param filepath
	 *            文件路径，例：D:\\work\\filetest
	 * @param filename
	 *            文件名称，例：test.txt
	 * @param obj
	 *            要写入的内容
	 * @throws IOException
	 */
	public static void objectToFile(String filepath, String filename, Object obj) throws IOException {
		File directory = new File(filepath);
		if (directory != null && !directory.exists()) {
			directory.mkdirs();
		}
		String path = filepath+ filename;
		File file = new File(path);
		if (!file.exists()) { // 判断文件是否存在
			try {
				file.createNewFile(); // 创建文件
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileWriter fileWritter = new FileWriter(file, true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write(obj.toString());
		bufferWritter.close();
	}

	/**
	 * 以行为单位读取文件
	 * @param fileName
	 * @return 文件内容
	 */
	public static String readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				sb.append(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return sb.toString();
	}
	
	public static void saveBit(InputStream inStream, String filepath, String filename)
			throws IOException {
		
		
		File directory = new File(filepath);
		
		if (directory != null && !directory.exists()) {
			directory.mkdirs();
		}
		
		String path = filepath + filename;

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();
		// 把outStream里的数据写入内存

		// 得到图片的二进制数据，以二进制封装得到数据，具有通用性
		byte[] data = outStream.toByteArray();
		// new一个文件对象用来保存图片，默认保存当前工程根目录
		File imageFile = new File(path);

		if (imageFile.exists()) {

			imageFile.delete();
		}

		imageFile.createNewFile();
		// 创建输出流
		FileOutputStream fileOutStream = new FileOutputStream(imageFile);
		// 写入数据
		fileOutStream.write(data);
		fileOutStream.close();

	}
	
	
	public static void deltefile(String filepath) {

		File file = new File(filepath);

		if (file.exists()) {

			file.delete();

		}
	}

	/**
	 * 上传文件到服务器  httpclient 3
	 * 
	 * @param InputStream 需要上传的文件流
	 * @param RequestURL 请求的rul
	 * @return 返回响应的内容
	 */
	public static int uploadFile(final InputStream fin, final String RequestURL) {
		int ret = 0;
		try {
			HttpClient client = null;
			String tString = RequestURL.replace("http://", "");
			String tempString = (tString).substring(0, tString.indexOf("/"));
			String ipString = tempString.substring(0, tempString.indexOf(":"));
			int port = Integer.parseInt(tempString.substring(tempString.indexOf(":") + 1));
			HostConfiguration hostConfig = new HostConfiguration();
			hostConfig.setHost(ipString, port);
			HttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
			HttpConnectionManagerParams params = new HttpConnectionManagerParams();
			params.setMaxConnectionsPerHost(hostConfig, 5);
			// 设置连接超时时间(单位毫秒)
			params.setConnectionTimeout(600000);
			// 设置读数据超时时间(单位毫秒)
			params.setSoTimeout(600000);
			// org.apache.commons.httpclient
			connectionManager.setParams(params);
			client = new HttpClient(connectionManager);

			Credentials creds = new UsernamePasswordCredentials("admin", "admin1881");
			client.getState().setCredentials(AuthScope.ANY, creds);
			client.setHostConfiguration(hostConfig);
			client.getParams().setParameter("CONNECTION_TIMEOUT", 600000); // 连接时间600s
			client.getParams().setParameter("SO_TIMEOUT", 600000); // 数据传输时间600s

			PutMethod upload = new PutMethod(RequestURL);

			RequestEntity entity = new InputStreamRequestEntity(fin);
			upload.setRequestHeader("Content-Type", "image/jpeg");

			upload.setRequestEntity(entity);

			ret = client.executeMethod(upload);

			System.out.println("这个是返回来的数据" + ret);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
}
