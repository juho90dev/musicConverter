package com.jh.musicConverter.UpDown.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jh.musicConverter.model.vo.Music;
import com.jh.musicConverter.model.vo.Users;

@Repository
public interface UpDownDao extends JpaRepository<Music, Integer>{
	
	Music save(Music m);
	
	
	
	
	

}
