package com.brent.backendblogsys.service.impl.article;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.brent.backendblogsys.mapper.ArticleMapper;
import com.brent.backendblogsys.pojo.Article;
import com.brent.backendblogsys.pojo.result.Result;
import com.brent.backendblogsys.service.article.GetArticleListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GetArticleListServiceImpl implements GetArticleListService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Result<IPage<Article>> getList(Integer page, Integer size, String keyword) {
        IPage<Article> articlePage = new Page<>(page, size);
        QueryWrapper<Article> qw = new QueryWrapper<>();

        // 如果关键词不为空，则匹配标题或描述
        if (keyword != null && !keyword.isBlank()) {
            qw.and(wrapper ->
                    wrapper.like("title", keyword)
                            .or()
                            .like("description", keyword)
            );
        }

        qw.orderByDesc("id"); // 保持 ID 倒序
        articlePage = articleMapper.selectPage(articlePage, qw);
        return Result.success("获取成功", articlePage);
    }
}
