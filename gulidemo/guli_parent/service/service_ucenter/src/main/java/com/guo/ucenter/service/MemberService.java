package com.guo.ucenter.service;

import com.guo.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guo.ucenter.entity.vo.LoginVO;
import com.guo.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author guoguo
 * @since 2022-08-05
 */
public interface MemberService extends IService<Member> {

    String login(LoginVO member);

    void register(RegisterVo registerVo);

    Member getOpenIdMember(String openid);
}
