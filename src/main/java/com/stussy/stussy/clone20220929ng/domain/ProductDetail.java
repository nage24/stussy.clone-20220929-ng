package com.stussy.stussy.clone20220929ng.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDetail {
    private int group_id;
    private String name;
    private int price;
    private String info_simple;
    private String info_detail;
    private String info_option;
    private String info_management;
    private String info_shipping;
    private String color;
    private String size;
    private List<ProductImgFile> productImgFiles;
}
