package org.saigon4paws.Controllers.Manager;

import jakarta.validation.Valid;
import org.saigon4paws.DTO.ProductTypeDTO;
import org.saigon4paws.Services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager/product-type")
public class ProductTypeController {
    @Autowired
    private ProductTypeService productTypeService;

    @RequestMapping(value = {"", "/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String productTypeIndex(Model model) {
        model.addAttribute("productTypes", productTypeService.getAllProductTypes());
        return "manager/product-type/index";
    }

    @GetMapping("/add")
    public String productTypeAdd(Model model) {
        ProductTypeDTO productTypeDTO = new ProductTypeDTO();
        model.addAttribute("productTypeDTO", productTypeDTO);
        return "manager/product-type/form";
    }

    @PostMapping("/add")
    public String productTypeAddSubmit(@ModelAttribute("productTypeDTO") @Valid ProductTypeDTO productTypeDTO,
                                      BindingResult result,
                                   Model model) {
        model.addAttribute("productTypeDTO", productTypeDTO);
        try {
            productTypeService.createProductType(productTypeDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "manager/product-type/form";
        }
        model.addAttribute("success", "Product type created successfully!");
        return "manager/product-type/form";
    }

    @GetMapping("/edit")
    public String productTypeEdit(@RequestParam("id") Integer id, Model model) {
        ProductTypeDTO productTypeDTO = productTypeService.getProductTypeDTOById(id);
        if (productTypeDTO == null) {
            model.addAttribute("error", "Product type not found!");
            return "manager/product-type/form";
        }
        model.addAttribute("productTypeDTO", productTypeDTO);
        return "manager/product-type/form";
    }

    @PostMapping("/edit")
    public String productTypeEditSubmit(@ModelAttribute("productTypeDTO") @Valid ProductTypeDTO productTypeDTO,
                                    BindingResult result,
                                    Model model) {
        model.addAttribute("productTypeDTO", productTypeDTO);
        ProductTypeDTO updatedProductTypeDTO;
        try {
            updatedProductTypeDTO = productTypeService.updateProductTypeDTOById(productTypeDTO.getId(), productTypeDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "manager/product-type/form";
        }
        model.addAttribute("productTypeDTO", updatedProductTypeDTO);
        model.addAttribute("success", "Product type updated successfully!");
        return "manager/product-type/form";
    }

    @PostMapping("/delete")
    public String productTypeDelete(@RequestParam("id") Integer id, Model model) {
        try {
            productTypeService.deleteProductTypeById(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "forward:/manager/product-type/";
        }
        model.addAttribute("success", "Product type deleted successfully!");
        return "go-back";
    }

}
