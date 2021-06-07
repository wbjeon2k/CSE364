$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/homepageContents"
    }).then(function(data) {
        console.log(data.Top10all);
        $('.top10all').append(data.Top10all);
    });
});