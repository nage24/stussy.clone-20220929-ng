package com.stussy.stussy.clone20220929ng.controller.admin.api;

import com.stussy.stussy.clone20220929ng.aop.annotation.LogAspect;
import com.stussy.stussy.clone20220929ng.aop.annotation.ValidAspect;
import com.stussy.stussy.clone20220929ng.dto.CMRespDto;
import com.stussy.stussy.clone20220929ng.dto.admin.ProductAdditionReqDto;
import com.stussy.stussy.clone20220929ng.dto.validation.ValidationSequence;
import com.stussy.stussy.clone20220929ng.service.admin.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/admin")
@RestController
public class ProductApi {

    @ValidAspect
    @LogAspect
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@Validated(ValidationSequence.class)ProductAdditionReqDto productAdditionReqDto, BindingResult bindingResult) {


        return ResponseEntity.ok().body(new CMRespDto<>(1, "Successfully added", null));
    }

}
