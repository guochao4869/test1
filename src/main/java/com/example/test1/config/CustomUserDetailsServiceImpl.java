package com.example.test1.config;

import com.example.test1.pojo.Result;
import com.example.test1.pojo.User;
import com.example.test1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gc
 */
@Component
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * 需新建配置类注册一个指定的加密方式Bean，或在下一步Security配置类中注册指定
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Result oneUser = userService.getOneUser(username);
        if (!oneUser.getResult()) {
            throw new UsernameNotFoundException("出异常了");
        }
        User data = (User) oneUser.getData();
        String role = data.getRole();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return new org.springframework.security.core.userdetails.User(
                data.getUsername(),
                passwordEncoder.encode(data.getPassword()),
                        authorities
        );
    }
}
