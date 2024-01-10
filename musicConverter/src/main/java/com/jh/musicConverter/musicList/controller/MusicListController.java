package com.jh.musicConverter.musicList.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
		String name="all";
		List<Music> files = mservice.selectFile();
		
		m.addAttribute("file", mservice.selectFile());
		m.addAttribute("name",name);
		System.out.println(name);
		return "list/fileList";
	}
	
	// 아빠 음원 파일 리스트
	@GetMapping("/fatherList")
	public String fatherList(Model m) {
		String name ="father";
		Users user = service.findUser(name);
		List<Music> music = mservice.selectPersonal(user);
		m.addAttribute("file", music);
		m.addAttribute("name",name);
		return "list/fileList";
	}
	
	// 엄마 음원 파일 리스트
	@GetMapping("/motherList")
	public String motherList(Model m) {
		String name = "mother";
		Users user = service.findUser(name);
		List<Music> music = mservice.selectPersonal(user);
		m.addAttribute("file", music);
		m.addAttribute("name",name);
		return "list/fileList";
	}
	
	// 형아 음원 파일 리스트
	@GetMapping("/dongheonList")
	public String dongheonList(Model m) {
		String name = "dongheon";
		Users user = service.findUser(name);
		List<Music> music = mservice.selectPersonal(user);
		m.addAttribute("file", music);
		m.addAttribute("name",name);
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
		m.addAttribute("name",name);
		return "list/fileList";
		
	}
	
	// 정렬 테스트
	@GetMapping("/artistAcs")
	public String listAcs(@RequestParam String name, Model m) {
		System.out.println(name);
//		Users user = service.findUser(name);
//		List<Music> music = mservice.selectPersonal(user);
//		m.addAttribute("file",music);
//		m.addAttribute("user", user);
//		m.addAttribute("name",name);
		
		if(name.equals("all")) {
			System.out.println("allallall");
			
			m.addAttribute("file", mservice.selectFile());
			m.addAttribute("name",name);
		}else {
			Users user = service.findUser(name);
			List<Music> music = mservice.selectPersonal(user);
			m.addAttribute("file",music);
			m.addAttribute("user", user);
			m.addAttribute("name",name);
		}
		return "list/fileList";
	}
	
	@GetMapping("/titleAsc")
	public String listDesc(@RequestParam String name, Model m) {
		
		if(name.equals("all")){
			
			m.addAttribute("file", mservice.allTitleAsc());
			m.addAttribute("name",name);
		}else {
			
			Users user = service.findUser(name);
			List<Music> music = mservice.selectTitleAsc(user);
			System.out.println(name);
			m.addAttribute("file",music);
			m.addAttribute("name",name);
		}
		
		
		
		
		return "list/fileList";
	}
	
	
	
}
