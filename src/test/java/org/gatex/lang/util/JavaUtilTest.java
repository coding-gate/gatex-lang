package org.gatex.lang.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class JavaUtilTest {

	JavaUtil javaUtil=new JavaUtil(";","/{0}/","./junitlib/*");
	
	
	@Test
	public void buildCompileStringTest() {
		String compileStr=javaUtil.buildCompileString("directory","Answer.java");
		assertEquals("javac -cp /directory/;./junitlib/* /directory/Answer.java",compileStr);
	}

	@Test
	public void buildRunStringTest(){
		String runStr =javaUtil.buildRunString("directory", "Answer.java");
		assertEquals("java -cp /directory/;./junitlib/* Answer.java",runStr);

	}

}
