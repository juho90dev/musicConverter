package com.jh.musicConverter.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jh.musicConverter.model.vo.Music;


@Repository
public interface MusicListDao extends JpaRepository<Music, Integer>{
	
	List<Music> findAll();

	Music findByArtistAndTitle(String artist, String title);

}
