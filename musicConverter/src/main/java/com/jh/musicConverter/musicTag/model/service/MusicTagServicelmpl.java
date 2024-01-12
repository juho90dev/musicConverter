package com.jh.musicConverter.musicTag.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jh.musicConverter.model.vo.Music;
import com.jh.musicConverter.musicTag.model.dao.MusicTagDao;

@Service
public class MusicTagServicelmpl implements MusicTagService {

	@Autowired
	private MusicTagDao mtdao;
	
	// mp3 음원 검색(id로)
	@Override
	public Music findById(int id) {
		return mtdao.findById(id);
	}

}
