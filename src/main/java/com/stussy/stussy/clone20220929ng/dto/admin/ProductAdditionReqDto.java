package com.stussy.stussy.clone20220929ng.dto.admin;

import com.stussy.stussy.clone20220929ng.domain.Product;
import com.stussy.stussy.clone20220929ng.dto.validation.ValidationGroups;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ProductAdditionReqDto { // Validation check

    @NotBlank(message = "빈 값일 수 없습니다", groups = ValidationGroups.NotBlankGroup.class)
    private String category;

    @NotBlank(message = "빈 값일 수 없습니다", groups = ValidationGroups.NotBlankGroup.class)
    private String name;

    @Max(value = 1000000, message = "설정할 수 있는 최대 가격은 100만 원 입니다")
    @Min(value = 0)
    private int price;

    @NotBlank(message = "빈 값일 수 없습니다", groups = ValidationGroups.NotBlankGroup.class)
    private String color;

    @NotBlank(message = "빈 값일 수 없습니다", groups = ValidationGroups.NotBlankGroup.class)
    private String size;


    private String info_simple;
    private String info_detail;
    private String info_option;
    private String info_management;
    private String info_shipping;
    private List<MultipartFile> files;


    public Product toProductEntity() {

        return Product.builder()
                .category(category)
                .name(name)
                .price(price)
                .color(color)
                .size(size)
                .info_simple(info_simple)
                .info_detail(info_detail)
                .info_option(info_option)
                .info_management(info_management)
                .info_shipping(info_shipping)
                .build();
    }

}
