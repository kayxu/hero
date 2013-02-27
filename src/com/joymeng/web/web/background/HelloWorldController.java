package com.joymeng.web.web.background;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joymeng.core.utils.FileUtil;

@Controller
@RequestMapping("/export")
public class HelloWorldController {
	@RequestMapping
	public void export(HttpServletResponse response){
		List<Map<String, ?>> lst = new ArrayList<>();
		Map<String ,String> map = new HashMap<String ,String>();
		map.put("姓名", "a");
		map.put("成绩", "100");
		lst.add(map);
		Map<String ,String> map1 = new HashMap<String ,String>();
		map1.put("姓名", "b");
		map1.put("成绩", "100");
		lst.add(map1);
		String ret = FileUtil.download("text_1" + ".xls", FileUtil.createExcelExportBuf(null, null, null, lst), response);
	}
}
