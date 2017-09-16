/**
 * Simple little click function to do a simple job, hit a url. Make sure it returns 200 else it has failed.
 */
$(".fetch_compare").click(function () {

    var message = $(".message");
    message.html("");

    $.ajax({
        url: "/price/api/compare/",
        data: null,
        type: 'GET',
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            message.html("<div class=\"alert alert-success\"><strong>Success!</strong> Fetched successfully.<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a></div>");
        },
        error: function (response) {
            message.html("<div class=\"alert alert-danger\"><strong>Failure!</strong> Fetch failed.<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a></div>");

        }
    });

});