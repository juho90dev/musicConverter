package com.jh.musicConverter.musicTag.model.service;

import com.jh.musicConverter.model.vo.Music;

public interface MusicTagService {
	
	// mp3 음원 검색(id로)
	Music findById(int id);

}
