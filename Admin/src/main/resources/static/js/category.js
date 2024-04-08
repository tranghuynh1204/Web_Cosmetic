$(document).ready(function () {
  let isDoubleClick = false;
  $("#tree-categories span").click(function () {
    if (!isDoubleClick) {
      $(this).toggleClass("active");
      $(this).next().toggle();
    }
  });
  $("#tree-categories span").on("dblclick", function () {
    isDoubleClick = true;
    setTimeout(function () {
      isDoubleClick = false;
    }, 300);
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
    }
  });
});
