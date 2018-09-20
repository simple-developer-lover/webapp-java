package indi.monkey.webapp.proxy;

import java.util.Map;
import java.util.Set;

import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.SocketResponse;
import indi.monkey.webapp.pojo.hibernate.taobao.TaobaoGoods_Bra;
import indi.monkey.webapp.pojo.hibernate.taobao.TaobaoShop;
import indi.monkey.webapp.pojo.hibernate.tieba.TiebaUser;

public interface SpiderProxy {
	SocketResponse<Set<TiebaUser>> baiduTieba(Request request);

	SocketResponse<Map<TaobaoShop, TaobaoGoods_Bra>> taobaoGoods(Request request);
}
