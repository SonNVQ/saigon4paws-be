package org.saigon4paws.Controllers.Manager;

import jakarta.validation.Valid;
import org.saigon4paws.DTO.ArticleCategoryDTO;
import org.saigon4paws.Services.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager/article-category")
public class ArticleCategoryController {
    @Autowired
    private ArticleCategoryService articleCategoryService;
    
    @RequestMapping(value = {"", "/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String articleCategoryIndex(Model model) {
        model.addAttribute("articleCategories", articleCategoryService.getAllArticleCategories());
        return "manager/article-category/index";
    }

    @GetMapping("/add")
    public String articleCategoryAdd(Model model) {
        ArticleCategoryDTO articleCategoryDTO = new ArticleCategoryDTO();
        model.addAttribute("articleCategoryDTO", articleCategoryDTO);
        return "manager/article-category/form";
    }

    @PostMapping("/add")
    public String articleCategoryAddSubmit(@ModelAttribute("articleCategoryDTO") @Valid ArticleCategoryDTO articleCategoryDTO,
                                   BindingResult result,
                                   Model model) {
        model.addAttribute("articleCategoryDTO", articleCategoryDTO);
        try {
            articleCategoryService.createArticleCategory(articleCategoryDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "manager/article-category/form";
        }
        model.addAttribute("success", "Article category created successfully!");
        return "manager/article-category/form";
    }

    @GetMapping("/edit")
    public String articleCategoryEdit(@RequestParam("id") Integer id, Model model) {
        ArticleCategoryDTO articleCategoryDTO = articleCategoryService.getArticleCategoryDTOById(id);
        if (articleCategoryDTO == null) {
            model.addAttribute("error", "Article category not found!");
            return "manager/article-category/form";
        }
        model.addAttribute("articleCategoryDTO", articleCategoryDTO);
        return "manager/article-category/form";
    }

    @PostMapping("/edit")
    public String articleCategoryEditSubmit(@ModelAttribute("articleCategoryDTO") @Valid ArticleCategoryDTO articleCategoryDTO,
                                    BindingResult result,
                                    Model model) {
        model.addAttribute("articleCategoryDTO", articleCategoryDTO);
        ArticleCategoryDTO updatedArticleCategoryDTO;
        try {
            updatedArticleCategoryDTO = articleCategoryService.updateArticleCategoryDTOById(articleCategoryDTO.getId(), articleCategoryDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "manager/article-category/form";
        }
        model.addAttribute("articleCategoryDTO", updatedArticleCategoryDTO);
        model.addAttribute("success", "Article category updated successfully!");
        return "manager/article-category/form";
    }

    @PostMapping("/delete")
    public String articleCategoryDelete(@RequestParam("id") Integer id, Model model) {
        try {
            articleCategoryService.deleteArticleCategoryById(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "forward:/manager/article-category/";
        }
        model.addAttribute("success", "Article category deleted successfully!");
        return "go-back";
    }
}
