package com.jh.musicConverter.musicList.model.service;

import java.util.List;

import com.jh.musicConverter.model.vo.Music;
import com.jh.musicConverter.model.vo.Users;

public interface MusicListService {
	
	// 파일 리스트
	List<Music> selectFile();
	
	// 모든 파일 제목으로 정렬
	List<Music> allTitleAsc();
	
	// 개인 리스트
	List<Music> selectPersonal(Users user);
	
	// 제목으로 정렬
	List<Music> selectTitleAsc(Users user);

//	// 파일 선택
//	Music selectFiles(String artist, String title);
	
	// 음원 검색
	Music findById(int id);
	
	// 음원 삭제
	Music deleteById(int id);
	
	// 사용자별, 검색어가 포함된 가수 검색
	List<Music> searchArtist(String keyword, Users user);
	
	// 전체, 검색어가 포함된 가수 검색
	List<Music> searchArtistAll(String keyword);
	
	// 사용자별, 검색어가 포함된 제목 검색
	List<Music> searchTitle(String keyword, Users user);
	
	// 전체, 검색어가 포함된 제목 검색
	List<Music> searchTitleAll(String keyword);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
