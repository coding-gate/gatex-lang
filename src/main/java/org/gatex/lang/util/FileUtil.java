package org.gatex.lang.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

@Slf4j
public class FileUtil {

	private FileUtil(){}


	public static void createFile(String pathStr, String content) throws IOException {
       //pathStr="/tmp/tempfiles/userid123/Example.java"
		log.info("Creating file as {}", pathStr);
		Path path = Paths.get(pathStr);
		Files.write(path, content.getBytes());		
	}
	
	public static String readFile(String pathStr) throws IOException {
	   log.info("Reading file from {}", pathStr);	
	   Path path = Paths.get(pathStr);
		return new String(Files.readAllBytes(path));
	}
	
	public static boolean createDirectory(String dirPath){
		boolean isSuccess=true;		
		File file=new File(dirPath);		
		 if (!file.exists()) {
			 isSuccess=file.mkdir();
		 }		
		log.info("Is directory created on path {} ? {}", dirPath, isSuccess);
		return isSuccess;
	}

	public static void deleteFile(String pathStr) throws IOException {
		//pathStr="/tmp/tempfiles/userid123/Example.java"
		log.info("Deleting file  {}", pathStr);
		Path path = Paths.get(pathStr);
		Files.delete(path);
	}
	
	
	
	public static void deleteDirectory(String directoryFilePath) throws IOException {
		log.info("Deleting directory from path {}", directoryFilePath);
		Path directory = Paths.get(directoryFilePath);

		if (Files.exists(directory)) {
			Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
					Files.delete(path);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path directory, IOException ioException) throws IOException {
					Files.delete(directory);
					return FileVisitResult.CONTINUE;
				}
			});
		}
	}
	
	
	
	
}
