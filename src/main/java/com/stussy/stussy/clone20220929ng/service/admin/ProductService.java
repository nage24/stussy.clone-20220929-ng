package com.stussy.stussy.clone20220929ng.service.admin;

import com.stussy.stussy.clone20220929ng.domain.Product;
import com.stussy.stussy.clone20220929ng.dto.admin.ProductAdditionReqDto;

import java.util.List;

public interface ProductService {

    public boolean addProduct(ProductAdditionReqDto productAdditionReqDto) throws Exception;
    public List<Product> getProductList(int pageNumber, String category, String searchText) throws Exception;

}
