package indi.monkey.webapp.dao.taobao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import indi.monkey.webapp.pojo.hibernate.taobao.Model_pic;

public interface TaobaoModelDao extends JpaRepository<Model_pic, Integer> {
/*
	@Query("select model from model_pic model WHERE model.shop_id = :shopId")
	Set<Model_pic> findAllByShopId(@Param("shopId") String shopId);*/
}
