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

var getTaobaoData = function() {
	this.load = function(data) {
		console.info(data);
		var mycharts = echarts.init(document.getElementById("cavans_div"));
		mycharts.clear();
		mycharts.setOption(data,true);
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