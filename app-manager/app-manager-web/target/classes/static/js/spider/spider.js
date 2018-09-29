function sendData(t) {
	var query = $(t).val();
	var func = $(t).removeAttr("onblur");
	var actionType = $(t).attr("action-Type");
	var data = {
		"data" : query
	};
	var requestUrl = basePath + service + "/" + actionType;
	console.log(requestUrl);
	if (query && query.length > 0 && JSON.stringify(result_data_all) == "{}") {
		$.post(requestUrl, data, function(result) {
			if (result && result.status == 200) {
				var data = JSON.parse(result.data);
				result_data_all = data;
				var spider = eval('(' + actionType + ')')
				new spider().load(data);
			} else {
				alert("error!!!");
			}
			$(t).attr("onblur", "sendUrl(this)");
		});
	}
}

function create_lines(lines_data) {
	var line_cavans = $("<cavans></cavans>");

	this.lines = function(data) {
		var option = {
			title : {
				text : '折线图堆叠'
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '邮件营销', '联盟广告', '视频广告', '直接访问', '搜索引擎' ]
			},
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			toolbox : {
				feature : {
					saveAsImage : {}
				}
			},
			xAxis : {
				type : 'category',
				boundaryGap : false,
				data : [ '周一', '周二', '周三', '周四', '周五', '周六', '周日' ]
			},
			yAxis : {
				type : 'value'
			},
			series : [ {
				name : '邮件营销',
				type : 'line',
				stack : '总量',
				data : [ 120, 132, 101, 134, 90, 230, 210 ],
				smooth : true
			}, {
				name : '联盟广告',
				type : 'line',
				stack : '总量',
				data : [ 220, 182, 191, 234, 290, 330, 310 ],
				smooth : true
			}, {
				name : '视频广告',
				type : 'line',
				stack : '总量',
				data : [ 150, 232, 201, 154, 190, 330, 410 ],
				smooth : true
			}, {
				name : '直接访问',
				type : 'line',
				stack : '总量',
				data : [ 320, 332, 301, 334, 390, 330, 320 ],
				smooth : true
			}, {
				name : '搜索引擎',
				type : 'line',
				stack : '总量',
				data : [ 820, 932, 901, 934, 1290, 1330, 1320 ],
				smooth : true
			}

			]
		};
		return option;
	}

}

function create_pie(pie_data) {
	this.scrollable_legend_pie_template = function(data) {
		var option = {
			title : {
				text : data.text,
				subtext : data.subtext,
				x : 'center'
			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				type : 'scroll',
				orient : 'vertical',
				right : 10,
				top : 20,
				bottom : 20,
				data : data.legendData,
				selected : data.selected
			},
			series : [ {
				name : data.name,
				type : 'pie',
				radius : '55%',
				center : [ '40%', '60%' ],
				data : data.seriesData,
				itemStyle : {
					emphasis : {
						shadowBlur : 10,
						shadowOffsetX : 0,
						shadowColor : 'rgba(0, 0, 0, 0.5)'
					}
				}
			} ]
		};
		return option;
	}
}