# jwp-basic-custom

## Memo
request.forward 를 하면 현재의 url 이 변경되지 않는다.  
href 등으로 로직이 이어질 것 같으면, sendRedirect 로 현재 url 을 갱신해주어야 한다.  
 
tomcat 이 root path 에 반환하는 view(html, htm, jsp) 를 welcomeFile 이라고 칭해진다. 
 기본적으로 web.xml 에 설정하지 않는다면, tomcat 은 root 위치에 index.html > index.html > index.jsp
 순서로 확인하여 있으면 반환한다.
 > 이 과정을 찾는 것이 오래 걸렸다. default 값이어서 tomcat 코드까지 파고 들어가야 한다. 그래서 global web.xml 을 두는 것이 명시적이어서 좋아보인다. 

## servlet 이란?
웹 페이지에서 사용자가 필요한 자료를 동적으로 반환하기 위해 고안된 자바 프로그램
필요한 자료에 응답하기 위해 연산하는 역할을 담당

### servlet container
* HttpServletRequest, HttpServletResponse 객체를 생성  
* 사용자의 요청이 들어오면 해당되는 서블릿을 찾아서 서블릿의 service() 호출
* 서블릿이 연산한 내용을 HttpServletResponse 에 담아서 사용자에게 응답
* HttpServletRequest, HttpServletResponse 객체를 소멸
* 서블릿의 생명 주기를 관리

### servlet 의 생명주기
  