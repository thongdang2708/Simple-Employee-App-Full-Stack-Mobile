package com.ltp.backend;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ltp.backend.exception.DepartmentNotFoundWithIdException;
import com.ltp.backend.exception.DepartmentNotFoundWithNameException;
import com.ltp.backend.exception.EmployeeNotFoundWithIdException;
import com.ltp.backend.exception.ErrorResponse;
import com.ltp.backend.exception.ManagerNotFoundWithIdException;
import com.ltp.backend.exception.RoleNotFoundWithIdException;
import com.ltp.backend.exception.SendMailException;
import com.ltp.backend.exception.UnauthorizationException;
import com.ltp.backend.exception.UserExistsException;
import com.ltp.backend.exception.UserNotFoundWithEmailException;
import com.ltp.backend.exception.UserNotFoundWithIdException;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<String> errors = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            errors.add(error.getDefaultMessage());
        }

        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ SendMailException.class })
    public ResponseEntity<Object> handleNotImplemented(RuntimeException ex) {
        ErrorResponse errors = new ErrorResponse(Arrays.asList(ex.getLocalizedMessage()));

        return new ResponseEntity<>(errors, HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler({ UserExistsException.class })
    public ResponseEntity<Object> handleConflictException(RuntimeException ex) {
        ErrorResponse errors = new ErrorResponse(Arrays.asList(ex.getLocalizedMessage()));

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ RoleNotFoundWithIdException.class, UserNotFoundWithIdException.class,
            UserNotFoundWithEmailException.class, DepartmentNotFoundWithIdException.class,
            ManagerNotFoundWithIdException.class, DepartmentNotFoundWithNameException.class,
            EmployeeNotFoundWithIdException.class })
    public ResponseEntity<Object> handleNotFoundException(RuntimeException ex) {
        ErrorResponse errors = new ErrorResponse(Arrays.asList(ex.getLocalizedMessage()));

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleNotAuthorized(RuntimeException ex) {
        ErrorResponse errors = new ErrorResponse(Arrays.asList(ex.getLocalizedMessage()));

        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

}
