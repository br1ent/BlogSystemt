package com.brent.backendblogsys.service.impl.article;

import com.brent.backendblogsys.mapper.ArticleMapper;
import com.brent.backendblogsys.pojo.Article;
import com.brent.backendblogsys.pojo.result.Result;
import com.brent.backendblogsys.service.article.GetArticleDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GetArticleDetailServiceImpl implements GetArticleDetailService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Result<Article> getDetail(Long id) {
        log.info("开始获取文章详情，文章ID: {}", id);
        Article article = articleMapper.selectById(id);
        log.info("查询到的文章: {}", article);
        if (article == null) {
            log.error("文章不存在，文章ID: {}", id);
            return Result.fail("文章不存在");
        }
        log.info("文章详情获取成功: {}", article);
        return Result.success("获取成功", article);
    }
}