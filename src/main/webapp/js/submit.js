'use strict';

/**
 * Ugly java script code for simple tests of shareit's resources interface.
 *  @author Axel Böttcher <axel.boettcher@hm.edu>
 */

var getCookie = function() {
    var  name = $("input[name=name]").val();
    var password = $("input[name=password]").val();
    var errorText = $("#errormessage");
    $.ajax({
        url: '/shareit/media/auth/'+name+'&'+password,
        type:'GET',
    })
        .done((data) => {
        var template = "{{#data}}{{token}}{{/data}}";
    Mustache.parse(template);
    //var output = Mustache.render(template, {data: data});
    //var now = new Date();
    //now.setTime(now.getTime() + 12 * 60 * 1000);
    //document.cookie = "token="+output+"; expires=" + now.toUTCString() + "; path=/";

    //errorText.removeClass("visible");
    //errorText.addClass("hidden");
    window.location.replace("index.html");
})
    .fail((error) => {
    errorText.addClass("visible");
    errorText.text(error.responseJSON.detail);
    errorText.removeClass("hidden");
});
}

/**
 * This function is used for transfer of new book info.
 */
var submitNewBook = function() {
	var json = JSON.stringify({
			title: $("input[name=title]").val(),
			author: $("input[name=author]").val(),
			isbn: $("input[name=isbn]").val()
	});
	var errorText = $("#errormessage");
    $.ajax({
        url: '/shareit/media/books/',
        type:'POST',
        contentType: 'application/json; charset=UTF-8',
        data: json
        })
        .done(() => {
			$("input[name=title]").val("");
			$("input[name=author]").val("");
			$("input[name=isbn]").val("");
        	
        	errorText.removeClass("visible");
        	errorText.addClass("hidden");
        })
        .fail((error) => {
        	errorText.addClass("visible");
        	errorText.text(error.responseJSON.detail);
        	errorText.removeClass("hidden");
        });
}

/**
 * Creates a list of all books using a Mustache-template.
 */
var listBooks = function() {
	$.ajax({
        url: '/shareit/media/books',
        type:'GET'
	})
	.done((data) => {
		var template = "<table class='u-full-width'><tbody>{{#data}}<tr><td>{{title}}</td><td>{{author}}</td><td>{{isbn}}</td></tr>{{/data}}</tbody></table>";
		Mustache.parse(template);
		var output = Mustache.render(template, {data: data});
		$("#content").html(output);
	});// no error handling
}

/**
 * Call backer for "navigational buttons" in left column. Used to set content in main part.
 */
var changeContent = function(content) {
	$.ajax({
        url: content,
        type:'GET'
	})
	.done((data) => {
		$("#content").html(data);
	});// no error handling
}
