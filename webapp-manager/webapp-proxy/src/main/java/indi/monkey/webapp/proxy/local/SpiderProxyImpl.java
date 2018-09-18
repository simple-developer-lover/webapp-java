package indi.monkey.webapp.proxy.local;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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

	private static String IP = "localhost";
	private static int PORT = 8888;
	private static final String ACTION_TYPE = "actionType";
	private static final String SUCCESS_CODE = "success";
	private static final String ERROR_CODE = "error";

	private String sendData(Request request) {
		Map<String, String> context = request.getContext();
		if (context.size() != 0) {
			SocketClient client = new SocketClient(IP, PORT);
			context.put(ACTION_TYPE, request.getActionName());
			String data = JSON.toJSONString(context);
			logger.info("start request data:{}", data);
			try {
				String msg = client.sendMsg(data);
				logger.info("socket response:{}", msg);
				return msg;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public SocketResponse<Set<TiebaUser>> baiduTieba(Request request) {
		String msg = sendData(request);
		SocketResponse<Set<TiebaUser>> resp = SocketResponse.of(msg);
		if (resp.getStatus() == ERROR_CODE) {
			throw new RuntimeException(resp.getMsg());
		}
		return resp;
	}

	@Override
	public SocketResponse<Set<TaobaoShop>> taobaoGoods(Request request) {
		String msg = sendData(request);
		SocketResponse<Set<TaobaoShop>> resp = SocketResponse.of(msg);
		if (resp.getStatus() == ERROR_CODE) {
			throw new RuntimeException(resp.getMsg());
		}
		return resp;
	}
}
