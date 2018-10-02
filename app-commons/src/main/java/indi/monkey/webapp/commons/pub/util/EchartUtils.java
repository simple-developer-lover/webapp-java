package indi.monkey.webapp.commons.pub.util;

import java.util.List;
import java.util.Map;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Pie;

public class EchartUtils {
	public static Option createOption(List<Map<String, Object>> list) {
		Option option = new Option();
		option.title("").tooltip(Trigger.axis).legend("");
		option.xAxis(new ValueAxis().boundaryGap(0d, 0.01));
		CategoryAxis categoryAxis = new CategoryAxis();
		// Bar
		Bar bar = new Bar("bar");
		// pie
		Pie pie = new Pie("pie");
		for (Map<String, Object> map : list) {
			categoryAxis.data(map.get("name"));
			bar.data(map.get("total"));
			pie.data(new PieData(map.get("name").toString(), map.get("total")));
		}
		option.yAxis(categoryAxis);
		pie.center(900, 300).radius(100);
		option.series(bar, pie);
		option.grid().x(180);
		return option;
	}
}
