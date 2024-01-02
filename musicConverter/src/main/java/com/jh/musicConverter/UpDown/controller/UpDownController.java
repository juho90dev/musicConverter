package com.jh.musicConverter.UpDown.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jh.musicConverter.UpDown.model.service.UpDownService;
import com.jh.musicConverter.model.service.ConverterService;
import com.jh.musicConverter.model.vo.Music;
import com.jh.musicConverter.model.vo.Users;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UpDownController {
	
	@Autowired
	private ConverterService cs;
	
	@Autowired
	private UpDownService service;
	
	@GetMapping("/musicUpload")
	public String musicUpload() {
		return "add/musicFileUpload";
	}
	
	
	@PostMapping("/upload")
	public String upload(MultipartHttpServletRequest mtRequest,@RequestParam("artist") String artist, @RequestParam("title") String title, 
			@RequestParam("userId") String name, Model m) {
		/*
		 * int users; switch(userId) { case "father": users = 1; break; case "mother":
		 * users = 2; break; case "dongheon": users = 3; break; case "juho": users = 4;
		 * break; }
		 */
		MultipartFile mFile = mtRequest.getFile("mp3");
		System.out.println(mFile.getName());
		System.out.println("------------");
		System.out.println(name);
		System.out.println("------------");
		System.out.println("------------");
		
		
		// String path = mtRequest.getServletContext().getRealPath("/resources/upload/test/");
		String path = "C:\\musicTest\\";
		File uploadPath = new File(path);
		System.out.println(path);
		// 디렉터리 자동 생성
		if(!uploadPath.exists()) uploadPath.mkdirs();
		
		String oriFileName = "";
		String ext = "";
		if(mFile != null) {
			try {
				oriFileName = mFile.getOriginalFilename();
				ext = oriFileName.substring(oriFileName.lastIndexOf("."));
			}catch (Exception e) {
				System.out.println("등록 실패");
			}
		}
		
	
		String rename = title+"_"+artist+ext;
		System.out.println(rename);
		try {
			mFile.transferTo(new File(path+rename));
		}catch(IOException e) {
			System.out.println("실패");
		}
		
		
		Users username = service.findUser(name);
		
		System.out.println(username.getName());
		Music music = Music.builder().title(title).artist(artist).filePath(path).name(service.findUser(name)).build();
		
		// System.out.println(music);

		
		service.insertFile(music);
		
		return "redirect:/";
	}
	
	// 전체 음원 파일 리스트
	@GetMapping("/fileList")
	public String fileList(Model m) {
		
		List<Music> files = service.selectFile();
		
		m.addAttribute("file", service.selectFile());
		return "list/fileList";
	}
	
	// 아빠 음원 파일 리스트
	@GetMapping("/fatherList")
	public String fatherList(Model m) {
		String name ="father";
		Users user = service.findUser(name);
		List<Music> music = service.selectPersonal(user);
		m.addAttribute("file", music);
		return "list/fileList";
	}
	
	// 엄마 음원 파일 리스트
	@GetMapping("/motherList")
	public String motherList(Model m) {
		String name = "mother";
		Users user = service.findUser(name);
		List<Music> music = service.selectPersonal(user);
		m.addAttribute("file", music);
		return "list/fileList";
	}
	
	// 형아 음원 파일 리스트
	@GetMapping("/dongheonList")
	public String dongheonList(Model m) {
		String name = "dongheon";
		Users user = service.findUser(name);
		List<Music> music = service.selectPersonal(user);
		m.addAttribute("file", music);
		return "list/fileList";
	}
	
	// 주호 음원 파일 리스트
	@GetMapping("/juhoList")
	public String juhoList(Model m) {
		String name = "juho";
		Users user = service.findUser(name);
		List<Music> music = service.selectPersonal(user);
		m.addAttribute("file",music);
		return "list/fileList";
		
	}
	
	@PostMapping("/fileDownload")
	public void fileDownload(@RequestParam("artist") String artist,@RequestParam("title") String title, @RequestParam("filePath") String filePath, HttpServletResponse res, 
			HttpServletRequest req, 
			@RequestHeader(value="User-agent") String header) {
		System.out.println("----------");
		System.out.println(artist);
		System.out.println(title);
		System.out.println(filePath);
		System.out.println("----------");
		

		String path = "C://musicTest/";
		
		System.out.println("test 파일경로 : " + filePath);
		File saveFile = new File(filePath);
		String rename = artist+"-"+title+".mp3";
		try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(saveFile));
				ServletOutputStream sos = res.getOutputStream();){
			boolean isMS = header.contains("Trident")||header.contains("MSIE");
			
			String encodeFilename = "";
			if(isMS) {
				encodeFilename = URLEncoder.encode(rename, "UTF-8");
				encodeFilename = encodeFilename.replace("\\+", "%20");
			}else {
				encodeFilename = new String(rename.getBytes("UTF-8"), "ISO-8859-1");
			}
			
			res.setContentType("application/octet-stream;charset=utf-8");
			
			res.setHeader("Content-Disposition", "attachment;filename=\""+encodeFilename+"\"");;
			
			int read = -1;
			while((read=bis.read())!= -1) {
				sos.write(read);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
