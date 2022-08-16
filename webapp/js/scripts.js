$(".answerWrite input[type=submit]").click(addAnswer);
$(".questionWrite input[type=submit]").click(addQuestion);

function addAnswer(e) {
  console.log('add answer function');
  e.preventDefault();
  const params = $("form[name=answer]").serialize();

  $.ajax({
    type: 'post',
    url: '/qna/answers',
    data: params,
    dataType: 'json',
    error: onError,
    success: onSuccess,
  })
}

function addQuestion(e) {
  console.log('add question function');
  e.preventDefault();
  const params = $("form[name=question]").serialize();

  $.ajax({
    type: 'post',
    url: '/qna/questions',
    data: params,
    error: function (xhr, status) {
      alert("error");
    },
    success: function (json, status) {
      window.location = "http://localhost:8080/"
    },
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
