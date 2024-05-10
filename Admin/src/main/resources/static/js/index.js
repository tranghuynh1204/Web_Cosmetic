$(document).ready(function () {
    $('#expand-icon').click(function () {
        $('#sidebar').toggleClass('expanded');
        $('#main-content').toggleClass('expanded');
    });
});
$(document).ready(function () {
    // Lấy URL hiện tại
    var currentUrl = window.location.href;

    // Duyệt qua tất cả các phần tử <li> trong #sidebar
    $('#sidebar ul li a').each(function () {
        // Kiểm tra nếu href của thẻ <a> chứa URL hiện tại
        if (currentUrl.indexOf($(this).attr('href')) !== -1) {
            $(this).addClass("active");
            $(this).parent().css("background-color", "rgb(254, 242, 248)");
        }
    });
});

$(document).ready(function () {
    $(".drop-zone").on("dragover", function (e) {
        e.preventDefault();
        $(this).addClass("dragover");
    });

    $(".drop-zone").on("dragleave", function (e) {
        e.preventDefault();
        $(this).removeClass("dragover");
    });
    $(".drop-zone").on("drop", function (e) {
        e.preventDefault();
        $(this).removeClass("dragover");
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
    // Lấy các phần tử và gọi hàm để thêm và loại bỏ lớp CSS cho mỗi phần tử
    var elements = [
        $(".search-box .input-box input"),
        $("#filter-brand-selectized"),
        $("#filter-category-selectized")
    ];
    elements.forEach(function (element) {
        element.focus(() => {
            $(".search-box .input-box").addClass("active");

        });
        element.blur(() => {
            $(".search-box .input-box").removeClass("active");

        });
    });

});
$(document).ready(function () {
    var sortIcon = $('#sort-icon');
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
    event.preventDefault();
    const imgElement = $("#img-preview");
    var reader = new FileReader();
    reader.onload = function (event) {
        imgElement.attr("src", event.target.result);
        $('#img-hidden').val(event.target.result);
    };
    reader.readAsDataURL(file);
}

function submitFormAndCheckImage(form, name) {
    event.preventDefault();
    var imageSrc = $('#img-preview').attr('src');
    if (!imageSrc || imageSrc.trim() === '') {
        alert(name + " phải có 1 ảnh");
    } else {
        submitForm(form);
    }
}

function submitForm(form) {
    event.preventDefault();
    if (validateForm()) {
        var savebtn = $('#save-button');
        var cancelbtn = $("#cancel-button");
        savebtn.html(`<div class="loader"></div>`)
        savebtn.attr('disabled', 'disabled');
        cancelbtn.attr('disabled', 'disabled');
        var formData = $(form).serialize();
        $.ajax({
            type: 'POST',
            url: $(form).attr('action'),
            data: formData,
            success: function (response) {
                alert(response);
                cancelbtn.click();
            },
            error: function (xhr, status, error) {
                alert(xhr.responseText);
                savebtn.html('Lưu');
                savebtn.removeAttr('disabled');
                cancelbtn.removeAttr('disabled');
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

function deleteRequest(model, nameEntity, action) {
    var isDelete = confirm("Bạn có chắc chắn muốn xoá " + nameEntity + " " + model.name.replace(/^[-]+/, "") + "?");
    if (isDelete) {
        var overlay = $('#overlay');
        overlay.show();
        $.ajax({
            url: "/admin/" + action + "/delete/" + model.id,
            type: "POST",
            success: function (response) {
                overlay.hide();
                alert(response);
                location.reload();
            },
            error: function (xhr, status, error) {
                overlay.hide();
                alert(xhr.responseText);
            }
        });
    }
}

$(document).ready(function () {
    $("#cancel-button").click(function () {
        console.log(document.referrer);
        var defaultLink = $(this).data('default-link');
        if (document.referrer === '' || window.location.href === document.referrer) {
            window.location.href = defaultLink;
        } else {
            window.location.href = document.referrer;
        }
    });
});
$(document).ready(function () {
    $('#button-go').click(function (event) {

        // Lấy action ban đầu của form
        var originalAction = $('#form-go').attr('action');

        // Lấy giá trị của thẻ có id là go-page
        var goPageValue = $('#input-go').val();

        // Cộng thêm giá trị của thẻ vào action của form
        var newAction = originalAction.replace(/\d+$/, '') + goPageValue;

        // Thiết lập action mới cho form
        $('#form-go').attr('action', newAction);

        // Gửi form đi
    });
});

function clearParent() {
    $("#category-parent").val(""); // Gán giá trị rỗng cho input type="text"
    $("#parent-id").val(""); // Gán giá trị rỗng cho input type="hidden"
}

$(document).ready(function () {
    $("#select-brand").selectize({
        maxItems: 1, // Chỉ cho phép chọn một giá trị
        placeholder: "Chọn thương hiệu",

    });
    $("#select-category").selectize({
        maxItems: 1, // Chỉ cho phép chọn một giá trị
        placeholder: "Chọn danh mục",
    });
});
$(document).ready(function () {
    $("#table-productLines tbody tr").dblclick(function () {
        var productLineId = $(this).data("product-line-id");
        var url = "/admin/product-lines/" + productLineId + "/products";
        window.location.href = url;
    });
});

function addColumn() {

    $("#classification-table thead tr:first").append("<th><button class='fas fa-trash trash-icon' onclick='deleteColumn(this)'></button></th>");
    $("#classification-table thead tr:last").append("<th><input type='text' value='Đơn vị'></th>");

    $("#classification-table tbody tr").each(function () {
        $(this).append("<td><input type='text'></td>");
    });
}

function addRow() {

    var numColumns = $('#classification-table th').length / 2;

    var newRow = $('<tr></tr>');
    newRow.append($('<td><button onclick="deleteRow(this)" class="fas fa-trash trash-icon"></button></td>'));
    for (var i = 0; i < numColumns - 1; i++) {
        newRow.append("<td><input type='text'></td>");
    }

    $('#classification-table tbody').append(newRow);

    newRow = $('<tr></tr>');
    for (let i = 0; i < 2; i++) {
        newRow.append('<td><input min="1" type="number"></td>');
    }
    newRow.append($('<td>').append($('<button>').text('Hình ảnh').click(function () {
        alert('Sản phẩm chưa lưu không thể chỉnh sửa ảnh!');
    })));
    $("#product-table tbody").append(newRow);
}

function deleteColumn(button) {
    if ($('#classification-table tr:first-child th').length <= 2) {
        alert("Phải có ít nhất 1 cột phân loại");
        return;
    }
    if (confirm("Bạn có chắc chắn muốn xoá cột này không?")) {
        var index = $(button).closest('th').index() + 1;
        $('#classification-table tr').each(function () {
            $(this).find('th:nth-child(' + index + ')').remove();
            $(this).find('td:nth-child(' + index + ')').remove();
        });
    }
}

function deleteRow(button) {
    var index = $(button).closest('tr').index() + 2;
    if (index <= 2) {
        alert("phải có ít nhất 1 sản phẩm trong dòng");
        return;
    }
    if (confirm("Bạn có chắc chắn muốn xoá dòng này không?")) {
        $('#classification-table tr').eq(index).remove();
        $('#product-table tr').eq(index).remove();
    }
}

function checkEmptyCells() {


    var emptyCellFound = false;

    $("#product-table tbody tr").each(function () {
        var cellText = $(this).find("input[type='number']:first").val();
        var cellNumber = parseInt(cellText);

        if (cellText === '' || cellText === 'null') {
            emptyCellFound = true;
            $(this).find("input[type='number']:first").addClass("red-background").click(function () {
                $(this).removeClass('red-background');
            });
            alert("Có ô trống trong bảng sản phẩm!" + cellText);
            return false;
        }
        if (cellNumber < 1) {
            emptyCellFound = true;
            $(this).find("input[type='number']:first").addClass("red-background").click(function () {
                $(this).removeClass('red-background');
            });
            alert("Có sản phẩm giá không hợp lệ!");
            return false;
        }

    });
    if (emptyCellFound) {
        return false;
    }
// Kiểm tra bảng phân loại
    var seen = {};
    $("#classification-table thead tr:last").find("input").each(function () {
        var cellText = $(this).val().trim().toLowerCase();

        if (cellText === '') {
            emptyCellFound = true;
            $(this).addClass("red-background").click(function () {
                $(this).removeClass('red-background');
            });
            alert("Có ô trống trong hàng đơn vị phân loại!");
            return false;
        }

        if (seen[cellText]) {
            emptyCellFound = true;
            $(this).addClass("red-background").click(function () {
                $(this).removeClass('red-background');
            });
            alert("Có ô bị trùng trong hàng đơn vị phân loại!");
            return false;
        }

        seen[cellText] = true;
    });
    if (emptyCellFound) {
        return false;
    }
// Kiểm tra bảng phân loại (tbody)
    $("#classification-table tbody tr").each(function () {
        $(this).find("input").each(function () {
            if ($(this).val().trim() === '') {
                emptyCellFound = true;
                $(this).addClass("red-background").click(function () {
                    $(this).removeClass('red-background');
                });
                alert("Có ô trống trong bảng phân loại!");
                return false;
            }
        });
    });
    if (emptyCellFound) {
        return false;
    }
    var numRows = $('#classification-table tbody tr').length;
    if (numRows === 0) {
        alert("phải có ít nhất 1 sản phẩm trong dòng");
        return false;
    }
    return true;
}

function saveProducts() {
    if (!checkEmptyCells()) {
        return;
    }
    var classifications = $("#classification-table thead tr:last").find("input").map(function () {
        return $(this).val();
    }).get().join("-");
    var idProductLine = $('#idProductLine').val();
    var products = {};
    var rows = $("#product-table tbody tr")
    $("#classification-table tbody tr").each(function (index) {
        var key = $(this).find("input").map(function () {
            return $(this).val();
        }).get().join("-");
        var id = rows.eq(index).find("input[type='hidden']").val();
        var price = rows.eq(index).find("td:eq(0) input").val();
        var salePrice = rows.eq(index).find("td:eq(1) input").val();
        var images = rows.eq(index).data('images');
        products[key] = {
            id: id,
            price: price,
            salePrice: salePrice,
            images: images
        };
    });

    $.ajax({
        url: "products/save",
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify({
            'id': idProductLine,
            'products': products,
            'classifications': classifications
        }),
        success: function (response) {
            alert(response);
            location.reload();
        },
        error: function (xhr, status, error) {
            alert(error);
            alert(xhr.responseText);
        }
    });
}

function showImages(id, images) {
    var popup = $("#popup-images");
    $("#id-product").val(id);
    var imageDrop = $("#images-drop");
    imageDrop.empty();
    imageDrop.append("<p>Kéo và thả ảnh vào đây</p>");
    $.each(images, function (index, image) {
        var img = $("<div>")
            .append($("<span>&times;</span>"))
            .append($("<img>").attr("src", image.link).attr("alt", image.id));
        imageDrop.append(img);
    });
    popup.css("display", "flex");
}

$(document).ready(function () {
    $("#images-drop").on("click", "span", function () {
        var remainingSpans = $("#images-drop span").length;
        if (remainingSpans === 1) {
            alert("Sản phẩm phải có 1 ảnh trở lên!");
        } else {
            $(this).parent().remove();
        }
    });
    $(".close-popup-image").on("click", function () {
        $("#popup-images").css("display", "none");
    });
});

function selectFiles(files) {
    event.preventDefault();
    for (var i = 0; i < files.length; i++) {
        var file = files[i];
        var reader = new FileReader();
        reader.onload = function (event) {
            var imgElement = $("<div>")
                .append($("<span>&times;</span>"))
                .append($("<img>").attr("src", event.target.result).attr("alt", ''));
            $("#images-drop").append(imgElement);
        };
        reader.readAsDataURL(file);
    }
}

function saveImages() {
    var savebtn = $('#save-button-product')
    savebtn.html(`<div class="loader"></div>`)
    savebtn.attr('disabled', 'disabled');
    var id = $("#id-product").val();
    var images = [];
    $("#images-drop img").each(function () {
        var alt = $(this).attr("alt");
        var src = $(this).attr("src");
        if (src.startsWith("http")) {
            src = src.substring(src.indexOf("upload/") + 7, src.lastIndexOf("."));
        }
        images.push({id: alt, name: src});
    });
    $.ajax({
        url: "product/save",
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify({
            'id': id,
            'images': images
        }),
        success: function (response) {
            alert(response);
            location.reload();
        },
        error: function (xhr, status, error) {
            savebtn.html('Lưu');
            savebtn.removeAttr('disabled');
            alert(error);
            alert(xhr.responseText);
        }
    });
}

