package com.jh.musicConverter.musicTag.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jh.musicConverter.model.vo.Music;

@Repository
public interface MusicTagDao extends JpaRepository<Music, Integer>{
	
	// 음원 검색
	Music findById(int id);

}


