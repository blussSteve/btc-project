package com.spring.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.btc.pojo.excel.AssetModel;
import com.btc.util.excel.AssetListener;
public class ExcelTest {

	@Test
	public void test() throws FileNotFoundException{
		
		String path="E://888.xlsx";
		ExcelTypeEnum excelTypeEnum=null;
		if(path.contains(ExcelTypeEnum.XLS.getValue())){
			 excelTypeEnum=ExcelTypeEnum.XLS;
		}
		if(path.contains(ExcelTypeEnum.XLSX.getValue())){
			 excelTypeEnum=ExcelTypeEnum.XLSX;
		}
		if(null==excelTypeEnum){
			System.out.println("..................");
		}
		System.out.println("..................");
		InputStream inputStream=new FileInputStream(new File(path));
	        try {
	            // 解析每行结果在listener中处理
	        	AssetListener<AssetModel> listener = new AssetListener<AssetModel>();

	            ExcelReader excelReader = new ExcelReader(inputStream, excelTypeEnum, null, listener);

	            excelReader.read(new Sheet(1, 0, AssetModel.class));
	           System.out.println(JSON.toJSONString(listener.getDatas()));
	        } catch (Exception e) {
	        	e.printStackTrace();
	        } finally {
	            try {
	                inputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	}
	
}
