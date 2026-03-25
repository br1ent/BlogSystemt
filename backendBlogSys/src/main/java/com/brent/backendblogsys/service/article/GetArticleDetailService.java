package com.brent.backendblogsys.service.article;

import com.brent.backendblogsys.pojo.Article;
import com.brent.backendblogsys.pojo.result.Result;

public interface GetArticleDetailService {
    Result<Article> getDetail(Long id);
}