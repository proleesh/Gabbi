package org.proleesh.gabbi.repository;

import org.proleesh.gabbi.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author sung-hyuklee
 * data: 2024.6.24
 * DAO(Data Access Object) 역할를 하는 Goods(상품)의 repository
 */
public interface GoodsRepository extends JpaRepository<Goods, Long>, QuerydslPredicateExecutor<Goods>, GoodsRepositoryCustom {

    // 상품 이름을 찾는 쿼리 메소드
    List<Goods> findByGoodsName(String goodsName);

    // 현재 상품값보다 작은 상품 데이터를 조회하는 쿼리 메소드
    List<Goods> findByGoodsPriceLessThan(Integer goodsPrice);

    // 현재 상품의 가격이 높은 순으로 조회하는 쿼리 메소도
    List<Goods> findByGoodsPriceLessThanOrderByGoodsPriceDesc(Integer goodsPrice);

    /**
     * [상품 데이터를 조회]
     * 상품 상세 설명을 파라미터로 받아 해당 내용을 상품 상세 설명에 포함하고 있는 데이터를 조회하며,
     * 정렬 순서는 가격이 높은 순으로 조회한다.
     * @param goodsDetail
     * @return
     */
    @Query("select g from Goods g where g.goodsDetail like %:goods_detail% order by g.goodsPrice desc")
    List<Goods> findByGoodsDetail(@Param("goods_detail") String goodsDetail);


    @Query(value = "select * from Goods g where g.goods_detail like %:goods_detail% order by g.goods_price desc", nativeQuery = true)
    List<Goods> findByItemDetailByNative(@Param("goods_detail") String itemDetail);



}
