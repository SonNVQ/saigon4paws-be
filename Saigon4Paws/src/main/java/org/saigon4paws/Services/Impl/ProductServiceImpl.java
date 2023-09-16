package org.saigon4paws.Services.Impl;

import org.saigon4paws.DTO.ProductDTO;
import org.saigon4paws.Models.Product;
import org.saigon4paws.Models.ProductType;
import org.saigon4paws.Models.ReliefGroup;
import org.saigon4paws.Repositories.ProductRepository;
import org.saigon4paws.Services.FileService;
import org.saigon4paws.Services.ProductService;
import org.saigon4paws.Services.ProductTypeService;
import org.saigon4paws.Services.ReliefGroupService;
import org.saigon4paws.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ReliefGroupService reliefGroupService;

    @Autowired
    private FileService fileService;

    @Value("${upload.product-photo.dir}")
    private String productPhotoDir;

    @Override
    public Page<Product> findAll(int page, int size) {
        if (page < 0)
            page = Constants.DEFAULT_PAGE_NUMBER;
        if (size < 0 || size > Constants.MAX_PAGE_SIZE)
            size = Constants.DEFAULT_PAGE_SIZE;
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getProductById(Long id) {
        if (id == null)
            return null;
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public ProductDTO getProductDTOById(Long id) {
        Product product = getProductById(id);
        if (product == null)
            return null;
        return getProductDTO(product);
    }

    private ProductDTO getProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .avatarUrl(product.getAvatarUrl())
                .productTypeId(product.getProductType().getId())
                .reliefGroupId(product.getReliefGroup().getId())
                .build();
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        ProductType productType = productTypeService.getProductTypeById(productDTO.getProductTypeId());
        ReliefGroup reliefGroup = reliefGroupService.getReliefGroupById(productDTO.getReliefGroupId());
        Product product = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .avatarUrl(productDTO.getAvatarUrl())
                .productType(productType)
                .reliefGroup(reliefGroup)
                .build();
        Product savedProduct = productRepository.save(product);
        return getProductDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) throws Exception {
        Product product = getProductById(id);
        if (product == null)
            throw new Exception("Product not found!");
        ProductType productType = productTypeService.getProductTypeById(productDTO.getProductTypeId());
        ReliefGroup reliefGroup = reliefGroupService.getReliefGroupById(productDTO.getReliefGroupId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        if (productDTO.getAvatarUrl() != null)
            product.setAvatarUrl(productDTO.getAvatarUrl());
        product.setProductType(productType);
        product.setReliefGroup(reliefGroup);
        Product savedProduct = productRepository.save(product);
        return getProductDTO(savedProduct);
    }

    @Override
    public void deleteProduct(Long id) throws Exception {
        Product product = getProductById(id);
        if (product == null)
            throw new Exception("Product not found!");
        productRepository.delete(product);
    }

    @Override
    public String uploadProductPhoto(MultipartFile avatar) throws Exception {
        return fileService.saveImageFromMultipartFile(productPhotoDir, avatar);
    }
}
