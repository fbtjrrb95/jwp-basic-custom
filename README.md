# jwp-basic-custom

## Memo
request.forward 를 하면 현재의 url 이 변경되지 않는다.  
href 등으로 로직이 이어질 것 같으면, sendRedirect 로 현재 url 을 갱신해주어야 한다.  
 
tomcat 이 root path 에 반환하는 view(html, htm, jsp) 를 welcomeFile 이라고 칭해진다. 
 기본적으로 web.xml 에 설정하지 않는다면, tomcat 은 root 위치에 index.html > index.html > index.jsp
 순서로 확인하여 있으면 반환한다.
 > 이 과정을 찾는 것이 오래 걸렸다. default 값이어서 tomcat 코드까지 파고 들어가야 한다. 그래서 global web.xml 을 두는 것이 명시적이어서 좋아보인다. 

## requestDispatcher



## servlet
웹 페이지에서 사용자가 필요한 자료를 동적으로 반환하기 위해 고안된 자바 프로그램
필요한 자료에 응답하기 위해 연산하는 역할을 담당

### servlet container 

![servlet-container](./images/servlet-container.png)


#### 동작 순서
* 웹서버와 서블릿이 소켓 생성하여 통신 가능하게 함
* HttpServletRequest, HttpServletResponse 객체를 생성  
* 사용자의 요청이 들어오면 해당되는 서블릿을 찾아서 서블릿의 service() 호출
* 서블릿이 연산한 내용을 HttpServletResponse 에 담아서 사용자에게 응답
* HttpServletRequest, HttpServletResponse 객체를 소멸

#### 역할
* 실제로 소켓을 만들고, 연결하는 로직 구현 
  * 개발자가 비지니스 로직에 집중할 수 있게 함
* 서블릿의 생명 주기를 관리
* 멀티 쓰레드 지원 및 관리

#### @WebServlet loadOnStartup
서블릿은 사용자에게 처음 요청이 왔을 때 생성 및 초기화
하지만, 사용자 요청과 관계없이 서블릿을 메모리에 로드하고 싶을 때 주는 옵션
(e.g. loadOnStartUp = 1)
할당된 숫자가 낮을 수록 먼저 생성

#### user dao refactoring
하나의 클래스는 한 가지 역할만 해야한다 라는 의미를 알 것 같다
리팩토링을 하다보니 느꼈는데, 한 가지 역할만 하도록 코드를 설계하게 되면 코드 리팩토링을 하기에 굉장히 집중도 있게 할 수 있다
한 가지, 특정 부분만 리팩토링을 하면 되기 때문에,

그러한 설계를 가져갈 수 있도록 고민하는 습관을 들여서 코드를 짠다면, 이 코드가 유지보수, 운영되기에 굉장히 쉬워질 것 같다

#### static 과 singleton 의 차이

#### @SuppressWarnings("rawtypes") ?

#### @FunctionalInterface
 익명 클래스를 만드는 대신에 람다를 사용할 경우 인터페이스의 메소드가 하나만 존재햐아 한다.
 또한, 람다 표현식으로 사용할 인터페이스라고 지정하려면 인터페이스에 @FunctionalInterface 애노테이션을 추가해야 한다.

#### 뷰와 모델 데이터 처리의 추상화 (ModelAndView)
 기존의 View 리턴타입으로 하니까, View 를 처리하기 위한 각각의 컨트롤러에서 중복된 코드가 발생했다,
 view와 model을 만드는 역할을 하는 데에 더 집중하는 저 클래스를 만들었고, 그래서 손쉽게 다양한 뷰에 대해 대응할 수 있었다.
 앞으로 이런 식의 여러 군데에서 중복된 코드가 발생하면 그 일만 하는 클래스를 만들고, 그것을 반환하건 인자로 넘기건 해서 처리하면 훨씬 유지보수가 편한 코드를 짤 수 있을 것 같다.
 
## Memo
 if/else 문이 반복되는 문제를 해결하고 싶다면, 인터페이스를 추가하여 해결할 수 있음

### 다음 코드가 멀티 스레드 환경에서 문제가 되는 이유
```java
public class ShowController extends AbstractController {
   private QuestionDao questionDao = new QuestionDao();
   private AnswerDao answerDao = new AnswerDao();
   private Question question;
   private List<Answer> answers;
  
   @Override
   public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
      Long questionId = Long.parseLong(req.getParameter("questionId"));
    
      question = questionDao.findById(questionId); // 1
      answers = answerDao.findAllByQuestionId(questionId); // 2
    
      ModelAndView mav = jspView("/qna/show.jsp");
      mav.addObject("question", question);
      mav.addObject("answers", answers);
      return mav;
   }
}
```

question 변수와 answers 변수가 클래스 변수로 선언이 되어 멀티스레드에서 1, 2 과정에서 동시에 접근하여 변경할 수 있음
그래서 특정 질문에 다른 답변이 노출될 수 있음

아래의 코드로 변경해야 함
```java
public class ShowQuestionController extends AbstractController {
    private QuestionDao questionDao = new QuestionDao();
    private AnswerDao answerDao = new AnswerDao();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        long questionId = Long.parseLong(req.getParameter("questionId"));

        Question question = questionDao.findById(questionId);
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);

        ModelAndView mav = jspView("/qna/show.jsp");
        mav.addObject("question", question);
        mav.addObject("answers", answers);
        return mav;
    }
}
```
> 변수 선언할 때의 스코프는 최대한 적게 가져가는 것을 습관으로 하자!

* script.js 에서 새로운 페이지를 띄우기 위해 window.location 을 새로 지정하면 호출했던 api 응답이 resource identifier not found 뜨면서 확인할 수 없다.