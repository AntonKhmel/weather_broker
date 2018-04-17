var prefix = '/WeatherBroker_war_exploded';

var GetWeather = function() {
    var weatherView = {
        city: $("#city").val(),
        value: ' '
    };

    var request = $("#request").val()

    $.ajax({
        type: 'POST',
        /*chicago*/
        url: prefix + '/weather/' + request,
        contentType:"application/json; charset=utf-8",
        data: JSON.stringify(weatherView),
        async: true,
        success: function(result) {
            // $('#result').text(JSON.stringify(result, "", 8));
            if (request === 'db') {
                $('#result').text(JSON.stringify(JSON.parse(result.value), "", 8));
            }
            if (request === 'yahoo') {
                $('#result').text('Success');
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            $('#result').text(jqXHR.responseText);
        }
    });
}

var Clear = function () {
    $("#city").val('');
};
