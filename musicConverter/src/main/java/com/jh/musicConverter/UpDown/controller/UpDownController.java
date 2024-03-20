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
import org.springframework.web.servlet.ModelAndView;

import com.jh.musicConverter.UpDown.model.service.UpDownService;
import com.jh.musicConverter.model.service.ConverterService;
import com.jh.musicConverter.model.vo.Music;
import com.jh.musicConverter.model.vo.Users;
import com.jh.musicConverter.musicList.model.service.MusicListService;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UpDownController {
	
	@Autowired
	private ConverterService cs;
	
	@Autowired
	private UpDownService service;
	
	@Autowired
	private MusicListService mservice;
	
	@GetMapping("/musicUpload")
	public String musicUpload() {
		return "add/musicFileUpload";
	}
	
	
	@PostMapping("/upload")
	public String upload(MultipartHttpServletRequest mtRequest,@RequestParam("artist") String artist, @RequestParam("title") String title, 
			@RequestParam("userId") String name, Model m) {

		MultipartFile mFile = mtRequest.getFile("mp3");
		System.out.println(mFile.getName());
		System.out.println("------------");
		System.out.println(name);
		System.out.println("------------");
		System.out.println("------------");
		
		
		String path = "/home/dongheon/ftp/"+name+"/";
		
		// String path = "C:\\musicTest\\";
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
		
	
		String rename = title+"-"+artist+ext;
		System.out.println(rename);
		try {
			mFile.transferTo(new File(path+rename));
		}catch(IOException e) {
			System.out.println("실패");
		}
		
		
		Users username = service.findUser(name);
		
		System.out.println(username.getName());
		Music music = Music.builder().title(title).artist(artist).filePath(path+rename).name(service.findUser(name)).build();
		
		// System.out.println(music);

		
		// service.insertFile(music);
		String msg="";
		String loc="";
		
		try {
			service.insertFile(music);
			msg = "mp3 업로드 완료!";
			loc = "/musicUpload";
		}catch(Exception e) {
			msg = "mp3 업로드 실패!";
			loc = "/musicUpload";
			
		}
				
		m.addAttribute("msg",msg);
		m.addAttribute("loc",loc);
		
		return "common/msg";
		//return "redirect:/";
	}

	
	// 음원&파일 삭제
	@PostMapping("/deleteFile")
	public String testDeleteP(@RequestParam int musicId, @RequestParam String name, @RequestParam String filePath) {
		System.out.println(musicId);
		System.out.println(name);
		File file = new File(filePath);
		if( file.exists() ){
			if(file.delete()){
				System.out.println("파일삭제 성공");
			}else{
				System.out.println("파일삭제 실패");
			}
		}else{
			System.out.println("파일이 존재하지 않습니다.");
		}
		mservice.deleteById(musicId);
		return "redirect:/"+name+"List";
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
