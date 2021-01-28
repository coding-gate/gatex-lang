package org.gatex.lang.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmdOutput {
	
	private Integer status;
	private String outputMsg;
	private String errorMsg;
	private String exceptionMsg="";	
	
	
	public String toString(){		
		StringBuilder sb=new StringBuilder();
		sb.append("Status :");
		sb.append(status);
		sb.append("\n");
		sb.append("OutputMsg :");
		sb.append(outputMsg);
		sb.append("\n");
		sb.append("ErrorMsg :");
		sb.append(errorMsg);
		sb.append("\n");
		sb.append("ExceptionMsg :");
		sb.append(exceptionMsg);
		
		return sb.toString();
		
	}

}
