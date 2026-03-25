package com.brent.backendblogsys.controller.article;

import com.brent.backendblogsys.pojo.result.Result;
import com.brent.backendblogsys.service.article.DeleteArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/article")
public class DeleteArticleController {
    @Autowired
    private DeleteArticleService deleteArticleService;

    @PostMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        log.info("收到删除文章请求：{}", id);
        return deleteArticleService.deleteArticle(id);
    }
}
