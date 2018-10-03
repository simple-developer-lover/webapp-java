package indi.monkey.webapp.commons.pub.util;

import java.util.List;
import java.util.Map;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Pie;

public class EchartUtils {
	public static Option createOption(String title, String legend, List<Map<String, Object>> list) {
		GsonOption option = new GsonOption();
		option.title(title);
		option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar),
				Tool.restore, Tool.saveAsImage);
		option.tooltip().show(true).formatter("{a} <br/> {b} : {c}");
		option.legend(legend);
		Bar bar = new Bar(title);
		CategoryAxis categoryAxis = new CategoryAxis();
		for (Map<String, Object> map : list) {
			categoryAxis.data(map.get("name"));
			bar.data(map);
		}
		option.xAxis(categoryAxis);
		option.yAxis(new ValueAxis());
		option.series(bar);
		return option;
	}
}
