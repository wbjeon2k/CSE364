# 임시 Readme

#### A short description of what you’ve finished for this milestone.
#### A description of how to run your program. An example of java command line will be good.
#### Roles of each member (i.e. who did what?)

1. 각자 무엇을 끝냈나요?
  Genre, Occupation 해당하는 rating들을 구하고 평균 점수를 계산한다.

2. 프로그램은 어떻게 작동시키면 되나요? java command line
  - ```java -cp ./target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar kr.twww.mrs.Main [Genre] [Occupation]```
  - example) ```java -cp ./target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar kr.twww.mrs.Main Adventure educator```

3. 각 멤버들은 무슨 역할을 했나요?

- 김태영:  
  * Docker 환경 구축
  * Project design:
    - Data parsing, data preprocessing, unit test high-level frame design, implement
    - main, Data parsing, data processing, unit test 역할 분담.
  * PM: 전체 프로젝트 총괄. 팀원들 구현 어려움 있으면 도움.
  
- 박상빈:  
  * dataPreprocess 구현. dataPreprocess: GetGenreList, GetOccupationList, GetScoreList
  * 사용자 입력 (genre, occupation) 입출력 전처리
  * 전처리 된 user, movie, rating list 에서 genre, occupation 바탕으로 필터링  

- 안진명: 
  * dataReader 구현. dataReader: ReadTextFromFile, ToUserList, ToMovieList, ToRatingList
  * movie, user, rating.dat 파싱
  * 파싱 된 string data 를 movie, user, rating list 로 변환.

- 전웅배: 
  * JUnit test 담당
  * dataReader, dataPreprocess method 별로 JUnit test code, test case 작성.
  * dataReaderTest: 4 tests (dataReader: ReadTextFromFile, ToUserList, ToMovieList, ToRatingList)
  * dataPreprocesTest: 5 tests (GetGenreList, GetOccupationList, GetScoreList, MovieFilter, UserFilter)
