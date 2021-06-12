$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/homepageContents"
    }).then(function(data) {
        if ( ("statusCode", "message") in data )
        {
            $('.movies').append("<p>" + data["statusCode"] + " Error: " + data["message"] + "</p>");
            return;
        }

        for ( let i in data ) {
            let genre = i.substr(5).toLowerCase();

            data[i].forEach(movie => {
                $('#' + genre).append("<a href='" + movie["imdb"] + "' target='_blank'><img src='" + movie["poster"] + "' width='19%' border='2' onerror=\"this.src='img.png'\" title='" + movie["title"] + "'></a>");
            });
        }
    });
});