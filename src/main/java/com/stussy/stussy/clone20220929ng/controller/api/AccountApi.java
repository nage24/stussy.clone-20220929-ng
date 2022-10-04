package com.stussy.stussy.clone20220929ng.controller.api;

import com.stussy.stussy.clone20220929ng.dto.CMRespDto;
import com.stussy.stussy.clone20220929ng.dto.account.RegisterReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/api/account")
@RestController
public class AccountApi {

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterReqDto registerReqDto, BindingResult bindingResult) { // 이 DTO 를 가져올 때 Valid 체크를 하겠다. ..
        // @Valid 를 주면 BindingResult 객체가 같이 따라 들어옴.

        if(bindingResult.hasErrors()){
            log.error("유효성 검사 오류 발생");

            Map<String, String> errorMap = new HashMap<String, String>();

            // 우선순위 ?
            bindingResult.getFieldErrors().forEach(error -> {
                log.info("Error: 필드명({}), 메세지({})" + error.getField(), error.getDefaultMessage()); // key , value
                if(!error.getCode().equals("NotBlank")) {
                    errorMap.put(error.getField(), error.getDefaultMessage());
                }
            });

            bindingResult.getFieldErrors().forEach(error -> {
                log.info("Error: 필드명({}), 메세지({})" + error.getField(), error.getDefaultMessage()); // key , value
                if(error.getCode().equals("NotBlank")) {
                    errorMap.put(error.getField(), error.getDefaultMessage());
                }
            });

            return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "유효성 검사 실패", errorMap));
        }

        log.info("{}", registerReqDto);
        return ResponseEntity.ok(null);
    }

    public ResponseEntity<?> login() {


        return ResponseEntity.ok(null);
    }

}
