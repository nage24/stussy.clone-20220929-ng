package com.stussy.stussy.clone20220929ng.dto.admin;

import com.stussy.stussy.clone20220929ng.domain.Product;
import com.stussy.stussy.clone20220929ng.validation.validation.ValidationGroups;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ProductModificationReqDto {

    @Min(value = 0, message = "상품 코드는 음수일 수 없습니다")
    private int id;

    @Max(value = 1000000, message = "설정할 수 있는 최대 가격은 100만 원 입니다")
    @Min(value = 0)
    private int price;

    @NotBlank(message = "빈 값일 수 없습니다", groups = ValidationGroups.NotBlankGroup.class)
    private String color;

    @NotBlank(message = "빈 값일 수 없습니다", groups = ValidationGroups.NotBlankGroup.class)
    private String size;


    private String infoSimple;
    private String infoDetail;
    private String infoOption;
    private String infoManagement;
    private String infoShipping;
    private List<String> deleteImgFiles;
    private List<MultipartFile> files;


    public Product toProductEntity() {
        return Product.builder()
                .id(id)
                .price(price)
                .color(color)
                .size(size)
                .info_simple(infoSimple)
                .info_detail(infoDetail)
                .info_option(infoOption)
                .info_management(infoManagement)
                .info_shipping(infoShipping)
                .build();


    }
}
