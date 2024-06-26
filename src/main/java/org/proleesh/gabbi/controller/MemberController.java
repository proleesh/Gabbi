package org.proleesh.gabbi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.MemberFormDTO;
import org.proleesh.gabbi.entity.Member;
import org.proleesh.gabbi.service.MemberService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDTO", new MemberFormDTO());
        return "member/memberForm";
    }


    @PostMapping("/new")
    public String newMember(@Valid MemberFormDTO memberFormDto,
                            BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "member/memberForm";
        }
        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        }catch(IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        return "redirect:/members/login";
    }

    @GetMapping(value="/login")
    public String loginMember(){
        return "member/memberLoginForm";
    }

    @GetMapping(value="/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 패스워드를 확인해주세요.");
        return "member/memberLoginForm";
    }
}
