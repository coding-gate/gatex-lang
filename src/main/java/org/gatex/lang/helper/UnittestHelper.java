package org.gatex.lang.helper;

import org.gatex.lang.dto.CmdOutput;
import org.gatex.lang.util.FileUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Arrays;


public abstract class UnittestHelper {

	protected String ANSWER_PATH_TMPL; // "/tempfiles/{0}/"

	private String getDirPath(String dirName) {
		return MessageFormat.format(ANSWER_PATH_TMPL, dirName);
	}

	public void saveFile(String dirName, String jsFileName, String content) throws IOException{
		String srcPath = getDirPath(dirName);
		FileUtil.createFile(srcPath+jsFileName, content);
	}

	public void deleteFiles(String dirName, String[] fileNames) throws IOException{
		String srcPath = getDirPath(dirName);
		for (String fileName:fileNames) {
			FileUtil.deleteFile(srcPath+fileName);
		}
	}

	public boolean createDir(String dirName) {
		String directoryPath = getDirPath(dirName);
		return FileUtil.createDirectory(directoryPath);
	}


	public void deleteDir(String dirName) throws IOException {
		String directoryPath = getDirPath(dirName);
		FileUtil.deleteDirectory(directoryPath);
	}

	protected static String getOutput(InputStream in) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		try(BufferedReader br = new BufferedReader(new InputStreamReader(in))){
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
		}
		return sb.toString();
	}

	public abstract CmdOutput execCommand(String dirName) throws IOException;
}
