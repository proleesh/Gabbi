package org.proleesh.gabbi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.GoodsFormDTO;
import org.proleesh.gabbi.dto.GoodsImgDTO;
import org.proleesh.gabbi.dto.GoodsSearchDTO;
import org.proleesh.gabbi.entity.Goods;
import org.proleesh.gabbi.entity.GoodsImg;
import org.proleesh.gabbi.repository.GoodsImgRepository;
import org.proleesh.gabbi.repository.GoodsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sung-hyuklee
 * date: 2024.6.24
 * 상품 서비스 로직 처리
 */
@Service
@Transactional
@RequiredArgsConstructor
public class GoodsService {
    private static final Logger logger = LoggerFactory.getLogger(GoodsService.class);

    private final GoodsRepository goodsRepository;
    private final GoodsImgService goodsImgService;
    private final GoodsImgRepository goodsImgRepository;
    private final FileService fileService;

    public Long saveGoods(GoodsFormDTO goodsFormDTO,
                          List<MultipartFile> goodsImgFileList) throws Exception {
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
    // 상품 정보 가져오기
    @Transactional(readOnly = true)
    public GoodsFormDTO getGoodsDtl(Long goodsId) {
        List<GoodsImg> goodsImgList = goodsImgRepository.findByGoodsIdOrderByIdAsc(goodsId);
        List<GoodsImgDTO> goodsImgDTOList = new ArrayList<>();
        for(GoodsImg goodsImg : goodsImgList){
            GoodsImgDTO goodsImgDTO = GoodsImgDTO.of(goodsImg);
            goodsImgDTOList.add(goodsImgDTO);
        }

        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(EntityNotFoundException::new);
        GoodsFormDTO goodsFormDTO = GoodsFormDTO.of(goods);
        goodsFormDTO.setGoodsImgDTOList(goodsImgDTOList);
        return goodsFormDTO;
    }

    /*
    상품 업데이트 감지
     */
    public Long updateGoods(GoodsFormDTO goodsFormDTO,
                            List<MultipartFile> goodsImgFileList) throws Exception {
        Goods goods = goodsRepository.findById(goodsFormDTO.getId())
                .orElseThrow(EntityNotFoundException::new);
        goods.updateGoods(goodsFormDTO);

        List<Long> goodsImgIds = goodsFormDTO.getGoodsImgIds();

        logger.info("goodsImgFileList size: {}", goodsImgFileList.size());
        logger.info("goodsImgIds size: {}", goodsImgIds.size());


        for(int i = 0; i < goodsImgFileList.size(); ++i){
            goodsImgService.updateGoodsImg(goodsImgIds.get(i),
                    goodsImgFileList.get(i));
        }

        return goods.getId();

    }

    @Transactional(readOnly = true)
    public Page<Goods> getAdminGoodsPage(GoodsSearchDTO goodsSearchDTO, Pageable pageable){
        return goodsRepository.getAdminGoodsPage(goodsSearchDTO, pageable);
    }

}
