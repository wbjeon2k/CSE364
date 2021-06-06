$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/homepageContents"
    }).then(function(data) {
        $('.top10all').append(data.id);
        $('.top10action').append(data.content);
    });
});