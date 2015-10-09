package pl.konczak.mystartupapp.web.restapi.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api/javascript",
   method = RequestMethod.POST)
@PreAuthorize("isAuthenticated()")
public class JavaScriptLogApi {

   private static final Logger LOGGER = LoggerFactory.getLogger(JavaScriptLogApi.class);

   @ApiOperation(value = "Registers UI log with info level [internal]",
      position = 1)
   @ApiResponses(value = {
      @ApiResponse(code = 200,
         message = "Log saved"),
      @ApiResponse(code = 400,
         message = "Invalid input")})
   @RequestMapping("/info")
   public void info(@RequestBody JavaScriptLog javaScriptLog) {
      LOGGER.info("Received JavaScript info. URL <{}> message <{}> cause <{}> stackTrace <{}>",
         javaScriptLog.getUrl(),
         javaScriptLog.getMessage(),
         javaScriptLog.getCause(),
         javaScriptLog.getStackTrace());
   }

   @ApiOperation(value = "Registers UI log with warn level [internal]",
      position = 2)
   @ApiResponses(value = {
      @ApiResponse(code = 200,
         message = "Log saved"),
      @ApiResponse(code = 400,
         message = "Invalid input")})
   @RequestMapping("/warn")
   public void warn(@RequestBody JavaScriptLog javaScriptLog) {
      LOGGER.warn("Received JavaScript warn. URL <{}> message <{}> cause <{}> stackTrace <{}>",
         javaScriptLog.getUrl(),
         javaScriptLog.getMessage(),
         javaScriptLog.getCause(),
         javaScriptLog.getStackTrace());
   }

   @ApiOperation(value = "Registers UI log with error level [internal]",
      position = 3)
   @ApiResponses(value = {
      @ApiResponse(code = 200,
         message = "Log saved"),
      @ApiResponse(code = 400,
         message = "Invalid input")})
   @RequestMapping("/error")
   public void error(@RequestBody JavaScriptLog javaScriptLog) {
      LOGGER.error("Received JavaScript error. URL <{}> message <{}> cause <{}> stackTrace <{}>",
         javaScriptLog.getUrl(),
         javaScriptLog.getMessage(),
         javaScriptLog.getCause(),
         javaScriptLog.getStackTrace());
   }

   @ApiOperation(value = "Registers UI log with debug level [internal]",
      position = 4)
   @ApiResponses(value = {
      @ApiResponse(code = 200,
         message = "Log saved"),
      @ApiResponse(code = 400,
         message = "Invalid input")})
   @RequestMapping("/debug")
   public void debug(@RequestBody JavaScriptLog javaScriptLog) {
      LOGGER.debug("Received JavaScript debug. URL <{}> message <{}> cause <{}> stackTrace <{}>",
         javaScriptLog.getUrl(),
         javaScriptLog.getMessage(),
         javaScriptLog.getCause(),
         javaScriptLog.getStackTrace());
   }
}
