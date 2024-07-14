package org.proleesh.gabbi.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.GoodsFormDTO;
import org.proleesh.gabbi.dto.GoodsSearchDTO;
import org.proleesh.gabbi.entity.Goods;
import org.proleesh.gabbi.repository.GoodsImgRepository;
import org.proleesh.gabbi.repository.GoodsRepository;
import org.proleesh.gabbi.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
/**
 * @author sung-hyuklee
 * date: 2024.6.27 ~ 7.8
 * Goods Controller
 */
@Controller
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;
    private final GoodsRepository goodsRepository;
    private final GoodsImgRepository goodsImgRepository;

    private final static Logger logger = LoggerFactory.getLogger(GoodsController.class);



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

        return "redirect:/admin/goodsAll";
    }

    @GetMapping("/admin/goods/{goodsId}")
    public String goodsDtl(@PathVariable("goodsId") Long goodsId, Model model){
        try{
            GoodsFormDTO goodsFormDTO = goodsService.getGoodsDtl(goodsId);
            model.addAttribute("goodsFormDTO", goodsFormDTO);
//            logger.info(String.valueOf(goodsFormDTO.getGoodsStockNumber()));
        }catch(EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("goodsFormDTO", new GoodsFormDTO());
            return "goods/goodsForm";
        }
        return "goods/goodsForm";
    }

    @PostMapping("/admin/goods/{goodsId}")
    public String goodsUpdate(@Valid GoodsFormDTO goodsFormDTO,
                              BindingResult bindingResult,
                              @RequestParam("goodsImgFile") List<MultipartFile> goodsImgFileList,
                              Model model){
        if(bindingResult.hasErrors()){
            return "goods/goodsForm";
        }

        if(goodsImgFileList.getFirst().isEmpty() && goodsFormDTO.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "goods/goodsForm";
        }

        try{
            goodsService.updateGoods(goodsFormDTO, goodsImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다." + e.getMessage());
            return "goods/goodsForm";
        }

        return "redirect:/admin/goodsAll";
    }

    @GetMapping(value={"/admin/goodsAll", "/admin/goodsAll/{page}"})
    public String goodsManage(GoodsSearchDTO goodsSearchDTO,
                              @PathVariable("page")Optional<Integer> page,
                              Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get():0, 3);
        Page<Goods> goodsAll = goodsService.getAdminGoodsPage(goodsSearchDTO, pageable);
        model.addAttribute("goodsAll", goodsAll);
        model.addAttribute("goodsSearchDTO", goodsSearchDTO);
        model.addAttribute("maxPage", 5);
        return "goods/goodsMng";
    }

    @PostMapping("/admin/goods/delete/{goodsId}")
    public String goodsDelete(@PathVariable("goodsId") Long id){
        goodsImgRepository.deleteByGoodsId(id);
        goodsRepository.deleteById(id);
        return "redirect:/admin/goodsAll";
    }

    @GetMapping("/goods/{goodsId}")
    public String goodsDetail(Model model, @PathVariable("goodsId") Long goodsId){
        GoodsFormDTO goodsFormDTO = goodsService.getGoodsDtl(goodsId);
        model.addAttribute("goods", goodsFormDTO);
        return "goods/goodsDetail";
    }

}
