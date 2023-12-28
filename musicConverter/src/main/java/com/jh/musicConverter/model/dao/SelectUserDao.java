package com.jh.musicConverter.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jh.musicConverter.model.vo.Users;

@Repository
public class SelectUserDao {
	
	
	public Users selectUser(SqlSessionTemplate session, String user) {
		System.out.println("넘어오는가");
		System.out.println(user);
		return session.selectOne("users.selectUser", user);
		
		
	}

}
