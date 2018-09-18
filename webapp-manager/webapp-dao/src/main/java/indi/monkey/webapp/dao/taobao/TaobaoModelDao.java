package indi.monkey.webapp.dao.taobao;

import org.springframework.data.jpa.repository.JpaRepository;

import indi.monkey.webapp.pojo.hibernate.taobao.Model_pic;

public interface TaobaoModelDao extends JpaRepository<Model_pic, Integer> {

}
