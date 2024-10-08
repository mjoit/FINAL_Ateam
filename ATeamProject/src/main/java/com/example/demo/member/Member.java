package com.example.demo.member;
import com.example.demo.career.Career;
import com.example.demo.cart.Cart;
import com.example.demo.course.Course;
import com.example.demo.courseReview.CourseReview;
import com.example.demo.file.Files;
import com.example.demo.quiz.Quiz;
import com.example.demo.registration.Registration;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memberKey;

	@Column(unique = true)
	private String memberId;
	
	private String password;
	
	private String telNo;
	
	private String birth;
	
	private String mname;
	
	private LocalDateTime createDate;
	
	private String nickname;
	
	
	@OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
	private Files profileImg; // 프로필이미지
	
	private String category;
	
	private String instructorYn;
	
	private LocalDateTime lastUpdateDate;
	
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL )
	private List<Cart> cartList; //장바구니
	
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<Course> courseList; // (강사) 생성한 강좌
	
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<Registration> regiList; // 수강중인 강의
	
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<CourseReview> CourseReviewList; // 강의 리뷰
	
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<Career> careerList; // (강사) 이력

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<Quiz> createQuizList;
}

