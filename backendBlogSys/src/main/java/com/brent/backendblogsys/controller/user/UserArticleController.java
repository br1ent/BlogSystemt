package com.brent.backendblogsys.controller.user;


import com.brent.backendblogsys.pojo.result.Result;
import com.brent.backendblogsys.service.impl.user.UserDetailsImpl;
import com.brent.backendblogsys.service.user.UserArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserArticleController {
    @Autowired
    private UserArticleService userArticleService;

    @GetMapping("/user_article")
    public Result<?> getUserArticles(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "4") Integer size,
            @RequestParam(required = false) String keyword
    ) {

        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Long userId = loginUser.getUser().getId();

        return Result.success(userArticleService.getArticlesByUserIdPage(userId, page, size, keyword));
    }
}
