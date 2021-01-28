package org.gatex.lang.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDTO {
	@NotNull(message="answerCode can't be null")
	String answerCode;
}
