
$(document).ready(function() {/* jQuery toggle layout */
$('#btnToggle').click(function() {
  if ($(this).hasClass('on')) {
    $('#main .col-md-6').addClass('col-md-4').removeClass('col-md-6');
    $(this).removeClass('on');
  }
  else {
    $('#main .col-md-4').addClass('col-md-6').removeClass('col-md-4');
    $(this).addClass('on');
  }
});

$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
  e.preventDefault();
  const queryString = $("form[name=answer]").serialize();

  $.ajax({
    type: 'post',
    url: 'qna/answer',
    data: queryString,
    dataType: 'json',
    error: onError,
    success: onSuccess,
  })
}

function onSuccess(json, status) {
  const answerTemplate = $("#answerTemplate").html();
  const template = answerTemplate.format(json.writer, new Date(json.createdDate), json.contents, json.answerId);
  $(".qna-comment-slipp-articles").prepend(template);
}
});