package org.oyach.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

/**
 * description
 *
 * @author oyach
 * @since 0.0.1
 */
public class SecurityUtils {

    public static void runAs(String username, String password, String... roles) {

        Assert.notNull(username, "Username must not be null!");
        Assert.notNull(password, "Password must not be null!");

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(username, password, AuthorityUtils.createAuthorityList(roles)));
    }
}
