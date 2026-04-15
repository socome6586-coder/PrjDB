# PrjDB

## 드라이버 다운로드 경로
https://mvnrepository.com/
 검색 : oracle
       ojdbc 11
     version 23.26.1.0.0
       Files : jar(7.3MB) 다운로드

sts Project : PrjDB 생성
  lib 폴더 생성
     ojdbc11-23.26.1.0.0.jar 붙여넣기

sts PrjDB project 선택, 오른쪽 마우스 버튼
    상단 Project 의 Properties 선택
    -> Java Build Path 
	-> Libraries 
	   classpath 를 선택
	     오른쪽 Add jar 버튼 클릭
		ojdbc11-23.26.1.0.0.jar 파일 선택

	PrjDB 프로젝트에
	  Referenced Libraries 항목이 생긴다