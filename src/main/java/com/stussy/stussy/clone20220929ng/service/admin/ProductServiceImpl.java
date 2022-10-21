package com.stussy.stussy.clone20220929ng.service.admin;

import com.stussy.stussy.clone20220929ng.domain.Product;
import com.stussy.stussy.clone20220929ng.domain.ProductImgFile;
import com.stussy.stussy.clone20220929ng.dto.admin.ProductAdditionReqDto;
import com.stussy.stussy.clone20220929ng.dto.admin.ProductListRespDto;
import com.stussy.stussy.clone20220929ng.dto.admin.ProductModificationReqDto;
import com.stussy.stussy.clone20220929ng.exception.CustomInternalServerErrorException;
import com.stussy.stussy.clone20220929ng.repository.admin.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    @Value("${file.path}")
    private String filePath;

    private final ProductRepository productRepository;

    @Override
    public boolean addProduct(ProductAdditionReqDto productAdditionReqDto) throws Exception {
        int resultCount = 0;

        List<MultipartFile> files = productAdditionReqDto.getFiles();
        List<ProductImgFile> productImgFiles = null;

        Product product = productAdditionReqDto.toProductEntity();
        resultCount = productRepository.saveProduct(product);

        if(files != null) {
            int productId = product.getId();
            productImgFiles = getProductImgFiles(files, productId);
            resultCount = productRepository.saveImgFiles(productImgFiles);
        }

        if(resultCount == 0){
            throw new CustomInternalServerErrorException("상품 등록 실패");
        }

        return true;
    }

    private List<ProductImgFile> getProductImgFiles(List<MultipartFile> files, int productId) throws Exception {
        List<ProductImgFile> productImgFiles = new ArrayList<ProductImgFile>();

        files.forEach(file -> {
            String originName = file.getOriginalFilename();
            String extension = originName.substring(originName.lastIndexOf("."));
            String temp_name = UUID.randomUUID().toString() + extension;

            Path uploadPath = Paths.get(filePath + "/product/" + temp_name);

            File f = new File(filePath + "/product");
            if(!f.exists()) {
                f.mkdirs();
            }

            try {
                Files.write(uploadPath, file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ProductImgFile productImgFile = ProductImgFile.builder()
                    .product_id(productId)
                    .origin_name(originName)
                    .temp_name(temp_name)
                    .build();

            productImgFiles.add(productImgFile);
        });

        return productImgFiles;
    }

    @Override
    public List<ProductListRespDto> getProductList(int pageNumber, String category, String searchText) throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("index", (pageNumber - 1) * 10);
        paramsMap.put("category", category);
        paramsMap.put("searchText", searchText);


        List<ProductListRespDto> list = new ArrayList<ProductListRespDto>();
        productRepository.getProductList(paramsMap).forEach(product -> {
            list.add(product.toListRespDto()); // product 를 하나씩 꺼내서, DTO 로 바꾼 후에 리스트에 넣어주는 것임.
        });



        return list;
    }

    @Override
    public boolean updateProduct(ProductModificationReqDto productModificationReqDto) throws Exception {

        boolean status = false;

        // update 이후에 delete 되어야 함.
        int result = productRepository.setProduct(productModificationReqDto.toProductEntity());

        if(result != 0) {
            status = true;
            boolean insertStatus = true;
            boolean deleteStatus = true;


            if(productModificationReqDto.getFiles() != null) {
                insertStatus = insertProductImg(productModificationReqDto.getFiles(), productModificationReqDto.getId());
            }

            if(productModificationReqDto.getDeleteImgFiles() != null) {
                deleteStatus = deleteProductImg(productModificationReqDto.getDeleteImgFiles(), productModificationReqDto.getId());
            }

            status = status && insertStatus && deleteStatus;
            if(status == false) {
                throw new CustomInternalServerErrorException("상품 수정 오류");
            }

        }

        return status;
    }

    private boolean insertProductImg(List<MultipartFile> files, int productId) throws Exception { // insert
        List<ProductImgFile> productImgFiles = getProductImgFiles(files, productId);

        return productRepository.saveImgFiles(productImgFiles) > 0;
    }

    private boolean deleteProductImg(List<String> deleteImgFiles, int productId) throws Exception { // key 값 찾아서 지워줘야함.
        boolean status = false;

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("productId", productId);
        map.put("deleteImgFiles", deleteImgFiles);

        int result = productRepository.deleteImgFiles(map);
        if (result != 0) {
            int deleteSuccessCount = 0;

            deleteImgFiles.forEach(temp_name -> {
                Path uploadPath = Paths.get(filePath + "/product/" + temp_name);

                File file = new File(uploadPath.toUri());
                if(file.exists()) {
                    file.delete();
                }
            });
            status = true;
        }

        return status;
    }

    @Override
    public boolean deleteProduct(int productId) throws Exception {
        List<ProductImgFile> productImgFiles = productRepository.getProductImgList(productId);

        if(productRepository.deleteProduct(productId) > 0) {
            productImgFiles.forEach(productImgFile -> {
                Path uploadPath = Paths.get(filePath + "/product/" + productImgFile.getTemp_name());

                File file = new File(uploadPath.toUri());
                if(file.exists()) {
                    file.delete();
                }
            });
            return true;
        }

        return false;
    }
}
