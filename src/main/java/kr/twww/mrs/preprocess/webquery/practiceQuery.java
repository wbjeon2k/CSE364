package kr.twww.mrs.preprocess.webquery;

import breeze.stats.distributions.AliasTable;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;

//public class practiceQuery implements Serializable {
public class practiceQuery{
    private String gender;
    private String age;
    private String occupation;
    private String genre;
    //private String title;
    //private String limit;


    public practiceQuery(String gender, String age, String occupation, String genre){
        this.gender = gender;
        this.age = age;
        this.occupation = occupation;
        this.genre = genre;
    }

    //public practiceQuery(String title, String limit){
      //  this.title = title;
        //this.limit = limit;
    //}

    public practiceQuery() { }

    public String getGender(){ return gender; }
    public String getAge() { return age; }
    public String getOccupation(){ return occupation; }
    public String getGenre(){ return genre; }
    //public String getTitle(){ return title; }
    //public String getLimit(){ return limit; }


    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    //public void setTitle(String title) { this.title = title; }

    //public void setLimit(String limit) { this.limit = limit; }
}
