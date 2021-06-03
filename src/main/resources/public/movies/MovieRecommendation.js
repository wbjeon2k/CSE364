$(document).ready(function() {
    $("#title").click(function() {
        var title_val = $(this).attr('value');
        $.ajax({
            url: "http://localhost:8080/movies/recommendations.html",
            data: {title: title_val, limit: ""},
            method: "GET",
            dataType: "json"
        }).then(function (data) {
            console.log(data[0].imdb)

            for (let i = 0; i < 10; i++) {
                var link = data[i].imdb;
                document.write("<a href = {link} target='_blank'><img src= width=19% border='2'></a>")
            }
        })
    })
})