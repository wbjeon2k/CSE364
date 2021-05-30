$(document).ready(function() {
    $.ajax({
        //url: "http://loalhost:8080/user/recommendations/"
        url : "http://localhost:8080/users/recommendations?gender=M&age=&occupation=retired&genres=/"
}).then(function(data) {
        $('.greeting-id').append(data.title);
    });
});