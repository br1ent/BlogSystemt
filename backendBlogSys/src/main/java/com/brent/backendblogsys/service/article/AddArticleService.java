package com.brent.backendblogsys.service.article;

import com.brent.backendblogsys.pojo.Article;
import com.brent.backendblogsys.pojo.result.Result;

public interface AddArticleService {
    Result<String> add(Article article);

}
