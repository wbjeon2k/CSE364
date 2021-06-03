$(document).ready(function() {
    let title = $("#gender option:selected").val();
    let occupation_val = $("#occupation option:selected").val();
    let genres_val = $("#genres option:selected").val();
    $.ajax({
            url: "http://localhost:8080/users/recommendations.html",
            data:{gender: gender_val, age: "", occupation: occupation_val, genres: genres_val},
            method: "GET",
            dataType: "json"
        }).then(function(data){
            console.log(data)
            for(let i=0; i<10; i++) {
                document.write("<a href = data[i].imdb target='_blank'><img src= width=19% border='2'></a>")
            }
    })
})