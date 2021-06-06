$(document).ready(function () {

    //("#users_button").on("click", function () { button 누르면 모델 실행됨, 하지만 'data' console로 넘어가지 않는다

        /*
        ToDo: html에서 입력받은 파일 이곳의 변수에 넣기
        이 부분은 users.html에 입력된 gender, occupation, age, genres를 받는 곳
        현재 html에서 데이터를 받지 못하고 있다.
         */
        let gender_val = $("#gender option:selected").val();
        // age 부분 필요
        let occupation_val = $("#occupation option:selected").val();
        let genres_val = $("#genres option:selected").val();
        $.ajax({
            url: "./recommendations.html",
            data: {gender: gender_val, age: "", occupation: occupation_val, genres: genres_val},
            method: "GET",
            dataType: "json"
        }).done(function (data) {
            console.log(gender_val)
            console.log(occupation_val)
            console.log(genres_val)
            console.log(data)

            for (let i = 0; i < 10; i++) {
                let url = data[i]['imdb'];
                console.log(data[i]['imdb']);
            }
        })
    //})
})
