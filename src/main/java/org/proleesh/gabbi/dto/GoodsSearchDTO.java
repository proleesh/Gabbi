package org.proleesh.gabbi.dto;

import lombok.Getter;
import lombok.Setter;
import org.proleesh.gabbi.constant.GoodsSellStatus;

/**
 * @author sung-hyuklee
 * date: 2024.7.3
 * 검색용 DTO
 */
@Getter
@Setter
public class GoodsSearchDTO {

    private String searchDateType;

    private GoodsSellStatus searchSellStatus;

    private String searchBy;

    private String searchQuery = "";
}
