function getURLParams(url) {
    var result = {};
    url.replace(/[?&]{1}([^=&#]+)=([^&#]*)/g, function(s, k, v) { result[k] = decodeURIComponent(v); });
    return result;
}

$(document).ready(function() {
    $("#submit").click(function () {
        let title = $("#title").val();
        let limit = $("#limit").val();

        location = "movies.html?title=" + title + "&limit=" + limit;
    });
});

$(document).ready(function() {
    let params = getURLParams(location.search);

    if ( (("title", "limit") in params) )
    {
        for ( let i in params ) {
            $("#" + i).val(params[i]);
        }

        $('.movies').append("<p>Please wait for recommendation ...</p>");
    }
    else
    {
        $('.movies').append("<p>Please enter your information for recommendation</p>");
        return;
    }

    $.ajax({
        url: "movies/recommendations.html",
        data: {title: params["title"], limit: params["limit"]},
        method: "GET",
        dataType: "json"
    }).then(function(data) {
        $('.movies').empty();

        if ( ("statusCode", "message") in data )
        {
            $('.movies').append("<p>" + data["statusCode"] + " Error: " + data["message"] + "</p>");
            return;
        }

        data.forEach(movie => {
            $('.movies').append("<a href='" + movie["imdb"] + "' target='_blank'><img src='" + movie["poster"] + "' width='19%' border='2' onerror=\"this.src='img.png'\" title='" + movie["title"] + "'></a>");
        });
    });
});