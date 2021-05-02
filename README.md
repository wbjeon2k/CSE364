
# CSE364 Team 4, Team Woongbae without Woongbae (TWwW)  

## Milestone 2 Readme

#### 1. ALS recommendation algorithm
  Our team has implemented Alternating Least Square(ALS) algorithm via ALS library supported by [org.apache.spark.mllib](https://spark.apache.org/docs/latest/mllib-collaborative-filtering.html#collaborative-filtering) in (Spark)[https://spark.apache.org/docs/latest/index.html] library.

  ALS is a way to implement Collaborative Filtering(CF), which is a method used to estimate a user's preference information based on preference information of other people.
  In this movie recommendation system, CF is used to estimate rating of a movie without a user's previous rating information on that specific movie. 

  Original ALS algorithm was introduced by [Koren et al](https://dl.acm.org/doi/10.1109/MC.2009.263), and supported by Spark libray.

  If a rank is set by n, number of users set by U, number of movies set by M, two data frames are made.
  First dataframe is for user, with $U x n$ size. Secodne dataframe is for movie, with $n x M$ size.
  Multiplying $U x n$ matrix and $n x M$ matrix makes a $U x M$ size inference matrix.

  ALS reduces the loss between the inference matrix and the actual dataset by Stochastic Gradient Descent(SGD).

  We set the ranks as 10, number of iterations as 20, regularization parameter as 0.01.

  Our program automatically generate and check [checksum](https://en.wikipedia.org/wiki/Checksum) of input data files,
  and only generate new model only if the original input data has been changed.

#### 2. How to use this program

Execute below command with proper parameters.

java -Xms1g -Xmx4g -cp target/cse364-project-1.0-SNAPSHOT-allinone.jar kr.twww.mrs.Main "[M/F]" "[Age]" "[Occupation]" "Adventure"

#### 3. Handling errors

For invalid occupations like "Wizard", error message is printed like below example.
```
Error: Invalid gender character
Error: Cannot recommendation failed
```

For invalid gender like "X", error message is printed like below example.
```
Error: Invalid gender character
Error: Cannot recommendation failed
```

For invalid genre like "Co", error message is printed like below example.
```
Error: Invalid genre string
Error: Cannot recommendation failed
```

If our program fails to generate checksum, error message is printed like below example.
```
Error: Get checksum failed
Error: Save checksum failed
```

If our program fails to load prediction model, error message is printed like below example.
```
Error: Create model failed
Error: Load model failed
```

#### 4. Roles of each member

- Kim Taeyeong
  * Made conceptual design of recommendation engine with ALS algorithm.
  * Implemented recommendation engine with ALS algorithm.
  * Implemented checksum routine for data files and model files.
  * Engaged in overall aspect of this project. 

- Jeon Woongbae: 
  * Made conceptual design of recommendation engine with ALS algorithm.
  * Implemented unit test PredictorImplTest.
  * FYI: Wrote this file!

  - Park Sangbeen, An Jinmyeong:
  * Added new link.dat link parser.
  * Renovated and upgraded DataReader part previously made in Milestone 1, enhanced data parsing speed.

## Milestone 1 Readme

#### 1. A short description of what this team finished for this milestone.
  We developed a program which
  - Receive user inputs, which are given a list of genres and an occupation.  
  - Extract the matching results from the rating.dat.   
  - Calculate the average rating score of the matching rating results.  

#### 2. A description of how to run this program. An example of java command line will be good.
  - ```java -cp ./target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar kr.twww.mrs.Main [Genre] [Occupation]```
  - example) ```java -cp ./target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar kr.twww.mrs.Main Adventure educator```

#### 3. Roles of each team member (i.e. who did what?)

- Kim Taeyeong
  * Enabled Docker setting, distrubuted the devloping environment for the team.
  * *Project design*:
    - Designed and Milestone 1 project scheme, implemented primitive structure.
    - Categorized development tasks into 3 parts: *data parsing, data preprocessing, and unit test.*
  * *Project Manager*:
    - Divided and distributed the development role into 3 parts: data parsing, data preprocessing, and unit test.
    - Managed the development process, helped other 3 teammates when there is a devlopment issue.
  
- Park Sangbeen:  
  * Developed `dataPreprocess` class. `dataPreprocess` class includes: `GetGenreList`, `GetOccupation`, `GetScoreList`
  * Preprocessed given 2 user inputs, genres and occupation. (`GetGenreList`,  `GetOccupationList`)
  * Implemented `GetScoreList`, which is to find all the matching rating result from given preprocessed dataset and given user input.

- An Jinmyeong: 
  * Developed `dataReader` class. `dataReader` class includes: `ReadTextFromFile`, `ToUserList`, `ToMovieList`, `ToRatingList`
  * Implemented string parser routine for `user.dat`, `ratings.dat`, `movies.dat`.
  * Implemented data process routine which makes a list of data from a parsed string. Converted raw `user/rating/movie.dat` into `ArrayList<User/Rating/Movie>`.

- Jeon Woongbae: 
  * Developed the entire JUnit unit tests by using [parametrized JUnit test](https://github.com/junit-team/junit4/wiki/Parameterized-tests).
  * Implemented JUnit test code, generated test cases for every methods in `dataReader` and `dataPreprocess` class.
  * `dataReaderTest`: 5 tests (`dataReaderTest`: `GetPathTest`, `ReadTextFromFileTest`, `ToUserListTest`, `ToMovieListTest`, `ToRatingListTest`)
  * `dataPreprocesTest`: 5 tests (`GetGenreListTest`, `GetOccupationTest`, `GetScoreListTest`, `MovieFilterTest`, `UserFilterTest`)
  * FYI: Wrote this file!
