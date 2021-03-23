package kr.twww.mrs.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import static org.junit.Assert.*;

/***
 * 원래 skeleton
 */

/*
public class DataReaderTest
{
    DataReaderImpl dataReader;

    @Before
    public void setUp() throws Exception
    {
       dataReader = new DataReaderImpl();
    }

    @After
    public void tearDown() throws Exception
    {
       dataReader = null;
    }

    @Test
    public void getUserList()
    {
        var userList =dataReader.GetUserList();
        assertNull(userList);
    }

}
*/

/**
 * Parametrized test 초기 시안.
 * 이런 형태로 다른 테스트들도 진행하면 될지 확인 필요!
 * //원래 dataReader 과 테스트를 어떻게 연결하면 효율적일까?
 * //원래 dataReader 의 path 에 어떻게 접근 & 수정?
 * //https://github.com/wbjeon2k/CSE364/issues/8#issuecomment-804520940
 * //하위 메소드 바로 접근해서 해결 + 개별 하위 메소드 테스트.
 *
 * https://ildann.tistory.com/5
 * // DataReader test
 * 1. 잘 읽어낸 텍스트 반환
 * 2. 잘못된 파일이라 null 반환
 * 3. 잘못된 파일이라 없는 파일 읽기 시도 -> 오류(크래시)
 */
@RunWith(Parameterized.class)
public class DataReaderTest {
    DataReaderImpl dataReader;
    private String question;
    private String answer;

    //parameter는 테스트 파일, 비교할 결과 파일 2개로 설정.
    @Parameters
    public static Collection<Object[]> testSet(){
        return Arrays.asList(new Object[][]{
                {"testUser1.dat", "resultUser1.dat"},
                {"testMovie1.dat", "resultMovie1.dat"},
                {"testRating1.dat", "resultRating1.dat"},

        });
    }

    public DataReaderTest(String Q, String A) {
        System.out.println("Test case started.");
        this.dataReader = new DataReaderImpl();
        this.question = Q;
        this.answer = A;
    }

    //user,movies,rating 들어왔을 때 각각 data/user,movies,rating.dat 으로 경로 만드는지 확인
    @Test
    public void testGetPathFromDataType(){
        DataType[] inputs = {DataType.USER, DataType.MOVIE, DataType.RATING};
        String[] results = {"data/user.dat", "data/movies.dat", "data/ratings.dat"};
        for(int i=0;i<3;++i){
            String result = dataReader.GetPathFromDataType(inputs[i]);
            assertSame(result, results[i]);
        }
    }

    //파싱 형태 맞춰서 한 줄의 string 으로 반환하는지 확인.
    //이 부분은 직접 파일 입출력 통해 확인.
    @Test
    public void testReadTextFromFile(){
        String path = "data/test/testReadText.dat";
        String testResult = dataReader.ReadTextFromFile(path);

        FileInputStream ifstream = null;
        try{
            ifstream = new FileInputStream("data/test/resultReadText.dat");
        }
        catch (FileNotFoundException e){
            System.out.println("Test File data/test/resultReadText.dat not found");
            return;
        }
        Scanner scanner = new Scanner(ifstream);

        String answer = scanner.nextLine();

        assertSame(answer, testResult);
    }

    //UserList 로 변환 하는지 테스트.
    //1 F 1 10 48067-100 5개 로 구성된 테스트 케이스 통과해야 한다.
    @Test
    public void testToUserList(String path_userTest){
        String read_text = dataReader.ReadTextFromFile(path_userTest);
        ArrayList<User> result = dataReader.ToUserList(read_text);

        assertEquals(result.size(), 5);

        /*
        public int userId;
        public Gender gender;
        public Age age;
        public Occupation occupation;
        public int zipCode;
        //1 F 1 10 48067-100 5개.
         */
        for(int i=0; i< result.size();++i){
            User now = result.get(i);
            assertEquals(now.userId, 1);
            assertEquals(now.gender, User.Gender.FEMALE);
            assertEquals(now.age, User.Age.UNDER_18);
            assertEquals(now.occupation, User.Occupation.K_12_STUDENT);
            //zipcode 자료형 String 으로 바꿨다고 가정!
            assertEquals( now.zipCode, "48067-100");
            //assertEquals( Integer.toString(now.zipCode), "48067-100");
            //1 F 1 10 48067-100
        }
    }


    //MovieList 로 변환 하는지 테스트.
    //1::A B C D (E F G) (1998)::Animation|Children's|Comedy
    //5개 로 구성된 테스트 케이스 통과해야 한다.
    @Test
    public void testToMovieList(String path_movieTest) {
        String read_text = dataReader.ReadTextFromFile(path_movieTest);
        ArrayList<Movie> result = dataReader.ToMovieList(read_text);

        assertEquals(result.size(), 5);

        /*
        public int movieId;
        public String title;
        public ArrayList<Genre> genres;
        1 "A B C D (E F G) (1998)" Animation|Children's|Comedy
        */
        Movie.Genre[] genres = {Movie.Genre.Animation, Movie.Genre.Children_s, Movie.Genre.Comedy};

        for(int i=0;i < result.size();++i){
            Movie now = result.get(i);
            assertEquals(now.movieId, i+1);
            assertEquals(now.title, "A B C D (E F G) (1998)");
            assertEquals(now.genres.size(), 3);
            for(int j=0;j<now.genres.size();++j){
                assertEquals(now.genres.get(j), genres[j]);
            }
        }

    }


    //RatingList 로 변환 하는지 테스트.
    //1::A B C D (E F G) (1998)::Animation|Children's|Comedy
    //5개 로 구성된 테스트 케이스 통과해야 한다.
    @Test
    public void testToRatingList(String path_ratingTest) {
        String read_text = dataReader.ReadTextFromFile(path_ratingTest);
        ArrayList<Rating> result = dataReader.ToRatingList(read_text);

        assertEquals(result.size(), 5);
        /*
        public int userId;
        public int movieId;
        public int rating;
        public int timestamp;
        */
        for(int i=0;i< result.size();++i){
            Rating now = result.get(i);
            assertEquals(now.userId, 1);
            assertEquals(now.movieId, i+1);
            assertEquals(now.rating, i+1);
            assertEquals(now.timestamp, 12345678);
        }
    }


    //parameter 로 파일 경로+이름 받으면
    //user, movie, rating 들어있는지 찾아서
    //해당 유형 return. 유형별 테스트 위함.
    public DataType testType(String parameter){
        if(parameter.contains("User")){
            return DataType.USER;
        }
        if(parameter.contains("Movie")){
            return DataType.MOVIE;
        }
        if(parameter.contains("Rating")){
            return DataType.RATING;
        }
        return null;
    }


    //parameter 들을 통해 진행.
    @Test
    public void parameterTest(){
        System.out.println("** Parameter Test **");

        //parameter 제대로 들어갔는지 확인.
        System.out.println("Parameter1: " + question + " Parameter2: " + answer);

        System.out.println("Printing Answer");

        //testcase path 정확히 찍히는지 확인.
        String testcase_path = "data/test/" + question;
        System.out.println("testcase path " + testcase_path + "\n");

        //chk: User, Movie, Rating 테스트 유형 결정.
        DataType chk = testType(testcase_path);

        //GetPathFromDataType(), ReadTextFromFile() 2개는 default 로 진행.
        testGetPathFromDataType();
        testReadTextFromFile();

        //chk 로 판별한 유형별로 테스트.
        if(chk == DataType.USER){
            System.out.println("User methods test");
            testToUserList(testcase_path);
        }
        if(chk == DataType.MOVIE){
            System.out.println("Movie methods test");
            testToMovieList(testcase_path);
        }
        if(chk == DataType.RATING){
            System.out.println("Rating methods test");
            testToRatingList(testcase_path);
        }

        //나중에는 resultX.dat 과 userList 비교하여 assert.
        //비교 자동화 하는것이 목표.
        assertTrue(true);
    }
}

