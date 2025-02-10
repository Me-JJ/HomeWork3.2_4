package com.Library.ManagementSys.advices;

import com.Library.ManagementSys.exceptions.ResourceNotFound;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFound exc)
    {
        ApiError ae=new ApiError();

        ae.setMessage(exc.getMessage());

        ae.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ApiResponse<>(ae),ae.getStatus());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception exc)
    {
        ApiError ae=new ApiError();
        ae.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        ae.setMessage("Sorry !!, Server not responding \n"+exc.getLocalizedMessage());

        return new ResponseEntity<>(new ApiResponse<>(ae),ae.getStatus());

    }




    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> InvalidDataInput(MethodArgumentNotValidException manve)
    {
        List<String> err=manve.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ApiError ae=new ApiError();
                ae.setStatus(HttpStatus.BAD_REQUEST);
                ae.setMessage("Input Arguments is not Valid");
                ae.setSubErrors(err);
        return new ResponseEntity<>(new ApiResponse<>(ae),HttpStatus.BAD_REQUEST);
    }
}
