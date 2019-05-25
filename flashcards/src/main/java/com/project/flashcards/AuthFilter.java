package com.project.flashcards;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// extends UsernamePasswordAuthenticationFilter
@Component
public class AuthFilter  extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final String authorization = request.getHeader("authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
          //  throw new ServletException("401 - UNAUTHORIZED");
            HttpServletResponse response = (HttpServletResponse) res;
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }else {
            final Claims claims = Jwts.parser().setSigningKey("123#&*zcvAWEE999").parseClaimsJws(authorization.substring(7))
                    .getBody();
            request.setAttribute("claims", claims);
            chain.doFilter(req, res);
        }
    }

}