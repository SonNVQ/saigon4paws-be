package org.saigon4paws.Controllers.Guest;

import org.saigon4paws.DTO.AdopterDTO;
import org.saigon4paws.Models.Product;
import org.saigon4paws.Services.ProductService;
import org.saigon4paws.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class GuestProductController {
    @Autowired
    private ProductService productService;
    
    @RequestMapping(value = {"/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String productIndex() {
        return "redirect:/product";
    }

    @RequestMapping(value = {""}, method = {RequestMethod.GET, RequestMethod.POST})
    public String productIndex(@RequestParam(value = "page", required = false) Integer pageNo,
                           @RequestParam(value = "size", required = false) Integer pageSize,
                           Model model) {
        if (pageNo == null || pageNo < 1)
            pageNo = 1;
        if (pageSize == null || pageSize < 1)
            pageSize = Constants.DEFAULT_PRODUCT_PAGE_SIZE;
        Page<Product> productPage = productService.findAll(pageNo - 1, pageSize);
        model.addAttribute("page", productPage);
        model.addAttribute("products", productPage.getContent());
        return "guest/product/index";
    }

    @RequestMapping(value = {"/detail"}, method = {RequestMethod.GET})
    public String productDetail(@RequestParam(value = "id", required = true) Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "guest/product/product-info";
    }

}
