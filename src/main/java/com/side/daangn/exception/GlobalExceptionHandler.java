package com.side.daangn.exception;

import com.side.daangn.util.ApiResponse;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.Locale;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableWebMvc
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {

    }

    //중복 데이터 예외
    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> DuplicateEmailException(DuplicateException ex) {
        return new ApiResponse<>(ex.getMessage(), HttpStatus.CONFLICT).toResponseEntity();
    }

    //찾을 수 없는 데이터
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> NotFoundException(NotFoundException ex) {
        return new ApiResponse<>(ex.getMessage(), HttpStatus.CONFLICT).toResponseEntity();
    }


    //로그인 시 비밀번호 틀렸을 때 발생
    @ExceptionHandler(IncorrectPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> incorrectPasswordException(IncorrectPasswordException ex){
        String errorMessage = ex.getMessage();
        return new ApiResponse<>(errorMessage, HttpStatus.UNAUTHORIZED).toResponseEntity();
    }


    //Valid 실패할 때 발생 (requestBody)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> methodArgumentNotValid(MethodArgumentNotValidException ex, Locale locale){
        System.out.println("???????");
            String firstErrorMessage =  ex.getBindingResult().getFieldErrors()
            .stream()
            .sorted((e1, e2) -> e1.getField().compareTo(e2.getField())) // 필드 이름 기준으로 정렬
            .findFirst() // 첫 번째 에러만 선택
            .map(FieldError::getDefaultMessage) // 첫 번째 에러의 메시지만 추출
            .orElse("Unknown error"); // 에러가 없을 때의 기본 메시지

          return new ApiResponse<>(firstErrorMessage, HttpStatus.BAD_REQUEST).toResponseEntity();
    }
    //    //Valid 실패할 때 발생 (requestParam)
    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> HandlerMethodValidationException(HandlerMethodValidationException ex, Locale locale){

        Object[] messageArgs = ex.getDetailMessageArguments();
        String firstMessage = messageArgs.length > 0 ? String.valueOf(messageArgs[0]) : "";

        return new ApiResponse<>(firstMessage, HttpStatus.BAD_REQUEST).toResponseEntity();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> unauthorizedException(UnauthorizedException ex){
        String errorMessage = ex.getMessage();
        return new ApiResponse<>(errorMessage, HttpStatus.UNAUTHORIZED).toResponseEntity();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류가 발생했습니다.");
        System.out.println("ex : " + ex.getClass());
        System.out.println("ex : " + Arrays.toString(ex.getStackTrace()));
        System.out.println("ex : " + ex.getMessage());
        return new ApiResponse<>("서버 내부 오류가 발생했습니다.",HttpStatus.INTERNAL_SERVER_ERROR).toResponseEntity();
    }

}
