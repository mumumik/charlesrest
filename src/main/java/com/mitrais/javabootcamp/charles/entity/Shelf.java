package com.mitrais.javabootcamp.charles.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="shelf")
public class Shelf {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="shelf_id")
	private int shelfId;
	
	@Column(name="max_capacity")
	private int maxCapacity;
	
	@Column(name="current_capacity")
	private int currentCapacity;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="shelf_id")
	private Set<Book> books;
	
	public Shelf() {
		
	}

	public Shelf(int maxCapacity, int currentCapacity, Set<Book> books) {
		this.maxCapacity = maxCapacity;
		this.currentCapacity = currentCapacity;
		this.books = books;
	}

	public Shelf(int shelfId, int maxCapacity, int currentCapacity, Set<Book> books) {
		this.shelfId = shelfId;
		this.maxCapacity = maxCapacity;
		this.currentCapacity = currentCapacity;
		this.books = books;
	}

	public int getShelfId() {
		return shelfId;
	}

	public void setShelfId(int shelfId) {
		this.shelfId = shelfId;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public int getCurrentCapacity() {
		return currentCapacity;
	}

	public void setCurrentCapacity(int currentCapacity) {
		this.currentCapacity = currentCapacity;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	public void addBook(Book theBook) {
		
		if(books == null) {
			books = new HashSet<>();
		}
		
		books.add(theBook);
		
	}
	
	public void removeBook(Book theBook) {
		
		books.remove(theBook);
		
	}
	
	@Override
	public String toString() {
		return "Shelf [shelfId=" + shelfId + ", maxCapacity=" + maxCapacity + ", currentCapacity=" + currentCapacity
				+ ", books=" + books + "]";
	}
	
	
	
}
