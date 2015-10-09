package pl.konczak.mystartupapp.web.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.http.MediaType;

class BufferedRequestWrapper
   extends HttpServletRequestWrapper {

   private final long id;
   private ByteArrayInputStream bais = null;
   private ByteArrayOutputStream baos = null;
   private BufferedServletInputStream bsis = null;
   private byte[] buffer = null;

   BufferedRequestWrapper(long requestId, HttpServletRequest req)
      throws IOException {
      super(req);            // Read InputStream and store its content in a buffer.            
      this.id = requestId;
      InputStream is = req.getInputStream();
      this.baos = new ByteArrayOutputStream();
      byte [] buf = new byte[1024];
      int letti;
      while ((letti = is.read(buf)) > 0) {
         this.baos.write(buf, 0, letti);
      }
      this.buffer = this.baos.toByteArray();
   }

   @Override
   public ServletInputStream getInputStream() {
      this.bais = new ByteArrayInputStream(this.buffer);
      this.bsis = new BufferedServletInputStream(this.bais);
      return this.bsis;
   }

   private String readRequestBody()
      throws IOException {
      BufferedReader reader = new BufferedReader(new InputStreamReader(this.getInputStream()));
      String line;
      StringBuilder inputBuffer = new StringBuilder();
      do {
         line = reader.readLine();
         if (line != null) {
            inputBuffer.append(line.trim());
         }
      } while (line != null);
      reader.close();
      return inputBuffer.toString().trim();
   }

   String getRequestBody()
      throws IOException {
      if (getContentType() != null
         && getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
         return readRequestBody();
      } else {
         return "Request contentType is not in JSON or body is empty";
      }
   }

   long getId() {
      return id;
   }

   public String readQueryString() {
      return super.getQueryString() == null ? "" : "?" + super.getQueryString();
   }
}
