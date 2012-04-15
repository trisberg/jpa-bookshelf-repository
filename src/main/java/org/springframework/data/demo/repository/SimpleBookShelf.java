package org.springframework.data.demo.repository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.demo.domain.Author;
import org.springframework.data.demo.domain.Book;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
public class SimpleBookShelf implements BookShelf {

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	BookRepository bookRepository;

	@Override
	@Transactional
	public void add(Book book) {
		save(book);
	}
	
	@Override
	@Transactional
	public void save(Book book) {
		lookUpAuthor(book);
		bookRepository.save(book);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Book find(String isbn) {
		return bookRepository.findOne(isbn);
	}
	
	@Override
	@Transactional
	public void remove(String isbn) {
		bookRepository.delete(isbn);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Book> findAll() {
		 return bookRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Book> findByCategoriesOrYear(Set<String> categories, String year) {
		String[] categoriesToMatch;
		if (categories == null) {
			categoriesToMatch = new String[] {};
		}
		else {
			categoriesToMatch = categories.toArray(new String[categories.size()]);
		}
		Date startDate = null;
		if (year != null && year.length() == 4) {
			DateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
			try {
				startDate = formatter.parse(year + "-01-01");
			} catch (ParseException e) {}
		}
		
		if (startDate != null) {
			if (categoriesToMatch.length > 0) {
				return bookRepository.findByPublishedGreaterThanAndCategoriesIn(startDate, categoriesToMatch);
			}
			else {
				return bookRepository.findByPublishedGreaterThan(startDate);
			}
		}
		else {
			if (categoriesToMatch.length > 0) {
				return bookRepository.findByCategoriesIn(categoriesToMatch);
			}
			else {
				return findAll();
			}
		}
	}

	private void lookUpAuthor(Book book) {
		System.out.println("TXTXTX -> " + TransactionSynchronizationManager.getCurrentTransactionName() + ": "+ TransactionSynchronizationManager.isActualTransactionActive());
		Author existing = authorRepository.findByName(book.getAuthor().getName());
		if (existing != null) {
			System.out.println("EXISTS " + existing.getId());
			book.setAuthor(existing);
		}
		else {
			authorRepository.save(book.getAuthor());
			System.out.println("NEW " + book.getAuthor().getId());
		}
	}

}
