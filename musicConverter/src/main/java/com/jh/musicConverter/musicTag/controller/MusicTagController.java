package com.jh.musicConverter.musicTag.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jh.musicConverter.model.vo.Music;
import com.jh.musicConverter.musicTag.model.service.MusicTagService;

@Controller
public class MusicTagController {
	
	@Autowired
	private MusicTagService mservice;
	
	// 음원 정보
	@GetMapping("/fileInfos")
	public String fileInfos(@RequestParam int id, Model mv) {
		System.out.println(id);
		Music info = mservice.findById(id);
		System.out.println("-------------");
		System.out.println(info.getArtist());
		System.out.println(info.getTitle());
		System.out.println(info.getAlbum());
		System.out.println(info.getGenre());
		System.out.println(info.getYear());
		System.out.println("-------------");
		mv.addAttribute("info",info);
		return "add/musicInfo";
	}
	
	@PostMapping("/upTag")
	public String upTag(@RequestParam String genre,@RequestParam String title, @RequestParam String artist, @RequestParam String album, @RequestParam int year, @RequestParam String lylics,
			@RequestParam String name,@RequestParam String filePath, Model model) throws IOException{
	
		System.out.println(artist);
		System.out.println(title);
		System.out.println(album);
		System.out.println(genre);
		System.out.println(year);
		System.out.println(lylics);
		System.out.println(filePath);
		
		String msg="";
		String loc="";
		
		try {
			msg = "mp3 Tag 저장 완료!";
			loc = "/"+name+"List";
		}catch(Exception e) {
			msg = "mp3 Tag 저장 실패!";
			loc = "/"+name+"List";
			
		}
		model.addAttribute("msg",msg);
		model.addAttribute("loc",loc);
		
		return "common/msg";
	}

}
