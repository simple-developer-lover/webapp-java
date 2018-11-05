package indi.monkey.webapp.proxy.local;

import indi.monkey.webapp.commons.annotation.ReserveProxy;
import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;
import indi.monkey.webapp.commons.exception.ScriptException;
import indi.monkey.webapp.proxy.BookProxy;
import lombok.extern.slf4j.Slf4j;

@ReserveProxy
@Slf4j
public class BookProxyImpl implements BookProxy {

	@Override
	public Response<?> search(Request request) throws ScriptException {
		return null;
	}

}
