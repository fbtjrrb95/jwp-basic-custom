<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="kr">
<head>
    <%@ include file="/include/header.jspf" %>
</head>
<body>
<%@ include file="/include/navigation.jspf" %>

<div class="container" id="main">
    <div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
        <div class="panel panel-default">
            <header class="qna-header">
                <h2 class="qna-title">${question.title}</h2>
            </header>
            <div class="content-main">
                <article class="article">
                    <div class="article-header">
                        <div class="article-header-thumb">
                            <img src="https://graph.facebook.com/v2.3/100000059371774/picture" class="article-author-thumb" alt="">
                        </div>
                        <div class="article-header-text">
                            <a href="/users/92/kimmunsu" class="article-author-name">${question.writer}</a>
                            <a href="/questions/413" class="article-header-time" title="퍼머링크">
                                <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${question.createdAt}" />
                                <i class="icon-link"></i>
                            </a>
                        </div>
                    </div>
                    <input class="article-doc" value="${question.contents}">
                    <div class="article-util">
                        <ul class="article-util-list">
                            <li>
                                <div class="question-update">
                                    <input type="hidden" name="questionId" value="${question.id}">
                                    <button class="link-update-article" type="submit">수정</button>
                                </div>
                            </li>
                            <li>
                                <div class="question-delete">
                                    <input type="hidden" name="questionId" value="${question.id}">
                                    <button class="link-delete-article" type="submit">삭제</button>
                                </div>
                            </li>
                            <li>
                                <a class="link-modify-article" href="/">목록</a>
                            </li>
                        </ul>
                    </div>
                </article>

                <div class="qna-comment">
                    <div class="qna-comment-slipp">
                        <p class="qna-comment-count"><strong>${question.answerCount}</strong>개의 의견</p>
                        <div class="qna-comment-slipp-articles">
                            <c:forEach items="${answers}" var="each">
                                <article class="article">
                                    <div class="article-header">
                                        <div class="article-header-thumb">
                                            <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                                        </div>
                                        <div class="article-header-text">
                                                ${each.writer}
                                            <div class="article-header-time"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${each.createdAt}" /></div>
                                        </div>
                                    </div>
                                    <div class="article-doc comment-doc">
                                        <p>${each.contents}</p>
                                    </div>
                                    <div class="article-util">
                                        <ul class="article-util-list">
                                            <li>
                                                <div class="answer-update">
                                                    <input type="hidden" name="answerId" value="${each.id}" />
                                                    <button type="submit" class="link-delete-article">수정</button>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="answer-delete">
                                                    <input type="hidden" name="answerId" value="${each.id}" />
                                                    <button type="submit" class="link-delete-article">삭제</button>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </article>
                            </c:forEach>
                            <div class="answerWrite">
                                <form name="answer" method="post">
                                    <input type="hidden" name="questionId" value="${question.id}">
                                    <div class="form-group col-lg-12">
                                        <textarea name="contents" id="contents" class="form-control" placeholder="답변 내용"></textarea>
                                    </div>
                                    <input class="btn btn-success pull-right" type="submit" value="답변하기" />
                                    <div class="clearfix" />
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/template" id="answerTemplate">
    <article class="article">
        <div class="article-header">
            <div class="article-header-thumb">
                <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
            </div>
            <div class="article-header-text">
                {0}
                <div class="article-header-time">{1}</div>
            </div>
        </div>
        <div class="article-doc comment-doc">
            {2}
        </div>
        <div class="article-util">
            <ul class="article-util-list">
                <li>
                    <a class="link-modify-article" href="/api/qna/updateAnswer/{3}">수정</a>
                </li>
                <li>
                    <div class="answerDelete">
                        <input type="hidden" name="answerId" value="{4}" />
                        <button type="submit" class="link-delete-article">삭제</button>
                    </div>
                </li>
            </ul>
        </div>
    </article>
</script>
<%@ include file="/include/footer.jspf" %>
</body>
</html>