package com.burgerbuilder.backend.Security;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class PublicURI {
    private  static final RequestMatcher PUBLIC_URI= new OrRequestMatcher(
            new AntPathRequestMatcher("/auth/**"),
            new AntPathRequestMatcher("**/reset-password"),
            new AntPathRequestMatcher("**/email-token-verification"),
            new AntPathRequestMatcher("/**", HttpMethod.OPTIONS.name())
    );

    public static RequestMatcher getPublicUri() {
        return PUBLIC_URI;
    }
}
