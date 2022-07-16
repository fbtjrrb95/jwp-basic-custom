<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="next.model.*" %>

<!DOCTYPE html>
<html lang="kr">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>SLiPP Java Web Programming</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <link href="../css/styles.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-fixed-top header">
    <div class="col-md-12">
        <div class="navbar-header">

            <a href="/" class="navbar-brand">SLiPP</a>
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse1">
                <i class="glyphicon glyphicon-search"></i>
            </button>

        </div>
        <div class="collapse navbar-collapse" id="navbar-collapse1">
            <form class="navbar-form pull-left">
                <div class="input-group" style="max-width:470px;">
                    <input type="text" class="form-control" placeholder="Search" name="srch-term" id="srch-term">
                    <div class="input-group-btn">
                        <button class="btn btn-default btn-primary" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                    </div>
                </div>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-bell"></i></a>
                    <ul class="dropdown-menu">
                        <li><a href="https://slipp.net" target="_blank">SLiPP</a></li>
                        <li><a href="https://facebook.com" target="_blank">Facebook</a></li>
                    </ul>
                </li>
                <li><a href="/users"><i class="glyphicon glyphicon-user"></i></a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="navbar navbar-default" id="subnav">
    <div class="col-md-12">
        <div class="navbar-header">
            <%
                User user = (User) session.getAttribute("user");
            %>
            <a href="#" style="margin-left:15px;" class="navbar-btn btn btn-default btn-plus dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-home" style="color:#dd1111;"></i> Home <small><i class="glyphicon glyphicon-chevron-down"></i></small></a>
            <ul class="nav dropdown-menu">
                <%
                    if (user != null) {
                        String userId = user.getUserId();
                %>
                <li>
                    <a href="/users/profile?userId=<%=(userId)%>"><i class="glyphicon glyphicon-user" style="color:#1111dd;"></i> Profile</a>
                </li>
                <%
                    }
                %>
                <li class="nav-divider"></li>
                <li><a href="#"><i class="glyphicon glyphicon-cog" style="color:#dd1111;"></i> Settings</a></li>
            </ul>
        </div>
        <div class="collapse navbar-collapse" id="navbar-collapse2">
            <ul class="nav navbar-nav navbar-right">
                <%
                    if (user != null) {
                %>
                <li class="active"><a href="/">Posts</a></li>
                <li><a href="/logout" role="button">로그아웃</a></li>
                <li><a href="/forms/users/update" role="button">개인정보수정</a></li>
                <%
                } else {
                %>
                <li><a href="/forms/login" role="button">로그인</a></li>
                <li><a href="/forms/signup" role="button">회원가입</a></li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
</div>
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
                                <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${question.createdDate}" />
                                <i class="icon-link"></i>
                            </a>
                        </div>
                    </div>
                    <div class="article-doc">
                        ${question.contents}
                    </div>
                    <div class="article-util">
                        <ul class="article-util-list">
                            <li>
                                <a class="link-modify-article" href="#">수정</a>
                            </li>
                            <li>
                                <form class="form-delete" action="#" method="POST">
                                    <input type="hidden" name="_method" value="DELETE">
                                    <button class="link-delete-article" type="submit">삭제</button>
                                </form>
                            </li>
                            <li>
                                <a class="link-modify-article" href="/">목록</a>
                            </li>
                        </ul>
                    </div>
                </article>

                <div class="qna-comment">
                    <div class="qna-comment-slipp">
                        <p class="qna-comment-count"><strong>${question.countOfComment}</strong>개의 의견</p>
                        <div class="qna-comment-slipp-articles">

                            <c:forEach items="${answers}" var="each">
                                <article class="article">
                                    <div class="article-header">
                                        <div class="article-header-thumb">
                                            <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                                        </div>
                                        <div class="article-header-text">
                                                ${each.writer}
                                            <div class="article-header-time"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${each.createdDate}" /></div>
                                        </div>
                                    </div>
                                    <div class="article-doc comment-doc">
                                        <p>${each.contents}</p>
                                    </div>
                                    <div class="article-util">
                                        <ul class="article-util-list">
                                            <li>
                                                <a class="link-modify-article" href="/api/qna/updateAnswer?answerId=${each.answerId}">수정</a>
                                            </li>
                                            <li>
                                                <form class="form-delete" action="/api/qna/deleteAnswer" method="POST">
                                                    <input type="hidden" name="answerId" value="${each.answerId}" />
                                                    <button type="submit" class="link-delete-article">삭제</button>
                                                </form>
                                            </li>
                                        </ul>
                                    </div>
                                </article>
                            </c:forEach>
                            <div class="answerWrite">
                                <form name="answer" method="post">
                                    <input type="hidden" name="questionId" value="${question.questionId}">
                                    <div class="form-group col-lg-4" style="padding-top:10px;">
                                        <input class="form-control" id="writer" name="writer" placeholder="이름">
                                    </div>
                                    <div class="form-group col-lg-12">
                                        <textarea name="contents" id="contents" class="form-control" placeholder=""></textarea>
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

<script src="../js/jquery-2.2.0.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/scripts.js"></script>
    </body>
</html>