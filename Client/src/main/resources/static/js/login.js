$(document).ready(function () {
    $('#register').click(function () {
        $('#container').addClass('active');
    });

    $('#login').click(function () {
        $('#container').removeClass('active');
    });

    $('#button-register').click(function () {

        if ($('#password').val() !== $('#confirmPassword').val()) {
            $('#message').text("Mật khẩu xác nhận không khớp");
            return false; // Prevent form submission
        }

        $.ajax({
            url: '/user/register', // Replace with the actual URL
            type: 'POST',
            data: $('#register-form').serialize(),
            success: function (response) {
                alert("Đăng kí thành công");
                location.reload();
            },
            error: function (xhr, status, error) {
                $('#message').text(xhr.responseText);
            }
        });
    });

    $('#sendOtp').click(function (){
        var nameValue = $('input[name="name"]').val();
        var phoneValue = $('input[name="phone"]').val();
        var mailValue = $('input[name="mail"]').val();
        var addressValue = $('input[name="address"]').val();
        var passwordValue = $('input[name="password"]').val();
        var confirmPasswordValue = $('input[name="confirmPassword"]').val();

        if (nameValue === "" || phoneValue === "" || mailValue === "" || addressValue === "" || passwordValue === "" || confirmPasswordValue === "") {
            alert("Vui lòng điền đầy đủ thông tin vào các trường!");
        } else if (passwordValue !== confirmPasswordValue) {
            alert("Mật khẩu và xác nhận mật khẩu không khớp!");
        } else {
            $('#spinner').css('display', 'inline-block');
            $.ajax({
                type: 'POST',
                url: '/send-otp/register',
                data: {
                    name: nameValue,
                    mail: mailValue
                },
                success: function (response) {
                    $('#message').html(response);
                    $('#spinner').css('display', 'none');
                },
                error: function (xhr, status, error) {
                    $('#message').text(xhr.responseText);
                }
            });
        }
    });

    $('#login-button').click(function () {
        $.ajax({
            type: 'POST',
            url: '/user/login',
            data: $('#login-button').serialize(),
            success: function(response){
                if(document.referrer !== ""){
                    window.history.back();
                } else {
                    window.location.href = 'home';
                }
            },
            error: function(xhr, status, error){
                alert(xhr.responseText)
            }
        });
    });

});

