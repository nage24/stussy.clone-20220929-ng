package com.stussy.stussy.clone20220929ng.controller.api;

import com.stussy.stussy.clone20220929ng.aop.annotation.LogAspect;
import com.stussy.stussy.clone20220929ng.aop.annotation.ValidAspect;
import com.stussy.stussy.clone20220929ng.dto.CMRespDto;
import com.stussy.stussy.clone20220929ng.dto.account.RegisterReqDto;
import com.stussy.stussy.clone20220929ng.dto.validation.ValidationSequence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/api/account")
@RestController
public class AccountApi {

    // @ 유효성검사를 어노테이션으로 처리 하여 AOP 구현하는 것임.

    @ValidAspect
    @LogAspect
    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated(ValidationSequence.class) @RequestBody RegisterReqDto registerReqDto, BindingResult bindingResult) { // 이 DTO 를 가져올 때 Valid 체크를 하겠다. ..
        // @Valid 를 주면 BindingResult 객체가 같이 따라 들어옴.
        // JSON 으로 받으려면  @ RequestBody 가 항상 필요하다 !
        // 유효성 검사 -> @Validated(ValidationSequence.class) 의 순서대로 ... !
        // DtO groups

//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//
//        if(bindingResult.hasErrors()){
//            log.error("유효성 검사 오류 발생");
//
//            Map<String, String> errorMap = new HashMap<String, String>();
//
//            // 우선순위 ?
//            bindingResult.getFieldErrors().forEach(error -> {
//                log.info("Error: 필드명({}), 메세지({})" + error.getField(), error.getDefaultMessage()); // !! -> key , value 의 map 으로 만드는 것이 핵심 !
//                if(!error.getCode().equals("NotBlank")) {
//                    errorMap.put(error.getField(), error.getDefaultMessage());
//                }
//            });
//
//            bindingResult.getFieldErrors().forEach(error -> {
//                log.info("Error: 필드명({}), 메세지({})" + error.getField(), error.getDefaultMessage()); // key , value
//                if(error.getCode().equals("NotBlank")) {
//                    errorMap.put(error.getField(), error.getDefaultMessage());
//                }
//            });
//
//            return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "유효성 검사 실패", errorMap));
//        }
//
//        log.info("{}", registerReqDto);
//
//        stopWatch.stop();
//        log.info("메소드 실행시간 >>> {}", stopWatch.getTotalTimeSeconds());
//
        return ResponseEntity.ok(null);
    }

    public ResponseEntity<?> login() {


        return ResponseEntity.ok(null);
    }

}
