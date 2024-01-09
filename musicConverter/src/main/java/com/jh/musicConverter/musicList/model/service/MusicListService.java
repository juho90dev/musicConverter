package com.jh.musicConverter.musicList.model.service;

import java.util.List;

import com.jh.musicConverter.model.vo.Music;
import com.jh.musicConverter.model.vo.Users;

public interface MusicListService {
	
	// 파일 리스트
	List<Music> selectFile();
	
	// 개인 리스트
	List<Music> selectPersonal(Users user);

	// 파일 선택
	Music selectFiles(String artist, String title);
	
	// 음원 검색
	Music findById(int id);
	
	// 음원 삭제
	Music deleteById(int id);
}
