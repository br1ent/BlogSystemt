package com.brent.backendblogsys.service.impl.article;

import com.brent.backendblogsys.mapper.ArticleMapper;
import com.brent.backendblogsys.pojo.Article;
import com.brent.backendblogsys.pojo.result.Result;
import com.brent.backendblogsys.service.article.AddArticleService;
import com.brent.backendblogsys.service.impl.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AddArticleServiceImpl implements AddArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    private UserDetailsImpl getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 关键逻辑：判断是否真正登录
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            throw new RuntimeException("操作失败：用户未登录或登录已过期");
        }

        return (UserDetailsImpl) authentication.getPrincipal();
    }

    @Override
    public Result<String> add(Article article) {
        UserDetailsImpl currentUser;
        try {
            currentUser = getCurrentUser();
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }

        if (article.getTitle() == null || article.getTitle().isEmpty()) {
            return Result.fail("文章标题不能为空!");
        }

        if (article.getTitle().length() > 255) {
            return Result.fail("文章标题长度不能超过255!");
        }

        if (article.getDescription() == null || article.getDescription().isEmpty()) {
            return Result.fail("文章描述不能为空!");
        }

        if (article.getContent() == null || article.getContent().isEmpty()) {
            return Result.fail("文章内容不能为空!");
        }

        // 设置用户ID和作者名字
        article.setUserId(currentUser.getUser().getId());
        article.setAuthorName(currentUser.getUser().getUsername());

        articleMapper.insert(article);

        return Result.success("文章发布成功！", null);
    }
}
