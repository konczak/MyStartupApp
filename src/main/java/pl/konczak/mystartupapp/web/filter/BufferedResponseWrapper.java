package pl.konczak.mystartupapp.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

class BufferedResponseWrapper
   extends HttpServletResponseWrapper {

   private static final String JSON = "json";

   private final long id;
   private final HttpServletResponse original;
   private TeeServletOutputStream tee;
   private ByteArrayOutputStream bos;
   private int httpStatus;

   BufferedResponseWrapper(long requestId, HttpServletResponse response) {
      super(response);
      this.id = requestId;
      original = response;
   }

   long getId() {
      return id;
   }

   String getContent() {
      if (getContentType() != null
         && getContentType().contains(JSON)
         && bos != null) {
         return bos.toString();
      } else {
         return "Response contentType is not JSON or content is empty";
      }
   }

   @Override
   public PrintWriter getWriter()
      throws IOException {
      return original.getWriter();
   }

   @Override
   public ServletOutputStream getOutputStream()
      throws IOException {
      if (tee == null) {
         bos = new ByteArrayOutputStream();
         tee = new TeeServletOutputStream(original.getOutputStream(), bos);
      }
      return tee;
   }

   @Override
   public String getCharacterEncoding() {
      return original.getCharacterEncoding();
   }

   @Override
   public String getContentType() {
      return original.getContentType();
   }

   @Override
   public void setCharacterEncoding(String charset) {
      original.setCharacterEncoding(charset);
   }

   @Override
   public void setContentLength(int len) {
      original.setContentLength(len);
   }

   @Override
   public void setContentType(String type) {
      original.setContentType(type);
   }

   @Override
   public void setBufferSize(int size) {
      original.setBufferSize(size);
   }

   @Override
   public int getBufferSize() {
      return original.getBufferSize();
   }

   @Override
   public void flushBuffer()
      throws IOException {
      tee.flush();
   }

   @Override
   public void resetBuffer() {
      original.resetBuffer();
   }

   @Override
   public boolean isCommitted() {
      return original.isCommitted();
   }

   @Override
   public void reset() {
      original.reset();
   }

   @Override
   public void setLocale(Locale loc) {
      original.setLocale(loc);
   }

   @Override
   public Locale getLocale() {
      return original.getLocale();
   }

   @Override
   public void addCookie(Cookie cookie) {
      original.addCookie(cookie);
   }

   @Override
   public boolean containsHeader(String name) {
      return original.containsHeader(name);
   }

   @Override
   public String encodeURL(String url) {
      return original.encodeURL(url);
   }

   @Override
   public String encodeRedirectURL(String url) {
      return original.encodeRedirectURL(url);
   }

   @SuppressWarnings("deprecation")
   @Override
   public String encodeUrl(String url) {
      return original.encodeUrl(url);
   }

   @SuppressWarnings("deprecation")
   @Override
   public String encodeRedirectUrl(String url) {
      return original.encodeRedirectUrl(url);
   }

   @Override
   public void sendError(int sc, String msg)
      throws IOException {
      httpStatus = sc;
      original.sendError(sc, msg);
   }

   @Override
   public void sendError(int sc)
      throws IOException {
      httpStatus = sc;
      original.sendError(sc);
   }

   @Override
   public void sendRedirect(String location)
      throws IOException {
      original.sendRedirect(location);
   }

   @Override
   public void setDateHeader(String name, long date) {
      original.setDateHeader(name, date);
   }

   @Override
   public void addDateHeader(String name, long date) {
      original.addDateHeader(name, date);
   }

   @Override
   public void setHeader(String name, String value) {
      original.setHeader(name, value);
   }

   @Override
   public void addHeader(String name, String value) {
      original.addHeader(name, value);
   }

   @Override
   public void setIntHeader(String name, int value) {
      original.setIntHeader(name, value);
   }

   @Override
   public void addIntHeader(String name, int value) {
      original.addIntHeader(name, value);
   }

   @Override
   public void setStatus(int sc) {
      httpStatus = sc;
      original.setStatus(sc);
   }

   @SuppressWarnings("deprecation")
   @Override
   public void setStatus(int sc, String sm) {
      original.setStatus(sc, sm);
   }

   public int getHttpStatus() {
      return httpStatus;
   }

   @Override
   public int getStatus() {
      return original.getStatus();
   }

   @Override
   public String getHeader(String name) {
      return original.getHeader(name);
   }

   @Override
   public Collection<String> getHeaders(String name) {

      return original.getHeaders(name);
   }

   @Override
   public Collection<String> getHeaderNames() {

      return original.getHeaderNames();
   }
}
