package com.mitrais.javabootcamp.charles.service;

import java.util.List;

import com.mitrais.javabootcamp.charles.entity.Shelf;

public interface ShelfService {
	
	public List<Shelf> findAll();
	
	public Shelf findById(int theId);
	
	public void save(Shelf theShelf);
	
	public void deleteById(int theId);
	
	public Shelf addBook(int shelfId, int bookId);
	
	public Shelf removeBook(int shelfId, int bookId);
}
