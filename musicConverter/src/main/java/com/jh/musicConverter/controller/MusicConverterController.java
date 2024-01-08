package com.jh.musicConverter.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.jh.musicConverter.model.service.ConverterService;
import com.jh.musicConverter.model.vo.Music;
import com.jh.musicConverter.model.vo.Users;

@Controller
public class MusicConverterController {

	@Autowired
	private ConverterService service;

	// 홈화면
	@GetMapping("/index")
	public String index() {
		return "redirect:/";
	}
	
	
	@GetMapping("/testModal")
	public String modal(Model model) {
		
		String msg="";
		String loc="";
		msg = "변환 성공";
		loc = "index";
			
		model.addAttribute("msg",msg);
		model.addAttribute("loc",loc);
		return "common/msg";
	}

	@PostMapping("/converter")
	public String converter(Model model, @RequestParam("artist") String artist, @RequestParam("title") String title,
			@RequestParam("album") String album, @RequestParam("year") String year, @RequestParam("lyric") String lyric,
			@RequestParam("genre") String genre) {
		System.out.println(artist);
		System.out.println(title);
		System.out.println(genre);
		System.out.println(album);
		System.out.println(year);
		System.out.println(lyric);
		return "redirect:/";
	}

	// 사용자 추가
	@PostMapping("/join")
	public String join(@RequestParam("name") String name) {
		Users u = Users.builder().name(name).build();
		System.out.println(name);

		service.join(u);
		return "redirect:/";
	}
	
	@GetMapping("/testPage")
	public String testPage() {
		return "test/testPage";
	}

	@GetMapping("/addUser")
	public String mypage() {
		return "add/addUser";
	}

}
