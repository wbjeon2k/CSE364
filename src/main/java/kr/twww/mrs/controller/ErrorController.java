package kr.twww.mrs.controller;

import kr.twww.mrs.controller.object.Error;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController
{
    @Override
    public String getErrorPath()
    {
        return null;
    }

    @RequestMapping("/error")
    public Error HandleError( HttpServletRequest request )
    {
        var errorStatusCode = (Integer)request
                .getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        var errorMessage = HttpStatus
                .valueOf(errorStatusCode)
                .getReasonPhrase();

        return new Error(
                errorStatusCode,
                errorMessage
        );
    }
}
