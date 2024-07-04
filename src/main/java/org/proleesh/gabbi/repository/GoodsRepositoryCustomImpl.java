package org.proleesh.gabbi.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.proleesh.gabbi.constant.GoodsSellStatus;
import org.proleesh.gabbi.dto.GoodsSearchDTO;
import org.proleesh.gabbi.dto.MainGoodsDTO;
import org.proleesh.gabbi.dto.QMainGoodsDTO;
import org.proleesh.gabbi.entity.Goods;
import org.proleesh.gabbi.entity.QGoods;
import org.proleesh.gabbi.entity.QGoodsImg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author sung-hyuklee
 * date: 2024.7.3
 */
public class GoodsRepositoryCustomImpl implements GoodsRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public GoodsRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchSellStatusEq(GoodsSellStatus goodsSellStatus){
        return goodsSellStatus == null ? null : QGoods.goods.goodsSellStatus.eq(goodsSellStatus);
    }

    /**
     * all: 상품 등록일 전체
     * 1d: 최근 하루 동안 등록된 상품
     * 1w: 최근 일주일 동안 등록된 상품
     * 1m: 최근 한달 동안 등록된 상품
     * 6m: 최근 6개월 동안 등록된 상품
     * @param searchDateType
     * @return
     */
    private BooleanExpression regDtsAfter(String searchDateType){
        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        }else if(StringUtils.equals("1d", searchDateType)){
            dateTime = dateTime.minusDays(1);
        }else if(StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        }else if(StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1);
        }else if(StringUtils.equals("6m", searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }
        return QGoods.goods.registerTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("goodsName", searchBy)){
            return QGoods.goods.goodsName.like("%" + searchQuery + "%");
        }else if(StringUtils.equals("createdBy", searchBy)){
            return QGoods.goods.createdBy.like("%" + searchQuery + "%");
        }
        return null;
    }

    @Override
    public Page<Goods> getAdminGoodsPage(GoodsSearchDTO goodsSearchDTO, Pageable pageable) {
        QueryResults<Goods> results = queryFactory
                .selectFrom(QGoods.goods)
                .where(regDtsAfter(goodsSearchDTO.getSearchDateType()),
                        searchSellStatusEq(goodsSearchDTO.getSearchSellStatus()),
                        searchByLike(goodsSearchDTO.getSearchBy(), goodsSearchDTO.getSearchQuery()))
                .orderBy(QGoods.goods.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Goods> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression goodsNameLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QGoods.goods.goodsName.like("%" + searchQuery + "%");
    }
    @Override
    public Page<MainGoodsDTO> getMainGoodsPage(GoodsSearchDTO goodsSearchDTO, Pageable pageable) {
        QGoods goods = QGoods.goods;
        QGoodsImg goodsImg = QGoodsImg.goodsImg;

        QueryResults<MainGoodsDTO> results = queryFactory
                .select(
                        new QMainGoodsDTO(
                                goods.id,
                                goods.goodsName,
                                goods.goodsDetail,
                                goodsImg.imgUrl,
                                goods.goodsPrice
                        ))
                .from(goodsImg)
                .join(goodsImg.goods, goods)
                .where(goodsImg.repImgYn.eq("Y"))
                .where(goodsNameLike(goodsSearchDTO.getSearchQuery()))
                .orderBy(goods.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainGoodsDTO> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
