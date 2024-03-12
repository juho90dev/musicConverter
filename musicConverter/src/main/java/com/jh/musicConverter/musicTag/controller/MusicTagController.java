package com.jh.musicConverter.musicTag.controller;

import java.io.File;
import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
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
//		System.out.println("-------------");
//		System.out.println(info.getArtist());
//		System.out.println(info.getTitle());
//		System.out.println(info.getAlbum());
//		System.out.println(info.getGenre());
//		System.out.println(info.getYear());
//		System.out.println("-------------");
		mv.addAttribute("info",info);
		return "add/musicInfo";
	}
	
	@PostMapping("/upTag")
	public String upTag(@RequestParam String genre,@RequestParam String title, @RequestParam String artist, @RequestParam String album, @RequestParam int year, @RequestParam String lylics,
			@RequestParam String name,@RequestParam String filePath, Model model, MultipartHttpServletRequest mtRequest) throws IOException{
	
		MultipartFile mFile = mtRequest.getFile("img");
		String path ="C:\\musicTest\\cover\\";
		
		System.out.println("--------------");
		System.out.println("가수 : " + artist);
		System.out.println("제목 : " + title);
		System.out.println("앨범 : " + album);
		System.out.println("장르 : " + genre);
		System.out.println("발매연도 : " + year);
		System.out.println("가사");
		System.out.println(lylics);
		System.out.println("파일 경로 : " + filePath);
		System.out.println("이미지파일 변환 : " + mFile);
		System.out.println("표지 저장 : " + path);
		System.out.println("--------------");
		
		String cImage = "";
		String ext = "";
		String image = "";
		
		if(mFile != null) {
			if(!mFile.isEmpty()) {
				cImage = mFile.getOriginalFilename();
				ext = cImage.substring(cImage.lastIndexOf("."));
				image = artist+"-"+title+ext;
				System.out.println(image);
				try {
					mFile.transferTo(new File(path+image));
				}catch(IOException e) {
					System.out.println("실패");
				}
			}
			
		}
		
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
