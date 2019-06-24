package com.mitrais.javabootcamp.charles.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mitrais.javabootcamp.charles.entity.Book;
import com.mitrais.javabootcamp.charles.entity.Shelf;
import com.mitrais.javabootcamp.charles.entity.Status;
import com.mitrais.javabootcamp.charles.repository.BookRepository;
import com.mitrais.javabootcamp.charles.repository.ShelfRepository;

@Service
public class ShelfServiceImpl implements ShelfService {

	@Autowired
	ShelfRepository shelfRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Override
	public List<Shelf> findAll() {
		
		List<Shelf> shelfs = shelfRepository.findAll();
		return shelfs;
		
	}

	@Override
	public Shelf findById(int theId) {
		
		Optional<Shelf> result = shelfRepository.findById(theId);
		
		Shelf theShelf = null;
		
		if(result.isPresent()) {
			theShelf = result.get();
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shelf Id not found - " + theId);
		}
		
		return theShelf;
	}

	@Override
	@Transactional
	public void save(Shelf theShelf) {
		
		shelfRepository.save(theShelf);

	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		
		Optional<Shelf> result = shelfRepository.findById(theId);
		
		if(!result.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shelf Id not found - " + theId);
		}
		
		shelfRepository.deleteById(theId);

	}

	@Override
	@Transactional
	public Shelf addBook(int shelfId, int bookId) {
		
		Optional<Shelf> result = shelfRepository.findById(shelfId);
		
		Shelf theShelf = null;
		
		if(result.isPresent()) {
			theShelf = result.get();
			Book theBook = null;
			Optional<Book> book = bookRepository.findById(bookId);
			if(book.isPresent()) {
				
				theBook = book.get();
				
				if(theBook.getStatus().equals(Status.SHELVED)) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This book is already in a shelf");
				}
				
				int shelfCurrentCapacity;
				int shelfMaximumCapacity;
				
				shelfCurrentCapacity = theShelf.getCurrentCapacity();
				shelfMaximumCapacity = theShelf.getMaxCapacity();
				shelfCurrentCapacity++;
				
				if (shelfCurrentCapacity > shelfMaximumCapacity) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shelf Current Capacity cannot exceed the Maximum Capacity");
				}
				
				theShelf.setCurrentCapacity(shelfCurrentCapacity);
				theBook.setStatus(Status.SHELVED);
				theShelf.addBook(theBook);
				
				shelfRepository.save(theShelf);
				
			}else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Id not found - " + bookId);
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shelf Id not found - " + shelfId);
		}
		
		return theShelf;
		
	}

	@Override
	@Transactional
	public Shelf removeBook(int shelfId, int bookId) {

		Optional<Shelf> result = shelfRepository.findById(shelfId);
		
		Shelf theShelf = null;
		
		if(result.isPresent()) {
			theShelf = result.get();
			Book theBook = null;
			Optional<Book> book = bookRepository.findById(bookId);
			if(book.isPresent()) {
				
				theBook = book.get();
				
				if(theBook.getStatus().equals(Status.NOT_SHELVED)) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This book is not shelved");
				}
				
				int shelfCurrentCapacity;
				
				shelfCurrentCapacity = theShelf.getCurrentCapacity();
				shelfCurrentCapacity--;
				
				theShelf.setCurrentCapacity(shelfCurrentCapacity);
				theBook.setStatus(Status.NOT_SHELVED);
				theShelf.removeBook(theBook);
				
				shelfRepository.save(theShelf);
				
			}else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Id not found - " + bookId);
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shelf Id not found - " + shelfId);
		}
		
		return theShelf;
		
	}

}
