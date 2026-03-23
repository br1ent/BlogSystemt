package com.brent.backendblogsys.controller.article;

import com.brent.backendblogsys.pojo.Article;
import com.brent.backendblogsys.pojo.result.Result;
import com.brent.backendblogsys.service.article.AddArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/article")
public class AddArticleController {
    @Autowired
    private AddArticleService addArticleService;

    @PostMapping("/add")
    public Result<String> add(@RequestBody Article article) {
        log.info("收到添加文章请求：{}", article);
        return addArticleService.add(article);
    }
}
