package org.proleesh.gabbi.dto;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.proleesh.gabbi.entity.GoodsImg;

@Getter
@Setter
public class GoodsImgDTO {
    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn;
    private static ModelMapper modelMapper = new ModelMapper();

    public static GoodsImgDTO of(GoodsImg goodsImg){
        return modelMapper.map(goodsImg, GoodsImgDTO.class);
    }
}
