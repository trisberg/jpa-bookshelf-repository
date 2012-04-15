package org.springframework.data.demo.repository;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.demo.domain.Author;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class AuthorRepositoryTest {
	
	@Inject
	AuthorRepository repo;
	
	@Transactional @Test
	public void testSave() {
		Author a = new Author();
		a.setName("John");
		repo.save(a);
	}

}
