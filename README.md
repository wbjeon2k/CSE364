[![Build-Test-Action](https://github.com/wbjeon2k/CSE364/actions/workflows/build-test-action.yml/badge.svg)](https://github.com/wbjeon2k/CSE364/actions/workflows/build-test-action.yml)

CSE364 Group 4, Team Woongbae without Woongbae (TWwW)
===
# [Milestone 4] Readme (General user guide)

## 1. What TWwW is about

Hi everyone, welcome to TWwW service, new kind of movie recommendation system for improving quality of your life.
Are you sick and tired of existing movie recommedation systems?
They used to show just the list of famous Hollywood movies and similar series of movies regardless of __who you are__.
But, don't worry about it anymore. Our new movie recommendation system is going to solve these information asymmetries and provide special personalized system.
In this guideline, we are going to introduce __how to use our service__ in terms of general users and __how the TWwW system works__.

## 2. How to use TWwW

TWwW recommendation system is based on __Web Service__. So, you will approach web pages when you use this recommendation service.
There are three kinds of html pages, which are `index.html`, `users.html` and `movies.html`.
And let me explain each pages one by one, and how to use these pages.

### 2.1 how to start TWwW

Follow these steps:
    
    1. Download Dockerfile and TWwW.war, and put them together within same folder.
    2. $ docker build -t image_name /path/to/Dockerfile
    3. $ docker run -d -p 8080:8080 image_name
    4. In your host machine, you can access the application in a web browser by visiting http://localhost:8080/TWwW

### 2.2 index.html
    
   This page is the first page you can see when you start this program.
   Basically, each page has a common navigation bar, which is composed of `Home`, `User Information`, `Favorite Movie`.
   If you click one of them, you can go to the page connected with navigation bar buttons.
    
   This page show that the lists of movie recommendation based on just __genre__, including `all`,`action`,`adventure`,`drama`, and `comedy`.
   Each genre has 10 kinds recommended movies, which are shown as the 10 images of movie posters.
   If you click one of the posters in a certain genre, you can go to the corresponding IMDB webpage.
    
   Therefore, if you want to watch a specific gerne of popular movies that many people enjoyed, just __scroll down to the genre you want in this page__.
    
### 2.3 users.html
    
   If you click `User Information` button on the navigation bar on the top of the page, you can come to this page.
   In this page, you can get your own recommendation list when you put your information into the page, which are `Gender`,`Age`,`Occupation`, and `Genres`.
    
   Follow these steps:
        
       1. Put your information using selecting tools and check boxes.
       2. You don't need fill out all the information.
       3. Press the submit button.
       4. You can figure out 10 kinds of movies recommended from your personal information.
    
### 2.4 movies.html
        
   If you click `Favorite Movie` button on the navigation bar, you can come to this page.
   In this page, you can have your own recommendation list when you type movie `title` you enjoyed to watch and limit, the number of recommended movies.
   TWwW service figures out your tastes and recommend new movies for you.
    
   Follow these steps:
    
       1. Type the type of movie and limit
       2. You don't have to fill out all the inputs.
       3. Press the submit button
       4. You can find N-numbers recommended movies. The default value of limit is 10.

### 2.5 ERROR CODE

If ERROR is coming out, check your inner code and input data using the ERROR messages from the web browser.

- Invalid input

    For example:
    
        1. "Invalid genre string": you should check movie genre inputs on the users.html page or URL.
        2. "Limit must be positive integer greater than zero": your limit input data were smaller than 0.
        3. "Cannot find movie title": you typed wrong movie title on the movies.html page or URL.
    
- Inner server ERROR

    For example:
    
        1. 5XX ERROR code: you should check your code added to the TWwW service when customizing.
        2. "Error in CreateModel : ...": there were some problems when building recommendation model.
        3. "No movies to recommend": you should check your movie dataset. 
    
### 2.6 User Tips

You can `share` your own movie lists using URL, add them to your bookmarks

For example,
    
If you insert personal information into the pages, you can find http://localhost:8080/TWwW/users.html?gender=M&age=20&occupation=&genres=Acition on the browser address bar.
You can share the recommended movie lists with your friends using this URL

       
## 3. How TWwW works

Before talking about how it works, let me tell you why TWwW service has different workflow with any other recommendation system first.

### 3.1 What's difference?

One of the important problems of recommendation systems is to find a balance from many kinds of factors, user informaton, history and so on.
So, many similar services used to misunderstand some users' tastes.
For example, they just recommended popular movies, trusted the rating from small numbers of users, or could not introduce the movies that have not been rated yet.
In addition, recommendation system can be used for not only OTT but also any kind of field, so system need to have flexiability and keep maintenance.

Therefore, TWwW service provide `the personalized recommendation` from machine learning and the opportunities `easy to customize` the entire service using powerful architectures.

And let me move on how TWwW works to achieve these goals.

### 3.2 Workflow

TWwW systems has 4 kinds of system architectures, which are __Data Manager__, __Preprocessor Engine__, __Web Engine__, and __ML model Manager__.

Let's see how it works

- Data Manager

It uses `Mongo DB` to process big sized of data efficiently and automatically.

- ML model Manager

The `Spark` is used for training the input data and making the model using machine learning.

- Preprocessor Engine

This is the main part of recommendation system. It brings the data from the `Mongo DB` and builds the ML model from the `Spark`,
and makes the recommeded movie lists based on the input data from web pages.

- Web Engine

It is the front-end of users, and has many direct interaction with the people who use the TWww system. The information that users put in is moved into controllers
and receive the response from the server by using `Jquery` and `Rest API`.

Each part of architectures has great capsulation functionality, so they can achieve good maintanence as well as their goals 

# [Milestone 3] Readme

## 1. Milestone 3 Design for Part 1 and Part 2

### 1.1 Part 1: REST API  

A brief workflow of a REST API service consist of 3 steps.  
First, a `Controller` class, annotated as `@RestController`, receives an input as a JSON type.  
Second, `@RequestBody` annotation enables to convert the given input as a `Resource` class.  
Third, the `Controller` processes the input and returns the corresponding output.  

Following contents of part `1.1` is a description about each components of our implementation of REST API.

#### 1.1.1 Controller class  

A controller class is mapped to a certain url via `@GetMapping` annotation.  
Then, the controller receives the input as a resource class form via `@RequestBody` annotation.  
Finally, the controller processes the input and returns an ouput, or returns an exception if there is an error.  

A `@RestControllerAdvice` class is not mapped to a certain url.  
Instead, it works as a global manager of all controllers and process exception handling.  

There are 2 controller classes, and a controller advice in our project. Below is the description about those 3 controller classes.  

- RecommendationController  

    `RecommendationController` is mapped to the url `/users/recommendations`. This is the controller responsible for user inputs.  

    `RecommendationController` has 2 methods, those are `Recommend( @RequestBody RequestByUser)` and `Recommend( @RequestBody RequestByMovie)`.
`Recommend( @RequestBody RequestByUser)` returns the output for the input given with user informations; gender, age, occupation, and genre.
`Recommend( @RequestBody RequestByMovie)` returns the ouput for the input given with movie title and limit.

- ErrorController

    `ErrorController` is mapped to the url `/error`.  

    This is the controller which handles exception thrown from web query operation.  
    It mainly handles errors for invalid CRUD methods, ill-typed inputs, and other various request errors.  

    ErrorController returns web request error, which corresponds to HTTP error code 4XX.

    For example, if an input is given as an invalid `PUT` method instead of the valid `GET` method,  
Errorcontroller receives the exception from the Servlet, extracts the error code and the error message,  
and returns this information to the ExceptionController as an Exception type.  

- ExceptionController  

    ExceptionController is annotated as `@RestControllerAdvice`, which is a global exception handler for the whole application.  
    Every exceptions made during the application operations could be handled in here, even though there is no designated controller for the exception.  

    ExceptionController returns Internal server error, which corresponds to HTTP error code 5XX.

    Exceptions due to internal server operations are not handled by the ErrorController.  
    They are sent directly to the ExceptionController in the Exception type.  
    For example, exceptions thrown in `preprocessimpl` are made by the `Spark` library operation, and they are catched at this ExceptionController.  

#### 1.1.2 Resource Class  

An input given in JSON form is converted into a resource class via @Requestbody annotation.  

A typical resource class consist of private attributes, and correspoding getter/setter for each attributes.  

There are 4 resource classes in this project.  

- RequestByUser  

    RequestByUser includes 4 attributes: gender, age, occupation, genre.  
    RequestByUser is used to get user input with above 4 attributes.  

- RequestByMovie  

    RequestByMovie includes 2 attributes: title, limit.  
    RequestByMovie is used to get user input with above 2 attributes.  
    `limit` is set by 10 as a default value.  

- Recommendation  
    Recommendation includes 3 attributes: title, genre, imdb.  
    `title` is the title of the recommended movie.  
    `genre` is the corresponding genre of the recommended movie.  
    `imdb` is the corresponding imdb link of the movie.  

- Error

    Error includes 2 attributes: statuscode, message.
    statuscode is the error code, typically indicates HTTP 4XX or 5XX errors.  
    message is the error message in the Exception, to indicate what kind of error occured in which operation.  

### 1.2 Part 2: Recommendation based on movie title  

Recommendation based on a movie title uses the prediction model implemented in the Milestone 2.  

Below is the pseudocode of the recommendation algorithm.  

0. Find the given movie from the entire movie list
    - Regularize the given title into lower-case letters, remove blanks.  
    - Find the movie from the movie list with the processed title.
1. Generate the expected rating of the given movie for all users.  
    - Filter out the given movie to prevent duplicate recommendation.  
2. Sort the users by expected rating and generate the user sample.  
    - Filter out users with too less rating reviews to enhance recommendation quality.  
    - Sort the users with the expected rating of the given movie.  
    - Pick `U` number of users from the top.  
    - Maximum size of user sample is defined by `MAX_PAIR_COUNT / filteredMovieList.size()`, which is approximately 200.  
3. Generate recommendation movie pool
    - Filter out movies with too less rating reviews to enhance recommendation quality.  
    - Filter out the given movie to prevent duplicate recommendation.  
    - Assume size of the movie pool as `M`.
4. Generate expected rating for all movies in movie pool, for all users in user sample.  
    - Size of the user sample is `U`.
    - Size of the movie pool is `M`.
    - Generate `U*M` number of expected ratings.  
5. Calculate average expected rating of each movies in the movie pool.
6. Select top `limit` number of movies with genre and average rating.  
    - Pick up to `limit/2` movies with similar genre with the given movie.  
        - For example, given a genre list of `A|B|C`,
        - Pick 5 highest rating movies with genre `A|B|C`.  
        - If there are less than 5 of them, pick from `A|B`, `B|C`, `C|A` groups.
        - If there are less than 5 of them, pick from `A`, `B`, `C` groups.
    - Pick rest of `limit/2` movies with the highest average rating.
        - Do not pick duplicates selected from the previous step.  
7. Return the recommendation list.  

  
## 2. How to Use  

- An input with gender, age, occupation, genre:  
`curl -X GET http://localhost:8080/users/recommendations -H "Content-type:application/json" -d '{"gender": "F", "age": "25", "occupation": "Grad student", "genre": "Action"}'`  
- An input with title, limit:  
`curl -X GET http://localhost:8080/movies/recommendations -H 'Content-type:application/json' -d '{"title": "Toy Story (1995)", "limit": 20}'`  


## 3. Handling Errors  

- This program handles various errors via java `Exception` class.  
Displaying all errors are nearly impossible, so we present some of the error messages here.  

- 404 error message:  
`{"statusCode":404,"message":"Not Found"}`  

  
## 4. Example Output  

This is an example output for `curl -X GET http://localhost:8080/movies/recommendations -H 'Content-type:application/json' -d '{"title": "Toy Story (1995)", "limit": 20}'`  

```JSON
[
  {
    "title": "Toy Story 2 (1999)",
    "genre": "Animation|Children's|Comedy",
    "imdb": "http://www.imdb.com/title/tt0120363"
  },
  {
    "title": "Bug's Life, A (1998)",
    "genre": "Animation|Children's|Comedy",
    "imdb": "http://www.imdb.com/title/tt0120623"
  },
  {
    "title": "Chicken Run (2000)",
    "genre": "Animation|Children's|Comedy",
    "imdb": "http://www.imdb.com/title/tt0120630"
  },
  {
    "title": "Saludos Amigos (1943)",
    "genre": "Animation|Children's|Comedy",
    "imdb": "http://www.imdb.com/title/tt0036326"
  },
  {
    "title": "American Tail, An (1986)",
    "genre": "Animation|Children's|Comedy",
    "imdb": "http://www.imdb.com/title/tt0090633"
  },
  {
    "title": "Aladdin and the King of Thieves (1996)",
    "genre": "Animation|Children's|Comedy",
    "imdb": "http://www.imdb.com/title/tt0115491"
  },
  {
    "title": "American Tail: Fievel Goes West, An (1991)",
    "genre": "Animation|Children's|Comedy",
    "imdb": "http://www.imdb.com/title/tt0101329"
  },
  {
    "title": "Rugrats Movie, The (1998)",
    "genre": "Animation|Children's|Comedy",
    "imdb": "http://www.imdb.com/title/tt0134067"
  },
  {
    "title": "Adventures of Rocky and Bullwinkle, The (2000)",
    "genre": "Animation|Children's|Comedy",
    "imdb": "http://www.imdb.com/title/tt0131704"
  },
  {
    "title": "Wrong Trousers, The (1993)",
    "genre": "Animation|Comedy",
    "imdb": "http://www.imdb.com/title/tt0108598"
  },
  {
    "title": "Passion in the Desert (1998)",
    "genre": "Adventure|Drama",
    "imdb": "http://www.imdb.com/title/tt0125980"
  },
  {
    "title": "Chushingura (1962)",
    "genre": "Drama",
    "imdb": "http://www.imdb.com/title/tt0055850"
  },
  {
    "title": "Before the Rain (Pred dozhdot) (1994)",
    "genre": "Drama",
    "imdb": "http://www.imdb.com/title/tt0110882"
  },
  {
    "title": "Raiders of the Lost Ark (1981)",
    "genre": "Action|Adventure",
    "imdb": "http://www.imdb.com/title/tt0082971"
  },
  {
    "title": "Star Wars: Episode IV - A New Hope (1977)",
    "genre": "Action|Adventure|Fantasy|Sci-Fi",
    "imdb": "http://www.imdb.com/title/tt0076759"
  },
  {
    "title": "Singin' in the Rain (1952)",
    "genre": "Musical|Romance",
    "imdb": "http://www.imdb.com/title/tt0045152"
  },
  {
    "title": "Sanjuro (1962)",
    "genre": "Action|Adventure",
    "imdb": "http://www.imdb.com/title/tt0056443"
  },
  {
    "title": "It's a Wonderful Life (1946)",
    "genre": "Drama",
    "imdb": "http://www.imdb.com/title/tt0038650"
  },
  {
    "title": "Maya Lin: A Strong Clear Vision (1994)",
    "genre": "Documentary",
    "imdb": "http://www.imdb.com/title/tt0110480"
  },
  {
    "title": "Schindler's List (1993)",
    "genre": "Drama|War",
    "imdb": "http://www.imdb.com/title/tt0108052"
  }
]

```

> The output can be different if the trained model is not the same.

## 5. Roles of Each Member  

- Kim Taeyeong
  * Designed and implemented resource classes and REST controller.  
  * Designed and implemented the movie recommendation mechanism.
  * Improved Spark recommendation engine reliability by adding checkpoint.

- Park Sangbeen, 
  * Designed and implemented the movie recommendation mechanism.

- Jeon Woongbae:  
  * Designed and implemented resource classes and REST controller.
  * Designed the movie recommendation mechanism.
  * FYI: Wrote this file!
    
- An Jinmyeong:  
  * Designed and implemented resource classes and REST controller.
  * Designed the movie recommendation mechanism.

# [Milestone 2] Readme

## 1. Recommendation Algorithm Design

### 1.1 Summary of the recommendation algorithm
Our team has implemented movie recommendation algorithm based on Alternating Least Square(ALS) algorithm.  
ALS algorithm library is supported by [org.apache.spark.mllib](https://spark.apache.org/docs/latest/mllib-collaborative-filtering.html#collaborative-filtering) in [Spark](https://spark.apache.org/docs/latest/index.html).  

Inputs for the prediction model are a list of users and a list of movies.  
The prediction model generated from ALS estimates the rating for each movie in the given list of movie.  
By iterating for all users, it is able to estimate the average rating of a user group for each movie.  
Inputs for the prediction model was refined with average rating and number of ratings by users, to enhance prediction quality.  

### 1.2 What is ALS?  

ALS is a way to implement Collaborative Filtering(CF), which is a method to estimate a user's preference information based on preference information of other people.  

In this movie recommendation system, CF is used to estimate rating of a movie without a user's previous rating information on that specific movie.  

Original ALS algorithm was introduced by [Koren et al](https://dl.acm.org/doi/10.1109/MC.2009.263), and is supported by Spark library.  

If a `rank` is set by size of `n`, number of users set by `U`, number of movies set by `M`, two data frame matrices are made.  

First dataframe matrix is for users, with `U * rank` size.  
Second dataframe matrix is for movies, with `rank * M` size.  

Multiplying `U * rank` matrix and `rank * M` matrix makes a `U * M` size **inference matrix**.  

ALS reduces the loss between the inference matrix and the actual dataset by Stochastic Gradient Descent(SGD).  

Parameter settings: `rank = 10`, `number of iterations = 20`, `regularization parameter = 0.01`  

### 1.3 Refining input data for ALS prediction  
Target user list and target movie list for prediction were refined to enhance prediction quality.  

Movies with too little amount of user ratings were excluded from prediction target to enhance prediction quality.  

When there are too many users in a group, a subset of users with sufficient amount of ratings is selected as a prediction target.  

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
      - Filter user list with given input parameters. (e.g. `"F" "25" "Grad Student"` "Adventure")
    - Target movie group:
      - Filter movie list with given input parameters. (e.g. "F" "25" "" `"Adventure|Action"`)
      - Exclude movies with a total number of reviews less than 10 from users.
  

4. Generate estimated rating for the filtered movie list.
    - Assume filtered user list `UF`, number of filtered users `A`.
    - Assume filtered movie list `MF`, number or filtered movies `B`.
    - For all `UF[i]`, get estimated ratings for each movie in `MF`.
    - This takes `O(AB)` time, so an arbitrary upperbound `U` of `(A * B)` is set to enhance performance.  
      If `(A * B) > U`, then sort `UF` by the number of ratings a user left, and pick top `(U / B)` users.  
      
    - `U == ceil(2 * sqrt(6040)) * 4000`, which is `624,000`.
      - 6,040 is the total number of users in `users.dat` file.
      - 4,000 is the *approximate* total number of movies in `movies.dat` file.

    - Statistical reason why this upperbound `U` works:
      - Population size: 6,040 in the worst case (entire user set)
      - Sample size: `ceil(2 * sqrt(6040)) == 156`

      Based on the population size and the sample size,  
      confidence level(*신뢰 구간* in Korean) and margin of error(*오차범위* in Korean)  
      in the worst case can be calculated.
      - Confidence Level: 80%
      - Margin of Error: 5%
      
      This is good enough even in the worst case, when all 6,040 user groups are set as target.  


5. Sort and get top 10 movies.
    - Calculate average estimated rating for movies in `MF`.
    - Sort `MF` by the average estimated rating.
    - Pick top 10 from `MF`.
  
## 2. How to Use  

Execute below command with proper parameters.    

Memory parameters `-Xms1g -Xmx4g` should be added for this program.

- `java -Xms1g -Xmx4g -cp target/cse364-project-1.0-SNAPSHOT-allinone.jar kr.twww.mrs.Main "[M/F]" "[Age]" "[Occupation]" "[Genre(s)]"`

- This program treats `""` as **all**.
  - `"F" "25" "" "Adventure"` will return **adventure genre** movies for **25 years old**, **female** group, for **all occupations**.
  - `"" "" ""`, which is same as `"" "" "" ""`, will return top 10 **any genre** movies **regardless** of **gender**, **age**, and **occupation**.  

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
