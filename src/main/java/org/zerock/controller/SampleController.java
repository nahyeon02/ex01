package org.zerock.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.SampleDTOList;
import org.zerock.domain.TodoDTO;

import lombok.extern.log4j.Log4j;


@Controller
@RequestMapping("/sample/*")
//현재 클래스의 모든 메서드들의 기본적인 URL
@Log4j
//log4j에러났을때 pom.xml에서 log4j <scope>부분 삭제하면됨
public class SampleController {
	
	 @InitBinder
	 //문자열데이터를 자동으로 date타입으로 변환
	 public void initBinder(WebDataBinder binder) {
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
	 }
	
	@RequestMapping("")
	public void basic() {
		log.info("basic...................");
	}
	

	@RequestMapping(value = "/basic", method = { RequestMethod.GET, RequestMethod.POST })
	public void basicGet() {

		log.info("basic get...................");

	}
	
	@GetMapping("/basicOnlyGet")
	//스프링 4.3부터 등장 get방식에서만 사용가능함.
	public void basicGet2() {

		log.info("basic get only get...................");

	}
	
	@GetMapping("/ex01")
	public String ex01(SampleDTO dto) {
	//http://localhost:7001/sample/ex01?name=AAA&age=11
		log.info("" + dto);

		return "ex01";
	}
	
	@GetMapping("/ex02")
	public String ex02(@RequestParam("name") String name22, @RequestParam("age") int age2) {
		//파라미터 타입에 맞게 자동변환
		//@RequestParam = 파라미터로 사용된 변수의 이름과 전달되는 파라미터의 이름이 다를때 유요하게 사용함.
		log.info("name22333: " + name22);
		log.info("age: " + age2);

		return "ex02";
	}
	
	@GetMapping("/exList")
	public String ex02List (@RequestParam("ids") ArrayList<String> ids) {
		//http://localhost:7001/sample/exList?ids=111&ids=222&ids=333
		log.info("ids=="+ids);
		
		return "ex02List";
	}
	
	@GetMapping("/ex02Array")
	public String ex02Array(@RequestParam("ids") String[] ids) {

		log.info("array ids: " + Arrays.toString(ids));

		return "ex02Array";
	}
	
	@GetMapping("/ex02Bean")
	public String ex02Bean(SampleDTOList list) {
		//http://localhost:7001/sample/ex02Bean?list%5B0%5D.name=aaa&list%5B2%5D.name=bbb
		log.info("list dtos: " + list);

		return "ex02Bean";
	}
	
	@GetMapping("/ex03")
	public String ex03(TodoDTO todo) {
		//http://localhost:7001/sample/ex03?title=test&dueDate=2018-01-01
		
		log.info("todo: " + todo);
		return "ex02";
	}
	
	@GetMapping("/ex03_2")
	public String ex03_2(TodoDTO todo) {
	//@DateTimeFormat(pattern = "yyyy/mm/dd")
	//날짜변환 어노테이션 이걸사용하면 @InitBinder은 필요하지않음. > DTO에 선언
	//http://localhost:7001/sample/ex03_2?title=test&dueDate=2018/01/01
		
		log.info("todo: " + todo);
		return "ex02";
	}
	
	@GetMapping("/ex04")
	public String ex04(SampleDTO dto, @ModelAttribute("page") int page) {
 //http://localhost:7001/sample/ex04?name=aaa&age=11&page=9
		//@ModelAttribute 를 통해서 page변수가 화면까지 간다
		
		log.info("dto: " + dto);
		log.info("page: " + page);

		return "/sample/ex04";
	}
	
	/* Controller의 return 타입
	 * 1. String : jsp파일의 경로와 파일이름을 나타내기위해 사용
	 * 2. void : 호출하는 URL과 동일한 이름의 jsp의미
	 * 3. VO,DTO : 주로 JSON타입의 데이터를 만들어서 반환
	 * 4. ResponseEntity : response할때 Http 헤더 정보와 내용을 가공하는 용도로 사용
	 * 5. Model, ModelAndView : Model로 데이터를 반환하거나 화면까지 같이 지정하는 경우
	 * 6. HttpHeaders : 응답에 내용없이 Http헤더 메시지만 전달
	 * */
	
	@GetMapping("/ex05")
	public void ex05() {
		log.info("/ex05..........");
	}
	
	@GetMapping("/ex06")
	public @ResponseBody SampleDTO ex06() {
		log.info("/ex06..........");

		SampleDTO dto = new SampleDTO();
		dto.setAge(10);
		dto.setName("홍길동");

		return dto;
	}
	

	@GetMapping("/ex07")
	public ResponseEntity<String> ex07() {
		log.info("/ex07..........");
		String msg = "{\"name\": \"홍길동\"}";
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
		
		return new ResponseEntity<>(msg, header, HttpStatus.OK);
		
	}
	
	@GetMapping("/exUpload")
	public void exUpload() {
		log.info("/exUpload..........");
	}

	@PostMapping("/exUploadPost")
	public void exUploadPost(ArrayList<MultipartFile> files) {

		files.forEach(file -> {
			log.info("----------------------------------");
			log.info("name:" + file.getOriginalFilename());
			log.info("size:" + file.getSize());

		});
	}

	
}
