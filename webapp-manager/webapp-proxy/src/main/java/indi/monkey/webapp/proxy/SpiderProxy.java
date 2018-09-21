package indi.monkey.webapp.proxy;

import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;

import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.SocketResponse;
import indi.monkey.webapp.pojo.hibernate.taobao.TaobaoGoods_Bra;
import indi.monkey.webapp.pojo.hibernate.taobao.TaobaoShop;
import indi.monkey.webapp.pojo.hibernate.tieba.TiebaUser;

//@FeignClient("app-server")
@RequestMapping("/spider-server")
public interface SpiderProxy {

	@RequestMapping("/baiduTieba")
	SocketResponse<Set<TiebaUser>> baiduTieba(Request request);

	@RequestMapping("/taobaogoods")
	SocketResponse<Map<TaobaoShop, TaobaoGoods_Bra>> taobaoGoods(Request request);
}
