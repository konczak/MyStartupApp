package pl.konczak.mystartupapp.web.restapi.logger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class JavaScriptLog {

   private String url;

   private String message;

   private String cause;

   private String stackTrace;

   @ApiModelProperty(value = "URL from which log comes",
      required = true,
      position = 1)
   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   @ApiModelProperty(value = "custom message",
      required = true,
      position = 2)
   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   @ApiModelProperty(value = "cause of error",
      required = false,
      position = 3)
   public String getCause() {
      return cause;
   }

   public void setCause(String cause) {
      this.cause = cause;
   }

   @ApiModelProperty(value = "StackTrace of error",
      required = false,
      position = 4)
   public String getStackTrace() {
      return stackTrace;
   }

   public void setStackTrace(String stackTrace) {
      this.stackTrace = stackTrace;
   }

}
