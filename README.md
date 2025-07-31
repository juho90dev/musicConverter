# musicConverter
mp3변환 API를 활용하여 youtube영상을 mp3파일로 변환 후 저장.
또한 저장된 mp3파일을 다운로드하여 재생이 가능

### 사용 기술
- java 17
- Spring Boot 3.2.0
  - maven project
      - Dependencies
        - Spring Boot DevTools
        - Spring Web
        - Lombok
        - Spring Data JDBC
        - MyBatis Framework
        - Thymeleaf    
- Oracle Database
- Thymeleaf


### ver 1.1
- API를 통해youtube 음악 영상을 mp3파일로 변환(youtube 영상 주소 필요) 후 지정된 폴더에 저장
- 변환된 음원의 저장 경로와 해당 음원의 정보 DB에 저장
- 저장된 음원 다운로드 기능

### ver 1.2
- DB데이터 삭제(저장 경로에 저장된 파일 또한 삭제)
- 가수/제목 별 정렬
