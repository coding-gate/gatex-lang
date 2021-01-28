package org.gatex.lang.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UnitTestAndAnswerDTO{	
	@NotNull(message="answerCode can't be null")
	String answerCode;
	@NotNull(message="unitTestCode can't be null")
	String unitTestCode;
}
