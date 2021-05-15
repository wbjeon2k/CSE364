package kr.twww.mrs.preprocess.webquery;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scala.Int;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

//https://cheese10yun.github.io/spring-guide-exception/
@RestController
public class QueryErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {

        String errorCode = (String) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        String template = "Error: ";
        template.concat("Code: " + errorCode + " ");
        template.concat("Message: " + errorMessage + "\n");
        return template;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
