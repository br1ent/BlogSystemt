package com.brent.backendblogsys.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brent.backendblogsys.mapper.ArticleMapper;
import com.brent.backendblogsys.pojo.Article;
import com.brent.backendblogsys.service.user.UserArticleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements UserArticleService {
    @Override
    public IPage<Article> getArticlesByUserIdPage(Long userId, Integer page, Integer size, String keyword) {
        Page<Article> articlePage = new Page<>(page, size);

        // 基础查询，根据用户id筛选
        LambdaQueryWrapper<Article> qw = new LambdaQueryWrapper<>();
        qw.eq(Article::getUserId, userId);

        if (StringUtils.hasText(keyword)) {
            qw.and(wrapper ->
                    wrapper.like(Article::getTitle, keyword)
                            .or()
                            .like(Article::getDescription, keyword)
            );
        }

        qw.orderByDesc(Article::getUpdateTime);

        return baseMapper.selectPage(articlePage, qw);
    }
}
