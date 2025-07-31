package com.jh.musicConverter.musicApi.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jh.musicConverter.UpDown.model.service.UpDownService;
import com.jh.musicConverter.model.vo.Music;
import com.jh.musicConverter.model.vo.Users;

@Controller
public class MusicApiController {
	
	@Autowired
	private UpDownService service;
	

	
	
	@PostMapping("/testSubmit")
	public String testSubmit( @RequestParam("artist") String artist, @RequestParam("title") String title,
			@RequestParam("youtube") String youtube, @RequestParam("user") String name){
		
		System.out.println(artist);
		System.out.println(title);
		System.out.println(youtube);
		System.out.println(name);
		return "test/testPage";
	}
	
	@PostMapping("/musicApi")
	public String testApi(Model model, @RequestParam("artist") String artist, @RequestParam("title") String title,
			@RequestParam("youtube") String youtube, @RequestParam("user") String user) throws IOException{
		
		System.out.println(artist);
		System.out.println(title);
		System.out.println(youtube);
		System.out.println(user);
		String eArtist = URLEncoder.encode(artist, "UTF-8");
		String eTitle = URLEncoder.encode(title, "UTF-8");
		StringBuilder result = new StringBuilder();
		  
		String urlStr = "http://localhost:9090/api/mp3?"+"user="+user+"&url="+youtube+"&title="+eTitle+"&artist="+eArtist;
		
		System.out.println(urlStr);
		
		URL url = new URL(urlStr);
		HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
		System.out.println(urlConnection);
		urlConnection.setRequestMethod("GET");
		
		int res = urlConnection.getResponseCode();
		
		System.out.println(res);
		System.out.println(urlConnection.getResponseMessage());

		
		
		BufferedReader br;
		br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
		String returnLine;
		
		StringBuffer sb = new StringBuffer();
		
		while((returnLine = br.readLine()) != null) {
			result.append(returnLine +"\n\r");
			
		}
		br.close();
		urlConnection.disconnect();
		
		String rePath;
		rePath = result.toString();
		rePath.replace("[^\\w']", "");
		rePath = rePath.replaceAll("\"", "");
		rePath = rePath.trim();
		System.out.println("반환값 : "+result);
		System.out.println("결과값 : "+rePath);
		
		// 유니코드 변환
		String filePath = convertString(rePath);
		System.out.println("test입니다");
		System.out.println(filePath);
		System.out.println("------------");
		filePath = filePath.stripTrailing();
		System.out.println("------------");
		System.out.println(filePath);
		System.out.println("------------");
		
		String msg="";
		String loc="";
		
		if(filePath.equals("null")) {
			msg = "YOUTUBE 변환 실패!";
			loc = "/index";
		}else {
			Users username = service.findUser(user); 
			Music music = Music.builder().title(title).artist(artist).filePath(filePath).name(username).build();
			service.insertFile(music);
			
			try {
				service.insertFile(music);
				msg = "YOUTUBE 변환 완료!";
				loc = "/index";
			}catch(Exception e) {
				msg = "YOUTUBE 변환 실패!";
				loc = "/index";
				
			}
			
		}
				
		model.addAttribute("msg",msg);
		model.addAttribute("loc",loc);
		
		return "common/msg";
		
		
		
	}
	
	@PostMapping("/testDown")
	public String testDown(Model model, @RequestParam("artist") String artist, @RequestParam("title") String title,
			@RequestParam("youtube") String youtube, @RequestParam("user") String user) throws IOException{
		
		System.out.println(user);
		System.out.println(artist);
		System.out.println(title);
		System.out.println(youtube);
		
		// 비디오 링크 주소
        String videoUrl = youtube;
 
        // 저장할 파일 주소+이름
        String fileName = "C:\\musicTest\\"+artist+"-"+title+".mp4";
 
        try {
            
            URL url = new URL(videoUrl);
            URLConnection connection = url.openConnection();
 
//            try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
//                 FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            try (BufferedInputStream in = new BufferedInputStream(new URL(videoUrl).openStream());
            		  FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
 
                byte[] buffer = new byte[1024];
                int bytesRead;
 
                while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
            }
 
            System.out.println("영상 다운로드 성공");
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
		
		return "test/testDown";
	}
	
	// 유니코드 변환
	public static String convertString(String val) {
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<val.length();i++) {
			if('\\' == val.charAt(i) && 'u' == val.charAt(i+1)) {
				Character r = (char) Integer.parseInt(val.substring(i+2, i+6), 16);
				sb.append(r);
				i+=5;
			}else {
				sb.append(val.charAt(i));
			}
		}
		
		return sb.toString();
	}
	


	


}
