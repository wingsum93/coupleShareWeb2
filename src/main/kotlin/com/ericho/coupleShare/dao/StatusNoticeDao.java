package com.ericho.coupleShare.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ericho.coupleShare.model.StatusNotice;

public interface StatusNoticeDao extends JpaRepository<StatusNotice,Integer>{

	List<StatusNotice> findByUsername(String username);
	List<StatusNotice> findByUsername(List<String> usernames);
	List<StatusNotice> findByUsernameOrderByDateDesc(String username);
	
	
	@Query(value="select * from status_notice a inner join (select username, max(id) as maxid from status_notice group by username) as b on a.id = b.maxid where a.username in ?1 "
			,nativeQuery = true)
	List<StatusNotice> findByLatestUsername(List<String> username);
}
