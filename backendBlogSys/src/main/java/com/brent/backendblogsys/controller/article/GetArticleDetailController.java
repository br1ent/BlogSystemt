package com.brent.backendblogsys.controller.article;

import com.brent.backendblogsys.pojo.Article;
import com.brent.backendblogsys.pojo.result.Result;
import com.brent.backendblogsys.service.article.GetArticleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/article")
public class GetArticleDetailController {
    @Autowired
    private GetArticleDetailService getArticleDetailService;

    @GetMapping("/getdetail/{id}")
    public Result<Article> getDetail(@PathVariable Long id) {
        return getArticleDetailService.getDetail(id);
    }
}