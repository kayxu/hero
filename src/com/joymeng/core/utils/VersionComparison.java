package com.joymeng.core.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.FileConst;

public class VersionComparison {
	private static Logger logger = LoggerFactory.getLogger(VersionComparison.class);
	/**
	 * 获取历史版本文件
	 */
	public static Map<String,File> newVersion(){
		Map<String,File> historyVersionMap = FileUtil.allDirectoryInPath(FileConst.VERSION_HISTORY_FOLDER);
		int newVeision = historyVersionMap.size()+1;
		return historyVersionMap;
	}
	
	public static String assembleAddress(File file,String name){
		if (file.getPath().endsWith(File.separator)) {
			return file.getPath()+name;
		} else {
			return file.getPath() + File.separator +name;
		}
	}
	
	/**
	 * 比较生成全新的增量文件
	 * @param oldFileName 全文件地址
	 * @param allFileName 新文件地址
	 * @throws IOException 
	 */
	public static void comparisonFile(String oldFileName) throws IOException{
		//---生成禅意文件集合
		logger.info("增量文件>>>老文件夹："+oldFileName+"|全文件夹："+FileConst.ALL_EDITION_FILE_PATH+"|比较>>>开始");
		Map<String,File> oldfileMap = new HashMap<>(); 
		Map<String,File> newfileMap = new HashMap<>();
		FileUtil.allFileInDirectory(oldFileName, oldfileMap, "", true, false,oldFileName);
		FileUtil.allFileInDirectory(FileConst.ALL_EDITION_FILE_PATH, newfileMap, "", true, false,FileConst.ALL_EDITION_FILE_PATH);
		Map<String,File> addfileMap = new HashMap<>();
		for(String pathName:newfileMap.keySet()){
			if(oldfileMap.containsKey(pathName)){
				//有这个文件
				File newFile =  newfileMap.get(pathName);
				File oldFile =  oldfileMap.get(pathName);
				if(newFile != null && oldFile != null && !(MD5FileUtil.getFileMD5String(newFile).equals(MD5FileUtil.getFileMD5String(oldFile)))){
					addfileMap.put(pathName, newfileMap.get(pathName));
				}
			}else{
				addfileMap.put(pathName, newfileMap.get(pathName));
			}
		}
		logger.info("增量文件>>>老文件夹："+oldFileName+"|全文件夹："+FileConst.ALL_EDITION_FILE_PATH+"|差异文件："+addfileMap.keySet());
		logger.info("增量文件>>>老文件夹："+oldFileName+"|全文件夹："+FileConst.ALL_EDITION_FILE_PATH+"|比较>>>结束");
		//---先清空差异文件夹
		FileUtil.delAllFile(FileConst.DIFFERENCES_VERSIONS_FILE_PATH);
		logger.info("增量文件>>>老文件夹："+oldFileName+"|全文件夹："+FileConst.ALL_EDITION_FILE_PATH+"|清空临时文件夹");
		//---差异文件生成到临时文件夹
		logger.info("增量文件>>>老文件夹："+oldFileName+"|全文件夹："+FileConst.ALL_EDITION_FILE_PATH+"|差异文件生成到临时文件夹>>>开始");
		for(String fileName:addfileMap.keySet()){
			FileUtil.copyFiles(addfileMap.get(fileName), new File(FileConst.DIFFERENCES_VERSIONS_FILE_PATH),fileName);
		}
		logger.info("增量文件>>>老文件夹："+oldFileName+"|全文件夹："+FileConst.ALL_EDITION_FILE_PATH+"|差异文件生成到临时文件夹>>>结束始");
	}
	/**
	 * 生成zip文件
	 */
	public static void zipFile(String filePath,String zipName){
		try {
			logger.info("ZIP文件生成>>>文件夹地址："+filePath+"|zip文件："+zipName+">>>开始");
			ZipUtils.zipFolder(FileConst.DIFFERENCES_VERSIONS_FILE_PATH, assembleAddress(new File(filePath), zipName));
			logger.info("ZIP文件生成>>>文件夹地址："+filePath+"|zip文件："+zipName+">>>完成");
		} catch (Exception e) {
			logger.info("ZIP文件生成>>>文件夹地址："+filePath+"|zip文件："+zipName+">>>失败");
			e.printStackTrace();
		}
	}
	
	public static void diffVersionZip() throws Exception{
		Map<String,File> historyVersionMap = newVersion();
		int newVersion = historyVersionMap.size();
		logger.info("新版本号："+newVersion);
		for(File file:historyVersionMap.values()){
			if(file != null){
				String old = file.getPath().substring(file.getPath().lastIndexOf(File.separator)+1,file.getPath().length());
				String newVersionName= old+"-"+newVersion+".zip";//zip
				comparisonFile(file.getPath());//比较差异文件
				zipFile(assembleAddress(new File(FileConst.ZIP_FILE_PATH),String.valueOf(newVersion)), newVersionName);
			}
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		VersionComparison.diffVersionZip();
	}

}
