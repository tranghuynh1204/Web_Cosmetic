$(document).ready(function () {
    $('#expand-icon').click(function () {
        $('#sidebar').toggleClass('expanded');
        $('#main-content').toggleClass('expanded');
    });
});

$(document).ready(function () {

    const dropZone = $("#image-drop");

    dropZone.on("dragover", function (e) {
        e.preventDefault();
        dropZone.addClass("dragover");
    });

    dropZone.on("dragleave", function (e) {
        e.preventDefault();
        dropZone.removeClass("dragover");
    });

});

function selectFile(file) {
    // if (fileInput.multiple) {
    //     var fragment = $(document.createDocumentFragment());
    //
    //     for (var i = 0; i < files.length; i++) {
    //         var file = files[i];
    //         var reader = new FileReader();
    //         reader.onload = function (event) {
    //             var imgElement = $("<img>").attr({
    //                 "alt": "img",
    //                 "src": event.target.result
    //             });
    //             // Thêm hình ảnh vào fragment thay vì trực tiếp vào DOM
    //             fragment.append(imgElement);
    //         };
    //         reader.readAsDataURL(file);
    //     }
    //
    //     $("#img-container").append(fragment);
    // } else {
    event.preventDefault();
    const imgElement = $("#img-preview");
    var reader = new FileReader();
        reader.onload = function (event) {
            imgElement.attr("src", event.target.result);
            $('#img-hidden').val(event.target.result);
        };
        reader.readAsDataURL(file);
    // }
}

$(document).ready(function () {
    $(".button").click(function () {
        var button = $(this);
        button.addClass("clicked");
        setTimeout(function () {
            button.removeClass("clicked");
        }, 300); // Thời gian (millisecond) sau khi loại bỏ lớp "clicked"
    });
});

function submitBrandForm(form) {
    event.preventDefault();
    var imageSrc = $('#img-preview').attr('src');
    if (!imageSrc || imageSrc.trim() === '') {
        alert("Thương hiệu phải có 1 ảnh");
    }
    else{
        submitForm(form,'/admin/brands');
    }
}

function submitForm(form,redirect) {
    if (validateForm()){
        var button = $('#save-button');
        button.html(`<div class="loader"></div>`)
        var formData = $(form).serialize();
        $.ajax({
            type: 'POST',
            url: $(form).attr('action'),
            data: formData,
            success: function (response) {
                alert(response);
                window.location.replace(redirect);
            },
            error: function (xhr, status, error) {
                alert(xhr.responseText);
                button.html('Lưu');
            },
        });
    }
}

function validateForm() {
    var isValid = true;
    $('[required]').each(function() {
        if ($(this).val().trim() === '') {
            isValid = false;
            // Hiển thị thông báo hoặc thực hiện hành động khác để thông báo cho người dùng biết trường bắt buộc không được để trống
            alert('Vui lòng điền đầy đủ thông tin.');
            return false; // Dừng vòng lặp khi gặp trường bắt buộc trống
        }
    });
    return isValid;
}