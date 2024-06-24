package org.proleesh.gabbi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author sung-hyuklee
 * date: 2024.6.24
 * 상품에 관한 Data Transfer Object 역할를하는 클래스
 */
@Getter
@Setter
public class GoodsDTO {
    private Long id;
    private String goodsName;
    private Integer goodsPrice;
    private String goodsDetail;
    private String goodsSellStatCd;
    private LocalDateTime registerTime;
    private LocalDateTime updateTime;



}
