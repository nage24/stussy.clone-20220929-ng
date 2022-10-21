package com.stussy.stussy.clone20220929ng.repository.admin;

import com.stussy.stussy.clone20220929ng.domain.Product;
import com.stussy.stussy.clone20220929ng.domain.ProductImgFile;
import com.stussy.stussy.clone20220929ng.dto.admin.ProductAdditionReqDto;
import com.stussy.stussy.clone20220929ng.dto.admin.ProductListRespDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductRepository {

    public int saveProduct(Product product) throws Exception;
    public int saveImgFiles(List<ProductImgFile> product_img_files) throws Exception;

    public List<Product> getProductList(Map<String, Object> map) throws Exception;
    public int setProduct(Product product) throws Exception;
    public int deleteImgFiles(Map<String, Object> map) throws Exception;
    public List<ProductImgFile> getProductImgList(int productId) throws Exception;
    public int deleteProduct(int productId) throws Exception;


}
