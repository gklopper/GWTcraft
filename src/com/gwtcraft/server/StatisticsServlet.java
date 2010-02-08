package com.gwtcraft.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.cache.CacheFactory;
import javax.cache.CacheStatistics;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class StatisticsServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");

			PrintWriter out = response.getWriter();

			out.println("<html><head><title>Cache stats</title></head><body>");
			
			//only need to do 1 cache
			//have seen that all stats are amalgamated
			doCache("Cache Statistics", ArmoryServiceImpl.cache24hours.getCacheStatistics(), out);

			out.println("</body></html>");
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private void doCache(String name, CacheStatistics stats, PrintWriter out) {
		out.println("<h1>" + name + "</h1>");
		int hits = stats.getCacheHits();
		int misses = stats.getCacheMisses();
		int numObjects = stats.getObjectCount();
		out.println("<p>Hits: " + hits + "</p>");
		out.println("<p>Misses: " + misses + "</p>");
		out.println(String.format("<p>Hits/misses: %.2f</p>", (double)hits/(double)misses));
		out.println("<p>Total objects: " + numObjects + "</p>");
		out.println("<br/>");
	}

}
