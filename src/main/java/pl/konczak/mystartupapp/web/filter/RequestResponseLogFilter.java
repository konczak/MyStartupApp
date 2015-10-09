package pl.konczak.mystartupapp.web.filter;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Logs HTTP request and response. Also registers MDC requestId.
 *
 * @author Piotr.Konczak
 */
public class RequestResponseLogFilter
   implements Filter {

   private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLogFilter.class);

   private static final AtomicLong ID = new AtomicLong(0);

   @Override
   public void init(FilterConfig filterConfig)
      throws ServletException {
      //nothing to do in init
   }

   @Override
   public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain)
      throws IOException,
             ServletException {
      long requestId = ID.incrementAndGet();
      MDC.put("requestId", Long.toString(requestId));

      try {
         handle(request, response, chain, requestId);
      } finally {
         MDC.remove("requestId");
      }
   }

   @Override
   public void destroy() {
      //nothing to clean
   }

   private void logRequest(BufferedRequestWrapper request) {
      try {
         LOGGER.info("{} {} {}{} <{}> <{}> <{}> <{}>",
            request.getId(),
            request.getMethod(),
            request.getRequestURI(),
            request.readQueryString(),
            request.getHeader("Accept"),
            request.getRemoteAddr(),
            request.getContentType(),
            request.getHeader("user-agent"));

         LOGGER.debug("{} {} {}{} <{}> <{}> <{}> <{}> <{}>",
            request.getId(),
            request.getMethod(),
            request.getRequestURI(),
            request.readQueryString(),
            request.getHeader("Accept"),
            request.getRemoteAddr(),
            request.getContentType(),
            request.getHeader("user-agent"),
            getRequestBody(request));

      } catch (Exception ex) {
         LOGGER.error("{} logRequest failed because of Exception <{}>",
            request.getId(), ex.getMessage(), ex);
      }
   }

   private void logResponse(BufferedResponseWrapper response, long time) {
      try {
         LOGGER.info("{} {}ms HTTP STATUS <{}> <{}>", response.getId(),
            time, response.getHttpStatus(), response.getContentType());

         LOGGER.debug("{} {}ms HTTP STATUS <{}> <{}> <{}>", response.getId(),
            time, response.getHttpStatus(), response.getContentType(),
            getResponseContent(response));

      } catch (Exception ex) {
         LOGGER.error("{} logResponse failed because of Exception <{}>",
            response.getId(), ex.getMessage(), ex);
      }
   }

   private String getRequestBody(BufferedRequestWrapper request)
      throws IOException {
      return request.getRequestBody();
   }

   private String getResponseContent(BufferedResponseWrapper response) {
      return response.getContent();
   }

   private void handle(ServletRequest request, ServletResponse response, FilterChain chain, long requestId)
      throws IOException,
             ServletException {
      long start = 0;
      String url = ((HttpServletRequest) request).getRequestURL().toString();

      if (url.contains("/api/")
         && !url.contains("/file")) {

         BufferedRequestWrapper bufferedReqest = new BufferedRequestWrapper(
            requestId, (HttpServletRequest) request);
         BufferedResponseWrapper bufferedResponse = new BufferedResponseWrapper(
            requestId, (HttpServletResponse) response);
         logRequest(bufferedReqest);
         try {
            start = System.currentTimeMillis();
            chain.doFilter(bufferedReqest, bufferedResponse);
         } catch (Exception e) {
            LOGGER.warn("Exception occures in doFilter <{}>", e.getMessage());
            throw e;
         } finally {
            long end = System.currentTimeMillis();
            logResponse(bufferedResponse, end - start);
         }
      } else {
         chain.doFilter(request, response);
      }
   }

}
