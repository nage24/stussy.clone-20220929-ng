package com.stussy.stussy.clone20220929ng.repository;

import com.stussy.stussy.clone20220929ng.domain.CollectionProduct;
import com.stussy.stussy.clone20220929ng.domain.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShopRepository {

    public List<CollectionProduct> getCollectionList(Map<String, Object> map) throws Exception;

}
