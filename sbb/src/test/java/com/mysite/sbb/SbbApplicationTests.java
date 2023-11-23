package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Test
	void test1() {
		Question q1 = new Question();
		q1.setSubject("질문1");
		q1.setContent("내용입니다1.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("질문2");
		q2.setContent("내용입니다2.");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);

		Question q3 = new Question();
		q3.setSubject("질문3");
		q3.setContent("내용입니다3.");
		q3.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q3);
	}

	@Test
	void test2() {
		List<Question> questionList = this.questionRepository.findAll();
		assertEquals(3, questionList.size());
		Question question = questionList.get(0);
		assertEquals("질문1", question.getSubject());
	}

	@Test
	void test3() {
		Optional<Question> oq = this.questionRepository.findById(1);
		if (oq.isPresent()) {
			Question q = oq.get();
			assertEquals("질문1", q.getSubject());
		}
	}

	@Test
	void test4() {
		Question question = this.questionRepository.findBySubject("질문1");
		assertEquals(1, question.getId());
	}

	@Test
	void test5() {
		Question question = this.questionRepository.findBySubjectAndContent("질문1", "내용입니다1.");
		assertEquals(1, question.getId());
	}

	@Test
	void test6() {
		List<Question> questionList= this.questionRepository.findBySubjectLike("질문%");
		assertEquals(1, questionList.get(0).getId());
	}

	@Test
	void test7() {
		Optional<Question> oq = this.questionRepository.findById(2);
		if (oq.isPresent()) {
			Question question = oq.get();
			question.setSubject("질문2 수정됌");
			this.questionRepository.save(question);
		}
	}

	@Test
	void test8() {
		Optional<Question> oq = this.questionRepository.findById(3);
		if (oq.isPresent()) {
			Question question = oq.get();
			this.questionRepository.delete(question);
		}
	}

	@Test
	void test9() {
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question question = oq.get();

		Answer a1 = new Answer();
		a1.setContent("답변1-1");
		a1.setCreateDate(LocalDateTime.now());
		a1.setQuestion(question);
		this.answerRepository.save(a1);

		Answer a2 = new Answer();
		a2.setContent("답변1-2");
		a2.setCreateDate(LocalDateTime.now());
		a2.setQuestion(question);
		this.answerRepository.save(a2);
	}

	@Transactional
	@Test
	void test10() {
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question question = oq.get();

		List<Answer> answerList = question.getAnswerList();

		assertEquals(2, answerList.size());
	}

}
