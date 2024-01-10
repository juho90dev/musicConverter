package com.jh.musicConverter.musicList.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jh.musicConverter.model.vo.Music;
import com.jh.musicConverter.model.vo.Users;
import com.jh.musicConverter.musicList.model.dao.MusicListDao;

@Service
public class MusicListServicelmpl implements MusicListService {
	
	@Autowired
	private MusicListDao mdao;
	

	@Override
	public List<Music> selectFile() {
		return mdao.findAllByOrderByArtistAsc();
	}

	@Override
	public List<Music> selectPersonal(Users user) {
		return mdao.findByNameOrderByArtistAsc(user);
	}

//	@Override
//	public Music selectFiles(String artist, String title) {
//		return mdao.findByArtistAndTitle(artist, title);
//	}

	@Override
	public Music findById(int id) {
		return mdao.findById(id);
	}

	@Override
	public Music deleteById(int id) {
		// TODO Auto-generated method stub
		return mdao.deleteById(id);
	}

}
