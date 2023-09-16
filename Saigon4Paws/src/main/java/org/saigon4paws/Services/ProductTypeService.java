package org.saigon4paws.Services;

import org.saigon4paws.DTO.ProductTypeDTO;
import org.saigon4paws.Models.ProductType;

import java.util.List;

public interface ProductTypeService {
    void createProductType(ProductTypeDTO productTypeDTO) throws Exception;

    List<ProductType> getAllProductTypes();

    ProductType getProductTypeById(Integer id);

    ProductTypeDTO getProductTypeDTOById(Integer id);

    ProductTypeDTO updateProductTypeDTOById(Integer id, ProductTypeDTO productTypeDTO) throws Exception;

    void deleteProductTypeById(Integer id) throws Exception;
}
