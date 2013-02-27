package com.joymeng.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.game.common.GameConst;

/**
 * excel类
 * 
 * @author xufangliang
 * 
 */
public class ExcelUtils {
	private static Logger logger = Logger.getLogger(ExcelUtils.class.getName());

	public static List<String> fileLst = new ArrayList<String>();

	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<String[]> excelInList(String filePath)
			throws Exception {
		logger.info(filePath + "文件读取开始>>>>>>>>>>>>>>>>>>>>>");
		File f = new File(filePath);
		ArrayList<String[]> excelList = excelInList(f);
		logger.info(filePath + "文件读取成功>>>>>>>>>>>>>>>>>>>>>");
		return excelList;
	}

	/**
	 * 读取excel文件
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<String[]> excelInList(File file) throws Exception {
		InputStream stream = new FileInputStream(file);
		Workbook book = new XSSFWorkbook(stream);
		Sheet sheet = book.getSheetAt(0);
		int totalRows = sheet.getPhysicalNumberOfRows();
		if (totalRows == 0) {
			return null;
		}
		int totalCells = sheet.getRow(0).getPhysicalNumberOfCells();

		ArrayList<String[]> list = new ArrayList<String[]>();
		for (int i = 0; i < totalRows; i++) {
			String[] objs = new String[totalCells];
			for (int j = 0; j < totalCells; j++) {
				if (totalRows >= 1 && sheet.getRow(0) != null) {
					try {
						String sh = sheet.getRow(i).getCell(j) == null ? ""
								: sheet.getRow(i).getCell(j).toString();
						if (sh.endsWith(".0")) {
							objs[j] = sh.substring(0, sh.length() - 2);
						} else {
							objs[j] = sh;
						}
					} catch (Exception e) {
						System.out.println("row=" + i + " cell=" + j);
						e.printStackTrace();
					}

				}
			}
			list.add(objs);
		}
		return list;
	}

	/**
	 * 获取英文名标签数组
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String[] fromExcelList(ArrayList<String[]> list) {
		String[] dataNotes = null;
		// 第一行为表头注释,第二行为英文字段说明
		if (list != null && list.size() > 1) {
			dataNotes = list.get(1);
		}
		return dataNotes;
	}

	/**
	 * excel信息生成字符串
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String[] toXmlString(String filePath) throws Exception {
		// 读取Excel第二行 对应英文标签
		ArrayList<String[]> list = excelInList(filePath);
		String[] dataNotes = fromExcelList(list);
		String[] data = null;
		if (list != null && list.size() > 2 && dataNotes != null
				&& dataNotes.length > 0) {
			data = new String[list.size() - 2];
			// 实际数据从第二行开始
			for (int i = 2; i < list.size(); i++) {
				StringBuffer sb = new StringBuffer();
				String[] datas = list.get(i);
				for (int j = 0; j < datas.length; j++) {
					sb.append(dataNotes[j]).append("=\"").append(datas[j])
							.append("\" ");
				}
				data[i - 2] = sb.toString();
			}
		}
		return data;
	}

	/**
	 * 生成注释
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static String toXmlcomment(ArrayList<String[]> list)
			throws Exception {
		String string = "";
		String[] dataNotes = fromExcelList(list);
		if (list != null && list.size() > 2 && dataNotes != null
				&& dataNotes.length > 0) {
			for (int i = 0; i < dataNotes.length; i++) {
				string += "  |" + dataNotes[i] + ":" + list.get(0)[i];
			}
		}
		return string;
	}

	/**
	 * 
	 * @param excelFilePath
	 *            excel文件地址
	 * @throws Exception
	 */
	public static void toInXml(String excelFilePath) throws Exception {
		logger.info("开始" + excelFilePath + "生成XML<<<<<<<<<<<<<<<");
		// 读取Excel第二行 对应英文标签
		ArrayList<String[]> list = excelInList(excelFilePath);
		String[] dataNotes = fromExcelList(list);
		if (list != null && list.size() > 2 && dataNotes != null
				&& dataNotes.length > 0) {
			String name = list.get(0)[0];
			Document doc = XmlUtils.blankDocument(name + "s");
			// 注释String
			String sb = toXmlcomment(list);
			XmlUtils.createComment(doc, sb);
			// 实际数据从第二行开始
			for (int i = 2; i < list.size(); i++) {

				Element e = XmlUtils.createChild(doc, doc.getDocumentElement(),
						name);
				String[] datas = list.get(i);
				for (int j = 0; j < datas.length; j++) {
					e.setAttribute(dataNotes[j], datas[j]);
				}

			}
			// xml存储地址 默认为./resource2/下
			String str = GameConst.RES_PATH_CH;
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				str = GameConst.RES_PATH_EN;
			}
			XmlUtils.save(GameConst.RES_PATH_CH + name + ".xml", doc);
		}
		logger.info(excelFilePath + "生成XML成功<<<<<<<<<<<<<<<");
	}

	/**
	 * 读取文件下所有文件
	 * 
	 * @param root
	 * @param fileType
	 * @return
	 */
	public static void recursion(String root, String fileType) {

		File file = new File(root);
		File[] subFile = file.listFiles();
		for (int i = 0; i < subFile.length; i++) {
			if (subFile[i].isDirectory()) {
				recursion(subFile[i].getAbsolutePath(), fileType);
			} else {
				String filename = subFile[i].getName();
				if (filename.endsWith(fileType)) {
					fileLst.add(root + filename);
				}
			}
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// fileLst.clear();
		// recursion("D:/java doc/策划/於云/数据/",".xlsx");
		// for(int i = 0 ;i<fileLst.size();i++){
		// toInXml(fileLst.get(i));
		// }
		// System.out.println(fileLst.size());
		toInXml("E:/hero/doc/数据表/怪物部队.xlsx");
		// toInXml("E:/hero/doc/数据表/酒馆.xlsx");
		// toInXml("E:/hero/doc/数据表/角色升级经验.xlsx");
		// toInXml("E:/hero/doc/数据表/训练台经验.xlsx");
		// toInXml("E:/hero/doc/数据表/城主数据.xlsx");
		// toInXml("E:/hero/doc/数据表/box.xlsx");
		// toInXml("E:/hero/doc/数据表/怪物monster.xlsx");
		// toInXml("E:/hero Doc/DOC/数据表/BuildingUpGrade.xlsx");

		// toInXml("E:/hero/doc/数据表/战斗奖励.xlsx");
		// toInXml("E:/hero/doc/数据表/关卡信息表.xlsx");
		// toInXml("E:/hero/doc/数据表/武将.xlsx");

		// toInXml("E:/hero/doc/数据表/将领训练经验表.xlsx");
		// toInXml("E:/hero/doc/数据表/武将品级.xlsx");
		// toInXml("E:/hero/doc/策划/於云/数据/武将.xlsx");
		// toInXml("E:/hero/doc/策划/於云/数据/武将品级.xlsx");
		// toInXml("E:/hero/doc/策划/於云/数据/姓名.xlsx");
		// toInXml("D:/document/策划/於云/数据/中奖几率data.xlsx");
		// toInXml("D:/document/策划/李欢欢/城主姓名new.xlsx");
	}

}
