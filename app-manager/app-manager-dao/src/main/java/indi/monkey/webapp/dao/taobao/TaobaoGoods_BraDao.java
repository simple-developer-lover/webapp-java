package indi.monkey.webapp.dao.taobao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import indi.monkey.webapp.pojo.hibernate.taobao.TaobaoGoods_Bra;

public interface TaobaoGoods_BraDao extends JpaRepository<TaobaoGoods_Bra, Integer> {

/*	@Query(value = "select bra from taobaogoods_bra bra where bra.cup =:cup")
	Set<TaobaoGoods_Bra> findAllByCup(@Param("cup") String cup);*/

}
