package org.saigon4paws.Controllers.Guest;

import jakarta.websocket.server.PathParam;
import org.saigon4paws.Models.Article;
import org.saigon4paws.Models.Pet;
import org.saigon4paws.Services.ArticleService;
import org.saigon4paws.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/article")
public class GuestArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = {"/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String petIndex() {
        return "redirect:/article";
    }

    @RequestMapping(value = {""}, method = {RequestMethod.GET, RequestMethod.POST})
    public String petIndex(@RequestParam(value = "page", required = false) Integer pageNo,
                           @RequestParam(value = "size", required = false) Integer pageSize,
                           Model model) {
        if (pageNo == null || pageNo < 1)
            pageNo = 1;
        if (pageSize == null || pageSize < 1)
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        Page<Article> articlePage = articleService.findAll(pageNo - 1, pageSize);
        model.addAttribute("page", articlePage);
        model.addAttribute("articles", articlePage.getContent());
        return "guest/article/index";
    }

    @GetMapping("/content")
    public String articleIndex(@RequestParam("id") Long id, Model model) {
        Article article = articleService.getArticleById(id);
        if (article == null) {
            return "redirect:/article";
        }
        model.addAttribute("article", article);
        return "guest/article/content";
    }
}
