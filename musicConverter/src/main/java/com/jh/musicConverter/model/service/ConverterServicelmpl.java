package com.jh.musicConverter.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jh.musicConverter.model.dao.ConverterDao;
import com.jh.musicConverter.model.dao.SelectUserDao;
import com.jh.musicConverter.model.vo.Music;
import com.jh.musicConverter.model.vo.Users;
import com.jh.musicConverter.musicList.model.dao.MusicListDao;

@Service
public class ConverterServicelmpl implements ConverterService {
	
	@Autowired
	private ConverterDao dao;
	
	@Autowired
	private MusicListDao mdao;
	
	@Autowired
	public ConverterServicelmpl(ConverterDao rep) {
		this.dao = dao;
	}
	
	@Autowired
	private SelectUserDao sudao;
	
	@Autowired
	private SqlSessionTemplate session;
	
	// 사용자 추가
	@Override
	public Users join(Users u) {
		
		return dao.save(u);
	}
	
	
	
	@Override
	public Users selectUser(String name) {
		return sudao.selectUser(session, name);
	}
	/*
	 * @Override public Music insertFile(Music m) { return dao.save(m); }
	 * 
	 * @Override public Users findUser(String userId) { return
	 * dao.findByName(userId); }
	 * 
	 * public List<Music> selectFile(){ return mdao.findAll(); }
	 */
}
