package com.stussy.stussy.clone20220929ng.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/account")
@RestController
public class AccountApi {

    @GetMapping("/register")
    public ResponseEntity<?> register() {

        return ResponseEntity.ok(null);
    }

}
