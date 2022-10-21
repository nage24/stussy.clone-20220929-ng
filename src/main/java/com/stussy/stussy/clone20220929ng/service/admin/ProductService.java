package com.stussy.stussy.clone20220929ng.service.admin;

import com.stussy.stussy.clone20220929ng.domain.Product;
import com.stussy.stussy.clone20220929ng.dto.admin.ProductAdditionReqDto;
import com.stussy.stussy.clone20220929ng.dto.admin.ProductListRespDto;
import com.stussy.stussy.clone20220929ng.dto.admin.ProductModificationReqDto;

import java.util.List;

public interface ProductService {

    public boolean addProduct(ProductAdditionReqDto productAdditionReqDto) throws Exception;
    public List<ProductListRespDto> getProductList(int pageNumber, String category, String searchText) throws Exception;

    public boolean updateProduct(ProductModificationReqDto  productModificationReqDto) throws Exception;
    public boolean deleteProduct(int productId) throws Exception;

}
