const loginForm = document.getElementById("login-form");
const loginErrorMsg = document.getElementById("login-error-msg");

const loginUrl = "/modeler/api/v2/users/log-in";


$("#login-form").on("submit", function(e) {
	console.log('here');
	const user = loginForm.username.value;
    const pass = loginForm.password.value;
    e.preventDefault();
    $.ajax({
        type: "POST",
        url: loginUrl,
        contentType:"application/json; charset=utf-8",
        data: JSON.stringify({ 
            username: user,
            password: pass
        }),
        success: function(data, textStatus) {
            console.log(data.redirect);
            window.location=data.redirect;
        },
        error: function(data, textStatus) {
        	console.log("error");
            console.log(textStatus);
        }
    });
});