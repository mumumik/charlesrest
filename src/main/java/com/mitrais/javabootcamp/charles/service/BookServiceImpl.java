package com.mitrais.javabootcamp.charles.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mitrais.javabootcamp.charles.entity.Book;
import com.mitrais.javabootcamp.charles.entity.Status;
import com.mitrais.javabootcamp.charles.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;
	
	@Autowired
	public BookServiceImpl(BookRepository theBookRepository) {
		bookRepository = theBookRepository;
	}
	
	@Override
	public Set<Book> findAll() {
		
		Set<Book> books = bookRepository.findAllByOrderByStatusAsc();
		
		return books;
	}

	@Override
	public Book findById(int theId) {
		
		Optional<Book> result = bookRepository.findById(theId);
		
		Book theBook = null;
		
		if(result.isPresent()){
			theBook = result.get();
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Id not found - " + theId);
		}
		
		return theBook;
	}

	@Override
	@Transactional
	public void save(Book theBook) {
		
		bookRepository.save(theBook);

	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		
		Optional<Book> result = bookRepository.findById(theId);
		
		if(!result.isPresent()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Id not found - " + theId);
		}
		
		bookRepository.deleteById(theId);

	}

	@Override
	public Set<Book> findByTitle(String theTitle) {
		
		Set<Book> books = bookRepository.findByTitleIgnoreCaseContaining(theTitle);		
		
		if(books.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no book with title - " + theTitle);
		} 
		
		return books;
		
	}

	@Override
	public Set<Book> findByStatus(Status theStatus) {

		Set<Book> books = bookRepository.findByStatus(theStatus);		
		
		if(books.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no book with status - " + theStatus);
		} 
		
		return books;
		
	}

}
