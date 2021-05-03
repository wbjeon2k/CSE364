CSE364 Group 4, Team Woongbae without Woongbae (TWwW)
===


# [Milestone 2] Readme

## 1. Recommendation Algorithm Design

### 1.1 Summary of the recommendation algorithm

Our team has implemented movie recommendation algorithm based on Alternating Least Square(ALS) algorithm. ALS library is supported by [org.apache.spark.mllib](https://spark.apache.org/docs/latest/mllib-collaborative-filtering.html#collaborative-filtering) in [Spark](https://spark.apache.org/docs/latest/index.html).

Inputs for the prediction model are a list of users and a list of movies.  

The prediction model generated from ALS estimates the rating for each movies in the given list of movie.  

By iterating for all users, it is able to estimate the average rating of a user group for each movies.  

Inputs for the prediction model was refined with average rating and number of ratings by users, to enhance prediction quality.  

### 1.2 What is ALS?

ALS is a way to implement Collaborative Filtering(CF), which is a method to estimate a user's preference information based on preference information of other people.  
In this movie recommendation system, CF is used to estimate rating of a movie without a user's previous rating information on that specific movie.  

Original ALS algorithm was introduced by [Koren et al](https://dl.acm.org/doi/10.1109/MC.2009.263), and is supported by Spark library.  

If a `rank` is set by `n`, number of users set by `U`, number of movies set by `M`, two data frame matrices are made.  
First dataframe matrix is for users, with `U * rank` size. Second dataframe matrix is for movies, with `rank * M` size.  

Multiplying `U * rank` matrix and `rank * M` matrix makes a `U * M` size inference matrix.  

ALS reduces the loss between the inference matrix and the actual dataset by Stochastic Gradient Descent(SGD).  

Parameter setting: `rank = 10, number of iterations = 20, regularization parameter = 0.01` 

### 1.2 Refining input data for ALS prediction

Target user list and target movie list for prediction were refined to enhance prediction quality.

Movies with too little amount of user ratings were excluded from predition target to enhance prediction quality.

When there are too many users in a group, subset of users with sufficient amount of ratings were selected as a prediction target.

### 1.3 Pseudocode of the recommendation algorithm

Code for the prediction model is in `preprocess/predict/PredictorImpl.java` file.  
Code for the input refining process is in `preprocess/predict/PreProcessorImpl.java` file.  

Below is a pseudocode of the recommendation algorithm.  

1. Check if given input data files (`ratings/user/movies.dat`) are identical to the previously given files.  
   Program automatically checks it via [checksum](https://en.wikipedia.org/wiki/Checksum).

2. If input data files are identical, skip generating new model. Load the predicton model.  

3. Generate prediction model with ALS algorithm. Parameter setting: `rank = 10, iteration = 20, learning rate = 0.01`  

4. Generate prediction targets.  
    - Target user group:  
      - Filter user list with given parameters. (e.g. F, Grad Student, etc.)  
    - Target movie group:  
      - Exclude movies with less than 10 total reviews from users in users.dat file.  
      - Filter movie list with given parameters. (e.g. Action|Comedy, etc.)  

5. Generate estimated rating for the filtered movie list.  
    - Assume filtered user list `UF`, number of filtered users `A`.  
    - Assume filtered movie list `MF`, number or filtered movies `B`.  
    - ```For all UF[i]: Get estimated ratings for each movie in MF```  
    - This takes `O(AB)` time, so an arbitrary upperbound `U` of `(A * B)` to enhance performance.  
      
      If `(A * B) > U`, then sort `UF` by the number of ratings a user left, and pick top `(U / B)` users.  
      
    - `U == 2 * sqrt(6600) * (3800)`, which is approximately 620,000.  
      6600 is the total number of users in `users.dat` file.  
      3800 is the total number of movies in `movies.dat` file.  

    - Statistical reason why this upperbound `U` works:  
        - Population size: 6600 in the worst case (entire user set)  
        - Sample size: 2*(sqrt(6600)) == 162  
      Based on the population size and the sample size,  
      confidence level(신뢰 구간 in Kor) and margin of error(오차범위 in Kor) can be calculated.  
        - Confidence Level: 80%  
        - Margin of Error: 5%  
      This is enough to support below specification in the worst case, when all 6600 user groups are set as target.  
      

6. Sort and get top 10 movies.  
    - Calculate average estimated rating for movies in MF.  
    - Sort MF by the average estimated rating.  
    - Pick top 10 from MF.  

## 2. How to Use  

Execute below command with proper parameters.  

- This program treats `""` as *all*.  
  For example:  
  - `"F" "25" "" "Adventure"` will return Adventure genre movies for 25 years old female group, for all occupations.  
  - `"" "" ""`, which is same as `"" "" "" ""`, will return top 10 movies for all all gender, age, and occupation.  

- Memory parameters `-Xms1g -Xmx4g` should be added to ensure the minimum and maximum amount of memory for this program.  

`java -Xms1g -Xmx4g -cp target/cse364-project-1.0-SNAPSHOT-allinone.jar kr.twww.mrs.Main "[M/F]" "[Age]" "[Occupation]" "[Genre(s)]"`  

## 3. Handling Errors  

- For invalid occupations like "Wizard", error message is printed like below example.  
    ```
    Error: Invalid gender character
    Error: Movie recommendation failed
    ```

- For invalid gender like "X", error message is printed like below example.  
    ```
    Error: Invalid gender character
    Error: Movie recommendation failed
    ```

- For invalid genre like "Co", error message is printed like below example.  
    ```
    Error: Invalid genre string
    Error: Movie recommendation failed
    ```

- If our program fails to generate a checksum for checking model data, error message is printed like below example.  
    ```
    Error: Get checksum failed
    Error: Save checksum failed
    ```

- If our program fails to create prediction model, error message is printed like below example.  
    ```
    Error: Create model failed
    Error: Load model failed
    ```
  
## 4. Example Output  

Example output for `F 25 Grad student Action|Comedy`:  
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

Example output for `M 25 Grad student Action|Comedy`:  
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

Example output for `F 30 "" Drama`:  
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
