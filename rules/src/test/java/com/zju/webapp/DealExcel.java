package com.zju.webapp;

import java.io.*;
import java.util.*;

import org.apache.cxf.common.util.StringUtils;

import jxl.*;
import jxl.read.biff.BiffException;

public class DealExcel {

	public static void main(String[] args) {
		double A = 1.3;
		double B = 1.31;
		
		if (A < B) {
			System.out.println("true");
		}
		try {
			double d = Double.parseDouble("");
			System.out.println(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void ___main(String[] args) {

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new FileWriter("D:/map.txt"));
			List<String[]> list = readExcel("D:/1.xls");
			for (String[] s : list) {
				if (!StringUtils.isEmpty(s[0]) && !StringUtils.isEmpty(s[1])) {
					if (s[0].length() != 4) {
						int length = 4 - s[0].length();
						for (int i = 0; i < length; i++) {
							s[0] = "0" + s[0];
							System.out.println("~~~");
						}
					}
					if (s[1].length() != 4) {
						int length = 4 - s[1].length();
						for (int i = 0; i < length; i++) {
							s[1] = "0" + s[1];
							System.out.println("~~~");
						}
					}
					output.write(s[0] + ":" + s[1] + "\n");
				}
			}
			output.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String[]> readExcel(String excelFileName) throws BiffException, IOException {

		List<String[]> list = new ArrayList<String[]>();
		Workbook rwb = null;
		Cell cell = null;
		InputStream stream = new FileInputStream(excelFileName);
		rwb = Workbook.getWorkbook(stream);
		Sheet sheet = rwb.getSheet(0);

		for (int i = 1; i < sheet.getRows(); i++) {
			String[] str = new String[sheet.getColumns()];
			for (int j = 0; j < 2; j++) {
				cell = sheet.getCell(j, i);
				str[j] = cell.getContents();
			}
			list.add(str);
		}
		return list;
	}
}
