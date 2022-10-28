package com.stussy.stussy.clone20220929ng.dto.order;

import lombok.Data;

@Data
public class OrderReqDto {
    private int groupId;
    private String productName;
    private int productPrice;
    private String productSize;
    private String productColor;
    private String productImg;
}
