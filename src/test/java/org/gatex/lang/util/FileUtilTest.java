package org.gatex.lang.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FileUtilTest {
	
	
	@Test
	public void createDirectoryTest() throws IOException {
		
		String dir="./CreateDir";		
		FileUtil.createDirectory(dir);	
		File file=new File(dir);				
		assertTrue(file.exists());
		FileUtil.deleteDirectory(dir);
	}
	
	
	@Test
	public void deleteDirectoryTest() throws IOException{	
		String dir="./DeleteDir";		
		FileUtil.createDirectory(dir);	
		FileUtil.deleteDirectory(dir);

		File file=new File(dir);		
		assertFalse(file.exists());
	}
	
	
	@Test	
	public void createFileTest() throws IOException {	
		String dir="./CreateFileDir";
		String path=dir+"/CreateFile.java";
		FileUtil.createDirectory(dir);
		String content="Test Data \n line 2";
		FileUtil.createFile(path, content);
		
		String actual=FileUtil.readFile(path); 		
		assertEquals(content, actual);
		FileUtil.deleteDirectory(dir);
		
	}

}
