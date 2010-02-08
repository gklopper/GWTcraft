package com.gwtcraft.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AroundFilter implements Filter {

	private static final Logger LOGGER = Logger.getLogger(AroundFilter.class.getName());
	
	@Override
	public void init(FilterConfig config) throws ServletException {}
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOGGER.info(((HttpServletRequest)request).getRequestURI());
		before((HttpServletRequest) request, (HttpServletResponse) response);
		chain.doFilter(request, response);
		after((HttpServletRequest) request, (HttpServletResponse) response);
	}
	
	public abstract void before(HttpServletRequest request, HttpServletResponse response);
	public abstract void after(HttpServletRequest request, HttpServletResponse response);
}
