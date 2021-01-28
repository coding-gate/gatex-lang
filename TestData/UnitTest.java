import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UnitTest {
	
	Answer ans=new Answer();
	
	@Test
	public void sucessTest(){
		assertEquals(2, ans.findLength("ab"));
	}
	
	@Test
	public void failureTest(){
		assertEquals(3, ans.findLength("abcd"));
	}
	
	@Test(expected=NullPointerException.class)
	public void nullPointerTest(){
		assertEquals(3, ans.findLength(null));
	}
	
	@Test(timeout=10)
	public void performanceTest(){
		assertEquals(3, ans.findLength("abc"));
	}


}
