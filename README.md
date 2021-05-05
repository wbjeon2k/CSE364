CSE364 Group 4, Team Woongbae without Woongbae (TWwW)
===

# [Milestone 2] Readme

## 1. Recommendation Algorithm Design

### 1.1 Summary of the recommendation algorithm
Our team has implemented movie recommendation algorithm based on Alternating Least Square(ALS) algorithm. ALS library is supported by [org.apache.spark.mllib](https://spark.apache.org/docs/latest/mllib-collaborative-filtering.html#collaborative-filtering) in [Spark](https://spark.apache.org/docs/latest/index.html).

Inputs for the prediction model are a list of users and a list of movies.  
The prediction model generated from ALS estimates the rating for each movie in the given list of movie.  
By iterating for all users, it is able to estimate the average rating of a user group for each movie.  
Inputs for the prediction model was refined with average rating and number of ratings by users, to enhance prediction quality.  

### 1.2 What is ALS?
ALS is a way to implement Collaborative Filtering(CF), which is a method to estimate a user's preference information based on preference information of other people.  

In this movie recommendation system, CF is used to estimate rating of a movie without a user's previous rating information on that specific movie.  

Original ALS algorithm was introduced by [Koren et al](https://dl.acm.org/doi/10.1109/MC.2009.263), and is supported by Spark library.  

If a `rank` is set by `n`, number of users set by `U`, number of movies set by `M`, two data frame matrices are made.  

First dataframe matrix is for users, with `U * rank` size.  
Second dataframe matrix is for movies, with `rank * M` size.  

Multiplying `U * rank` matrix and `rank * M` matrix makes a `U * M` size inference matrix.  

ALS reduces the loss between the inference matrix and the actual dataset by Stochastic Gradient Descent(SGD).  

Parameter settings: `rank = 10`, `number of iterations = 20`, `regularization parameter = 0.01`  

### 1.3 Refining input data for ALS prediction  
Target user list and target movie list for prediction were refined to enhance prediction quality.  

Movies with too little amount of user ratings were excluded from prediction target to enhance prediction quality.  

When there are too many users in a group, subset of users with a sufficient amount of ratings is selected as a prediction target.  

### 1.4 Pseudocode of the recommendation algorithm
Codes for refinement process of the inputs is in `preprocess/predict/PreprocessorImpl.java` file.  
Codes for the prediction model is in `preprocess/predict/PredictorImpl.java` file.

Below is a pseudocode of the movie recommendation algorithm.

1. Check [checksum](https://en.wikipedia.org/wiki/Checksum) of given data files (`ratings.dat`, `user.dat`, `movies.dat`) and trained model data file.
    - If checksum is valid and model already exists, skip generating new model, proceed to prediction.  


2. Generate prediction model with ALS algorithm.  
    - Parameter settings: `rank = 10`, `iteration = 20`, `learning rate = 0.01`  


3. Generate prediction targets.
    - Target user group:  
      - Filter user list with given input parameters. (e.g. `"F" "25" ""` "Adventure")
    - Target movie group:
      - Filter movie list with given input parameters. (e.g. "F" "25" "" `"Adventure"`)
      - Exclude movies with a total number of reviews less than 10 from users.
  

4. Generate estimated rating for the filtered movie list.
    - Assume filtered user list `UF`, number of filtered users `A`.
    - Assume filtered movie list `MF`, number or filtered movies `B`.
    - For all `UF[i]`, get estimated ratings for each movie in `MF`.
    - This takes `O(AB)` time, so an arbitrary upperbound `U` of `(A * B)` is set to enhance performance.  
      If `(A * B) > U`, then sort `UF` by the number of ratings a user left, and pick top `(U / B)` users.  
      
    - `U == ceil(2 * sqrt(6040)) * 4000`, which is 624,000.
      - 6,040 is the total number of users in `users.dat` file.
      - 4,000 is the *approximate* total number of movies in `movies.dat` file.

    - Statistical reason why this upperbound `U` works:
      - Population size: 6,040 in the worst case (entire user set)
      - Sample size: `ceil(2 * sqrt(6040)) == 156`

      Based on the population size and the sample size,  
      confidence level(*신뢰 구간* in korean) and margin of error(*오차범위* in korean) can be calculated.
      - Confidence Level: 80%
      - Margin of Error: 5%
      
      This is enough even in the worst case, when all 6,040 user groups are set as target.  


5. Sort and get top 10 movies.
    - Calculate average estimated rating for movies in `MF`.
    - Sort `MF` by the average estimated rating.
    - Pick top 10 from `MF`.
  
## 2. How to Use
Execute below command with proper parameters.  

- This program treats `""` as **all**.
  - `"F" "25" "" "Adventure"` will return **adventure genre** movies for **25 years old**, **female** group, for **all occupations**.
  - `"" "" ""`, which is same as `"" "" "" ""`, will return top 10 **any genre** movies **regardless** of **gender**, **age**, and **occupation**.
  

Memory parameters `-Xms1g -Xmx4g` should be added for this program.

- `java -Xms1g -Xmx4g -cp target/cse364-project-1.0-SNAPSHOT-allinone.jar kr.twww.mrs.Main "[M/F]" "[Age]" "[Occupation]" "[Genre(s)]"`

## 3. Handling Errors
- For invalid gender like "X", error message is printed like below example.
    ```
    Error: Invalid gender character
    Error: Movie recommendation failed
    ```

- For invalid age like "fifty", error message is printed like below example.
    ```
    Error: Invalid age string
    Error: Movie recommendation failed
    ```

- For invalid occupations like "Wizard", error message is printed like below example.
    ```
    Error: Invalid occupation string
    Error: Movie recommendation failed
    ```

- For invalid genre like "Co", error message is printed like below example.
    ```
    Error: Invalid genre string
    Error: Movie recommendation failed
    ```

- If our program fails to generate or save a checksum for checking model data, error message is printed like below example.
    ```
    Error: Get checksum failed
    ```
    ```
    Error: Save checksum failed
    ```

- If our program fails to create prediction model, error message is printed like below example.
    ```
    Error: Create model failed
    Error: Movie recommendation failed
    ```
  
## 4. Example Output
- Example output for `"F" "25" "Grad student" "Action|Comedy"`:
  ```
  1. Sanjuro (1962) (http://www.imdb.com/title/tt0056443)
  2. Visitors, The (Les Visiteurs) (1993) (http://www.imdb.com/title/tt0108500)
  3. Close Shave, A (1995) (http://www.imdb.com/title/tt0112691)
  4. Life Is Beautiful (La Vita ? bella) (1997) (http://www.imdb.com/title/tt0118799)
  5. Sum of Us, The (1994) (http://www.imdb.com/title/tt0111309)
  6. American Beauty (1999) (http://www.imdb.com/title/tt0169547)
  7. Wrong Trousers, The (1993) (http://www.imdb.com/title/tt0108598)
  8. Still Crazy (1998) (http://www.imdb.com/title/tt0149151)
  9. Cinema Paradiso (1988) (http://www.imdb.com/title/tt0095765)
  10. Different for Girls (1996) (http://www.imdb.com/title/tt0116102)
  ```

- Example output for `"M" "25" "Grad student" "Action|Comedy"`:
  ```
  1. Sanjuro (1962) (http://www.imdb.com/title/tt0056443)
  2. Godfather, The (1972) (http://www.imdb.com/title/tt0068646)
  3. Star Wars: Episode IV - A New Hope (1977) (http://www.imdb.com/title/tt0076759)
  4. Raiders of the Lost Ark (1981) (http://www.imdb.com/title/tt0082971)
  5. Monty Python and the Holy Grail (1974) (http://www.imdb.com/title/tt0071853)
  6. Star Wars: Episode V - The Empire Strikes Back (1980) (http://www.imdb.com/title/tt0080684)
  7. Godfather: Part II, The (1974) (http://www.imdb.com/title/tt0071562)
  8. Saving Private Ryan (1998) (http://www.imdb.com/title/tt0120815)
  9. Seven Samurai (The Magnificent Seven) (Shichinin no samurai) (1954) (http://www.imdb.com/title/tt0047478)
  10. Close Shave, A (1995) (http://www.imdb.com/title/tt0112691)
  ```

- Example output for `"F" "30" "" "Drama"`:
  ```
  1. Chushingura (1962) (http://www.imdb.com/title/tt0055850)
  2. Before the Rain (Pred dozhdot) (1994) (http://www.imdb.com/title/tt0110882)
  3. Eighth Day, The (Le Huiti?me jour ) (1996) (http://www.imdb.com/title/tt0116581)
  4. Schindler's List (1993) (http://www.imdb.com/title/tt0108052)
  5. Shawshank Redemption, The (1994) (http://www.imdb.com/title/tt0111161)
  6. Hearts and Minds (1996) (http://www.imdb.com/title/tt0116506)
  7. To Kill a Mockingbird (1962) (http://www.imdb.com/title/tt0056592)
  8. Life Is Beautiful (La Vita ? bella) (1997) (http://www.imdb.com/title/tt0118799)
  9. Rain (1932) (http://www.imdb.com/title/tt0023369)
  10. It's a Wonderful Life (1946) (http://www.imdb.com/title/tt0038650)
  ```

- Example output for `"M" "20" "" "Action|Drama|Comedy"`:
  ```
  1. Autumn Sonata (H?stsonaten ) (1978) (http://www.imdb.com/title/tt0077711)
  2. Sanjuro (1962) (http://www.imdb.com/title/tt0056443)
  3. Shawshank Redemption, The (1994) (http://www.imdb.com/title/tt0111161)
  4. Visitors, The (Les Visiteurs) (1993) (http://www.imdb.com/title/tt0108500)
  5. Monty Python and the Holy Grail (1974) (http://www.imdb.com/title/tt0071853)
  6. Star Wars: Episode IV - A New Hope (1977) (http://www.imdb.com/title/tt0076759)
  7. Raiders of the Lost Ark (1981) (http://www.imdb.com/title/tt0082971)
  8. After Life (1998) (http://www.imdb.com/title/tt0165078)
  9. Godfather, The (1972) (http://www.imdb.com/title/tt0068646)
  10. Matrix, The (1999) (http://www.imdb.com/title/tt0133093)
  ```

> The output can be different if the trained model is not the same.

## 5. Roles of Each Member
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
  * Refactored and improved DataReader part previously made in Milestone 1, enhanced data parsing speed.
  
---

# [Milestone 1] Readme

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
  * Enabled Docker setting, distributed the developing environment for the team.
  * *Project design*:
    - Designed and Milestone 1 project scheme, implemented primitive structure.
    - Categorized development tasks into 3 parts: *data parsing, data preprocessing, and unit test.*
  * *Project Manager*:
    - Divided and distributed the development role into 3 parts: data parsing, data preprocessing, and unit test.
    - Managed the development process, helped other 3 teammates when there is a development issue.
  
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
  * Implemented JUnit test code, generated test cases for every method in `dataReader` and `dataPreprocess` class.
  * `dataReaderTest`: 5 tests (`dataReaderTest`: `GetPathTest`, `ReadTextFromFileTest`, `ToUserListTest`, `ToMovieListTest`, `ToRatingListTest`)
  * `dataPreprocesTest`: 5 tests (`GetGenreListTest`, `GetOccupationTest`, `GetScoreListTest`, `MovieFilterTest`, `UserFilterTest`)
  * FYI: Wrote this file!
