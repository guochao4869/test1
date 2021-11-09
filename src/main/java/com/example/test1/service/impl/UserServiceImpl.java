package com.example.test1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.test1.common.CommonEnum;
import com.example.test1.mapper.UserMapper;
import com.example.test1.pojo.Result;
import com.example.test1.pojo.User;
import com.example.test1.service.UserService;
import com.example.test1.utils.JwtUtil;
import org.apache.commons.lang.time.DateUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author gc
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Result login(User user) {
        User on = this.getOne(new QueryWrapper<User>().eq("username", user.getUsername())
                .eq("password", user.getPassword()));
        if (ObjectUtils.isEmpty(on)) {
            return new Result(false, "无", CommonEnum.ERROR);
        }
        // 生成jwt令牌 id,主题,失效时间，默认一个小时
        System.out.println(JSONObject.valueToString(on));
        String jwt = JwtUtil.createJWT(on.getId(), on.getUsername(), null);
        // 时间为1小时
        stringRedisTemplate.opsForValue().set(jwt, jwt, 60, TimeUnit.MINUTES);
        return new Result(true, "成功", jwt, CommonEnum.OK);
    }

    @Override
    public Result getUser() {
        return new Result(true, "成功", this.baseMapper.selectList(null), CommonEnum.OK);
    }

    @Override
    public Result getOneUser(String username) {
        User on = this.getOne(new QueryWrapper<User>().eq("username", username));
        if (ObjectUtils.isEmpty(on)) {
            return new Result(false, "无",null, CommonEnum.ERROR);
        }
        return new Result(true, "成功", on, CommonEnum.OK);
    }

}
