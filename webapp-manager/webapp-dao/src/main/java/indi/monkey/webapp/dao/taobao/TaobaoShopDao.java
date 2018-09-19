package indi.monkey.webapp.dao.taobao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import indi.monkey.webapp.pojo.hibernate.taobao.TaobaoShop;

public interface TaobaoShopDao extends JpaRepository<TaobaoShop, Integer> {

	@Query(value = "select nick,shop_id,seller_id,shop_id from taobao_shop where shop_id = :shopId")
	Set<TaobaoShop> findAllByShopId(@Param("shopId") String shopId);
}
