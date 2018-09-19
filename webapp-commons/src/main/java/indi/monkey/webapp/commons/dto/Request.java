package indi.monkey.webapp.commons.dto;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;
import com.google.gson.Gson;

import indi.monkey.webapp.commons.pub.util.StringUtils;
import indi.monkey.webapp.commons.pub.util.UUIDUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Request implements Serializable {

	/**
	 * 
	 */
	transient private static Gson gson = new Gson();

	private static final long serialVersionUID = 5960932972846007724L;
	private int requestId;
	private String sessionId;
	private String uuid;
	private String actionName;
	private Map<String, String> context;

	public static final Request of(String actionName, HttpServletRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		Map<String, String> data = Maps.newHashMap();
		String sessionId = null;
		for (Iterator<Entry<String, String[]>> it = map.entrySet().iterator(); it.hasNext();) {
			Entry<String, String[]> e = it.next();
			String[] value = e.getValue();
			if (value != null && value.length > 0) {
				if ("sessionId".equals(e.getKey())) {
					sessionId = e.getValue()[0];
				} else {
					if (value.length > 1) {
						data.put(e.getKey(), gson.toJson(value));
					} else {
						data.put(e.getKey(), value[0]);
					}
				}
			}
		}
		if (StringUtils.isEmpty(sessionId)) {
			Object obj = request.getSession().getAttribute("sessionId");
			if (obj != null) {
				sessionId = obj.toString();
			}
		}
		return Request.builder().requestId(request.hashCode()).uuid(UUIDUtil.getUUID32()).context(data)
				.sessionId(sessionId).actionName(actionName).build();
	}

	public String putParam(String key, String value) {
		if (context == null) {
			context = Maps.newHashMap();
		}
		return context.put(key, value);
	}

	public void putParams(Map<String, String> params) {
		if (params == null || params.isEmpty()) {
			return;
		}
		if (context == null) {
			context = Maps.newHashMap();
		}
		context.putAll(params);
	}

	public String getParam(String key) {
		return context.get(key);
	}

	public String getParam(String key, String defaultVal) {
		return context.getOrDefault(key, defaultVal);
	}
}
