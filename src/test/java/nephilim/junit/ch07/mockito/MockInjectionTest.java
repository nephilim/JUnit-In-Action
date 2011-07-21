package nephilim.junit.ch07.mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nephilim.junit.ch07.mockito.target.SomeRepository;
import nephilim.junit.ch07.mockito.target.SomeService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MockInjectionTest {
	@Mock private SomeRepository mockedRepository;
	
	@InjectMocks private SomeService service = new SomeService();
	
	@Before 
    public void initMocks() {
		 // Before 대신 MockitoJUnitRunner로 테스트 케이스를 수행해도 된다.
         MockitoAnnotations.initMocks(this);
    }

    @Test 
    public void verifying() {
        service.addEmptyString();	// repo에 빈문자열 추가(key = "1","2","3",...)
        verify(mockedRepository).save(any(String.class), eq(""));
    }
    
    @Test 
    public void stubbing() {
    	when(mockedRepository.find("*")).thenReturn("asterisk");
    	
    	assertEquals("stubbing 한대로 asterisk를 반환해야 함",
    			service.get("*"), 
    			"asterisk");
    }
}
