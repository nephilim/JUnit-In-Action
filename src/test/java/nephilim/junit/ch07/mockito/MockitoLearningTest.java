package nephilim.junit.ch07.mockito;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.exceptions.misusing.InvalidUseOfMatchersException;

public class MockitoLearningTest {

	@Test 
	public void mock객체의디폴트값확인() {
		List<Integer> list = mock(List.class);
		
		// default value를 반환한다.
		assertThat("객체에 해당하는 default 값은 null이어야 한다.",
				list.get(0),is(nullValue()));
	}
	
	@Test
	public void verify를이용한메서드호출여부확인() {
		List<Integer> list = mock(List.class);

		list.get(0);
		
		// get(0) 호출 여부를 확인
		verify(list).get(0);
	}
	
	@Test(expected=AssertionError.class)
	public void verify를이용한메서드호출여부확인_호출하지않은인자는verify시에러발생() {
		
		List<Integer> list = mock(List.class);

		list.get(0);
		
		// get(1) 호출 여부를 확인
		// > 호출한 적이 없어 AssertionError 발생
		verify(list).get(1);
	}
	
	@Test 
	public void 정수리스트stubbing_verifying() {
		List<Integer> list = mock(List.class);
		when(list.get(0)).thenReturn(10);
		
		// 리스트 원소 반환을 검증
		assertThat("인자 0의 경우 지정한 대로 10을 반환해야 함", 
				list.get(0), is(equalTo(10)));
		
		// 지정하지 않은 원소
		assertThat("지정하지 않지 않은 인자는 default 값을 반환해야 함",
				list.get(1), is(nullValue()));
		
		// 0호출 여부를 확인
		// mock은 자신의 호출을 recording하고 있다
		verify(list).get(0);
	}
	
	@Test
	public void 호출횟수검증 () {
		List<Integer> mockedList = mock(List.class);
		when(mockedList.get(0)).thenReturn(10);

		// 총 3회 호출
		assertThat("인자 0의 경우 지정한 대로 10을 반환해야 함", 
				mockedList.get(0), is(equalTo(10)));
		assertThat(mockedList.get(0), is(equalTo(10)));
		assertThat(mockedList.get(0), is(equalTo(10)));
		
		// 호출 횟수를 확인
		verify(mockedList, atLeast(2)).get(0);
		verify(mockedList, times(3)).get(0);
		
		// 호출되지 않았음을 확인
		verify(mockedList, never()).get(10);
	}
	
		
	@Test(expected = MockitoException.class)
	public void FinalClass목객체는작성불가() {
		/*
		 * final, anonymous, primitive types는 
		 * mock을 생성할 수 없다.
		 * String final 
		 */
		mock(String.class);
	}
	
	interface SomeInterface { public int someMethod();	}
	
	@Test
	public void 인터페이스구현체를작성할때목이용하기() {
		// mock을 이용해 interface의 간편 구현체를 만들기도 한다.
		SomeInterface someInterface = mock(SomeInterface.class);
		when(someInterface.someMethod()).thenReturn(10);
		
		assertEquals(10, someInterface.someMethod());
	}
	
	@Test
	public void argument_matcher이용하기_stubbing() {
		final String RETURN_MSG = "Always Me!"; 
		List<String> mockedList = mock(List.class);
		List<String> realList = new LinkedList<String>();
		
		//어떠한 정수를 전달해도 정해진 메시지가 반환된다
		when(mockedList.get(anyInt())).thenReturn(RETURN_MSG);
		assertThat( 
				"임의의 정수를 전달해도 정해진 메시지가 반환되어야 한다",
				mockedList.get(0), is(equalTo(RETURN_MSG)));
		assertThat( 
				mockedList.get(1), is(equalTo(RETURN_MSG)));
		assertThat( 
				mockedList.get(999), is(equalTo(RETURN_MSG)));
		
		// -1에 대한 동작 확인
		assertThat( 
				"실제 리스트와는 달리 -1을 전달해도 정해진 메시지가 반환된다",
				mockedList.get(-1), is(equalTo(RETURN_MSG)));
		
		try {
			realList.get(-1);
			fail("LinkedList의 경우 -1번째의 원소를 조회하면" + 
				 "java.lang.IndexOutOfBoundsException: -1이 발생해야 함");
		} catch ( IndexOutOfBoundsException indexExeption) {
			// succeed
		} 
	}
	
	@Test
	public void argument_matcher이용하기_stubbing_고급_hamcrestmatcher함께사용() {
		// 각종 matcher는 다음을 참고
		// http://mockito.googlecode.com/svn/branches/1.6/javadoc/org/mockito/Matchers.html
	}
	
	@Test
	public void argument_matcher이용하기_타입확인() {
		Map<String, Integer> mockedMap = mock(HashMap.class);
		
		mockedMap.put("KEY10", new Integer(10));
		
		// isA는 class 종류를 확인하는 matcher
		verify(mockedMap).put(eq("KEY10"), isA(Integer.class));
		//verify(mockedMap).put("KEY10", new Integer(10));
	}
	
	@Test(expected=InvalidUseOfMatchersException.class)
	public void argument_matcher를이용했다면_모든인자에matcher를써야함() {
		Map<String, Integer> mockedMap = mock(HashMap.class);
		mockedMap.put("KEY10", new Integer(10));
		
		// 인자에 모두 matcher를 적용하지 않아 예외 발생
		verify(mockedMap).put("KEY10", isA(Integer.class));
	}
	
	@Test
	public void argument_matcher이용하기_veryfing() {
		final String RETURN_MSG = "Always Me!"; 
		List<String> mockedList = mock(List.class);
		
		when(mockedList.get(1)).thenReturn(RETURN_MSG);
		
		assertThat( mockedList.get(1), is(equalTo(RETURN_MSG)) );
		
		// 임의의 정수를 인자로 get()을 호출한 적이 있는지 검증 
		verify(mockedList).get(anyInt());
	}
	
	@Test(expected=RuntimeException.class)
	public void 예외_stubbing() {
		List<String> mockedList = mock(List.class);
		
		when(mockedList.get(anyInt())).thenThrow(new RuntimeException());
		
		//예외 발생
		mockedList.get(91);
	}
	
	@Test
	public void 연속적으로변화는결과stubbing() {
		final String RETURN_MSG = "Always Me!"; 
		List<String> mockedList = mock(List.class);
		
		when(mockedList.get(0))
			.thenReturn(RETURN_MSG)							// 1번째 호출에는 문자열 반환
			.thenThrow(new IllegalStateException());		// 2번째 호출에는 예외 발생
		
		assertThat( mockedList.get(0), is(equalTo(RETURN_MSG)) );
		
		try {
			mockedList.get(0);
			fail();
		} catch ( IllegalStateException ise) {
			// succeed
		}
		
		// 참고- 연속적인 결과 반환에 대한 간략한 표기법
		// when(mock.someMethod("some arg")) .thenReturn("one", "two", "three");
	}
	
	@Test
	public void void메서드stubbing_doThrow() {
		List<String> mockedList = mock(List.class);
		doThrow(new RuntimeException()).when(mockedList).clear();	// when 의 형태가 달라진다는 것이 포인트
		
		try {
			mockedList.clear();
			fail();
		} catch (RuntimeException e) {
			//succeed
		}
	}
	
	@Test
	public void void메서드stubbing_doReturn() {
		final String RETURN_MSG = "Always Me!"; 
		List<String> mockedList = mock(List.class);
		
		when(mockedList.get(0)).thenThrow(new RuntimeException());
		// ... 사용
		
		// 중요! 다음의 overriding 시도는 예외가 발생함
		//when(mockedList.get(0)).thenReturn(RETURN_MSG);
		
		//doReturn은 정상적인 return이 불가능한 상황에서 사용된다.
		doReturn(RETURN_MSG).when(mockedList).get(0);
		
		assertThat("성공적으로 오버라이딩되어 메시지가 반환되어야 함",
				mockedList.get(0), is(equalTo(RETURN_MSG)));
	}
	
	@Test
	public void spying() {
		List<String> list = new LinkedList();
		List<String> spiedList = spy(list);
		
		spiedList.add("A");
		spiedList.add("B");
		
		// 정상 동작
		assertThat(spiedList.get(0),is(equalTo("A")));
		assertThat(spiedList.get(1),is(equalTo("B")));
		try {
			spiedList.get(2);
			fail();
		} catch( IndexOutOfBoundsException indexException) {
			//succeed
		}
		
		// 일부의 상황에 대한 stubbing 가능
		//when(spiedList.get(2)).thenReturn("C");		// 호출이 되는 순간 예외 발생 = 잘못된 예 
		
		doReturn("C").when(spiedList).get(2);
		assertThat(spiedList.get(2),is(equalTo("C")));
	}

	
	// @Mock, @Spy, @InjectMocks
	// MockitoAnnotations.initMocks(testClass);
}
