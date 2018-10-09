package com.cn.webapp.test;

public class TestStr {
	public static void main(String[] args) {
		String s = "\r\n" + 
				"\r\n" + 
				"<!DOCTYPE html>\r\n" + 
				"<html lang=\"zh-cn\">\r\n" + 
				"<head>\r\n" + 
				"<meta charset=\"utf-8\"/>\r\n" + 
				"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\r\n" + 
				"<meta name=\"referrer\" content=\"origin\" />\r\n" + 
				"<title>用正则表达式替换指定标签中的内容 - 幽冥狂_七 - 博客园</title>\r\n" + 
				"<meta property=\"og:description\" content=\"用正则表达式替换指定标签中的内容，也是在工作中遇到的就顺手记了下来 string sXML = &quot;ABCDEFCSF&lt;p&gt;123123&lt;/p&gt;KOPL&quot;;/\" />\r\n" + 
				"<link type=\"text/css\" rel=\"stylesheet\" href=\"/bundles/blog-common.css?v=giTNza-Of-PEt5UsELhFQAR7G6-bfaSa4oolcq7i9-o1\"/>\r\n" + 
				"<link id=\"MainCss\" type=\"text/css\" rel=\"stylesheet\" href=\"/skins/InsideDotNet/bundle-InsideDotNet.css?v=hm5Ncnf1UmSaS2roxKOjrIG9bKc-5gPzmHFjhD4ZWn01\"/>\r\n" + 
				"<link type=\"text/css\" rel=\"stylesheet\" href=\"/blog/customcss/235456.css?v=Sot8bp7fK6%2fWQhr7uc1kUyTTTDA%3d\"/>\r\n" + 
				"<link id=\"mobile-style\" media=\"only screen and (max-width: 767px)\" type=\"text/css\" rel=\"stylesheet\" href=\"/skins/InsideDotNet/bundle-InsideDotNet-mobile.css?v=d9LctKHRIQp9rreugMcQ1-UJuq_j1fo0GZXTXj8Bqrk1\"/>\r\n" + 
				"<link title=\"RSS\" type=\"application/rss+xml\" rel=\"alternate\" href=\"https://www.cnblogs.com/youmingkuang/rss\"/>\r\n" + 
				"<link title=\"RSD\" type=\"application/rsd+xml\" rel=\"EditURI\" href=\"https://www.cnblogs.com/youmingkuang/rsd.xml\"/>\r\n" + 
				"<link type=\"application/wlwmanifest+xml\" rel=\"wlwmanifest\" href=\"https://www.cnblogs.com/youmingkuang/wlwmanifest.xml\"/>\r\n" + 
				"<script src=\"//common.cnblogs.com/scripts/jquery-2.2.0.min.js\"></script>\r\n" + 
				"<script type=\"text/javascript\">var currentBlogApp = 'youmingkuang', cb_enable_mathjax=false;var isLogined=false;</script>\r\n" + 
				"<script src=\"/bundles/blog-common.js?v=uRtdr-1gMEEkV91OavXV0CFFUNRV5dhGp1Mfq37o2Xw1\" type=\"text/javascript\"></script>\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"<a name=\"top\"></a>\r\n" + 
				"<!--PageBeginHtml Block Begin-->\r\n" + 
				"<div id=\"hd\">\r\n" + 
				"	<img src=\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530870745644&di=b6deb552f8b643daff6f8b307b9f3f5d&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fback_pic%2Fqk%2Fback_origin_pic%2F00%2F02%2F02%2Ffe79425d03a5b3503f31e24712f2270e.jpg\" height=\"300\" width=\"100%\"/>\r\n" + 
				"</div>\r\n" + 
				"<!--PageBeginHtml Block End-->\r\n" + 
				"\r\n" + 
				"<!--done-->\r\n" + 
				"<div id=\"home\">\r\n" + 
				"<div id=\"header\">\r\n" + 
				"	<div id=\"blogTitle\">\r\n" + 
				"	<div style=\"display:none\"><img src=\"/Skins/insideDotNet/images/top.gif\" alt=\"谨以此模板祝贺【博客园开发者征途】系列图书《你必须知道的.net》出版发行\" /></div>\r\n" + 
				"	<a id=\"lnkBlogLogo\" href=\"https://www.cnblogs.com/youmingkuang/\"><img id=\"blogLogo\" src=\"/Skins/custom/images/logo.gif\" alt=\"返回主页\" /></a>			\r\n" + 
				"		\r\n" + 
				"<!--done-->\r\n" + 
				"<h1><a id=\"Header1_HeaderTitle\" class=\"headermaintitle\" href=\"https://www.cnblogs.com/youmingkuang/\">幽冥狂_七</a></h1>\r\n" + 
				"<h2></h2>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"		\r\n" + 
				"	</div><!--end: blogTitle 博客的标题和副标题 -->\r\n" + 
				"	<div id=\"navigator\">\r\n" + 
				"		\r\n" + 
				"<ul id=\"navList\">\r\n" + 
				"    <li><a id=\"blog_nav_sitehome\" class=\"menu\" href=\"https://www.cnblogs.com/\">博客园</a></li>\r\n" + 
				"    <li><a id=\"MyLinks1_SpaceLink\" class=\"menu\" href=\"http://ing.cnblogs.com/\">闪存</a></li>\r\n" + 
				"    <li><a id=\"blog_nav_myhome\" class=\"menu\" href=\"https://www.cnblogs.com/youmingkuang/\">首页</a></li>\r\n" + 
				"    <li><a id=\"blog_nav_newpost\" class=\"menu\" rel=\"nofollow\" href=\"https://i.cnblogs.com/EditPosts.aspx?opt=1\">新随笔</a></li>\r\n" + 
				"    <li><a id=\"blog_nav_contact\" accesskey=\"9\" class=\"menu\" rel=\"nofollow\" href=\"https://msg.cnblogs.com/send/%E5%B9%BD%E5%86%A5%E7%8B%82_%E4%B8%83\">联系</a></li>\r\n" + 
				"    <li><a id=\"blog_nav_admin\" class=\"menu\" rel=\"nofollow\" href=\"https://i.cnblogs.com/\">管理</a></li>\r\n" + 
				"    <li><a id=\"blog_nav_rss\" class=\"menu\" href=\"https://www.cnblogs.com/youmingkuang/rss\">订阅</a>\r\n" + 
				"        <a id=\"blog_nav_rss_image\" class=\"aHeaderXML\" href=\"https://www.cnblogs.com/youmingkuang/rss\"><img src=\"//www.cnblogs.com/images/xml.gif\" alt=\"订阅\" /></a></li>\r\n" + 
				"</ul>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"		<div class=\"clear\"></div>\r\n" + 
				"		<div class=\"blogStats\">\r\n" + 
				"			\r\n" + 
				"			<div id=\"blog_stats\">\r\n" + 
				"<!--done-->\r\n" + 
				"随笔- 277&nbsp;\r\n" + 
				"文章- 1&nbsp;\r\n" + 
				"评论- 13&nbsp;\r\n" + 
				"</div>\r\n" + 
				"			\r\n" + 
				"		</div><!--end: blogStats -->\r\n" + 
				"	</div><!--end: navigator 博客导航栏 -->\r\n" + 
				"</div><!--end: header 头部 -->\r\n" + 
				"\r\n" + 
				"<div id=\"main\">\r\n" + 
				"	<div id=\"mainContent\">\r\n" + 
				"	<div class=\"forFlow\">\r\n" + 
				"		\r\n" + 
				"<div id=\"post_detail\">\r\n" + 
				"<!--done-->\r\n" + 
				"<div id=\"topics\">\r\n" + 
				"	<div class = \"post\">\r\n" + 
				"		<h1 class = \"postTitle\">\r\n" + 
				"			<a id=\"cb_post_title_url\" class=\"postTitle2\" href=\"https://www.cnblogs.com/youmingkuang/p/5126739.html\">用正则表达式替换指定标签中的内容</a>\r\n" + 
				"		</h1>\r\n" + 
				"		<div class=\"clear\"></div>\r\n" + 
				"		<div class=\"postBody\">\r\n" + 
				"			<div id=\"cnblogs_post_body\" class=\"blogpost-body\"><p>用正则表达式替换指定标签中的内容，也是在工作中遇到的就顺手记了下来</p>\r\n" + 
				"<div class=\"cnblogs_code\">\r\n" + 
				"<pre><span style=\"color: #0000ff;\">string</span> sXML = \"ABCDEFCSF&lt;p&gt;123123&lt;/p&gt;KOPL\";<br />//现在我要去掉P标签中的内容，实现</pre>\r\n" + 
				"<pre>ABCDEFCSFKOPL这样的效果。<br /><br />引入：using System.Text.RegularExpressions;<br /><br /></pre>\r\n" + 
				"<p>　　Regex rg = new Regex(\"&lt;pict&gt;.*&lt;/pict&gt;\");</p>\r\n" + 
				"<p>　　sXML = rg.Replace(sXML, \"\");</p>\r\n" + 
				"<pre>就实现</pre>\r\n" + 
				"<pre>ABCDEFCSFKOPL</pre>\r\n" + 
				"<pre></pre>\r\n" + 
				"</div>\r\n" + 
				"<p>&nbsp;</p>\r\n" + 
				"<div class=\"cnblogs_code\">\r\n" + 
				"<pre>   <span style=\"color: #808080;\">///</span> <span style=\"color: #808080;\">&lt;summary&gt;</span>\r\n" + 
				"   <span style=\"color: #808080;\">///</span><span style=\"color: #008000;\"> 去掉所以的HTML标签\r\n" + 
				"   </span><span style=\"color: #808080;\">///</span> <span style=\"color: #808080;\">&lt;/summary&gt;</span>\r\n" + 
				"   <span style=\"color: #808080;\">///</span> <span style=\"color: #808080;\">&lt;param name=\"str\"&gt;&lt;/param&gt;</span>\r\n" + 
				"   <span style=\"color: #808080;\">///</span> <span style=\"color: #808080;\">&lt;returns&gt;&lt;/returns&gt;</span>\r\n" + 
				" <span style=\"color: #0000ff;\">public</span> <span style=\"color: #0000ff;\">static</span> <span style=\"color: #0000ff;\">string</span> DropHTML(<span style=\"color: #0000ff;\">string</span><span style=\"color: #000000;\"> str)\r\n" + 
				"        {\r\n" + 
				"            </span><span style=\"color: #0000ff;\">string</span> st =<span style=\"color: #000000;\"> str;\r\n" + 
				"\r\n" + 
				"            </span><span style=\"color: #008000;\">//</span><span style=\"color: #008000;\">去掉所以的HTML标签</span>\r\n" + 
				"            <span style=\"color: #0000ff;\">string</span> regx = <span style=\"color: #800000;\">\"</span><span style=\"color: #800000;\">&lt;[^&gt;]+&gt;</span><span style=\"color: #800000;\">\"</span><span style=\"color: #000000;\">;\r\n" + 
				"            </span><span style=\"color: #008000;\">//</span><span style=\"color: #008000;\">去换行去空格</span>\r\n" + 
				"            <span style=\"color: #0000ff;\">string</span> regEx_space = <span style=\"color: #800000;\">\"</span><span style=\"color: #800000;\">\\\\s*|\\t|\\r|\\n</span><span style=\"color: #800000;\">\"</span><span style=\"color: #000000;\">;\r\n" + 
				"\r\n" + 
				"            str </span>= Regex.Replace(str, regx, <span style=\"color: #0000ff;\">string</span><span style=\"color: #000000;\">.Empty, RegexOptions.IgnoreCase);\r\n" + 
				"            str </span>= Regex.Replace(str, regEx_space, <span style=\"color: #0000ff;\">string</span><span style=\"color: #000000;\">.Empty, RegexOptions.IgnoreCase);\r\n" + 
				"            </span><span style=\"color: #008000;\">//</span><span style=\"color: #008000;\">var sts = regx.Replace(st, \"\");</span>\r\n" + 
				"\r\n" + 
				"            <span style=\"color: #0000ff;\">return</span><span style=\"color: #000000;\"> str;\r\n" + 
				"        }</span></pre>\r\n" + 
				"</div>\r\n" + 
				"<p>&nbsp;</p></div><div id=\"MySignature\"></div>\r\n" + 
				"<div class=\"clear\"></div>\r\n" + 
				"<div id=\"blog_post_info_block\">\r\n" + 
				"<div id=\"BlogPostCategory\"></div>\r\n" + 
				"<div id=\"EntryTag\"></div>\r\n" + 
				"<div id=\"blog_post_info\">\r\n" + 
				"</div>\r\n" + 
				"<div class=\"clear\"></div>\r\n" + 
				"<div id=\"post_next_prev\"></div>\r\n" + 
				"</div>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"		</div>\r\n" + 
				"		<div class = \"postDesc\">posted @ <span id=\"post-date\">2016-01-13 11:37</span> <a href='https://www.cnblogs.com/youmingkuang/'>幽冥狂_七</a> 阅读(<span id=\"post_view_count\">...</span>) 评论(<span id=\"post_comment_count\">...</span>)  <a href =\"https://i.cnblogs.com/EditPosts.aspx?postid=5126739\" rel=\"nofollow\">编辑</a> <a href=\"#\" onclick=\"AddToWz(5126739);return false;\">收藏</a></div>\r\n" + 
				"	</div>\r\n" + 
				"	<script type=\"text/javascript\">var allowComments=true,cb_blogId=235456,cb_entryId=5126739,cb_blogApp=currentBlogApp,cb_blogUserGuid='0dc0bbf5-fb29-e511-b908-9dcfd8948a71',cb_entryCreatedDate='2016/1/13 11:37:00';loadViewCount(cb_entryId);var cb_postType=1;</script>\r\n" + 
				"	\r\n" + 
				"</div><!--end: topics 文章、评论容器-->\r\n" + 
				"</div><a name=\"!comments\"></a><div id=\"blog-comments-placeholder\"></div><script type=\"text/javascript\">var commentManager = new blogCommentManager();commentManager.renderComments(0);</script>\r\n" + 
				"<div id='comment_form' class='commentform'>\r\n" + 
				"<a name='commentform'></a>\r\n" + 
				"<div id='divCommentShow'></div>\r\n" + 
				"<div id='comment_nav'><span id='span_refresh_tips'></span><a href='javascript:void(0);' onclick='return RefreshCommentList();' id='lnk_RefreshComments' runat='server' clientidmode='Static'>刷新评论</a><a href='#' onclick='return RefreshPage();'>刷新页面</a><a href='#top'>返回顶部</a></div>\r\n" + 
				"<div id='comment_form_container'></div>\r\n" + 
				"<div class='ad_text_commentbox' id='ad_text_under_commentbox'></div>\r\n" + 
				"<div id='ad_t2'></div>\r\n" + 
				"<div id='opt_under_post'></div>\r\n" + 
				"<div id='cnblogs_c1' class='c_ad_block'></div>\r\n" + 
				"<div id='under_post_news'></div>\r\n" + 
				"<script async='async' src='https://www.googletagservices.com/tag/js/gpt.js'></script>\r\n" + 
				"<script>\r\n" + 
				"  var googletag = googletag || {};\r\n" + 
				"  googletag.cmd = googletag.cmd || [];\r\n" + 
				"</script>\r\n" + 
				"\r\n" + 
				"<script>\r\n" + 
				"  googletag.cmd.push(function() {\r\n" + 
				"    googletag.defineSlot('/1090369/C2', [468, 60], 'div-gpt-ad-1539008685004-0').addService(googletag.pubads());\r\n" + 
				"    googletag.pubads().enableSingleRequest();\r\n" + 
				"    googletag.enableServices();\r\n" + 
				"  });\r\n" + 
				"</script>\r\n" + 
				"<div id='cnblogs_c2' class='c_ad_block'>\r\n" + 
				"    <div id='div-gpt-ad-1539008685004-0' style='height:60px; width:468px;'>\r\n" + 
				"    <script>\r\n" + 
				"    googletag.cmd.push(function() { googletag.display('div-gpt-ad-1539008685004-0'); });\r\n" + 
				"    </script>\r\n" + 
				"    </div>\r\n" + 
				"</div>\r\n" + 
				"<div id='under_post_kb'></div>\r\n" + 
				"<div id='HistoryToday' class='c_ad_block'></div>\r\n" + 
				"<script type='text/javascript'>\r\n" + 
				"    fixPostBody();\r\n" + 
				"    setTimeout(function () { incrementViewCount(cb_entryId); }, 50);\r\n" + 
				"    deliverAdT2();\r\n" + 
				"    deliverAdC1();\r\n" + 
				"    deliverAdC2();    \r\n" + 
				"    loadNewsAndKb();\r\n" + 
				"    loadBlogSignature();\r\n" + 
				"    LoadPostInfoBlock(cb_blogId, cb_entryId, cb_blogApp, cb_blogUserGuid);\r\n" + 
				"    GetPrevNextPost(cb_entryId, cb_blogId, cb_entryCreatedDate, cb_postType);\r\n" + 
				"    loadOptUnderPost();\r\n" + 
				"    GetHistoryToday(cb_blogId, cb_blogApp, cb_entryCreatedDate);   \r\n" + 
				"</script>\r\n" + 
				"</div>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"	</div><!--end: forFlow -->\r\n" + 
				"	</div><!--end: mainContent 主体内容容器-->\r\n" + 
				"\r\n" + 
				"	<div id=\"sideBar\">\r\n" + 
				"		<div id=\"sideBarMain\">\r\n" + 
				"			\r\n" + 
				"<!--done-->\r\n" + 
				"<div class=\"newsItem\">\r\n" + 
				"<h3 class=\"catListTitle\">公告</h3>\r\n" + 
				"	<div id=\"blog-news\"></div><script type=\"text/javascript\">loadBlogNews();</script>\r\n" + 
				"</div>\r\n" + 
				"\r\n" + 
				"			<div id=\"calendar\"><div id=\"blog-calendar\" style=\"display:none\"></div><script type=\"text/javascript\">loadBlogDefaultCalendar();</script></div>\r\n" + 
				"			\r\n" + 
				"			<DIV id=\"leftcontentcontainer\">\r\n" + 
				"				<div id=\"blog-sidecolumn\"></div><script type=\"text/javascript\">loadBlogSideColumn();</script>\r\n" + 
				"			</DIV>\r\n" + 
				"			\r\n" + 
				"		</div><!--end: sideBarMain -->\r\n" + 
				"	</div><!--end: sideBar 侧边栏容器 -->\r\n" + 
				"	<div class=\"clear\"></div>\r\n" + 
				"	<div class=\"clear\"></div>\r\n" + 
				"	</div><!--end: main -->\r\n" + 
				"	<div class=\"clear\"></div>\r\n" + 
				"	<div id=\"footer\">\r\n" + 
				"		\r\n" + 
				"<!--done-->\r\n" + 
				"Copyright &copy;2018 幽冥狂_七 谨以此模板祝贺【博客园开发者征途】系列图书之《你必须知道的.NET》出版发行\r\n" + 
				"	</div><!--end: footer -->\r\n" + 
				"</div><!--end: home 自定义的最大容器 -->\r\n" + 
				"</body>\r\n" + 
				"</html>\r\n" + 
				"";
		
		s = s.replaceAll("[\\/|\n|\t|<html.*>|<script.*>|<div.*>|<a.*>|<body.*>|<p.*>|<input.*>|<!--.*-->]", "");
		System.out.println(s);
		
	}
}
