package com.brent.backendblogsys.service.article;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brent.backendblogsys.pojo.Article;
import com.brent.backendblogsys.pojo.result.Result;

public interface GetArticleListService {
    Result<IPage<Article>> getList(Integer page, Integer size, String keyword);
}
