package com.stussy.stussy.clone20220929ng.controller.api;

import com.stussy.stussy.clone20220929ng.dto.CMRespDto;
import com.stussy.stussy.clone20220929ng.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShopApi {

    private final ShopService shopService;

    @GetMapping("/collections/{category}")
    public ResponseEntity<?> getCollection(@PathVariable  String category, int page) throws Exception {

        return ResponseEntity.ok(new CMRespDto<>(1, "Successfully get collection", shopService.getCollections(category, page)));
    }

    @GetMapping("/products/{groupId}")
    public ResponseEntity<?> getProduct(@PathVariable int groupId) throws Exception {
        return ResponseEntity.ok(new CMRespDto<>(1, "Successfully get product", shopService.getProductDetails(groupId)));
    }


}
