package com.brent.backendblogsys.controller.article;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brent.backendblogsys.pojo.Article;
import com.brent.backendblogsys.pojo.result.Result;
import com.brent.backendblogsys.service.article.GetArticleListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/article")
public class GetArticleListController {
    @Autowired
    private GetArticleListService getArticleListService;

    @GetMapping("/getlist")
    public Result<IPage<Article>> getList(@RequestParam Integer page,
                                          @RequestParam Integer size,
                                          @RequestParam(required = false) String keyword
    ) {
        return getArticleListService.getList(page, size, keyword);
    }
}