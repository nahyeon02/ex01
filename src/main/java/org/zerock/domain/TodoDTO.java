package org.zerock.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TodoDTO {
	private String title;
	
	@DateTimeFormat(pattern = "yyyy/mm/dd")
	private Date dueDate;
	//날짜변환 어노테이션 이걸사용하면 @InitBinder은 필요하지않음.
}
