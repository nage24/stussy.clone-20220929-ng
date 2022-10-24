package com.stussy.stussy.clone20220929ng.dto.admin;

import com.stussy.stussy.clone20220929ng.domain.ProductImgFile;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductListRespDto{

    private int id;
    private String category;

    private String name;
    private int price;
    private String color;
    private String size;

    private String infoSimple;
    private String infoDetail;
    private String infoOption;
    private String infoManagement;
    private String infoShipping;

    private List<ProductImgFile> productImgFiles;

    private int productTotalCount;
}
