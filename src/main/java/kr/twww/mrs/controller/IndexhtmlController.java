package kr.twww.mrs.controller;

import kr.twww.mrs.controller.object.IndexhtmlObject;
import kr.twww.mrs.controller.object.Recommendation;
import kr.twww.mrs.preprocess.Preprocessor;
import kr.twww.mrs.preprocess.object.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
public class IndexhtmlController {
    @Autowired
    private Preprocessor preprocessor;

    ArrayList<Score> allscoreList;

    @RequestMapping("/")
    public void Index( HttpServletResponse response ) throws IOException
    {
        response.sendRedirect("index.html");
    }

    /*
    index.html 전용 controller.
    index.html 출력 예시는 링크 참조. https://pastebin.com/wntxJbPW
    순서대로 반환되지 않음! all - action - comedy - adventure - drama 순으로 나오지 않는다.
    index.html 대신 /homepageContents 로 mapping.
     */
    @GetMapping("/homepageContents")
    public IndexhtmlObject homepageReturn () throws Exception {

        try{
            allscoreList = preprocessor.getindexhtmlScoreList();
            System.out.println(allscoreList.size());
        }
        catch (Exception e){
            throw new Exception("Error in  IndexhtmlController homepageReturn getindexhtmlScoreList");
        }

        ArrayList<Recommendation> top10all = getTop10all();
        ArrayList<Recommendation> top10Action = getTop10Action();
        ArrayList<Recommendation> top10Comedy = getTop10Comedy();
        ArrayList<Recommendation> top10Adventure = getTop10Adventure();
        ArrayList<Recommendation> top10Drama = getTop10Drama();


        return new IndexhtmlObject(top10all,top10Action,top10Comedy,top10Adventure,top10Drama);
    }

    public ArrayList<Recommendation> getTop10all(){
        ArrayList<Score> top10score = new ArrayList<Score>();
        for(int i=0;i<10;++i) top10score.add(allscoreList.get(i));

        return ScoreToRec(top10score);
    }

    public ArrayList<Recommendation> getTop10Action(){
        ArrayList<Score> top10score = new ArrayList<Score>();
        int cnt=0;
        for(int i=0;i<allscoreList.size();++i){
            String tmpGenre = allscoreList.get(i).movie.GetGenresText();
            if(tmpGenre.contains("Action")){
                top10score.add(allscoreList.get(i));
                ++cnt;
            }
            if(cnt==10) break;
        }

        assert (top10score.size() == 10);
        return ScoreToRec(top10score);
    }

    public ArrayList<Recommendation> getTop10Comedy(){
        ArrayList<Score> top10score = new ArrayList<Score>();
        int cnt=0;
        for(int i=0;i<allscoreList.size();++i){
            String tmpGenre = allscoreList.get(i).movie.GetGenresText();
            if(tmpGenre.contains("Comedy")){
                top10score.add(allscoreList.get(i));
                ++cnt;
            }
            if(cnt==10) break;
        }

        assert (top10score.size() == 10);
        return ScoreToRec(top10score);
    }

    public ArrayList<Recommendation> getTop10Adventure(){
        ArrayList<Score> top10score = new ArrayList<Score>();
        int cnt=0;
        for(int i=0;i<allscoreList.size();++i){
            String tmpGenre = allscoreList.get(i).movie.GetGenresText();
            if(tmpGenre.contains("Adventure")){
                top10score.add(allscoreList.get(i));
                ++cnt;
            }
            if(cnt==10) break;
        }

        assert (top10score.size() == 10);
        return ScoreToRec(top10score);
    }

    public ArrayList<Recommendation> getTop10Drama(){
        ArrayList<Score> top10score = new ArrayList<Score>();
        int cnt=0;
        for(int i=0;i<allscoreList.size();++i){
            String tmpGenre = allscoreList.get(i).movie.GetGenresText();
            if(tmpGenre.contains("Drama")){
                top10score.add(allscoreList.get(i));
                ++cnt;
            }
            if(cnt==10) break;
        }

        assert (top10score.size() == 10);
        return ScoreToRec(top10score);
    }

    ArrayList<Recommendation> ScoreToRec(ArrayList<Score> top10score){
        return (ArrayList<Recommendation>)top10score
                .stream()
                .map(
                        score -> new Recommendation(
                                score.movie.title,
                                score.movie.GetGenresText(),
                                score.link.GetURL()
                                , score.poster.getPosterLink()
                        )
                ).collect(Collectors.toList());
    }


}

