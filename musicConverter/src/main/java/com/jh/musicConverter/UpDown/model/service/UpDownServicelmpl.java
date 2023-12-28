/**
 * 
 */
package com.jh.musicConverter.UpDown.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jh.musicConverter.UpDown.model.dao.UpDownDao;
import com.jh.musicConverter.model.dao.ConverterDao;
import com.jh.musicConverter.model.dao.MusicListDao;
import com.jh.musicConverter.model.vo.Music;
import com.jh.musicConverter.model.vo.Users;

/**
 * 
 */
@Service
public class UpDownServicelmpl implements UpDownService {

	@Autowired
	private UpDownDao dao;
	
	@Autowired
	private MusicListDao mdao;
	
	@Autowired
	private ConverterDao cdao;
	
	
	@Override
	public Music insertFile(Music m) {
		return dao.save(m);
	}

	@Override
	public Users findUser(String name) {
		return cdao.findByName(name);
	}

	@Override
	public List<Music> selectFile() {
		return mdao.findAll();
	}
	
	@Override
	public Music selectFiles(String artist, String title) {
		return mdao.findByArtistAndTitle(artist, title);
	}

}
