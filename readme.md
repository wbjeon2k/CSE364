## Milestone 1 Readme

#### 1. A short description of what this team finished for this milestone.
  We developed a program which
  - Receive user inputs, which are given a list of genres and an occupation.  
  - Extract the matching results from the rating.dat.   
  - Calculate the average rating score of the matching rating results.  

#### 2. A description of how to run this program. An example of java command line will be good.
  - ```java -cp ./target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar kr.twww.mrs.Main [Genre] [Occupation]```
  - example) ```java -cp ./target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar kr.twww.mrs.Main Adventure educator```

#### Roles of each team member (i.e. who did what?)

- Kim Taeyoung
  * Enabled Docker setting, distrubuted the devloping environment for the team.
  * *Project design*:
    - Designed and implemented Milestone 1 project high-level scheme,  
      categorized development tasks into 3 parts: *data parsing, data preprocessing, and unit test.*
  * *Project Manager*:
    - Divided and distributed the development role into 3 parts: data parsing, data preprocessing, and unit test.
    - Managed the development process, helped other 3 teammates when there is a devlopment issue.
  
- Park Sangbeen:  
  * Developed `dataPreprocess` class. `dataPreprocess` class includes: `GetGenreList`, `GetOccupation`, `GetScoreList`
  * Preprocessed given 2 user inputs, genres and occupation. (`GetGenreList`,  `GetOccupationList`)
  * Implemented `GetScoreList`, which is to find all the matching rating result from given preprocessed dataset and given user input.

- Ahn Jinmyeong: 
  * Developed `dataReader` class. `dataReader` class includes: `ReadTextFromFile`, `ToUserList`, `ToMovieList`, `ToRatingList`
  * Implemented string parser routine for `user.dat`, `ratings.dat`, `movies.dat`.
  * Implemented data process routine which makes a list of data from a parsed string. Converted raw `user/rating/movie.dat` into `ArrayList<User/Rating/Movie>`.

- Jeon Woongbae: 
  * Developed the entire JUnit unit tests by using [parametrized JUnit test](https://github.com/junit-team/junit4/wiki/Parameterized-tests).
  * Implemented JUnit test code, generated test cases for every methods in `dataReader` and `dataPreprocess` class.
  * `dataReaderTest`: 5 tests (`dataReaderTest`: `GetPathTest`, `ReadTextFromFileTest`, `ToUserListTest`, `ToMovieListTest`, `ToRatingListTest`)
  * `dataPreprocesTest`: 5 tests (`GetGenreListTest`, `GetOccupationTest`, `GetScoreListTest`, `MovieFilterTest`, `UserFilterTest`)
