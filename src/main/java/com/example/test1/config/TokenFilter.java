package com.example.test1.config;

import com.alibaba.fastjson.JSONObject;
import com.example.test1.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * token过滤器
 * @author gc
 */
@Component
@Slf4j
public class TokenFilter extends OncePerRequestFilter {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        Map<String, Object> loginToken = getLoginToken(httpServletRequest);
        if (!ObjectUtils.isEmpty(loginToken)) {
            String username = (String) loginToken.get("username");
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginToken, null, userDetails.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // 每次成功就刷新token
            String token = (String) loginToken.get("token");
            stringRedisTemplate.opsForValue().set(token, token, 60, TimeUnit.SECONDS);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private Map<String, Object> getLoginToken(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)) {
            return null;
        }
        // 去redis中判断是否有token
        String token = stringRedisTemplate.opsForValue().get(authorization);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        Map<String, Object> map = new HashMap<>(16);
        try {
            Object subject = JwtUtil.parseJWT(token).get("sub");
            map.put("token", authorization);
            map.put("username", subject);
        }catch (Exception e) {
            log.error("jwt出错了");
        }
        return map;
    }
}
