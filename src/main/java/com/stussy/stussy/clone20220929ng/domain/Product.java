package com.stussy.stussy.clone20220929ng.domain;

import com.stussy.stussy.clone20220929ng.dto.validation.ValidationGroups;
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
    private String name;
    private String group_id;
    private int price;
    private String color;
    private String size;

    private String info_simple;
    private String info_detail;
    private String info_option;
    private String info_management;
    private String info_shipping;

    private int img_id;
    private List<ProductImgFile> product_img_files;

    private LocalDateTime create_date;
    private LocalDateTime update_date;

}
