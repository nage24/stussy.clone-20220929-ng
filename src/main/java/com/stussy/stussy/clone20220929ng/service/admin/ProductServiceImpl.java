package com.stussy.stussy.clone20220929ng.service.admin;

import com.stussy.stussy.clone20220929ng.domain.Product;
import com.stussy.stussy.clone20220929ng.domain.ProductImgFile;
import com.stussy.stussy.clone20220929ng.dto.admin.ProductAdditionReqDto;
import com.stussy.stussy.clone20220929ng.exception.CustomInternalServerErrorException;
import com.stussy.stussy.clone20220929ng.repository.admin.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Value("${file.path}")
    private String filePath;

    @Override
    public boolean addProduct(ProductAdditionReqDto productAdditionReqDto) throws Exception {
        int resultCount = 0;

        List<MultipartFile> files = productAdditionReqDto.getFiles();
        List<ProductImgFile> productImgFiles = null; // new ArrayList<ProductImgFile>();

        Product product = productAdditionReqDto.toProductEntity();
        resultCount = productRepository.addProduct(product);

        if(files != null) {
            int productId = product.getId();
            productImgFiles = getProductImgFiles(files, productId);
            resultCount += productRepository.saveImgFiles(productImgFiles);
        }

        if(resultCount == 0){
            throw new CustomInternalServerErrorException("상품 등록 실패");
        }

        return resultCount != 0; // 0 이 아니면 true ; 0(insert 되지 않음) 이면 false
    }

    private List<ProductImgFile> getProductImgFiles(List<MultipartFile> files, int productId) throws Exception {

        List<ProductImgFile> productImgFiles = new ArrayList<ProductImgFile>();

        files.forEach(file -> {
            String originName = file.getOriginalFilename();
            String extention = originName.substring(originName.lastIndexOf(".")); // 확장자명 . 부터 끝까지
            String temp_name = UUID.randomUUID().toString() + extention;

            Path uploadPath = Paths.get(filePath, "/product" + temp_name);

            File f = new File(filePath, "/product");
            if (!f.exists()) {
                f.mkdirs();
            }

            try {
                Files.write(uploadPath, file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ProductImgFile productImgFile = ProductImgFile.builder()
                    .product_id(0)
                    .origin_name(originName)
                    .temp_name(temp_name)
                    .build();

            productImgFiles.add(productImgFile);

        });

        return productImgFiles;
    }

}
