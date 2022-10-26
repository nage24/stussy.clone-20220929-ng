package com.stussy.stussy.clone20220929ng.dto.shop;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ProductDetailDto {

    private int groupId;
    private String name;
    private int price;
    private String infoSimple;
    private String infoDetail;
    private String infoOption;
    private String infoManagement;
    private String infoShipping;
    private Map<String, List<String>> options;
    private List<String> imgNames;


}
