package com.jh.musicConverter.musicApi.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MusicApiController {
	
	/*
	 * @GetMapping("/testPage") public String testPage() { return "test/testPage"; }
	 */
	
	
	@PostMapping("/testSubmit")
	public String testSubmit( @RequestParam("artist") String artist, @RequestParam("title") String title,
			@RequestParam("youtube") String youtube, @RequestParam("userId") String name){
		
		System.out.println(artist);
		System.out.println(title);
		System.out.println(youtube);
		System.out.println(name);
		return "test/testPage";
	}
	
	@PostMapping("/test")
	@ResponseBody
	public String testApi( @RequestParam("artist") String artist, @RequestParam("title") String title,
			@RequestParam("youtube") String youtube, @RequestParam("user") String user) throws IOException{
		
		System.out.println(artist);
		System.out.println(title);
		System.out.println(youtube);
		System.out.println(user);
		String eArtist = URLEncoder.encode(artist, "UTF-8");
		String eTitle = URLEncoder.encode(title, "UTF-8");
		StringBuilder result = new StringBuilder();
		
		String urlStr = "http://juhohome.iptime.org:9991/api/mp3?"+"user="+user+"&url="+youtube+"&title="+eTitle+"&artist="+eArtist;
		
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
		System.out.println("결과값"+result);
		return"/";
	}
	
	@GetMapping("/test2")
	@ResponseBody
	public String testApi2() throws IOException{
		
		StringBuilder result = new StringBuilder();
		
		String urlStr ="http://juhohome.iptime.org:9991/api/mp3?user=juho&url=https://youtube/iD54IrEyiK8&title=흰수염고래&artist=윤도현";
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
		System.out.println("결과값"+result);
		
		return "/";
	}
	
	@GetMapping("/testGet.do")
	public String test3 (@RequestParam("artist") String artist, @RequestParam("title") String title,
	@RequestParam("youtube") String youtube, @RequestParam("userId") String name){
		System.out.println(artist);
		System.out.println(title);
		System.out.println(youtube);
		System.out.println(name);
		return "test/testPage";
	}

}
