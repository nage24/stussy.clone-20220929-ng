package com.stussy.stussy.clone20220929ng.aop;

import com.stussy.stussy.clone20220929ng.dto.CMRespDto;
import com.stussy.stussy.clone20220929ng.dto.account.RegisterReqDto;
import com.stussy.stussy.clone20220929ng.exception.CustomValidationException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class ValidationAOP {

    @Pointcut("@annotation(com.stussy.stussy.clone20220929ng.aop.annotation.ValidAspect)")
    private void annotationValid() {}

   // @Around("annotationValid()")
    @Before("annotationValid()") // Around / Before ; ProceedingJoinPoint 를 가지고 오고, 일반 JoinPoint 도 있음. -> 전후처리가 없음. 무조건 메소드 실행전에 실행 -> Proceed 가 없다.
    public void before(JoinPoint joinPoint) throws Throwable {
    // public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        BeanPropertyBindingResult bindingResult = null;

        log.info("args 배열 0번째 >>> {}", args[0]);
        log.info("args 배열 1번째 >>>{}", args[1]);

        for (Object arg : args) {
            if (arg.getClass() == BeanPropertyBindingResult.class) {
                bindingResult = (BeanPropertyBindingResult) arg;
                break;
            }
        }

        if (bindingResult == null) {
            // return joinPoint.proceed();
        }

        if (bindingResult.hasErrors()) {
            log.info("확인로그 잘 들어갔나요? > {}", bindingResult);
            log.error("유효성 검사 오류 발생");

            Map<String, String> errorMap = new HashMap<String, String>();

            bindingResult.getFieldErrors().forEach(error -> {
                log.info("Error: 필드명({}), 메세지({})" + error.getField(), error.getDefaultMessage());
                errorMap.put(error.getField(), error.getDefaultMessage());
            });

            // throw new CustomValidationException("Validation failed", errorMap); // 예외 발생

            // return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "유효성 검사 실패", errorMap));
        }
        // return joinPoint.proceed();
    }

    @AfterReturning(value = "annotationValid()", returning = "returnObj") // return object ; api 에서 유효성 검사를 @Before 에서 실행함.
    // 실행 후에 AfterReturning 이 있어야지만 메소드로 다시 돌아가게 됨.
    public void afterReturning(JoinPoint joinPoint, Object returnObj) {
        log.info("Validation success: {}", returnObj);
    }

}






//        for(int i = 0; i < args.length; i++) {
//            log.info("{}", args[i]);
//
//            if (args[i].getClass() == BeanPropertyBindingResult.class) {
//
//                bindingResult = (BeanPropertyBindingResult) args[i];
//            }
//
//            log.info("확인로그 잘 들어갔나요? > {}", bindingResult);
//
//            if(bindingResult == null) {
//                try {
//                    return joinPoint.proceed();
//                } catch (Throwable e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//            if (bindingResult.hasErrors()) {
//
//                log.error("유효성 검사 오류 발생");
//                Map<String, String> errorMap = new HashMap<String, String>();
//
//                bindingResult.getFieldErrors().forEach(error -> {
//                    log.info("Error: 필드명({}), 메세지({})" + error.getField(), error.getDefaultMessage());
//                    if (!error.getCode().equals("NotBlank")) {
//                        errorMap.put(error.getField(), error.getDefaultMessage());
//                    }
//                });
//
//                bindingResult.getFieldErrors().forEach(error -> {
//                    log.info("Error: 필드명({}), 메세지({})" + error.getField(), error.getDefaultMessage());
//                    if (error.getCode().equals("NotBlank")) {
//                        errorMap.put(error.getField(), error.getDefaultMessage());
//                    }
//                });
//
//                return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "유효성 검사 실패", errorMap));
//            }
//        }
//
//        return ResponseEntity.ok(null);
//    }
