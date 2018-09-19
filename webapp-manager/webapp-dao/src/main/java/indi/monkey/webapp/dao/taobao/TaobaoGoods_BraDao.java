package indi.monkey.webapp.dao.taobao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import indi.monkey.webapp.pojo.hibernate.taobao.TaobaoGoods_Bra;

public interface TaobaoGoods_BraDao extends JpaRepository<TaobaoGoods_Bra, Integer> {

	@Query(value = "select rateContent,shop_id,size,cup,color from taobaogoods_bra where cup = :cup")
	Set<TaobaoGoods_Bra> findAllByCup(@Param(value = "cup") String cup);

}
