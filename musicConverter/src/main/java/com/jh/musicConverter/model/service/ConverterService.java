package com.jh.musicConverter.model.service;

import java.util.List;

import com.jh.musicConverter.model.vo.Music;
import com.jh.musicConverter.model.vo.Users;

public interface ConverterService {
	
	// 사용자 추가
	Users join(Users user);

	Users selectUser(String name);
	
	
	/*
	 * // 파일 추가 Music insertFile(Music m);
	 * 
	 * Users findUser(String userId);
	 * 
	 * // 파일 리스트 List<Music> selectFile();
	 */
}
