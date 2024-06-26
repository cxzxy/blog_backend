package com.example.blog.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.dto.UserPasswordDTO;
import com.example.blog.dto.UserRegisterDTO;
import com.example.blog.entity.*;
import com.example.blog.mapper.*;
import com.example.blog.service.UserService;
import com.example.blog.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProfileMapper profileMapper;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private JavaMailSenderImpl javaMailSenderImpl;

    @Autowired
    private AccessControlMapper accessControlMapper;

    @Autowired
    private MoodMapper moodMapper;

    @Autowired
    private SpaceDecorationMapper spaceDecorationMapper;
    /**
     * 用户注册方法
     * @param email 用户邮箱
     * @param password 用户密码
     * @return 注册成功返回true，失败返回false
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String register(UserRegisterDTO user) {
        //检查验证码是否正确
        String code = user.getCode();
        String email = user.getEmail();
        String realCode = (String) redisTemplate.opsForValue().get(email);
        if(!code.equals(realCode)){
            return "-1";
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        User existedUser = userMapper.selectOne(queryWrapper);
        // 检查邮箱是否已被注册
        if(existedUser != null) {
            return "-2";
        }

        // 创建用户
        User newUser = new User();
        Date created_at = new Date();
        Date updated_at = new Date();
        // 密码加密
        newUser.setPassword(PasswordUtil.encode(user.getPassword()));
        newUser.setEmail(email);
        newUser.setCreatedAt(created_at);
        newUser.setUpdatedAt(updated_at);
        userMapper.insert(newUser);
        // 获取用户ID映射成account
        String account = newUser.getUserId()*2+2345+"";
        newUser.setAccount(account);
        userMapper.updateById(newUser);

        // 创建用户档案
        Profile profile = new Profile();
        profile.setUserId(newUser.getUserId());
        // 设置默认用户名
        profile.setUserName("用户" + newUser.getUserId());
        // 设置默认头像
        String avatarUrl = "https://profile-avatar.csdnimg.cn/83e8ac377a404091a3774eb13c377e6c_x__mind.jpg!1";
        profile.setAvatarUrl(avatarUrl);
        profileMapper.insert(profile);

        // 注册成功删除验证码
        redisTemplate.delete(email);
        //创建权限
        AccessControl accessControl = AccessControl.builder().userId(newUser.getUserId()).permissionType("friends_only").build();
        accessControlMapper.insert(accessControl);
        //创建心情
        Mood mood = Mood.builder().userId(newUser.getUserId()).moodContent("悠哉哉").updatedAt(new Date()).build();
        moodMapper.insert(mood);
        //创建空间装扮
        SpaceDecoration spaceDecoration = SpaceDecoration.builder().userId(newUser.getUserId()).themeIndex("0").updatedAt(new Date()).build();
        spaceDecorationMapper.insert(spaceDecoration);
        return account;
    }

    /**
     * 用户登录方法
     * @param email 用户邮箱
     * @param password 用户密码
     * @return 登录成功返回user_id，用户不存在返回-1，密码错误返回-2
     */
    @Override
    public int login(User user) {
            String password = user.getPassword();
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account", user.getAccount());
            user = userMapper.selectOne(queryWrapper);
            if(user == null) {
                return -1;
            } else {
                String encodedPassword = user.getPassword();
                if(PasswordUtil.matches(password, encodedPassword)) {
                    return user.getUserId();
                } else {
                    return -2;
                }
            }

    }

    /**
     * 用户注销方法
     * @param userId 用户ID
     * @return 注销成功返回true，失败返回false
     */
    @Override
    public boolean logout(int userId) {
        User user = userMapper.selectById(userId);
        if(user == null) {
            return false;
        }else{
            userMapper.deleteById(userId);
            return true;
        }
    }

    /**
     * 修改密码
     * @param email 用户邮箱
     * @param Password 旧密码
     * @param newPassword 新密码
     * @return 修改成功返回0，用户不存在返回-1，密码错误返回-2
     */
    @Override
    public int updatePassword(UserPasswordDTO userPasswordDTO) {
        String password = userPasswordDTO.getPassword();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", userPasswordDTO.getEmail());
        User user = userMapper.selectOne(queryWrapper);
        if(user == null) {
            return -1;
        } else {
            String encodedPassword = user.getPassword();
            if(PasswordUtil.matches(password, encodedPassword)) {
                user.setPassword(PasswordUtil.encode(userPasswordDTO.getNewPassword()));
                userMapper.updateById(user);
                return 0;
            } else {
                return -2;
            }
        }
    }

    /**
     * 发送验证码
     * @param email 用户邮箱
     * @return 发送成功返回0
     */
    @Override
    public int sendCode(String email) {
        try {
            //创建一个邮件
            SimpleMailMessage message = new SimpleMailMessage();
            //设置邮件的标题
            message.setSubject("欢迎注册博客系统");
            //设置发送人
            message.setFrom("2460456193@qq.com");
            //设置接收人
            message.setTo(email);
            //生成6位随机验证码
            String code = RandomUtil.randomNumbers(6);

            //验证码存入redis，设置过期时间为5分钟
            boolean result = redisTemplate.opsForValue().setIfAbsent(email, code, 5, TimeUnit.MINUTES);

            //设置邮件的内容
            String content = "【验证码】您的验证码为：" + code + " 。 验证码五分钟内有效，逾期作废。\n\n\n" +
                    "------------------------------\n\n\n" ;
            message.setText(content);
            //发送邮件
            javaMailSenderImpl.setPassword("kczwrlkxpmygecah");
            javaMailSenderImpl.setHost("smtp.qq.com");
            javaMailSenderImpl.setUsername("2460456193@qq.com");
            javaMailSenderImpl.setPort(587);
            javaMailSenderImpl.send(message);

            return 0;
        } catch (MailException e) {
            throw new RuntimeException("邮件发送失败");
        }

    }
}
