package org.saigon4paws.Services.Impl;

import org.saigon4paws.DTO.ProductTypeDTO;
import org.saigon4paws.Models.ProductType;
import org.saigon4paws.Repositories.ProductTypeRepository;
import org.saigon4paws.Services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Override
    public void createProductType(ProductTypeDTO productTypeDTO) throws Exception {
        ProductType existingProductType = productTypeRepository.findByName(productTypeDTO.getName());
        if (existingProductType != null) {
            throw new Exception("Product type already exists!");
        }
        ProductType productType = ProductType.builder()
                .name(productTypeDTO.getName())
                .build();
        productTypeRepository.save(productType);
    }

    @Override
    public List<ProductType> getAllProductTypes() {
        return productTypeRepository.findAll();
    }

    @Override
    public ProductType getProductTypeById(Integer id) {
        if (id == null)
            return null;
        return productTypeRepository.findById(id).orElse(null);
    }

    @Override
    public ProductTypeDTO getProductTypeDTOById(Integer id) {
        ProductType productType = getProductTypeById(id);
        if (productType == null)
            return null;
        return ProductTypeDTO.builder()
                .id(productType.getId())
                .name(productType.getName())
                .build();
    }

    @Override
    public ProductTypeDTO updateProductTypeDTOById(Integer id, ProductTypeDTO productTypeDTO) throws Exception {
        ProductType productType = getProductTypeById(id);
        if (productType == null)
            throw new Exception("Product type not found!");
        productType.setName(productTypeDTO.getName());
        productTypeRepository.save(productType);
        return ProductTypeDTO.builder()
                .id(productType.getId())
                .name(productType.getName())
                .build();
    }

    @Override
    public void deleteProductTypeById(Integer id) throws Exception {
        ProductType productType = getProductTypeById(id);
        if (productType == null)
            throw new Exception("Product type not found!");
        productTypeRepository.delete(productType);
    }
}
