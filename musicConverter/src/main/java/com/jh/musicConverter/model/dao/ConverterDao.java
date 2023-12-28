package com.jh.musicConverter.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jh.musicConverter.model.vo.Music;
import com.jh.musicConverter.model.vo.Users;


@Repository
public interface ConverterDao extends JpaRepository<Users, Integer> {
	
	Users save(Users u);
	
	Music save(Music m);


	Users findByName(String name);
	
	
}
