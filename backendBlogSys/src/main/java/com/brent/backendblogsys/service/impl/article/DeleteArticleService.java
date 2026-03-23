package com.brent.backendblogsys.service.impl.article;

import com.brent.backendblogsys.mapper.ArticleMapper;
import com.brent.backendblogsys.pojo.Article;
import com.brent.backendblogsys.pojo.result.Result;
import com.brent.backendblogsys.service.impl.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DeleteArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new RuntimeException("请先登录后再进行删除操作");
        }
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        return loginUser.getUser().getId();
    }

    public Result<String> delete(Integer articleId) {
        Article article = articleMapper.selectById(articleId);

        if (article == null) {
            return Result.fail("文章不存在或已被删除！");
        }

        // 权限校验：只能删除自己的文章
        try {
            Long currentUserId = getCurrentUserId();
            if (!article.getUserId().equals(currentUserId)) {
                return Result.fail("权限不足：你只能删除自己发布的文章！");
            }
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }

        articleMapper.deleteById(articleId);
        return Result.success("文章删除成功", null);
    }
}
