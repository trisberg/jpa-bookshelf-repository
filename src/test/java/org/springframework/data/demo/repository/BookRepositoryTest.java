package org.springframework.data.demo.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.demo.domain.Author;
import org.springframework.data.demo.domain.Book;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class BookRepositoryTest {
	
	@Inject
	AuthorRepository auth;
	
	@Inject
	BookRepository repo;
	
	@Transactional @Test
	@Rollback(false)
	public void testSave() {
		Author a = auth.findByName("John");
		if (a == null) {
			auth.save(a);
		}
		Book b = new Book();
		b.setAuthor(a);
		b.setIsbn("1234567890");
		b.setTitle("Testing JPA with Spring");
		Set<String> c = new HashSet<String>();
		c.add("Java");
		c.add("Spring");
		b.setCategories(c);
		b.setPrice(new BigDecimal("22.55"));
		b.setPublished(new Date());
		repo.save(b);
	}

}
