$('#Submit').click(function() {
    $.ajax({
        url: "http://localhost:8080/homepageContents"
    }).then(function(data) {
        $('#top10all').empty('');
        console.log(data);
        let json = JSON.parse(data);
        for (var i in json) {
            var row = $('<tr>');
            row.append($('<td id=' + json[i].Id + '>').html(json[i].Id));
            row.append($('<td id=' + json["Top10all"] + '>').html(json[i].Id));
            row.append($('<td id=' + json[i] + '>').html(json[i]));
            $('</tr>');
            $('#top10all').append(row);
        }
    });
});

/*
TODO:
https://stackoverflow.com/questions/5289078/parse-json-from-jquery-ajax-success-data
https://upcake.tistory.com/271?category=897762

현재 상황:
localhost:8080/index.html 들어갔을때 홈페이지가 로딩 된다.
현재 버전의 index.html 은 테스트를 위해 주석 처리한 상태. 풀고 쓰면 된다.

ajax 의 url 은 index.html 이 아니라, "http://localhost:8080/homepageContents" 를 통해 홈페이지 전용 영화 목록을 받는다.
출력 예시는 링크 참조. https://pastebin.com/wntxJbPW
--> 순서대로 반환되지 않음! all - action - comedy - adventure - drama 순으로 나오지 않는다.

애초에 json 형태로 반환이 되고, 장르가 고정된 순서대로 반환되지 않기 때문에,
JSON을 읽어서 tag 별로 추출해야한다.
eg) "Top10all" 을 통해 [{},{},...,{}] 추출하기.

JSON 을 읽는것은 json = JSON.parse(res); 와 같은 형태로 parsing 할 수 있다.

해야하는것:
아래 형식의 코드를 통해서,
여기서 받은 json 데이터에서 영화 정보를 추출하고,
row.append($('<td id=' + json[i].Id + '>').html(json[i].Id));
형태로 html 문서를 이 js 에서 작성한 다음,
index.html 에서는 js 에서 작성된 html 파일을 받기만 하는 형태로 만들어야 하는것 같다.

방향은 알겠는데 성공은 하지 못한 상태. 이 방법으로 접근하면 될 것 같다.

https://stackoverflow.com/questions/5289078/parse-json-from-jquery-ajax-success-data
https://upcake.tistory.com/271?category=897762

stackoverflow 맨 위 답변 말고 밑의 답변들도 읽으면 도움이 된다.

json = JSON.parse(res);
for (var i in json) {
    var row = $('<tr>');
    row.append($('<td id=' + json[i].Id + '>').html(json[i].Id));
    row.append($('<td id=' + json[i].Name + '>').html(json[i].Name));
    $('</tr>');
    $('#ProductList').append(row);
}

 */