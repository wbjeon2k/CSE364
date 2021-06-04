$(document).ready(function() {
    /*
    이 부분은 users.html에 입력된 gender, occupation, age, genres를 받는 곳
    user.html을 보면 select라는 테그가 있고, 각각 id가 있음
     */
    let gender_val = $("#gender option:selected").val();
    let occupation_val = $("#occupation option:selected").val();
    let genres_val = $("#genres option:selected").val();
    $.ajax({
            url: "./recommendations.html",
            data:{gender: gender_val, age: "", occupation: occupation_val, genres: genres_val},
            method: "GET",
            dataType: "json"
        }).then(function(data){
            console.log(gender_val)
            console.log(occupation_val)
            console.log(genres_val)
            console.log(data)

            for(let i=0; i<10; i++) {
                let url = data[i]['imdb'];
                console.log(data[i]['imdb']);
                document.write("<a href=\"' + url + '\" target='_blank'><img src= width=19% border='2'></a>")
            }
    })
})