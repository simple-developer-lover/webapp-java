package indi.monkey.webapp.proxy.local;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import indi.monkey.webapp.commons.annotation.ReserveProxy;
import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.SocketResponse;
import indi.monkey.webapp.commons.socket.SocketClient;
import indi.monkey.webapp.pojo.hibernate.taobao.TaobaoShop;
import indi.monkey.webapp.pojo.hibernate.tieba.TiebaUser;
import indi.monkey.webapp.proxy.SpiderProxy;

@ReserveProxy
public class SpiderProxyImpl implements SpiderProxy {

	private static final Logger logger = LoggerFactory.getLogger(SpiderProxyImpl.class);

	private static final String IP = "localhost";
	private static final int PORT = 8888;
	private static final String ACTION_TYPE = "actionType";
	private static final String SUCCESS_CODE = "success";
	private static final String ERROR_CODE = "error";
	private static final String ERROR_MSG = "socket response error... :";

	private String sendData(Request request) {
		Map<String, String> context = request.getContext();
		if (context.size() != 0) {
			SocketClient client = new SocketClient(IP, PORT);
			String data = JSON.toJSONString(context);
			logger.info("start request data:{}", data);
			try {
				String msg = client.sendMsg(data);
				logger.info("socket response:{} ...", msg.substring(0, Math.min(100, msg.length())));
				return msg;
			} catch (Exception e) {
				logger.error(ERROR_MSG, e);
				throw e;
			}
		} else {
			throw new IllegalArgumentException("request context can not be empty");
		}
	}

	@Override
	public SocketResponse<Set<TiebaUser>> baiduTieba(Request request) {
		String msg = sendData(request);
		SocketResponse<Set<TiebaUser>> resp = JSON.parseObject(msg,
				new TypeReference<SocketResponse<Set<TiebaUser>>>() {
				});
		Assert.isTrue(resp != null && SUCCESS_CODE.equals(resp.getStatus()), ERROR_MSG + resp);
		return resp;
	}

	@Override
	public SocketResponse<List<TaobaoShop>> taobaoGoods(Request request) {
		String msg = sendData(request);
		SocketResponse<List<TaobaoShop>> resp = JSON.parseObject(msg,
				new TypeReference<SocketResponse<List<TaobaoShop>>>() {
				});
		Assert.isTrue(resp != null && SUCCESS_CODE.equals(resp.getStatus()), ERROR_MSG + resp);
		return resp;
	}
}
