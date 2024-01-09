package com.jh.musicConverter.musicList.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jh.musicConverter.UpDown.model.service.UpDownService;
import com.jh.musicConverter.model.vo.Music;
import com.jh.musicConverter.model.vo.Users;
import com.jh.musicConverter.musicList.model.service.MusicListService;

@Controller
public class MusicListController {

	@Autowired
	private UpDownService service;
	
	@Autowired
	private MusicListService mservice;
	
	// 전체 음원 리스트
	@GetMapping("/fileList")
	public String fileList(Model m) {
		
		List<Music> files = mservice.selectFile();
		
		m.addAttribute("file", mservice.selectFile());
		return "list/fileList";
	}
	
	// 아빠 음원 파일 리스트
	@GetMapping("/fatherList")
	public String fatherList(Model m) {
		String name ="father";
		Users user = service.findUser(name);
		List<Music> music = mservice.selectPersonal(user);
		m.addAttribute("file", music);
		return "list/fileList";
	}
	
	// 엄마 음원 파일 리스트
	@GetMapping("/motherList")
	public String motherList(Model m) {
		String name = "mother";
		Users user = service.findUser(name);
		List<Music> music = mservice.selectPersonal(user);
		m.addAttribute("file", music);
		return "list/fileList";
	}
	
	// 형아 음원 파일 리스트
	@GetMapping("/dongheonList")
	public String dongheonList(Model m) {
		String name = "dongheon";
		Users user = service.findUser(name);
		List<Music> music = mservice.selectPersonal(user);
		m.addAttribute("file", music);
		return "list/fileList";
	}
	
	// 주호 음원 파일 리스트
	@GetMapping("/juhoList")
	public String juhoList(Model m) {
		String name = "juho";
		Users user = service.findUser(name);
		List<Music> music = mservice.selectPersonal(user);
		m.addAttribute("file",music);
		System.out.println(user);
		m.addAttribute("user", user);
		return "list/fileList";
		
	}
}
