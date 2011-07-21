JUnit In Action 사내 스터디
===========================
* 진행: 2011/7월~ 
* 참여: nephilim, kayem, yunuuuu

진행 
-----
- 2부
	6. Jetty										
	7. Mock > Mockito로 진행					
	8. In-Cotainer		
- 3부
	9. Ant(x)	
	10. Maven(△) - nephilim?							
	11. CI(x)	
- 4부
	12. UI - 									
	13. Ajax(!GWT) 							
	14. Server-side(cactus) 
	15. JSP(!JSF)
	16. OSGi(x)
	17. 데이터베이스 
	18. JPA(x)
	19. 팁
	
참고 자료
----------
* [Mocks Aren't Stubs](http://martinfowler.com/articles/mocksArentStubs.html#TheDifferenceBetweenMocksAndStubs)
	- Stub 은 테스트 과정에서 일어나는 호출에 대해 지정된 답변을 제공하고, 그 밖의 테스트를 위해 별도로 프로그래밍 되지 않은 질의에 대해서는 대게 아무런 대응을 하지 않는다. 또한 Stub은 email gateway stub 이 '보낸' 메시지를 기억하거나, '보낸' 메일 개수를 저장하는 것과 같이, 호출된 내용에 대한 정보를 기록할 수 있다.
	- Mock은 Mock 객체가 수신할 것으로 예상되는 호출들을 예측하여 미리 프로그래밍한 객체이다. 
	- 해석 from: [오리대마왕집](http://kingori.egloos.com/4169398)