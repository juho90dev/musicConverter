package com.jh.musicConverter.musicList.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jh.musicConverter.UpDown.model.service.UpDownService;
import com.jh.musicConverter.model.vo.Music;
import com.jh.musicConverter.model.vo.Users;
import com.jh.musicConverter.musicList.model.service.MusicListService;
import com.jh.musicConverter.util.Pager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
	
	// 스크롤 테스트
	@GetMapping("/ScrollTest")
	public String scrollTest() {
		int count = mservice.fileCount();
		System.out.println(count);
		return "list/scrollTest";
	}
	
	@GetMapping("/scrollTests")
	public String scrollTests(HttpServletRequest request, HttpSession session) {
		
		
		
		
		
		return "list/scrollTest";
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
	
	// 가수로 정렬
	@GetMapping("/artistAcs")
	public String listAcs(@RequestParam String name, Model m) {
		System.out.println(name);
		
		if(name.equals("all")) {
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
	
	@PostMapping("/searchMusic")
	public String searchMusic(@RequestParam("searchType") String searchType, String keyword,@RequestParam String name, Model m) {
		System.out.println(searchType);
		System.out.println(keyword);
		System.out.println(name);
		if(searchType.equals("searchArtist")) {
			// 가수 검색
			if(name.equals("all")) {
				System.out.println("전체");
				List<Music> music = mservice.searchArtistAll(keyword);
				m.addAttribute("file",music);
				m.addAttribute("name",name);
			}else {
				Users user = service.findUser(name);
				List<Music> music = mservice.searchArtist(keyword, user);
				System.out.println(music);
				
				m.addAttribute("file", music);
				m.addAttribute("name", name);
				
			}
			
		}else {
			// 제목 검색
			if(name.equals("all")) {
				List<Music> music = mservice.searchTitleAll(keyword);
				m.addAttribute("file", music);
				m.addAttribute("name", name);
			}else {
				Users user = service.findUser(name);
				List<Music> music = mservice.searchTitle(keyword, user);
				m.addAttribute("file", music);
				m.addAttribute("name", name);
			}
		}
		return "list/fileList";
	}

	
	
	
}
