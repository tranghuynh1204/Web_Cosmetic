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
$(document).ready(function () {
    // Xử lý sự kiện khi nút phân trang được click
    $("#go").click(function () {
        // Lấy chiều dài của phân trang
        var paginationWidth = $(".pagination").width();
        var gopage = $(".go-page");
        // Áp dụng chiều dài của phân trang cho phần gopage
        gopage.css("width", paginationWidth);
        gopage.toggleClass("active");
    });

    // Xử lý các sự kiện khác ở đây (nếu cần)
});
$(document).ready(function () {
    var element = $(".search-box .input-box input");
    element.focus(() => {
        $(".search-box .input-box").addClass("active");
    });
    element.blur(() => {
        $(".search-box .input-box").removeClass("active");
    });
});
$(document).ready(function () {
    var sortIcon= $('#sort-icon');
    var img = $(this).find('#sort-icon img');
    sortIcon.mouseover(function () {
        if (img.attr('src') === '/admin/images/asc-icon.png') {
            img.attr('src', '/admin/images/desc-icon.png');
        } else {
            img.attr('src', '/admin/images/asc-icon.png');
        }
        img.css('transform', 'scale(1.3)');
    });
    sortIcon.mouseout(function () {
        if (img.attr('src') === '/admin/images/asc-icon.png') {
            img.attr('src', '/admin/images/desc-icon.png');
        } else {
            img.attr('src', '/admin/images/asc-icon.png');
        }
        img.css('transform', 'scale(1)');
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
    } else {
        submitForm(form);
    }
}

function submitForm(form) {
    if (validateForm()) {
        var button = $('#save-button');
        button.html(`<div class="loader"></div>`)
        button.attr('disabled', 'disabled');
        var formData = $(form).serialize();
        $.ajax({
            type: 'POST',
            url: $(form).attr('action'),
            data: formData,
            success: function (response) {
                alert(response);
                window.location.href = document.referrer;
            },
            error: function (xhr, status, error) {
                alert(xhr.responseText);
                button.html('Lưu');
                button.removeAttr('disabled');
            },
        });
    }
}

function validateForm() {
    var isValid = true;
    $('[required]').each(function () {
        if ($(this).val().trim() === '') {
            isValid = false;
            // Hiển thị thông báo hoặc thực hiện hành động khác để thông báo cho người dùng biết trường bắt buộc không được để trống
            alert('Vui lòng điền đầy đủ thông tin.');
            return false; // Dừng vòng lặp khi gặp trường bắt buộc trống
        }
    });
    return isValid;
}

function deleteRequest(brand, nameEntity, action) {
    var isDelete = confirm("Bạn có chắc chắn muốn xoá " + nameEntity + " " + brand.name + "?");
    if (isDelete) {
        $.ajax({
            url: "/admin/" + action + "/delete/" + brand.id,
            type: "POST",
            success: function (response) {
                alert(response);
                location.reload();
            },
            error: function (xhr, status, error) {
                alert(xhr.responseText);
            }
        });
    }
}

$(document).ready(function () {
    $('#button-go').click(function (event) {

        // Lấy action ban đầu của form
        var originalAction = $('#form-go').attr('action');

        // Lấy giá trị của thẻ có id là go-page
        var goPageValue = $('#input-go').val();

        // Cộng thêm giá trị của thẻ vào action của form
        var newAction = originalAction + goPageValue;

        // Thiết lập action mới cho form
        $('#form-go').attr('action', newAction);

        // Gửi form đi
    });
});