package com.btc.util.excel;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

public class AssetListener<T> extends AnalysisEventListener<T> {
	// 自定义用于暂时存储data。
	// 可以通过实例获取该值
	private List<T> datas = new ArrayList<T>();

	public void invoke(T object, AnalysisContext context) {
		datas.add(object);// 数据存储到list，供批量处理，或后续自己业务逻辑处理。
	}
	public void doAfterAllAnalysed(AnalysisContext context) {
		// datas.clear();//解析结束销毁不用的资源
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
}
