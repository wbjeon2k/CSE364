$(document).ready(function() {
    $("#title").keyup(function() {
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
                document.write("<a href = link target='_blank'><img src= width=19% border='2'></a>")
            }
        })
    })
})