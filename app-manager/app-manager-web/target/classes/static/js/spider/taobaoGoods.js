var taobaoGoods = function() {
	this.load = function(data) {
		var requestUrl = basePath + service + "/loadtaobaodata";
		$.post(requestUrl, function(result) {
			if (result && result.status == 200) {
				loadData(resultData);
			} else {
				alert("error!!!");
			}
		})
	}
}

function buildBarOption(data) {
	var option = {
		title : {
			text : data.title
		},
		tooltip : {},
		lengend : {
			data : data.legend
		},
		xAxis : {
			data : data.xaxis
		},
		yAxis : {},
		series : data.series
	};
	return option;
}

function buildLineOption(data) {
	var option = {
		title : {
			text : data.title
		},
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			data : data.legend
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
			data : data.xaxis
		},
		yAxis : {
			type : 'value'
		},
		series : data.series
	};
	return option;
}

var getTaobaoData = function() {
	this.load = function(data) {
		var bar_charts = echarts.init(document.getElementById("bar_cavans"));
		var line_cavans = echarts.init(document.getElementById("line_cavans"));
		bar_charts.clear();
		bar_charts.setOption(buildBarOption(data.barData));
		line_cavans.clear();
		var option = buildLineOption(data.lineData);
		line_cavans.setOption(option);
	}
}

function loadData(data) {
	if (data['content']) {
		loadDataContent(data['content']);
	}
	if (data['bras']) {
		loadBras(data['bras']);
	}
}

function loadDataContent(data) {

}

function loadBras(data) {

}