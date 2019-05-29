package com.mitrais.javabootcamp.charles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.mitrais.javabootcamp.charles.entity.Book;
import com.mitrais.javabootcamp.charles.entity.Shelf;
import com.mitrais.javabootcamp.charles.entity.Status;
import com.mitrais.javabootcamp.charles.repository.BookRepository;
import com.mitrais.javabootcamp.charles.repository.ShelfRepository;
import com.mitrais.javabootcamp.charles.service.ShelfServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ShelfServiceTest {
	
	@InjectMocks
	private ShelfServiceImpl shelfService;
	@Mock
	private ShelfRepository shelfRepository;
	@Mock
	private BookRepository bookRepository;
	
	@Test
	public void findById_ReturnsOk() {
		
		//setup
		when(shelfRepository.findById(anyInt())).thenReturn(
				Optional.of(new Shelf(1, 10, 0, null))
		);
				
		
		//action
		Shelf actual = shelfService.findById(1);
		
		//assertion
		Shelf shelf = actual;
		assertEquals(1, shelf.getShelfId());
		verify(shelfRepository, times(1)).findById(anyInt());
		
	}
	
	@Test(expected = RuntimeException.class)
	public void findById_ReturnsRuntimeException(){
		
		//setup
		when(shelfRepository.findById(anyInt())).thenReturn(
				Optional.empty()
		);
		
		//action & assertion
		shelfService.findById(1);
		
	}
	
	@Test
	public void addBook_ReturnsOk() {
		
		//setup
		Book theBook = new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.NOT_SHELVED,"Paul Creswick");
		Shelf theShelf = new Shelf(1, 10, 0, null);
		when(shelfRepository.findById(anyInt())).thenReturn(Optional.of(theShelf));
		when(bookRepository.findById(anyInt())).thenReturn(Optional.of(theBook));
		when(shelfRepository.save(any(Shelf.class))).thenReturn(theShelf);
		
		//action
		Shelf actual = shelfService.addBook(1, 1);
		
		//assertion
		Set<Book> expBook = new HashSet<>();
		expBook.add(new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.SHELVED,"Paul Creswick"));
		Shelf expShelf = new Shelf(1, 10, 1, expBook);
		assertEquals(expShelf.getCurrentCapacity(), actual.getCurrentCapacity());
		assertEquals(expShelf.getBooks().toString(),actual.getBooks().toString());
		
	}
	
	@Test(expected = RuntimeException.class)
	public void addBook_ShelfIdNotFound_ReturnsRuntimeException() {
		
		//setup
		when(shelfRepository.findById(anyInt())).thenReturn(Optional.empty());
		
		//action & assertion
		shelfService.addBook(1, 1);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void addBook_BookIdNotFound_ReturnsRuntimeException() {
		
		//setup
		Shelf theShelf = new Shelf(1, 10, 0, null);
		when(shelfRepository.findById(anyInt())).thenReturn(Optional.of(theShelf));
		when(bookRepository.findById(anyInt())).thenReturn(Optional.empty());
		
		//action & assertion
		shelfService.addBook(1, 1);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void addBook_BookStatusShelved_ReturnsRuntimeException() {
		
		//setup
		Shelf theShelf = new Shelf(1, 10, 0, null);
		Book theBook = new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.SHELVED,"Paul Creswick");
		when(shelfRepository.findById(anyInt())).thenReturn(Optional.of(theShelf));
		when(bookRepository.findById(anyInt())).thenReturn(Optional.of(theBook));
		
		//action & assertion
		shelfService.addBook(1, 1);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void addBook_ShelfCurrentCapacityExceedShelfMaximumCapacity_ReturnsRuntimeException() {
		
		//setup
		Shelf theShelf = new Shelf(1, 10, 11, null);
		Book theBook = new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.SHELVED,"Paul Creswick");
		when(shelfRepository.findById(anyInt())).thenReturn(Optional.of(theShelf));
		when(bookRepository.findById(anyInt())).thenReturn(Optional.of(theBook));
		
		//action & assertion
		shelfService.addBook(1, 1);
		
	}
	
	@Test
	public void removeBook_ReturnsOk() {
		
		//setup
		Book theBook = new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.SHELVED,"Paul Creswick");
		Set<Book> bookList = new HashSet<>();		
		bookList.add(theBook);
		Shelf theShelf = new Shelf(1, 10, 1, bookList);
		when(shelfRepository.findById(anyInt())).thenReturn(Optional.of(theShelf));
		when(bookRepository.findById(anyInt())).thenReturn(Optional.of(theBook));
		when(shelfRepository.save(any(Shelf.class))).thenReturn(theShelf);
		
		//action
		Shelf actual = shelfService.removeBook(1, 1);
		
		//assertion
		Set<Book> expBook = new HashSet<>();		
		Shelf expShelf = new Shelf(1, 10, 0, expBook);
		verify(shelfRepository, times(1)).findById(anyInt());
		verify(bookRepository, times(1)).findById(anyInt());
		verify(shelfRepository, times(1)).save(any(Shelf.class));
		assertEquals(expShelf.getCurrentCapacity(), actual.getCurrentCapacity());
		assertTrue(actual.getBooks().containsAll(expShelf.getBooks()));
		
	}
	
	@Test(expected = RuntimeException.class)
	public void removeBook_ShelfIdNotFound_ReturnsRuntimeException() {
		
		//setup
		when(shelfRepository.findById(anyInt())).thenReturn(Optional.empty());
		
		//action & assertion
		shelfService.removeBook(1, 1);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void removeBook_BookIdNotFound_ReturnsRuntimeException() {
		
		//setup
		Book theBook = new Book(1, "978-979-2763-37-9","ROBIN HOOD 2",Status.SHELVED,"Paul Creswick");
		Set<Book> bookList = new HashSet<>();		
		bookList.add(theBook);
		Shelf theShelf = new Shelf(1, 10, 1, bookList);
		when(shelfRepository.findById(anyInt())).thenReturn(Optional.of(theShelf));
		when(bookRepository.findById(anyInt())).thenReturn(Optional.empty());
		
		//action & assertion
		shelfService.removeBook(1, 1);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void removeBook_BookStatusNotShelved_ReturnsRuntimeException() {
		
		//setup
		Book theBook = new Book(1, "978-979-2763-37-9","ROBIN HOOD 2",Status.SHELVED,"Paul Creswick");
		Set<Book> bookList = new HashSet<>();		
		bookList.add(theBook);
		Shelf theShelf = new Shelf(1, 10, 1, bookList);
		Book removedBook = new Book(2, "978-979-2763-37-9","ROBIN HOOD",Status.NOT_SHELVED,"Paul Creswick");
		when(shelfRepository.findById(anyInt())).thenReturn(Optional.of(theShelf));
		when(bookRepository.findById(anyInt())).thenReturn(Optional.of(removedBook));
		
		//action & assertion
		shelfService.removeBook(1, 2);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void deleteById_ReturnsRuntimeException(){
		
		//setup
		when(shelfRepository.findById(anyInt())).thenReturn(
				Optional.empty()
		);
		
		//action & assertion
		shelfService.deleteById(1);
		
	}

}
