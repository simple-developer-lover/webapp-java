package indi.monkey.webapp.dao.tieba;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import indi.monkey.webapp.pojo.hibernate.tieba.TiebaUser;


public interface TiebaUserDao extends JpaRepository<TiebaUser, String> {

	TiebaUser findUsersByUserName(@Param(value = "userName") String username);

	@Query(value = "select username,url,userhead,userAge,usersex,tbTitleContent from tieba_user where 1 = 1 and "
			+ " (case when :user.username !=null then :user.username = user.username )"
			+ " (case when :user.url != null then :user.url = user.url )"
			+ " (case when :user.userHead != null then :user.userHead = user.userHead ) "
			+ " (case when :user.userAge != null then :user.userAge = user.userAge ) "
			+ " (case when :user.userSex != null then :user.userSex = user.userSex ) "
			+ " (case when :user.tbTitleContent != null then :user.tbTitleContent = user.tbTitleContent )", nativeQuery = true)
	List<TiebaUser> findUserByParams(@Param(value = "user") TiebaUser user);
}
