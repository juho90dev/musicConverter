package com.jh.musicConverter.UpDown.model.service;

import java.util.List;

import com.jh.musicConverter.model.vo.Music;
import com.jh.musicConverter.model.vo.Users;

public interface UpDownService {
	
	// 파일 추가
	Music insertFile(Music m);

	// 사용자 검색
	Users findUser(String name);
	
//	// 파일 리스트
//	List<Music> selectFile();
//	
//	// 아빠 리스트
//	List<Music> selectPersonal(Users user);
//
//	// 파일 선택
//	Music selectFiles(String artist, String title);
//	
//	// 음원 검색
//	Music findById(int id);
	
//	// 음원 삭제
//	Music deleteById(int id);
//	
}
