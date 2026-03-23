package com.brent.backendblogsys.service.article;

import com.brent.backendblogsys.pojo.result.Result;

public interface DeleteArticleService {
    Result<String> deleteArticle(Integer articleId);
}
