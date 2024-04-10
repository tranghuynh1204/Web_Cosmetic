$(document).ready(function () {
    $("#tree-categories span").click(function () {
        $(this).toggleClass("active");
        $(this).next().toggle();
    });
    $("#tree-categories span").on("dblclick", function () {
        $("#category-parent").val(this.innerHTML);
        $("#parent-id").val(this.id);
    });
    const id = $("#id").val();
    if (id) {
        // Loại bỏ sự kiện double click cho span và tất cả các phần tử con trong nó
        $("#" + id).off("dblclick");
        $("#" + id)
            .next("ul")
            .find("span")
            .off("dblclick");
    }

    $("#tree-categories li").each(function () {
        if ($(this).find("ul").children().length === 0) {
            $(this).addClass("empty-ul");
            $(this).find("button").css('display', 'block');
        }
    });
});
