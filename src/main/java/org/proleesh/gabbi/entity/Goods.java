package org.proleesh.gabbi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.proleesh.gabbi.constant.GoodsSellStatus;
import org.proleesh.gabbi.dto.GoodsFormDTO;

import java.util.List;

/**
 * @author sung-hyuklee
 * data: 2024.6.24
 * 상품 관리 엔터티(도메인)
 */
@Entity
@Table(name="goods")
@Getter
@Setter
@ToString
public class Goods extends BaseEntity {


    @Id
    @Column(name="goods_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 상품의 유일한 식별 코드

    @Column(name="goods_name", nullable = false, length = 50)
    private String goodsName; // 상품의 이름

    @Column(name="goods_price", nullable = false)
    private int goodsPrice; // 상품의 가격

    @Column(name="goods_stock_number", nullable = false)
    private int goodsStockNumber; // 상품의 재고수량

    @Lob
    @Column(name="goods_detail",nullable = false, length=655555555)
    private String goodsDetail; // 상품의 상세 설명

    @Enumerated(EnumType.STRING)
    private GoodsSellStatus goodsSellStatus; // 상품 판매 상태

//    private LocalDateTime registerTime; // 등록 시간
//    private LocalDateTime updateTime; // 수정 시간

    @ManyToMany
    @JoinTable(name = "member_goods",
            joinColumns = @JoinColumn(name="member_id"),
            inverseJoinColumns = @JoinColumn(name="goods_id"))
            private List<Member> member;


    public void updateGoods(GoodsFormDTO goodsFormDTO) {
        this.goodsName = goodsFormDTO.getGoodsName();
        this.goodsPrice = goodsFormDTO.getGoodsPrice();
        this.goodsDetail = goodsFormDTO.getGoodsDetail();
        this.goodsStockNumber = goodsFormDTO.getGoodsStockNumber();
        this.goodsSellStatus = goodsFormDTO.getGoodsSellStatus();
    }


}
