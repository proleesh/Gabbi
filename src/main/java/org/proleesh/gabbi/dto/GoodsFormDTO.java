package org.proleesh.gabbi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.proleesh.gabbi.constant.GoodsSellStatus;
import org.proleesh.gabbi.entity.Goods;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GoodsFormDTO {
    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String goodsName;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer goodsPrice;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String goodsDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    private GoodsSellStatus goodsSellStatus;

    private List<GoodsImgDTO> goodsImgDTOList = new ArrayList<>();

    private List<Long> goodsImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Goods createGoods(){
        return modelMapper.map(this, Goods.class);
    }
    public static GoodsFormDTO of(Goods goods){
        return modelMapper.map(goods, GoodsFormDTO.class);
    }
}
