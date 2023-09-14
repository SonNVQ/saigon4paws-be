package org.saigon4paws.Controllers.Manager;


import jakarta.validation.Valid;
import org.saigon4paws.DTO.ArticleDTO;
import org.saigon4paws.Models.Article;
import org.saigon4paws.Models.ArticleCategory;
import org.saigon4paws.Models.ReliefGroup;
import org.saigon4paws.Models.SponsorshipInformation;
import org.saigon4paws.Services.ArticleCategoryService;
import org.saigon4paws.Services.ArticleService;
import org.saigon4paws.Services.ReliefGroupService;
import org.saigon4paws.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/manager/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Autowired
    private ReliefGroupService reliefGroupService;

    @RequestMapping(value = {"/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String articleIndex() {
        return "redirect:/manager/article";
    }

    @RequestMapping(value = {""}, method = {RequestMethod.GET, RequestMethod.POST})
    public String articleIndex(@RequestParam(value = "page", required = false) Integer pageNo,
                               @RequestParam(value = "size", required = false) Integer pageSize,
                               Model model) {
        if (pageNo == null || pageNo < 1)
            pageNo = 1;
        if (pageSize == null || pageSize < 1)
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        Page<Article> articlePage = articleService.findAll(pageNo - 1, pageSize);
        model.addAttribute("page", articlePage);
        model.addAttribute("articles", articlePage.getContent());
        return "manager/article/index";
    }

    @GetMapping("/add")
    public String createArticle(Model model) {
        List<ArticleCategory> articleCategories = articleCategoryService.getAllArticleCategories();
        List<ReliefGroup> reliefGroups = reliefGroupService.getAllReliefGroups();
        ArticleDTO articleDTO = new ArticleDTO();
        model.addAttribute("articleCategories", articleCategories);
        model.addAttribute("reliefGroups", reliefGroups);
        model.addAttribute("articleDTO", articleDTO);
        return "manager/article/form";
    }

    @PostMapping("/add")
    public String createArticle(@ModelAttribute("articleDTO") @Valid ArticleDTO articleDTO,
                                @RequestParam("avatar") MultipartFile avatar,
                                BindingResult result,
                                Model model) {
        List<ArticleCategory> articleCategories = articleCategoryService.getAllArticleCategories();
        List<ReliefGroup> reliefGroups = reliefGroupService.getAllReliefGroups();
        model.addAttribute("articleCategories", articleCategories);
        model.addAttribute("reliefGroups", reliefGroups);
        if (result.hasErrors()) {
            return "manager/article/form";
        }
        ArticleDTO savedArticleDTO;
        try {
            String avatarUrl = articleService.uploadArticlePhoto(avatar);
            articleDTO.setAvatarUrl(avatarUrl);
            savedArticleDTO = articleService.createArticle(articleDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "manager/article/form";
        }
        model.addAttribute("articleDTO", savedArticleDTO);
        model.addAttribute("success", "Article created successfully!");
        return "manager/article/form";
    }

    @GetMapping("/edit")
    public String editArticle(@Param("id") Long id, Model model) {
        ArticleDTO articleDTO = articleService.getArticleDTOById(id);
        if (articleDTO == null) {
            return "redirect:/manager/article";
        }
        List<ArticleCategory> articleCategories = articleCategoryService.getAllArticleCategories();
        List<ReliefGroup> reliefGroups = reliefGroupService.getAllReliefGroups();
        model.addAttribute("articleCategories", articleCategories);
        model.addAttribute("reliefGroups", reliefGroups);
        model.addAttribute("articleDTO", articleDTO);
        return "manager/article/form";
    }

    @PostMapping("/edit")
    public String editArticle(@ModelAttribute("articleDTO") @Valid ArticleDTO articleDTO,
                              @RequestParam("avatar") MultipartFile avatar,
                              BindingResult result,
                              Model model) {
        List<ArticleCategory> articleCategories = articleCategoryService.getAllArticleCategories();
        List<ReliefGroup> reliefGroups = reliefGroupService.getAllReliefGroups();
        model.addAttribute("articleCategories", articleCategories);
        model.addAttribute("reliefGroups", reliefGroups);
        if (result.hasErrors()) {
            return "manager/article/form";
        }
        ArticleDTO savedArticleDTO;
        try {
            String avatarUrl = articleService.uploadArticlePhoto(avatar);
            articleDTO.setAvatarUrl(avatarUrl);
            savedArticleDTO = articleService.updateArticle(articleDTO.getId(), articleDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "manager/article/form";
        }
        model.addAttribute("articleDTO", savedArticleDTO);
        model.addAttribute("success", "Article updated successfully!");
        return "manager/article/form";
    }

    @PostMapping("/delete")
    public String deleteArticle(@RequestParam("id") Long id, Model model) {
        try {
            articleService.deleteArticle(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "manager/article/index";
        }
        model.addAttribute("success", "Article deleted successfully!");
        return "go-back";
    }
}
