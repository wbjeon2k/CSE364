package kr.twww.mrs.preprocess.webquery;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        //https://cheese10yun.github.io/spring-guide-exception/

        if(status == null){
            return "ErrController: HttpServletRequest null status error\n";
        }

        Integer statusCode = Integer.valueOf(status.toString());

        if(statusCode == HttpStatus.NOT_FOUND.value()) {
            return "404 ERROR: No such URL.\n";
        }
        if(statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()){
            return "405 ERROR: Method Not Allowed.\n";
        }
        if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return "500 ERROR: Internal Server Error.\n";
        }
        if(statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()){
            return "503 ERROR: Service Unavailable.\n";
        }

        //do something like logging
        return "ERROR: Undefined Error\n";
    }

    @RequestMapping("/error/405")
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String handler_405(){
        return "405 ERROR: Method Not Allowed.\n";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
