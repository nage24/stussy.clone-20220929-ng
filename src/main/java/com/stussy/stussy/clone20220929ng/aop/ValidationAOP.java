package com.stussy.stussy.clone20220929ng.aop;

import com.stussy.stussy.clone20220929ng.dto.CMRespDto;
import com.stussy.stussy.clone20220929ng.dto.account.RegisterReqDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
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

    @Around("annotationValid()")
    public Object around(ProceedingJoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();
        BindingResult bindingResult = null;

        for(int i = 0; i < args.length; i++) {
            log.info("{}", args[i]);

            if (args[i].getClass() == BeanPropertyBindingResult.class) {

                bindingResult = (BindingResult) args[i];
            }

            log.info("확인로그 잘 들어갔나요? > {}", bindingResult);

            if(bindingResult == null) {
                try {
                    return joinPoint.proceed();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }

            if (bindingResult.hasErrors()) {

                log.error("유효성 검사 오류 발생");
                Map<String, String> errorMap = new HashMap<String, String>();

                bindingResult.getFieldErrors().forEach(error -> {
                    log.info("Error: 필드명({}), 메세지({})" + error.getField(), error.getDefaultMessage());
                    if (!error.getCode().equals("NotBlank")) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                });

                bindingResult.getFieldErrors().forEach(error -> {
                    log.info("Error: 필드명({}), 메세지({})" + error.getField(), error.getDefaultMessage());
                    if (error.getCode().equals("NotBlank")) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                });

                return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "유효성 검사 실패", errorMap));
            }
        }

        return ResponseEntity.ok(null);
    }


}
