function getURLParams(url) {
    var result = {};
    url.replace(/[?&]{1}([^=&#]+)=([^&#]*)/g, function(s, k, v) { result[k] = decodeURIComponent(v); });
    return result;
}

$(document).ready(function() {
    $("#submit").click(function () {
        let gender = $("#gender").val();
        let age = $("#age").val();
        let occupation = $("#occupation").val();
        let genres = [];

        $("input:checkbox[name='genres']").each(function() {
            if ( this.checked )
            {
                genres.push(this.value);
            }
        });

        genres = genres.join("%7C");

        location = "users.html?gender=" + gender + "&age=" + age + "&occupation=" + occupation + "&genres=" + genres;
    });
});

$(document).ready(function() {
    let params = getURLParams(location.search);

    if ( (("gender", "age", "occupation", "genres") in params) )
    {
        for ( let i in params ) {
            if ( i === "genres" )
            {
                if ( params["genres"] === "" ) continue;

                let genres = params["genres"].split("|");
                genres.forEach(genre => {
                    $("input:checkbox[value='" + genre + "']").prop("checked", true);
                });
            }
            else
            {
                $("#" + i).val(params[i]);
            }
        }

        $('.movies').append("<p>Please wait for recommendation ...</p>");
    }
    else
    {
        $('.movies').append("<p>Please enter your information for recommendation</p>");
        return;
    }

    $.ajax({
        url: "http://localhost:8080/users/recommendations.html",
        data: {gender: params["gender"], age: params["age"], occupation: params["occupation"], genres: params["genres"]},
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