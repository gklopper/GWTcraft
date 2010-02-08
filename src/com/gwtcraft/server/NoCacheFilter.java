package com.gwtcraft.server;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoCacheFilter extends AroundFilter {

	private static final Logger LOGGER = Logger.getLogger(NoCacheFilter.class.getName());
	
	@Override
	public void before(HttpServletRequest request, HttpServletResponse response) {}
	
	@Override
	public void after(HttpServletRequest request, HttpServletResponse response) {
		HttpServletRequest req = (HttpServletRequest) request;
		LOGGER.info("URI: " + req.getRequestURI());
		if (req.getRequestURI().contains("nocache")) {
			LOGGER.info("Not caching: " + req.getRequestURI());
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.setHeader("Cache-Control", "no-cache, must-revalidate");
		}
	}
}
