$(document).ready(function() {
    //$("#movies_button").on("click", function (){ 버튼 누르면 출력
        //ToDo: html에서 입력받은 title과 limit(이건 상빈이가 만들면) 데이터를 이곳 변수에 넣기
        let title_val = $("#title").val();
        $.ajax({
            url: "./recommendations.html",
            data: {title: title_val, limit: ""},
            method: "GET",
            dataType: "json"
        }).then(function (data) {
            for (let i = 0; i < 10; i++) {
                console.log(title_val)
                console.log(data[i]['imdb'])
                let link = data[i]['imdb'];
                //ToDo: UserRecommendation.js부분과 동일
                document.write("<a href = link target='_blank'><img src= width=19% border='2'></a>")
            }
        })
    //})
})