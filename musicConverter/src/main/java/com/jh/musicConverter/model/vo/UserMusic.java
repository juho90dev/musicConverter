package com.jh.musicConverter.model.vo;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class UserMusic {
	
	@Id
	@GeneratedValue(generator = "seq_musicId", strategy = GenerationType.SEQUENCE)
	private int id;
	
//	// 음악 고유 번호
//	@ManyToOne
//	@JoinColumn(name="musicId")
//	private Music music;
//	
//	// 사용자 고유 번호
//	@ManyToOne
//	@JoinColumn(name="userId")
//	private User user;

}
