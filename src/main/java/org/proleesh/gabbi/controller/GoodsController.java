package org.proleesh.gabbi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.GoodsFormDTO;
import org.proleesh.gabbi.service.GoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    @GetMapping("/admin/goods/new")
    public String goodsForm(Model model){
        model.addAttribute("goodsFormDTO", new GoodsFormDTO());
        return "goods/goodsForm";
    }

    @PostMapping("/admin/goods/new")
    public String goodsNew(@Valid GoodsFormDTO goodsFormDTO,
                           BindingResult bindingResult,
                           Model model,
                           @RequestParam("goodsImgFile") List<MultipartFile> goodsImgFileList){
        if(bindingResult.hasErrors()){
            return "goods/goodsForm";
        }

        if(goodsImgFileList.get(0).isEmpty() && goodsFormDTO.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "goods/goodsForm";
        }

        try{
            goodsService.saveGoods(goodsFormDTO, goodsImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage() + " 라는 에러 발생!!!");
            return "goods/goodsForm";
        }

        return "redirect:/";
    }

}
