package kr.twww.mrs.controller;

import kr.twww.mrs.controller.object.Error;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController
{
    @ExceptionHandler(value = Exception.class)
    public Error HandleException( Exception exception )
    {
        return new Error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage()
        );
    }
}
