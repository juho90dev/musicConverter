package com.jh.musicConverter.musicList.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jh.musicConverter.model.vo.Music;
import com.jh.musicConverter.model.vo.Users;


@Repository
public interface MusicListDao extends JpaRepository<Music, Integer>{
	
	List<Music> findAllByOrderByArtistAsc();
	
	// 음원 검색
	Music findById(int id);

	Music findByArtistAndTitle(String artist, String title);
	
	List<Music> findByName(Users user);
	
	// 음원 삭제
	Music deleteById(int id);

}
