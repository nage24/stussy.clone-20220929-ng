package com.stussy.stussy.clone20220929ng.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAop {

    @Pointcut("@annotation(com.stussy.stussy.clone20220929ng.aop.annotation.LogAspect)") // annotation 연결
    private void annotationPointCut() {}
    
    @Pointcut("execution(* com.stussy.stussy.clone20220929ng.controller.api.*.*(..))") // 맨 앞의 * 은 return 타입.
    private void executionPointCut() {}

    // @Around("executionPointCut() || annotationPointCut()")
    @Around("annotationPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs(); // argument ; 매개변수 하나하나

        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String[] argNames = codeSignature.getParameterNames();


        StringBuilder argNameString = new StringBuilder();
        StringBuilder argDataString = new StringBuilder();
        for(int i = 0; i < args.length; i++) {
            argNameString.append(argNames[i]);
            argDataString.append(args[i].toString());

            if(i < args.length - 1) {
                argNameString.append(", ");
                argDataString.append(", ");
            }

            // log.info("매개변수명 >> {}", argNames[i]);
            //log.info("매개변수 값 >> {}", args[i]);
        }

        log.info("Method Call -- {}.{}({}) >> {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                argNameString.toString(),
                argDataString.toString()
                ); // 클래스.메소드.(파라미터)

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        // log.info("리턴 데이터 >> {}", result);
        log.info("Method Return -- {}.{}({}) >> {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                argNameString.toString(),
                argDataString.toString()
        ); // 클래스.메소드.(파라미터)

        return result;

//        try {
//            return joinPoint.proceed();
//        } catch (Throwable e) {
//            throw new RuntimeException(e);
//        }
    }

}
