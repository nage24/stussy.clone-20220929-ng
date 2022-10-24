package com.stussy.stussy.clone20220929ng.service;

import com.stussy.stussy.clone20220929ng.dto.shop.CollectionListRespDto;

import java.util.List;

public interface ShopService {

    public List<CollectionListRespDto> getCollections(String category, int page) throws Exception;
}
