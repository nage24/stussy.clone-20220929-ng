package com.stussy.stussy.clone20220929ng.dto.shop;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CollectionListRespDto {
    private int group_id;
    private String category;
    private String name;
    private int price;
    private String imgName;
}
