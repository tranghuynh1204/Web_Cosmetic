
$(document).ready(function () {
    $("#tree-categories span").click(function () {
        $(this).toggleClass("active");
        $(this).next().toggle();
    });

    $('#tree-categories li').each(function () {
        if ($(this).find('ul').children().length === 0) {
            $(this).addClass('empty-ul');
        }
    });
});
$(document).ready(function() {
    $("#tree-categories span").on("dblclick", function() {
        $("#category-parent").val(this.innerHTML);
        $("#parent-id").val(this.id);
    });
});
