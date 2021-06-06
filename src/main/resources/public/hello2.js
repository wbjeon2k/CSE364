$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/homepageContents"
    }).then(function(data) {
        console.log(data);
        $('.top10all').append(data);
        $('.top10action').append(data);
    });
});