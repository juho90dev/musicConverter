package com.jh.musicConverter.musicTag.controller;

import java.io.File;
import java.io.IOException;

import org.apache.ibatis.annotations.Param;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.id3.ID3v24Tag;
import org.jaudiotagger.tag.images.Artwork;
import org.jaudiotagger.tag.images.ArtworkFactory;
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
		System.out.println("사용자 : " + name);
		System.out.println("--------------");
		
		String testName = "juho";
		String cImage = "";
		String ext = "";
		String image = "";
		String testFile = "C:\\musicTest\\";
		
		File mp3File = new File(path);
		if(!mp3File.exists()) {
			System.out.println("파일이 존재하지 않습니다." + testFile);
		}
		try {
			AudioFile audiFile = AudioFileIO.read(mp3File);
			Tag tag = audiFile.getTag();
			if(tag == null) {
				System.out.println("태그를 찾을 수 없습니다." + filePath);
				tag = new ID3v24Tag();
				audiFile.setTag(tag);
			}
			tag.setField(org.jaudiotagger.tag.FieldKey.TITLE, title);
			tag.setField(org.jaudiotagger.tag.FieldKey.ARTIST, artist);
			tag.setField(org.jaudiotagger.tag.FieldKey.ALBUM, album);
			tag.setField(org.jaudiotagger.tag.FieldKey.LYRICIST, lylics);
			tag.setField(org.jaudiotagger.tag.FieldKey.GENRE, genre);
			tag.setField(org.jaudiotagger.tag.FieldKey.YEAR,String.valueOf(year));
			tag.setField(org.jaudiotagger.tag.FieldKey.COVER_ART, image);
		} catch (IOException e) {
            System.err.println("파일 입출력 오류: " + e.getMessage());
        } catch (org.jaudiotagger.tag.TagException e) {
            System.err.println("태그 처리 오류: " + e.getMessage());
        } catch (org.jaudiotagger.audio.exceptions.ReadOnlyFileException e) {
            System.err.println("읽기 전용 파일 오류: " + e.getMessage());
        } catch (org.jaudiotagger.audio.exceptions.InvalidAudioFrameException e) {
            System.err.println("유효하지 않은 오디오 프레임 오류: " + e.getMessage());
        } catch (Exception e) { // 기타 예상치 못한 오류 처리
            System.err.println("알 수 없는 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }

		
		
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
			loc = "/"+testName+"List";
		}catch(Exception e) {
			msg = "mp3 Tag 저장 실패!";
			loc = "/"+testName+"List";
			
		}
		model.addAttribute("msg",msg);
		model.addAttribute("loc",loc);
		
		return "common/msg";
	}
	
	


	
	@PostMapping("/upTagTest")
	public String upTagTest(
			@RequestParam String artist, // 가수
			@RequestParam String title, // 제목
			@RequestParam String genre, // 장르
			@RequestParam String album, // 앨범명
			@RequestParam String year, // 발매연도
			@RequestParam String lylics, // 가사
			@RequestParam String name, // 사용자
			@RequestParam MultipartFile musicPath, // 음악파일
			@RequestParam MultipartFile file, // 사진파일
			Model model,
			MultipartHttpServletRequest mtRequest) throws IOException{
	
		MultipartFile mFile = mtRequest.getFile("file");
		String path ="C:\\Users\\juho\\Desktop\\musicTest\\cover\\";
		
		System.out.println("--------------");
		System.out.println("가수 : " + artist);
		System.out.println("제목 : " + title);
		System.out.println("앨범 : " + album);
		System.out.println("장르 : " + genre);
		System.out.println("발매연도 : " + year);
		System.out.println("파일 : " + musicPath.getOriginalFilename());
		System.out.println("가사");
		System.out.println(lylics);
		System.out.println("이미지파일 변환 : " + mFile);
		System.out.println("표지 저장 : " + path);
		System.out.println("사용자 : " + name);
		System.out.println("--------------");
		
		String testName = "juho";
		String cImage = "";
		String ext = "";
		String image = "";
		String testFile = "C:\\Users\\juho\\Desktop\\musicTest\\"+musicPath.getOriginalFilename();
		
		File mp3File = new File(testFile);
		if(!mp3File.exists()) {
			System.out.println("파일이 존재하지 않습니다." + testFile);
		}
		try {
			AudioFile audioFile = AudioFileIO.read(mp3File);
			Tag tag = audioFile.getTag();
			if(tag == null) {
				System.out.println("태그를 찾을 수 없습니다." + testFile);
				tag = new ID3v24Tag();
				audioFile.setTag(tag);
			}
			tag.setField(org.jaudiotagger.tag.FieldKey.TITLE, title);
			tag.setField(org.jaudiotagger.tag.FieldKey.ARTIST, artist);
			tag.setField(org.jaudiotagger.tag.FieldKey.ALBUM, album);
			tag.setField(org.jaudiotagger.tag.FieldKey.LYRICS, lylics);
			tag.setField(org.jaudiotagger.tag.FieldKey.GENRE, genre);
			tag.deleteField(org.jaudiotagger.tag.FieldKey.YEAR);
			tag.setField(org.jaudiotagger.tag.FieldKey.YEAR,year);
			tag.deleteField(org.jaudiotagger.tag.FieldKey.COVER_ART);
			//tag.setField(org.jaudiotagger.tag.FieldKey.COVER_ART, image);
			 if (file != null && !file.isEmpty()) {
		            File tempFile = null;
		            try {
		                String originalFilename = file.getOriginalFilename();
		                String fileExtension = "";
		                if (originalFilename != null && originalFilename.contains(".")) {
		                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
		                }

		                tempFile = File.createTempFile("coverArt-", fileExtension);
		                System.out.println("1. 임시 파일 생성 성공: " + tempFile.getAbsolutePath());
		                file.transferTo(tempFile);
		                System.out.println("2. 임시 파일에 데이터 복사 완료. 크기: " + tempFile.length() + " bytes");
		                Artwork artwork = ArtworkFactory.createArtworkFromFile(tempFile);
		                System.out.println("3. Artwork 객체 생성 성공 여부: " + (artwork != null));
		                if (artwork != null) {
		                    System.out.println("4. Artwork 데이터 크기: " + artwork.getBinaryData().length + " bytes");
		               }
		                if (artwork != null) {
		                    tag.setField(artwork);
		                    System.out.println("5. 앨범 표지 태그에 설정 완료.");
		                } else {
		                    System.err.println("Artwork 객체가 유효하지 않아 이미지를 추가할 수 없습니다.");
		                }

		            } catch (IOException e) {
		                System.err.println("앨범 표지 처리 중 오류: " + e.getMessage());
		                // 오류가 발생해도 나머지 태그는 저장될 수 있도록 예외만 출력
		            } finally {
		                // 작업 완료 후 임시 파일 삭제
		                if (tempFile != null && tempFile.exists()) {
		                    tempFile.delete();
		                }
		            }
		        }
			
			AudioFileIO.write(audioFile);
		} catch (org.jaudiotagger.audio.exceptions.CannotWriteException e) {
	        // 가장 구체적인 예외를 먼저 처리
	        System.err.println("파일 쓰기 오류: 쓰기 권한이 없거나 파일이 잠겨있을 수 있습니다.");
	        e.printStackTrace();
	        return "errorPage";
	    } catch (IOException e) {
            System.err.println("파일 입출력 오류: " + e.getMessage());
        } catch (org.jaudiotagger.tag.TagException e) {
            System.err.println("태그 처리 오류: " + e.getMessage());
        } catch (org.jaudiotagger.audio.exceptions.ReadOnlyFileException e) {
            System.err.println("읽기 전용 파일 오류: " + e.getMessage());
        } catch (org.jaudiotagger.audio.exceptions.InvalidAudioFrameException e) {
            System.err.println("유효하지 않은 오디오 프레임 오류: " + e.getMessage());
        } catch (Exception e) { // 기타 예상치 못한 오류 처리
            System.err.println("알 수 없는 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }

//		
//		
//		if(mFile != null) {
//			if(!mFile.isEmpty()) {
//				cImage = mFile.getOriginalFilename();
//				ext = cImage.substring(cImage.lastIndexOf("."));
//				image = artist+"-"+title+ext;
//				System.out.println(image);
//				try {
//					mFile.transferTo(new File(path+image));
//				}catch(IOException e) {
//					System.out.println("실패");
//				}
//			}
//			
//		}
//		
//		String msg="";
//		String loc="";
//		
//		try {
//			msg = "mp3 Tag 저장 완료!";
//			loc = "/"+testName+"List";
//		}catch(Exception e) {
//			msg = "mp3 Tag 저장 실패!";
//			loc = "/"+testName+"List";
//			
//		}
//		model.addAttribute("msg",msg);
//		model.addAttribute("loc",loc);
//		
//		return "common/msg";
		return "add/tagUpload";
	}
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
