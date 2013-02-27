package com.joymeng.core.spring.oxm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * �����ļ���ز����Ĺ����࣬������log4j����Ҫlog4j���֧�֡�
 */
public final class FileUtils {
	private static Logger log4j = Logger.getLogger(FileUtils.class.getName());

	private FileUtils() {
		
	}

	/**
	 * �õ��ļ��������������޷���λ�ļ�����null��
	 * 
	 * @param relativePath
	 *            �ļ���Ե�ǰӦ�ó�������������·����
	 * @return �ļ�����������
	 */
	public static InputStream getResourceStream(String relativePath) {
		return Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(relativePath);
	}

	/**
	 * �ر���������
	 * @param is
	 * ��������������null��
	 */
	public static void closeInputStream(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
	}

	public static void closeFileOutputStream(FileOutputStream fos) {
		if (fos != null) {
			try {
				fos.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * ���ļ�·������ȡĿ¼·��������ļ�·������Ŀ¼����null��
	 * 
	 * @param filePath
	 *            �ļ�·����
	 * @return Ŀ¼·��������'/'�����ϵͳ���ļ��ָ����β��
	 */
	public static String extractDirPath(String filePath) {
		int separatePos = Math.max(filePath.lastIndexOf('/'), filePath
				.lastIndexOf('\\')); // �ָ�Ŀ¼���ļ����λ��
		return separatePos == -1 ? null : filePath.substring(0, separatePos);
	}
	
	/**
	 * ���ļ�·������ȡ�ļ���, ����ָ����null
	 * @param filePath
	 * @return �ļ���, ����ָ����null
	 */
	public static String extractFileName(String filePath) {
		int separatePos = Math.max(filePath.lastIndexOf('/'), filePath
				.lastIndexOf('\\')); // �ָ�Ŀ¼���ļ����λ��
		return separatePos == -1 ? null : filePath.substring(separatePos + 1, filePath.length());
	}

	/**
	 * ��·�������ļ�����������ͬ·�����ļ��򲻽�����
	 * 
	 * @param filePath
	 *            Ҫ�����ļ���·����
	 * @return ��ʾ���ļ���File����
	 * @throws IOException
	 *             ��·����Ŀ¼���ļ�ʱ�������쳣��
	 */
	public static File makeFile(String filePath) throws IOException {
		File file = new File(filePath);
		if (file.isFile())
			return file;
		if (filePath.endsWith("/") || filePath.endsWith("\\"))
			throw new IOException(filePath + " is a directory");

		String dirPath = extractDirPath(filePath); // �ļ�����Ŀ¼��·��

		if (dirPath != null) { // ���ļ�����Ŀ¼���������Ƚ�Ŀ¼
			makeFolder(dirPath);
		}

		file.createNewFile();
		log4j.info("�ļ��Ѵ���: " + filePath);
		return file;
	}

	/**
	 * �½�Ŀ¼,֧�ֽ����༶Ŀ¼
	 * 
	 * @param folderPath
	 *            �½�Ŀ¼��·���ַ�
	 * @return boolean,���Ŀ¼�����ɹ�����true,���򷵻�false
	 */
	public static boolean makeFolder(String folderPath) {
		try {
			File myFilePath = new File(folderPath);
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
				System.out.println("�½�Ŀ¼Ϊ��" + folderPath);
				log4j.info("�½�Ŀ¼Ϊ��" + folderPath);
			} else {
				System.out.println("Ŀ¼�Ѿ�����: " + folderPath);
				log4j.info("�½�Ŀ¼Ϊ��" + folderPath);
			}
		} catch (Exception e) {
			System.out.println("�½�Ŀ¼��������");
			e.printStackTrace();
			log4j.error("�½�Ŀ¼����: " + folderPath);
			return false;
		}
		return true;
	}

	/**
	 * ɾ���ļ�
	 * 
	 * @param filePathAndName
	 *            Ҫɾ���ļ���·��
	 * @return boolean ɾ��ɹ�����true,ɾ��ʧ�ܷ���false
	 */
	public static boolean deleteFile(String filePathAndName) {
		try {
			File myDelFile = new File(filePathAndName);
			if (myDelFile.exists()) {
				myDelFile.delete();
				log4j.info("�ļ���" + filePathAndName + " ��ɾ��!!!");
			}
		} catch (Exception e) {
			System.out.println("ɾ���ļ���������");
			e.printStackTrace();
			log4j.error("�ļ���" + filePathAndName + " ɾ�����!!!");
			return false;
		}
		return true;
	}

	/**
	 * �ݹ�ɾ��ָ��Ŀ¼�������ļ������ļ���
	 * 
	 * @param path
	 *            ĳһĿ¼��·��,��"c:\cs"
	 * @param ifDeleteFolder
	 *            booleanֵ,���true,��ɾ��Ŀ¼�������ļ����ļ���;���false,��ֻɾ��Ŀ¼�������ļ�,���ļ��н�����
	 */
	public static void deleteAllFile(String path, boolean ifDeleteFolder) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		String temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith("\\") || path.endsWith("/"))
				temp = path + tempList[i];
			else
				temp = path + File.separator + tempList[i];
			if ((new File(temp)).isFile()) {
				deleteFile(temp);
			} else if ((new File(temp)).isDirectory() && ifDeleteFolder) {
				deleteAllFile(path + File.separator + tempList[i],
						ifDeleteFolder);// ��ɾ���ļ���������ļ�
				deleteFolder(path + File.separator + tempList[i]);// ��ɾ����ļ���
			}
		}
	}

	/**
	 * ɾ���ļ���,����������ļ�
	 * 
	 * @param folderPath
	 *            �ļ���·���ַ�
	 */
	public static void deleteFolder(String folderPath) {
		try {
			File myFilePath = new File(folderPath);
			if (myFilePath.exists()) {
				deleteAllFile(folderPath, true); // ɾ����������������
				myFilePath.delete(); // ɾ����ļ���
			}
			log4j.info("ok!ɾ���ļ��гɹ�: " + folderPath);
		} catch (Exception e) {
			System.out.println("ɾ���ļ��в�������");
			e.printStackTrace();
			log4j.error("ɾ���ļ���ʧ��: " + folderPath);
		}
	}
	
	public static void copyFile(File file,String targetPath){
		if(file != null){
			copyFile(file.getAbsolutePath(), targetPath);
		}
	}

	/**
	 * �����ļ�,���Ŀ���ļ���·��������,���Զ��½�·��
	 * 
	 * @param sourcePath
	 *            Դ�ļ�·��, e.g. "c:/cs.txt"
	 * @param targetPath
	 *            Ŀ���ļ�·�� e.g. "f:/bb/cs.txt"
	 */
	public static void copyFile(String sourcePath, String targetPath) {
		InputStream inStream = null;
		FileOutputStream fos = null;
		try {
			int byteSum = 0;
			int byteRead = 0;
			File sourcefile = new File(sourcePath);
			if (sourcefile.exists()) { // �ļ�����ʱ
				inStream = new FileInputStream(sourcePath); // ����ԭ�ļ�
				String dirPath = extractDirPath(targetPath); // �ļ�����Ŀ¼��·��
				if (dirPath != null) { // ���ļ�����Ŀ¼���������Ƚ�Ŀ¼
					makeFolder(dirPath);
				}
				fos = new FileOutputStream(targetPath);
				byte[] buffer = new byte[1444];
				while ((byteRead = inStream.read(buffer)) != -1) {
					byteSum += byteRead; // �ֽ��� �ļ���С
					fos.write(buffer, 0, byteRead);
				}
				System.out.println("�ļ���С: " + byteSum);

				log4j.info("Ҫ���Ƶ��ļ�·��-->" + sourcePath);
				log4j.info("Ŀ���ļ�·��-->" + targetPath);
				log4j.info("�ļ���С-->" + byteSum);

			}
		} catch (Exception e) {
			System.out.println("���Ƶ����ļ���������");
			e.printStackTrace();
			log4j.debug("�����ļ�����: " + sourcePath);
		} finally {
			closeInputStream(inStream);
			closeFileOutputStream(fos);
		}
	}

	/**
	 * ��·�����ļ���ƴ������
	 * 
	 * @param folderPath
	 *            ĳһ�ļ���·���ַ�e.g. "c:\cs\" �� "c:\cs"
	 * @param fileName
	 *            ĳһ�ļ����ַ�, e.g. "cs.txt"
	 * @return �ļ�ȫ·�����ַ�
	 */
	public static String makeFilePath(String folderPath, String fileName) {
		return folderPath.endsWith("\\") || folderPath.endsWith("/") ? folderPath
				+ fileName
				: folderPath + File.separatorChar + fileName;
	}

	/**
	 * ��ĳһ�ļ����µ������ļ������ļ��п�����Ŀ���ļ��У���Ŀ���ļ��в����ڽ��Զ�����
	 * 
	 * @param sourcePath
	 *            Դ�ļ����ַ�e.g. "c:\cs"
	 * @param targetPath
	 *            Ŀ���ļ����ַ�e.g. "d:\tt\qq"
	 */
	public static void copyFolder(String sourcePath, String targetPath) {
		FileInputStream input = null;
		FileOutputStream output = null;
		try {
			makeFolder(targetPath); // ����ļ��в����� �������ļ���
			String[] file = new File(sourcePath).list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				String tempPath = makeFilePath(sourcePath, file[i]);
				temp = new File(tempPath);
				String target = null;
				if (temp.isFile()) {
					input = new FileInputStream(temp);
					output = new FileOutputStream(target = makeFilePath(
							targetPath, file[i]));
					byte[] b = new byte[1024 * 5];
					int len = 0;
					int sum = 0;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
						sum += len;
					}
					output.flush();
					closeInputStream(input);
					closeFileOutputStream(output);

					log4j.info("Ҫ���Ƶ��ļ�·��-->" + tempPath);
					log4j.info("Ŀ���ļ�·��-->" + target);
					log4j.info("�ļ���С-->" + sum);

				} else if (temp.isDirectory()) {// ��������ļ���
					copyFolder(sourcePath + '/' + file[i], targetPath + '/'
							+ file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("��������ļ������ݲ�������");
			e.printStackTrace();
		} finally {
			closeInputStream(input);
			closeFileOutputStream(output);
		}
	}

	/**
	 * �ƶ��ļ�
	 * 
	 * @param oldFilePath
	 *            ���ļ�·���ַ�, e.g. "c:\tt\cs.txt"
	 * @param newFilePath
	 *            ���ļ�·���ַ�, e.g. "d:\kk\cs.txt"
	 */
	public static void moveFile(String oldFilePath, String newFilePath) {
		copyFile(oldFilePath, newFilePath);
		deleteFile(oldFilePath);
	}

	/**
	 * �ƶ��ļ���
	 * 
	 * @param oldFolderPath
	 *            ���ļ���·���ַ�e.g. "c:\cs"
	 * @param newFolderPath
	 *            ���ļ���·���ַ�e.g. "d:\cs"
	 */
	public static void moveFolder(String oldFolderPath, String newFolderPath) {
		copyFolder(oldFolderPath, newFolderPath);
		deleteFolder(oldFolderPath);

	}

	/**
	 * ���ĳһ�ļ����µ������ļ���·������
	 * 
	 * @param filePath
	 *            �ļ���·��
	 * @return ArrayList�����е�ÿ��Ԫ����һ���ļ���·�����ַ�
	 */
	public static ArrayList getFilePathFromFolder(String filePath) {
		ArrayList fileNames = new ArrayList();
		File file = new File(filePath);
		try{
		File[] tempFile = file.listFiles();
		for (int i = 0; i < tempFile.length; i++) {
			if (tempFile[i].isFile()) {
				String tempFileName = tempFile[i].getName();
				fileNames.add(makeFilePath(filePath, tempFileName));
			}
		}
		}catch(Exception e){
			fileNames.add("�����ļ����");
		}
		return fileNames;
	}

	/**
	 * ���ĳһ�ļ����µ�����TXT��txt�ļ���ļ���
	 * 
	 * @param filePath
	 *            �ļ���·��
	 * @return ArrayList�����е�ÿ��Ԫ����һ���ļ�����ַ�
	 */
	public static ArrayList getFileNameFromFolder(String filePath) {
		ArrayList fileNames = new ArrayList();
		File file = new File(filePath);
		File[] tempFile = file.listFiles();
		for (int i = 0; i < tempFile.length; i++) {
			if (tempFile[i].isFile())
				fileNames.add(tempFile[i].getName());
		}
		return fileNames;
	}
	
	/**
	 * ���ĳһ·���������ļ�����Ƶļ���
	 * @param filePath
	 * @return
	 */
	public static ArrayList getFolderNameFromFolder(String filePath) {
		ArrayList fileNames = new ArrayList();
		File file = new File(filePath);
		File[] tempFile = file.listFiles();
		for (int i = 0; i < tempFile.length; i++) {
			if (tempFile[i].isDirectory())
				fileNames.add(tempFile[i].getName());
		}
		return fileNames;		
	}

	/**
	 * ���ĳһ�ļ����µ������ļ�������
	 * 
	 * @param filePath
	 *            �ļ���·��
	 * @return int �ļ�����
	 */
	public static int getFileCount(String filePath) {
		int count = 0;
		try{
		File file = new File(filePath);
		if(!isFolderExist(filePath)) return count;
		File[] tempFile = file.listFiles();
		for (int i = 0; i < tempFile.length; i++) {
			if (tempFile[i].isFile())
				count++;
		}
		}catch(Exception fe){
			count =0;
		}
		return count;
	}
	
	/**
	 * ���ĳһ·����Ҫ��ƥ����ļ��ĸ���
	 * @param filePath �ļ���·��
	 * @param matchs ��Ҫƥ����ļ����ַ�,��".*a.*",�����ַ�����ƥ�乤��
	 * 				  ֱ�ӷ���·���µ��ļ�����
	 * @return int ƥ���ļ�����ļ�����
	 */
	public static int getFileCount(String filePath, String matchs) {
		int count = 0;
		if(!isFolderExist(filePath)) return count;
		if(matchs.equals("") || matchs == null) return getFileCount(filePath);
		File file = new File(filePath);
		log4j.info("filePath in getFileCount: " + filePath);
		log4j.info("matchs in getFileCount: " + matchs);
		File[] tempFile = file.listFiles();
		for (int i = 0; i < tempFile.length; i++) {
			if (tempFile[i].isFile())
				if(Pattern.matches(matchs, tempFile[i].getName()))
					count++;
		}
		return count;		
	}

	public static int getStrCountFromFile(String filePath, String str) {
		if(!isFileExist(filePath)) return 0;
		FileReader fr = null;
		BufferedReader br = null;
		int count = 0;
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			String line = null;
			while((line = br.readLine()) != null) {
				if(line.indexOf(str) != -1) count++;
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null) br.close();
				if(fr != null) fr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	/**
	 * ���ĳһ�ļ�������
	 * @param filePath �ļ���·��
	 * 
	 * @return int ����
	 */
	public static int getFileLineCount(String filePath){
		if(!isFileExist(filePath)) 
			return 0;
		FileReader fr = null;
		BufferedReader br = null;
		int count = 0;
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			String line = null;
			while((line = br.readLine()) != null) {				
				count++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null) br.close();
				if(fr != null) fr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//System.out.println("count="+count);
		return count;
	}
	
	/**
	 * �ж�ĳһ�ļ��Ƿ�Ϊ��
	 * 
	 * @param filePath
	 *            �ļ���·���ַ�e.g. "c:\cs.txt"
	 * @return ����ļ�Ϊ�շ���true, ���򷵻�false
	 * @throws IOException
	 */
	public static boolean ifFileIsNull(String filePath) throws IOException {
		boolean result = false;
		FileReader fr = new FileReader(filePath);
		if (fr.read() == -1) {
			result = true;
			System.out.println(filePath + " �ļ�Ϊ��!");
			log4j.info(filePath + " �ļ�Ϊ��!");
		} else {
			System.out.println(filePath + " �ļ���Ϊ��!");
			log4j.info(filePath + " �ļ���Ϊ��!");
		}
		fr.close();
		return result;
	}

	/**
	 * �ж��ļ��Ƿ����
	 * 
	 * @param fileName
	 *            �ļ�·���ַ�e.g. "c:\cs.txt"
	 * @return ���ļ����ڷ���true,���򷵻�false
	 */
	public static boolean isFileExist(String fileName) {
		// �ж��ļ����Ƿ�Ϊ��
		if (fileName == null || fileName.length() == 0) {
			log4j.error("�ļ����Ϊ0");
			return false;
		} else {
			// �����ļ� �ж��ļ��Ƿ����
			File file = new File(fileName);
			if (!file.exists() || file.isDirectory()) {
				log4j.error(fileName + "������");
				return false;
			}
		}
		return true;
	}

	/**
	 * �ж��ļ����Ƿ����
	 * 
	 * @param folderPath���ļ���·���ַ�e.g.
	 *            "c:\cs"
	 * @return ���ļ��д��ڷ���true, ���򷵻�false
	 */
	public static boolean isFolderExist(String folderPath) {
		File file = new File(folderPath);
		return file.isDirectory() ? true : false;
	}

	/**
	 * ����ļ��Ĵ�С
	 * 
	 * @param filePath
	 *            �ļ�·���ַ�e.g. "c:\cs.txt"
	 * @return �����ļ��Ĵ�С,��λkb,����ļ������ڷ���null
	 */
	public static Double getFileSize(String filePath) {
		if (!isFileExist(filePath))
			return null;
		else{
		File file = new File(filePath);
		double intNum = Math.ceil(file.length() / 1024.0);
		return new Double(intNum);
		}
		
	}
	
	/**
	 * ����ļ��Ĵ�С,�ֽڱ�ʾ
	 * 
	 * @param filePath
	 *            �ļ�·���ַ�e.g. "c:\cs.txt"
	 * @return �����ļ��Ĵ�С,��λkb,����ļ������ڷ���null
	 */
	public static Double getFileByteSize(String filePath) {
		if (!isFileExist(filePath))
			return null;
		else{
		File file = new File(filePath);
		double intNum = Math.ceil(file.length());
		return new Double(intNum);
		}
		
	}
	
	/**
	 * �������Ƽ��ļ��Ĵ�С(�ֽ�)
	 * 
	 * @param filePath
	 *            �ļ�·���ַ�e.g. "c:\cs.txt"
	 * @return �����ļ��Ĵ�С,��λkb,����ļ������ڷ���null
	 */
	public static Double getWhpjFileSize(String filePath) {
		if (!isFileExist(filePath))
			return null;
		else{
		File file = new File(filePath);
		return new Double(file.length());
		}
		
	}

	/**
	 * ����ļ�������޸�ʱ��
	 * @param filePath �ļ�·���ַ�e.g. "c:\cs.txt"
	 * @return �����ļ������޸����ڵ��ַ�,����ļ������ڷ���null
	 */
	public static String fileModifyTime(String filePath) {
		if (!isFileExist(filePath)) return null;
		else{
		File file = new File(filePath);

		long timeStamp = file.lastModified();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String tsForm = formatter.format(new Date(timeStamp));
        return tsForm;
		}
	}
	
	/**
	 * ����ĳһ�ļ����µ������ļ�,����һ��ArrayList,ÿ��Ԫ������һ����ArrayList,
	 * ��ArrayList������ֶ�,�������ļ���ȫ·��(String),�ļ����޸�����(String),
	 * �ļ��Ĵ�С(Double)
	 * @param folderPath, ĳһ�ļ��е�·��
	 * @return ArrayList
	 */
	public static ArrayList getFilesSizeModifyTime(String folderPath){
		List returnList = new ArrayList();
		List filePathList = getFilePathFromFolder(folderPath);
		for(int i=0;i<filePathList.size();i++){
			List tempList = new ArrayList();
			String filePath = (String)filePathList.get(i);
			String modifyTime = FileUtils.fileModifyTime(filePath);
			Double fileSize = FileUtils.getFileSize(filePath);
			tempList.add(filePath);
			tempList.add(modifyTime);
			tempList.add(fileSize);
			returnList.add(tempList);
		}
		return (ArrayList)returnList;
	}
	
	/**
	 * ���ĳһ�ļ����µ�����TXT��txt�ļ���ļ���
	 * 
	 * @param filePath
	 *            �ļ���·��
	 * @return ArrayList�����е�ÿ��Ԫ����һ���ļ�����ַ�
	 */
	public static ArrayList getTxtFileNameFromFolder(String filePath) {
		ArrayList fileNames = new ArrayList();
		File file = new File(filePath);
		File[] tempFile = file.listFiles();
		for (int i = 0; i < tempFile.length; i++) {
			if (tempFile[i].isFile())
				if(tempFile[i].getName().indexOf("TXT") !=-1||tempFile[i].getName().indexOf("txt")!=-1){
				fileNames.add(tempFile[i].getName());
				}
		}
		return fileNames;
	}
	
	/**
	 * ���ĳһ�ļ����µ�����xml��XML�ļ���ļ���
	 * 
	 * @param filePath
	 *            �ļ���·��
	 * @return ArrayList�����е�ÿ��Ԫ����һ���ļ�����ַ�
	 */
	public static ArrayList getXmlFileNameFromFolder(String filePath) {
		ArrayList fileNames = new ArrayList();
		File file = new File(filePath);
		File[] tempFile = file.listFiles();
		for (int i = 0; i < tempFile.length; i++) {
			if (tempFile[i].isFile())
				if(tempFile[i].getName().indexOf("XML") !=-1||tempFile[i].getName().indexOf("xml")!=-1){
				fileNames.add(tempFile[i].getName());
				}
		}
		return fileNames;
	}
	
//	public void copyFiles


}
