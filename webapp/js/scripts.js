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
    error: (xhr, status) => {
      alert("error");
    },
    success: (json, status) => {
      const answerTemplate = $('#answerTemplate').html();
      const template = answerTemplate.format(json.writer, new Date(json.createdAt), json.contents, json.id, json.id);
      $(".qna-comment-slipp-articles").prepend(template);
    },
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
    error: (xhr, status) => {
      alert("error");
    },
    success: (json, status) => {
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