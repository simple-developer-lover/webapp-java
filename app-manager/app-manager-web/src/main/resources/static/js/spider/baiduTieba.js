var baiduTieba = function() {
	this.load = function(data) {
		loadUserData(data.data);
		loadImg(data.picPath);
	}
}

function changeUserTable(t) {
	var $this = $(t);
	if ($this.val() == 'hide') {
		$("#userTable").css("display", "none");
	} else {
		$("#userTable").css("display", "");
	}
}

function loadImg(picPath) {
	var imgSrc = basePath + "file/download?serviceType=default&picPath="
			+ encodeURI(picPath);
	var img = new Image();
	img.src = imgSrc;
	img.onload = function() {
		console.log("load img .....src:" + this.src);
		var wordcloud = $("#wordcloud");
		if (this.width > 0 && this.height > 0) {
			var width = 500;
			var scale = width / this.width;
			var height = this.height * scale;
			wordcloud.html("");
			wordcloud.append("<img src='" + imgSrc + "' width='" + width
					+ "px' height='" + height + "px'/>");
		} else {
			this.src = imgSrc;
			if (wordcloud.html() == "") {
				$("#wordcloud").append("<img src=''/>");
			}
		}
	}
}

function loadUserData(data) {
	var index = 0;
	var table = $("#userTable");
	var male_count = 0;
	var female_count = 0;
	for (k in data) {
		var tr;
		if (index % 3 == 0) {
			tr = $("<tr></tr>");
			table.append(tr);
		}
		var td = $("<td></td>");
		var user = JSON.parse(k);
		var content = data[k];
		var user_div = $("<div></div>");
		var img = $("<img src='" + user.head
				+ "' width='110px' height='110px'/>");
		var a = $("<a></a>");
		a.append(img);
		var username = $("<a></a>");
		username.text(user.name);
		username.attr("href", user.url);
		username.css("color", user.sex == "female" ? "red" : "blue");
		user_div.append(a);
		user_div.css("border", user.sex == "female" ? "3px solid red"
				: "3px solid blue");
		var titleCount = $("<span></span>");
		var ul = $("<ul></ul>");
		var li_titleCount = $("<li></li>");
		li_titleCount.text("发言:");
		var li_tbAge = $("<li></li>");
		li_tbAge.text("吧龄:");
		ul.append(li_titleCount);
		ul.append(li_tbAge);
		titleCount.text(user.tbTitleCount);
		li_titleCount.append(titleCount);
		user_div.append(ul);
		var tbAge = $("<span></span>");
		tbAge.text(user.tbAge);
		li_tbAge.append(tbAge);
		user_div.append(username);
		td.append(user_div);
		tr.append(td);
		if (user.sex == "female") {
			female_count++;
		} else {
			male_count++;
		}
		index++;
	}
}