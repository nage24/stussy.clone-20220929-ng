package com.stussy.stussy.clone20220929ng.domain;

import com.stussy.stussy.clone20220929ng.dto.shop.CollectionListRespDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CollectionProduct {

    private int groupId;
    private String category;
    private String name;
    private int price;
    private String temp_name;
    private int total_count;


    public CollectionListRespDto toListRespDto() {
        return CollectionListRespDto.builder()
                .group_id(groupId)
                .category(category)
                .name(name)
                .price(price)
                .imgName(temp_name)
                .totalCount(total_count)
                .build();
    }
}
