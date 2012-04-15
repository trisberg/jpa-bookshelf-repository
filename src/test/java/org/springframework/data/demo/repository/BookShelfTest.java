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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class BookShelfTest {
	
	@Inject
	BookShelf bookShelf;
	
	@Transactional @Test
	public void testSave() {
		Author a = new Author();
		a.setName("John");
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
		Set<Book> books = new HashSet<Book>();
		books.add(b);
		a.setBooks(books);
		bookShelf.add(b);
	}

}
