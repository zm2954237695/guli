package com.guo.ucenter.controller;


import com.guo.commonutil.JWTUtils;
import com.guo.commonutil.R;
import com.guo.ucenter.entity.Member;
import com.guo.ucenter.entity.vo.LoginVO;
import com.guo.ucenter.entity.vo.RegisterVo;
import com.guo.ucenter.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("login")
    public R login(@RequestBody LoginVO loginVo){
        String token = memberService.login(loginVo);
        return R.success().data("token",token);
    }

    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.success();
    }

    @GetMapping("getMemberInfo")
    public  R getMemberInfo(HttpServletRequest request){
        String id = JWTUtils.getMemberIdByJwtToken(request);
        Member userInfo = memberService.getById(id);
        return R.success().data("userInfo",userInfo);
    }
}
