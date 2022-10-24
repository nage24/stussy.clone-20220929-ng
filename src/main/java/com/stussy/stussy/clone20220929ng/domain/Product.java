package com.stussy.stussy.clone20220929ng.domain;

import com.stussy.stussy.clone20220929ng.dto.admin.ProductListRespDto;
import com.stussy.stussy.clone20220929ng.dto.shop.CollectionListRespDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
public class Product {

    private int id;
    private String category;
    private String group_id;
    private String name;
    private int price;
    private String color;
    private String size;

    private String info_simple;
    private String info_detail;
    private String info_option;
    private String info_management;
    private String info_shipping;

    private List<ProductImgFile> product_img_files;

    private int product_total_count;

    private LocalDateTime create_date;
    private LocalDateTime update_date;

    public ProductListRespDto toListRespDto() {

        return ProductListRespDto.builder()
                .id(id)
                .category(category)
                .name(name)
                .price(price)
                .color(color)
                .size(size)
                .infoSimple(info_simple)
                .infoDetail(info_detail)
                .infoOption(info_option)
                .infoManagement(info_management)
                .infoShipping(info_shipping)
                .productImgFiles(product_img_files)
                .productTotalCount(product_total_count)
                .build();
    }

}
