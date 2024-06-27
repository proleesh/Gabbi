package org.proleesh.gabbi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.entity.GoodsImg;
import org.proleesh.gabbi.repository.GoodsImgRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class GoodsImgService {
    @Value("${goodsImgLocation}")
    private String goodsImgLocation;

    private final GoodsImgRepository goodsImgRepository;

    private final FileService fileService;

    public void saveGoodsImg(GoodsImg goodsImg, MultipartFile goodsImgFile) throws Exception{
        String oriImgName = goodsImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        // 파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(goodsImgLocation, oriImgName, goodsImgFile.getBytes());
            imgUrl = "/images/item/" + imgName;
        }
        // 상품 이미지 정보 저장
        goodsImg.updateItemImg(oriImgName, imgName, imgUrl);
        goodsImgRepository.save(goodsImg);
    }

    public void updateItemImg(Long itemImgId, MultipartFile goodsImgFile) throws Exception {
        if (!goodsImgFile.isEmpty()) {
            GoodsImg savedItemImg = goodsImgRepository.findById(itemImgId)
                    .orElseThrow(EntityNotFoundException::new);

            // 기존 이미지 파일 삭제
            if (!StringUtils.isEmpty(savedItemImg.getImgName())) {
                fileService.deleteFile(goodsImgLocation + "/" + savedItemImg.getImgName());
            }

            String oriImgName = goodsImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(goodsImgLocation, oriImgName, goodsImgFile.getBytes());
            String imgUrl = "/images/item/" + imgName;
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
        }
    }
}
