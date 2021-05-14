package kr.twww.mrs.preprocess.webquery;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
@RestControllerAdvice
public class practiceControllerAdvice {

    //모든 Controller 에서 일어나는 Exception 에 대해 전역적으로 예외처리
    @ExceptionHandler(value = Exception.class)
    public String handleDemoExceptionForGlobal(Exception e) {
        return ("ExceptionHandler Invoked:\n" + e.getMessage());
    }
}
