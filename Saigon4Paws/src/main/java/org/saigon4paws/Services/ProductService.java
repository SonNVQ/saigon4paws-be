package org.saigon4paws.Services;

import org.saigon4paws.DTO.ProductDTO;
import org.saigon4paws.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    Page<Product> findAll(int page, int size);

    Product getProductById(Long id);

    ProductDTO getProductDTOById(Long id);

    ProductDTO createProduct(ProductDTO articleDTO);

    ProductDTO updateProduct(Long id, ProductDTO articleDTO) throws Exception;

    void deleteProduct(Long id) throws Exception;

    String uploadProductPhoto(MultipartFile avatar) throws Exception;
}
