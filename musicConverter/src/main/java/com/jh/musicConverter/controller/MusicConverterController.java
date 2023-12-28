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

	@GetMapping("/index")
	public String index() {
		return "index";
	}

	@GetMapping("/index2")
	public String index2() {
		return "index2";
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

	/*
	 * @PostMapping("/upload") public String upload(MultipartHttpServletRequest
	 * mtRequest,@RequestParam("artist") String artist, @RequestParam("title")
	 * String title,
	 * 
	 * @RequestParam("userId") String userId, Model m) {
	 * 
	 * int users; switch(userId) { case "father": users = 1; break; case "mother":
	 * users = 2; break; case "dongheon": users = 3; break; case "juho": users = 4;
	 * break; }
	 * 
	 * MultipartFile mFile = mtRequest.getFile("mp3");
	 * System.out.println(mFile.getName()); System.out.println("------------");
	 * System.out.println(userId); System.out.println("------------");
	 * System.out.println("------------");
	 * 
	 * 
	 * String path =
	 * mtRequest.getServletContext().getRealPath("/resources/upload/test/"); File
	 * uploadPath = new File(path); System.out.println(path); // 디렉터리 자동 생성
	 * if(!uploadPath.exists()) uploadPath.mkdirs();
	 * 
	 * String oriFileName = ""; String ext = ""; if(mFile != null) { try {
	 * oriFileName = mFile.getOriginalFilename(); ext =
	 * oriFileName.substring(oriFileName.lastIndexOf(".")); }catch (Exception e) {
	 * System.out.println("등록 실패"); } }
	 * 
	 * 
	 * String rename = artist+"_"+title+ext; try { mFile.transferTo(new
	 * File(path+rename)); }catch(IOException e) { System.out.println("실패"); }
	 * 
	 * System.out.println(service.findUser(userId));
	 * 
	 * Music music =
	 * Music.builder().title(title).artist(artist).filePath(path).name(service.
	 * findUser(userId)).build();
	 * 
	 * // System.out.println(music);
	 * 
	 * 
	 * service.insertFile(music);
	 * 
	 * return "redirect:/"; }
	 * 
	 * 
	 * @GetMapping("fileList") public String fileList(Model m) {
	 * 
	 * List<Music> files = service.selectFile();
	 * 
	 * System.out.println(files);
	 * 
	 * m.addAttribute("file", service.selectFile());
	 * 
	 * model.addObject("files",files); model.setViewName("list/fileList");
	 * 
	 * return "list/fileList"; }
	 */

}
