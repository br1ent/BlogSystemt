package com.brent.backendblogsys.service.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.brent.backendblogsys.pojo.Article;

public interface UserArticleService extends IService<Article> {
    IPage<Article> getArticlesByUserIdPage(Long userId, Integer page, Integer size, String keyword);
}
