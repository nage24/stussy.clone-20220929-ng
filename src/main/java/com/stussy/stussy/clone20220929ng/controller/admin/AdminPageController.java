package com.stussy.stussy.clone20220929ng.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class AdminPageController {

    @GetMapping("/product/addition")
    public String loadProductPage() {
        return "admin/product_add";
    }

}
