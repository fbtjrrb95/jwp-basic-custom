# jwp-basic-custom

## 6.1.3
 request.forward 를 하면 현재의 url 이 변경되지 않는다.  
 href 등으로 로직이 이어질 것 같으면, sendRedirect 로 현재 url 을 갱신해주어야 한다. 
 
 tomcat 이 root path 에 반환하는 view(html, htm, jsp) 를 welcomeFile 이라고 칭해진다. 
 기본적으로 web.xml 에 설정하지 않는다면, tomcat 은 root 위치에 index.html > index.html > index.jsp
 순서로 확인하여 있으면 반환한다.  
 
 이 과정을 찾는 것이 오래 걸렸다. default 값이어서 tomcat 코드까지 파고 들어가야 한다. 그래서 global web.xml 을 두는 것이 명시적이어서 좋아보인다. 
