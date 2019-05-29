package com.mitrais.javabootcamp.charles.service;

import java.util.Set;

import com.mitrais.javabootcamp.charles.entity.Book;
import com.mitrais.javabootcamp.charles.entity.Status;

public interface BookService {

	public Set<Book> findAll();
	
	public Book findById(int theId);
	
	public Set<Book> findByTitle(String theTitle);
	
	public Set<Book> findByStatus(Status theStatus);
	
	public void save(Book theBook);
	
	public void deleteById(int theId);
	
}
