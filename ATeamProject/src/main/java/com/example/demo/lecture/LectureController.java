package com.example.demo.lecture;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.course.Course;
import com.example.demo.course.CourseService;
import com.example.demo.course.NotFoundException;
import com.example.demo.file.FileController;
import com.example.demo.file.Files;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller

public class LectureController { //강의 영상(동영상) 페이지 관련
	private final LectureService ls;
	private final CourseService cs;
	private final FileController fc;
	
	@PreAuthorize("isAuthenticated()") // 로그인 한 경우에만 요청 처리
	@GetMapping("/course/{courseKey}/view/{lectureKey}")
	public String viewLecture(Model model, @PathVariable("courseKey")Integer coursekey, 
			@PathVariable("lectureKey") long lectureKey, LectureForm lectureForm) throws SsakException, NotFoundException{
		Lecture l = this.ls.getLecture(lectureKey);
		Course c = this.cs.getCourse(coursekey); // 강의 목차 불러올때 courseKey 값 필요하므로 선언
		model.addAttribute("lecture", l);
		model.addAttribute("course", c);
		return "LectureView";
	}
	
	
//	------------------------addLecture-----------------
	@PreAuthorize("isAuthenticated()") // 로그인 한 경우에만 요청 처리
	@GetMapping("/course/{course_key}/addLecture")
	public String addLecture(Model model, LectureForm lectureForm,
			@PathVariable("course_key")Integer course_key) throws NotFoundException{
		Course c = this.cs.getCourse(course_key);
		model.addAttribute("course",c);
		return "AddLecture";
	}
		
	
	@PostMapping("/course/{course_key}/addlecture") //강의 만든사람만 영상 추가 가능
	public String addlecture(Model model, @PathVariable("course_key") Integer course_key,  HttpServletRequest request,
			@Valid LectureForm lectureForm, BindingResult bindingResult, @RequestParam(value = "file") MultipartFile file) throws Exception{
		Course c = this.cs.getCourse(course_key);
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("course", c);
			return "AddLecture";
		}
		Lecture l = this.ls.returncreate(c, lectureForm.getTitle(),lectureForm.getObjective());
		fc.videofileInsert(request, file, l);
		return String.format("redirect:/course/%s", course_key);
	
	}
	
}
