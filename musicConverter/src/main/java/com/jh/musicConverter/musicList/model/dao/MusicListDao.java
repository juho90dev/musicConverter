package com.jh.musicConverter.musicList.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jh.musicConverter.model.vo.Music;
import com.jh.musicConverter.model.vo.Users;


@Repository
public interface MusicListDao extends JpaRepository<Music, Integer>{
	
	// 음원 전체
	List<Music> findAllByOrderByArtistAsc();
	
	List<Music> findAllByOrderByTitleAsc();
	
	// 음원 검색
	Music findById(int id);

	Music findByArtistAndTitle(String artist, String title);
	
	// 개인 음원(가수로 정렬)
	List<Music> findByNameOrderByArtistAsc(Users user);
	
	// 개인 음원(제목으로 정렬)
	List<Music> findByNameOrderByTitleAsc(Users user);
	
	
	// 음원 삭제
	Music deleteById(int id);
	
	// 사용자별/검색어가 포함된 가수 검색
	List<Music> findByNameAndArtistContainingIgnoreCase(Users user, String keyword);
	
	// 사용자별, 검색어가 포함된 제목 검색
	List<Music> findByNameAndTitleContainingIgnoreCase(Users user, String keyword);
	
	// 전체, 검색어가 포함된 가수 
	List<Music> findByArtistContainingIgnoreCase(String keyword);
	
	// 전체, 검색어가 포함된 제목 검색
	List<Music> findByTitleContainingIgnoreCase(String keyword);

	
	
	int countBy();
}
