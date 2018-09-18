package indi.monkey.webapp.proxy.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import indi.monkey.webapp.commons.annotation.ReserveProxy;
import indi.monkey.webapp.commons.dto.Request;
import indi.monkey.webapp.commons.dto.Response;
import indi.monkey.webapp.commons.exception.ScriptException;
import indi.monkey.webapp.proxy.BookProxy;

@ReserveProxy
public class BookProxyImpl implements BookProxy {

	private static final Logger logger = LoggerFactory.getLogger(BookProxyImpl.class);

	@Override
	public Response<?> search(Request request) throws ScriptException {
		return null;
	}

}
