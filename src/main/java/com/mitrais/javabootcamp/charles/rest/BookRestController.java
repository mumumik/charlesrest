package com.mitrais.javabootcamp.charles.rest;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.javabootcamp.charles.entity.Book;
import com.mitrais.javabootcamp.charles.entity.Status;
import com.mitrais.javabootcamp.charles.service.BookService;

@RestController
@RequestMapping("books")
public class BookRestController {

	private BookService bookService;
	
	@Autowired
	public BookRestController(BookService theBookService) {
		bookService = theBookService;
	}
	
	@GetMapping
	public Set<Book> findAll(){
		
		return bookService.findAll();
		
	}
	
	@GetMapping(value = "{bookId}")
	public Book getBook(@PathVariable int bookId) {
		
		return bookService.findById(bookId);
		
	}
	
	@GetMapping(params = "bookTitle")
	public Set<Book> getBookByTitle(@RequestParam String bookTitle) {
		
		return bookService.findByTitle(bookTitle);
		
	}
	
	@GetMapping(params = "bookStatus")
	public Set<Book> getBookByStatus(@RequestParam Status bookStatus) {
		
		return bookService.findByStatus(bookStatus);
		
	}
	
	@PostMapping
	public Book saveBook(@RequestBody Book theBook) {
		
		theBook.setId(0);
		theBook.setStatus(Status.NOT_SHELVED);
		
		bookService.save(theBook);
		
		return theBook;
		
	}
	
	@PutMapping
	public Book updateBook(@RequestBody Book theBook) {
		
		bookService.save(theBook);
		
		return theBook;
		
	}
	
	@DeleteMapping(value = "{bookId}")
	public String deleteBook(@PathVariable int bookId) {
		
		bookService.deleteById(bookId);
		
		return "Deleted Book Id - " + bookId;
		
	}
}
