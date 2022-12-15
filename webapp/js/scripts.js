$(".answerWrite input[type=submit]").click(addAnswer);
$(".questionWrite input[type=submit]").click(addQuestion);
$(".question-delete button[type=submit]").click(deleteQuestion);
$(".question-update button[type=submit]").click(updateQuestion);
$(".qna-comment").on("click", ".answerDelete", deleteAnswer);

function addAnswer(e) {
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
      const answer = json.answer;
      const template = answerTemplate.format(answer.writer, new Date(answer.createdAt), answer.contents, answer.id, answer.id);
      $('.qna-comment-slipp-articles').prepend(template);
      $('#writer').val('');
      $('#contents').val('');
    },
  })
}

function addQuestion(e) {
  e.preventDefault();
  const params = $("form[name=question]").serialize();

  $.ajax({
    type: 'post',
    url: '/qna/questions',
    data: params,
    error: (xhr, status) => {
      if (xhr.status === 403) {
        alert('forbidden');
      } else {
        alert('error');
      }
    },
    success: (json, status) => {
      window.location = "http://localhost:8080/"
    },
  })
}

function deleteAnswer(e) {
  e.preventDefault();
  const answerId = $(this).closest('div').find('input[name=answerId]').val();
  const document = $(this).closest('.article');
  $.ajax({
    type: 'delete',
    url: '/qna/answers/' + answerId,
    error: (xhr, status) => {
      alert('error');
    },
    success: (json, status) => {
      document.remove();
      alert('success');
    },
  })
}

function deleteQuestion(e) {
  e.preventDefault();
  const questionId = $(this).closest('div').find('input[name=questionId]').val();
  $.ajax({
    type: 'delete',
    url: '/qna/questions/' + questionId,
    error: (xhr, status) => {
      alert('error');
    },
    success: (json, status) => {
      window.location = "http://localhost:8080/"
    },
  })
}

function updateQuestion(e) {
  e.preventDefault();
  const contents = $('.article-doc').val();
  const data = {
    contents,
  };
  const questionId = $(this).closest('div').find('input[name=questionId]').val();
  $.ajax({
    type: 'put',
    url: '/qna/questions/' + questionId,
    contentType: 'application/json',
    data: JSON.stringify(data),
    error: (xhr, status) => {
      alert('error');
    },
    success: (json, status) => {
      $('.article-doc').val(json.contents);
    },
  })
}

String.prototype.format = function () {
  const args = arguments;
  return this.replace(/{(\d+)}/g, function (match, number) {
    return typeof args[number] != 'undefined' ? args[number] : match;
  });
};