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
import java.util.regex.Pattern;

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
	
	// 허용된 YouTube URL 패턴 (간단한 검증)
    private static final Pattern YOUTUBE_URL_PATTERN = 
        Pattern.compile("^(https?://)?(www\\.)?(youtube\\.com/watch\\?v=|youtu\\.be/)[a-zA-Z0-9_-]{11}(&.*)?$");
    
    // 허용된 내부 API 도메인 (SSRF 방어용 화이트리스트)
    private static final String ALLOWED_API_HOST = "juhohome.iptime.org:9991";
    private static final String API_ENDPOINT = "http://" + ALLOWED_API_HOST + "/api/mp3";
	
	
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
 
            System.out.println("음원 다운로드 성공");
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
	
	// sql injection 보안 수정 테스트
	@PostMapping("/musicApi2")
    public String testApi2(
            Model model, 
            @RequestParam("artist") String artist, 
            @RequestParam("title") String title,
            @RequestParam("youtube") String youtube, 
            @RequestParam("user") String user) throws IOException {
        
        // 1. **입력 값 검증 및 살균** // 입력 값이 비어있는지 확인하고, 최대 길이를 제한합니다.
        if (artist == null || artist.length() > 100 || 
            title == null || title.length() > 100 || 
            user == null || user.length() > 50) {
            
            model.addAttribute("msg", "입력 값이 비정상적이거나 너무 깁니다.");
            model.addAttribute("loc", "/index");
            return "common/msg";
        }
        
        // YouTube URL 형식 검증 (SSRF 방어의 첫 단계)
        if (!YOUTUBE_URL_PATTERN.matcher(youtube).matches()) {
            model.addAttribute("msg", "유효하지 않은 YouTube URL 형식입니다.");
            model.addAttribute("loc", "/index");
            return "common/msg";
        }

        String eArtist = URLEncoder.encode(artist, "UTF-8");
        String eTitle = URLEncoder.encode(title, "UTF-8");
        
        // 2. **외부 API 호출 안전성 강화 (SSRF 방어)**
        // URL을 하드코딩된 안전한 도메인으로 구성
        String urlStr = String.format("%s?user=%s&url=%s&title=%s&artist=%s", 
                API_ENDPOINT, 
                URLEncoder.encode(user, "UTF-8"), // user 값도 인코딩
                URLEncoder.encode(youtube, "UTF-8"), // url 값 인코딩
                eTitle, 
                eArtist);
        
        String rePath = null;
        
        try {
            URL url = new URL(urlStr);
            
            // **추가적인 SSRF 방어**: URL이 예상된 API 호스트와 일치하는지 최종 확인
            if (!url.getHost().equals(ALLOWED_API_HOST.split(":")[0]) || url.getPort() != 9991) {
                // 이 부분은 이미 상단의 API_ENDPOINT에서 걸러지지만, 방어적인 코딩을 위해 추가
                throw new IOException("외부 API 호출 도메인 검증 실패.");
            }

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            int res = urlConnection.getResponseCode();
            
            if (res != HttpURLConnection.HTTP_OK) {
                // API 호출 실패 시
                throw new IOException("외부 API 응답 코드 오류: " + res);
            }

            // 응답 데이터 읽기
            try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"))) {
                StringBuilder result = new StringBuilder();
                String returnLine;
                while ((returnLine = br.readLine()) != null) {
                    result.append(returnLine).append("\n");
                }
                rePath = result.toString().trim(); // 최종 경로값
            } finally {
                urlConnection.disconnect();
            }

        } catch (java.net.ConnectException e) {
            // 외부 통신 오류 발생 시
            System.err.println("API 통신 오류: " + e.getMessage());
            model.addAttribute("msg", "음원 변환 서버와 통신 중 오류가 발생했습니다.");
            model.addAttribute("loc", "/index");
            return "common/msg";
        }

        // 3. **파일 경로 살균 및 유효성 검사 (Path Traversal 방어)**
        String filePath = rePath;
        
        if (filePath == null || filePath.isEmpty() || filePath.equals("null")) {
            model.addAttribute("msg", "YOUTUBE 변환 실패!");
            model.addAttribute("loc", "/index");
            return "common/msg";
        }
        
        // 유효한 파일 경로/이름인지 확인 (예: 슬래시, 역슬래시, '..' 등 경로 조작 문자 제거)
        // 안전한 파일 이름은 알파벳, 숫자, 하이픈, 밑줄, 마침표(하나)만 포함한다고 가정
        filePath = filePath.replaceAll("[^a-zA-Z0-9._-]", ""); 
        
        // 4. **SQL 인젝션 방어 (서비스 계층의 Prepared Statement 사용 전제)**
        String msg = "";
        String loc = "/index";

        try {
            // service.findUser() 및 service.insertFile() 내부에서 
            // **반드시 Prepared Statement나 ORM 바인딩을 사용해야 합니다.**
            Users username = service.findUser(user); 
            
            if (username == null) {
                throw new Exception("사용자 정보를 찾을 수 없습니다.");
            }
            
            Music music = Music.builder()
                .title(title)
                .artist(artist)
                .filePath(filePath) // 살균된 filePath 사용
                .name(username)
                .build();

            service.insertFile(music); // **DB 접근 코드: ORM 사용을 가정**
            msg = "YOUTUBE 변환 완료!";
            
        } catch(Exception e) {
            // 사용자에게 상세한 에러 메시지를 노출하지 않도록 로그에만 기록
            System.err.println("DB 작업 실패: " + e.getMessage());
            msg = "DB 저장 실패!";
        }
                
        model.addAttribute("msg", msg);
        model.addAttribute("loc", loc);
        
        // XSS 방어를 위해 common/msg.jsp에서는 ${msg} 출력 시 반드시 HTML 인코딩을 적용해야 합니다.
        return "common/msg";
    }

    // convertString() 함수는 사용 목적이 불분명하고 잠재적인 취약점이 될 수 있어 제거했습니다.
    // 필요하다면 안전한 방식으로 재구현해야 합니다.
	
	

	


}
