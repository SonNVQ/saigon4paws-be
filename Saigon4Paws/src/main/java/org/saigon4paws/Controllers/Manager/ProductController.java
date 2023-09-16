package org.saigon4paws.Controllers.Manager;

import jakarta.validation.Valid;
import org.saigon4paws.DTO.ProductDTO;
import org.saigon4paws.Models.Product;
import org.saigon4paws.Services.ProductService;
import org.saigon4paws.Services.ProductTypeService;
import org.saigon4paws.Services.ReliefGroupService;
import org.saigon4paws.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/manager/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ReliefGroupService reliefGroupService;


    @RequestMapping(value = {"/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String productIndex() {
        return "redirect:/manager/product";
    }

    @RequestMapping(value = {""}, method = {RequestMethod.GET, RequestMethod.POST})
    public String productIndex(@RequestParam(value = "page", required = false) Integer pageNo,
                           @RequestParam(value = "size", required = false) Integer pageSize,
                           Model model) {
        if (pageNo == null || pageNo < 1)
            pageNo = 1;
        if (pageSize == null || pageSize < 1)
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        Page<Product> productPage = productService.findAll(pageNo - 1, pageSize);
        model.addAttribute("page", productPage);
        model.addAttribute("products", productPage.getContent());
        return "manager/product/index";
    }

    @GetMapping("/add")
    public String productAdd(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("productTypes", productTypeService.getAllProductTypes());
        model.addAttribute("reliefGroups", reliefGroupService.getAllReliefGroups());
        return "manager/product/form";
    }

    @PostMapping("/add")
    public String productAdd(@ModelAttribute("productDTO") @Valid ProductDTO productDTO,
                         @RequestParam("avatar") MultipartFile avatar,
                         BindingResult result,
                         Model model) {
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("productTypes", productTypeService.getAllProductTypes());
        model.addAttribute("reliefGroups", reliefGroupService.getAllReliefGroups());
        if (result.hasErrors()) {
            return "manager/product/form";
        }
        try {
            String savedPhotoUrl = productService.uploadProductPhoto(avatar);
            productDTO.setAvatarUrl(savedPhotoUrl);
            productService.createProduct(productDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "manager/product/form";
        }
        model.addAttribute("success", "Product created successfully!");
        return "manager/product/form";
    }

    @GetMapping("/edit")
    public String productEdit(@RequestParam("id") Long id, Model model) {
        ProductDTO existingProductDTO = productService.getProductDTOById(id);
        if (existingProductDTO == null) {
            model.addAttribute("error", "Product not found!");
            return "manager/product/form";
        }
        model.addAttribute("isEditPage", true);
        model.addAttribute("productDTO", existingProductDTO);
        model.addAttribute("productTypes", productTypeService.getAllProductTypes());
        model.addAttribute("reliefGroups", reliefGroupService.getAllReliefGroups());
        return "manager/product/form";
    }

    @PostMapping("/edit")
    public String productEditSubmit(@ModelAttribute("productDTO") @Valid ProductDTO productDTO,
                                @RequestParam(value = "avatar", required = false) MultipartFile avatar,
                                BindingResult result,
                                Model model) {
        model.addAttribute("isEditPage", true);
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("productTypes", productTypeService.getAllProductTypes());
        model.addAttribute("reliefGroups", reliefGroupService.getAllReliefGroups());
        Product existingProduct = productService.getProductById(productDTO.getId());
        if (existingProduct == null) {
            model.addAttribute("error", "Product not found!");
            return "manager/product/form";
        }
        if (result.hasErrors()) {
            return "manager/product/form";
        }
        try {
            String savedPhotoUrl = productService.uploadProductPhoto(avatar);
            productDTO.setAvatarUrl(savedPhotoUrl);
            ProductDTO updatedProductDTO = productService.updateProduct(productDTO.getId(), productDTO);
            model.addAttribute("productDTO", updatedProductDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "manager/product/form";
        }
        model.addAttribute("success", "Product updated successfully!");
        return "manager/product/form";
    }

    @PostMapping("/delete")
    public String productDelete(@RequestParam("id") Long id, Model model) {
        try {
            productService.deleteProduct(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "forward:/manager/product";
        }
        model.addAttribute("success", "Product deleted successfully!");
        return "go-back";
    }

}
