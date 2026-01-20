package com.jh.musicConverter.model.vo;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@Document(indexName = "music_archive")
public class MusicDocument {
	
	
	// JPA ID와 매칭 (엘라스틱서치 Document ID)
	@Id
	private int musicId;
	
	// 가수 (Artist): 자동 완성 검색 대상
    // 'search_as_you_type'으로 설정하여 접두어 검색(prefix search)을 최적화
	@Field(type = FieldType.Search_As_You_Type)
	private String artist;
	
	// 제목 (Title): 자동 완성 검색 대상
    @Field(type = FieldType.Search_As_You_Type)
    private String title;
    
    // 가사 (Lyrics): 전문 검색 대상 (자동 완성보다는 일반 검색에 사용)
    @Field(type = FieldType.Text)
	private String lyrics;
	
    // 장르 (Genre): 정확한 매칭에 적합한 KEYWORD 타입
    @Field(type = FieldType.Keyword)
	private String genre;
	
    // 앨범 (Album)
    // 앨범 이름도 자동 완성이 필요하다면 Search_As_You_Type을 사용하거나, 아니면 일반 Text/Keyword를 사용
    @Field(type = FieldType.Text)
	private String album;
	
    
    // 앨범 표지, 발매일, 파일 경로는 검색에 사용되지 않으므로 필요 시 Keyword 타입 사용
    @Field(type = FieldType.Keyword)
	private String albumCover;
	
    @Field(type = FieldType.Keyword)
	private String year;
	
    @Field(type = FieldType.Keyword)
	private String filePath;

    // 등록한 사용자 이름은 ID처럼 사용되므로 Keyword 타입 사용
    @Field(type = FieldType.Keyword)
	private String userName;
    
    
    
    
    // 참고: ManyToOne 관계였던 Users 객체 대신, 검색에 필요한 식별자(예: userName)만 저장하는 것이 일반적
}
