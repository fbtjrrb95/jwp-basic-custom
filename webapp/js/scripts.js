$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
  console.log('add answer function');
  e.preventDefault();
  const queryString = $("form[name=answer]").serialize();

  $.ajax({
    type: 'post',
    url: '/qna/answer',
    data: queryString,
    dataType: 'json',
    error: onError,
    success: onSuccess,
  })
}

String.prototype.format = function () {
  const args = arguments;
  return this.replace(/{(\d+)}/g, function (match, number) {
    return typeof args[number] != 'undefined' ? args[number] : match;
  });
};

function onSuccess(json, status) {
  const answerTemplate = $("#answerTemplate").html();
  const template = answerTemplate.format(json.writer, new Date(json.createdDate), json.contents, json.answerId);
  $(".qna-comment-slipp-articles").prepend(template);
}

function onError(xhr, status) {
  alert("error");
}
