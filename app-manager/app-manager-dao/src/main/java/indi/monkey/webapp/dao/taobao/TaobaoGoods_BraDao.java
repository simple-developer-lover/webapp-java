package indi.monkey.webapp.dao.taobao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import indi.monkey.webapp.pojo.hibernate.taobao.TaobaoGoods_Bra;

public interface TaobaoGoods_BraDao extends JpaRepository<TaobaoGoods_Bra, Integer> {

	@Query(value = "select bra from TaobaoGoods_Bra bra where bra.cup like CONCAT('%',?1,'%')")
	Set<TaobaoGoods_Bra> findByCup(String cup);

}
