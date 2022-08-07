package com.guo.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guo.commonutil.JWTUtils;
import com.guo.commonutil.MD5;
import com.guo.servicebase.exceptionhandler.GuliException;
import com.guo.ucenter.entity.Member;
import com.guo.ucenter.entity.vo.LoginVO;
import com.guo.ucenter.entity.vo.RegisterVo;
import com.guo.ucenter.mapper.MemberMapper;
import com.guo.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author guoguo
 * @since 2022-08-05
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public String login(LoginVO member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败");
        }
        LambdaQueryWrapper<Member> wrapper =  new LambdaQueryWrapper<>();
        wrapper.eq(Member::getMobile,mobile);
        Member one = this.getOne(wrapper);
        if(one==null){
            throw new GuliException(20001,"手机号不存在");
        }
        password= MD5.encrypt(password);
        if(!password.equals(one.getPassword())){
            throw new GuliException(20001,"登录失败");
        }
        String token = JWTUtils.getJwtToken(one.getId(), one.getNickname());
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)||StringUtils.isEmpty(nickname)||StringUtils.isEmpty(code)){
            throw new GuliException(20001,"注册失败");
        }
        //判断验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            throw new GuliException(20001,"验证码错误");
        }
        //手机号不能重复
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count>0){
            throw new GuliException(20001,"该手机号已存在");
        }
        Member member = new Member();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://pic3.zhimg.com/v2-c2c2dd8b7b7cc609c1a81f65308ea30d_xl.jpg");
        baseMapper.insert(member);
    }

    @Override
    public Member getOpenIdMember(String openid) {
        QueryWrapper<Member>wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        return baseMapper.selectOne(wrapper);
    }
}
