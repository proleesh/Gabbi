package org.proleesh.gabbi.service;

import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.GoodsFormDTO;
import org.proleesh.gabbi.entity.Goods;
import org.proleesh.gabbi.entity.GoodsImg;
import org.proleesh.gabbi.repository.GoodsImgRepository;
import org.proleesh.gabbi.repository.GoodsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GoodsService {
    private final GoodsRepository goodsRepository;
    private final GoodsImgService goodsImgService;
    private final GoodsImgRepository goodsImgRepository;

    public Long saveGoods(GoodsFormDTO goodsFormDTO, List<MultipartFile> goodsImgFileList) throws Exception {
        // 상품 등록
        Goods goods  = goodsFormDTO.createGoods();
        goodsRepository.save(goods);

        // 이미지 등록
        for(int i = 0; i < goodsImgFileList.size(); ++i){
            GoodsImg goodsImg = new GoodsImg();
            goodsImg.setGoods(goods);
            if(i == 0){ goodsImg.setRepImgYn("Y");
            }else { goodsImg.setRepImgYn("N");
            }
            goodsImgService.saveGoodsImg(goodsImg, goodsImgFileList.get(i));
        }

        return goods.getId();

    }
}
