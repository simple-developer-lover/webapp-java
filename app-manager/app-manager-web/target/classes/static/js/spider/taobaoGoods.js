var taobaoGoods = function() {
	this.load = function(data) {
		var requestUrl = basePath + service + "/loadtaobaodata;
		$.post(requestUrl,function(result) {
			if (result && result.status == 200) {
				loadData(resultData);
			} else {
				alert("error!!!");
			}
		}
	}
}

function loadData(data) {
	if(data['content']){
		loadDataContent(data['content']);
	}
	if(data['bras']){
		loadBras(data['bras']);
	}
}

function loadDataContent(data){
	
}

function loadBras(data){
	
}