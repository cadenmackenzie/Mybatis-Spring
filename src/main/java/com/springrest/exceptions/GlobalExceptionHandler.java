package com.springrest.exceptions;

import com.springrest.model.CustomResponseObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(InvalidRequestException.class)
    public CustomResponseObject APINotFoundExceptionHandler (InvalidRequestException ae){
        CustomResponseObject response = new CustomResponseObject();

        response.setError(ae);
        response.setStatus_code(ae.getStatus_code());
        response.setMessage(ae.getMessage());

        return response;
    }

}
