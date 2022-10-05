package com.stussy.stussy.clone20220929ng.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

    /*
        메소드 실행 시간을 계산해주는 로직
    */

@Slf4j
@Aspect // Aop 객체로 쓸 것임.
@Component // IoC 컴포넌트 등록해야 함.
public class TimerAop {

    @Pointcut("execution(* com.stussy.stussy.clone20220929ng.controller..*.*(..))")
    private void executionPointCut(){}

    @Around("executionPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 메소드 실행 전 로직
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();


        Object result = joinPoint.proceed(); // 메소드에 리턴이 있는 경우 result 를 가져옴. (메소드의 정보들을 받음. )
        // 메소드 실행 후 로직
        stopWatch.stop();
        // log.info("메소드 실행시간 >>> {}", stopWatch.getTotalTimeSeconds());

        // 어떤 메소드에서 실행 되었는지 띄워줌.
        log.info("class: {}, method: {} >>> {}",
                joinPoint.getSignature().getDeclaringTypeName(),    // 클래스명
                joinPoint.getSignature().getName(),                 // 메소드명
                stopWatch.getTotalTimeSeconds());

        return result;
    }

}
