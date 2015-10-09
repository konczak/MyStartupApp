package pl.konczak.mystartupapp.web.filter;

import java.io.ByteArrayInputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

class BufferedServletInputStream
   extends ServletInputStream {

   private final ByteArrayInputStream bais;

   BufferedServletInputStream(ByteArrayInputStream bais) {
      this.bais = bais;
   }

   @Override
   public int available() {
      return this.bais.available();
   }

   @Override
   public int read() {
      return this.bais.read();
   }

   @Override
   public int read(byte[] buf, int off, int len) {
      return this.bais.read(buf, off, len);
   }

   @Override
   public boolean isFinished() {
      return bais.available() == 0;
   }

   @Override
   public boolean isReady() {
      return true;
   }

   @Override
   public void setReadListener(ReadListener rl) {
      throw new UnsupportedOperationException("Not supported yet.");
   }
}
