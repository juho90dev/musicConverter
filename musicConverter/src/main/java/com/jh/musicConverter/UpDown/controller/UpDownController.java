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
	
	
	@GetMapping("fileList")
	public String fileList(Model m) {
		
		List<Music> files = service.selectFile();
		
		System.out.println(files);
		
		m.addAttribute("file", service.selectFile());
		/*
		 * model.addObject("files",files); model.setViewName("list/fileList");
		 */
		return "list/fileList";
	}
	
	
	@PostMapping("/fileDownload")
	public void fileDownload(@RequestParam("artist") String artist,@RequestParam("title") String title, HttpServletResponse res, 
			HttpServletRequest req, 
			@RequestHeader(value="User-agent") String header) {
		System.out.println("----------");
		System.out.println(artist);
		System.out.println(title);
		System.out.println("----------");
		

		// String path = req.getServletContext().getRealPath("/resources/upload/test/");
		String path = "C:\\musicTest\\";
		
		System.out.println("test 파일경로 : " + path);
		File saveFile = new File(path+title+"_"+artist+".mp3");
		String rename = artist+"_"+title+".mp3";
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
