package com.stussy.stussy.clone20220929ng.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // Runtime 때 쓰겠다, 그 때 컴파일 하겠다.
@Target({ElementType.TYPE, ElementType.METHOD}) // 어노테이션을 달 수 있는 곳, Type : class , Method : 메소드 ; 클래스, 메소드 위에 달 수 있음.
public @interface LogAspect {


}
