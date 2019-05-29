package com.mitrais.javabootcamp.charles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.mitrais.javabootcamp.charles.entity.Book;
import com.mitrais.javabootcamp.charles.entity.Status;
import com.mitrais.javabootcamp.charles.repository.BookRepository;
import com.mitrais.javabootcamp.charles.service.BookServiceImpl;


@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

	@InjectMocks
	private BookServiceImpl bookService;
	@Mock
	private BookRepository bookRepository;
	
	@Test
	public void findById_ReturnsOk() {
		
		//setup
		when(bookRepository.findById(anyInt())).thenReturn(
				Optional.of(new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.NOT_SHELVED,"Paul Creswick"))
		);
				
		
		//action
		Book actual = bookService.findById(1);
		
		//assertion
		Book book = actual;
		assertEquals("ROBIN HOOD", book.getTitle());
		verify(bookRepository, times(1)).findById(anyInt());
		
	}
	
	@Test(expected = RuntimeException.class)
	public void findById_ReturnsRuntimeException(){
		
		//setup
		when(bookRepository.findById(anyInt())).thenReturn(
				Optional.empty()
		);
		
		//action & assertion
		bookService.findById(1);
		
	}
	
	@Test
	public void findAll_ReturnsOk() {
		
		//setup
		Set<Book> bookList = new HashSet<>();
		bookList.add(new Book(3, "978-979-2763-37-9","ROBIN HOOD 3",Status.NOT_SHELVED,"Paul Creswick"));
		bookList.add(new Book(1, "978-979-2763-37-5","ROBIN HOOD",Status.SHELVED,"Paul Creswick"));
		bookList.add(new Book(2, "978-979-2763-37-4","ROBIN HOOD 2",Status.SHELVED,"Paul Creswick"));
		when(bookRepository.findAllByOrderByStatusAsc()).thenReturn(bookList);
		
		//action
		Set<Book> actual = bookService.findAll();
		
		//assertion
		Set<Book> expected = new HashSet<>();
		expected.add(new Book(3, "978-979-2763-37-9","ROBIN HOOD 3",Status.NOT_SHELVED,"Paul Creswick"));
		expected.add(new Book(1, "978-979-2763-37-5","ROBIN HOOD",Status.SHELVED,"Paul Creswick"));
		expected.add(new Book(2, "978-979-2763-37-4","ROBIN HOOD 2",Status.SHELVED,"Paul Creswick"));
		assertTrue(actual.containsAll(expected));
		
	}
	
	@Test
	public void findByTitle_ReturnsBooks() {
		
		//setup
		Set<Book> bookList = new HashSet<>();
		bookList.add(new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.NOT_SHELVED,"Paul Creswick"));
		bookList.add(new Book(2, "978-979-2763-37-5","ROBIN HOOD 2",Status.NOT_SHELVED,"Paul Creswick"));
		bookList.add(new Book(3, "978-979-2763-37-4","ROBIN HOOD 3",Status.NOT_SHELVED,"Paul Creswick"));
		when(bookRepository.findByTitleIgnoreCaseContaining(anyString())).thenReturn(bookList);
		
		//action
		Set<Book> actual = bookService.findByTitle("robin");		
		
		//assertion
		Set<Book> expected = new HashSet<>();
		expected.add(new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.NOT_SHELVED,"Paul Creswick"));
		expected.add(new Book(2, "978-979-2763-37-5","ROBIN HOOD 2",Status.NOT_SHELVED,"Paul Creswick"));
		expected.add(new Book(3, "978-979-2763-37-4","ROBIN HOOD 3",Status.NOT_SHELVED,"Paul Creswick"));
		assertTrue(actual.containsAll(expected));
		
	}
	
	@Test(expected = RuntimeException.class)
	public void findByTitle_ReturnsRuntimeException() {
		
		//setup
		Set<Book> bookList = new HashSet<>();
		when(bookRepository.findByTitleIgnoreCaseContaining(anyString())).thenReturn(bookList);
			
		//action & assertion
		bookService.findByTitle("robin");
		
	}
	
	@Test
	public void findByStatus_ReturnsOk() {
		
		//setup
		Set<Book> bookList = new HashSet<>();
		bookList.add(new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.NOT_SHELVED,"Paul Creswick"));		
		when(bookRepository.findByStatus(any(Status.class))).thenReturn(bookList);
		
		//action
		Set<Book> actual = bookService.findByStatus(Status.NOT_SHELVED);
		
		//assertion
		Set<Book> expected = new HashSet<>();
		expected.add(new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.NOT_SHELVED,"Paul Creswick"));
		assertTrue(actual.containsAll(expected));
		
	}
	
	@Test(expected = RuntimeException.class)
	public void findByStatus_ReturnsRuntimeException() {
		
		//setup
		Set<Book> bookList = new HashSet<>();
		when(bookRepository.findByStatus(any(Status.class))).thenReturn(bookList);
			
		//action & assertion
		bookService.findByStatus(Status.NOT_SHELVED);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void deleteById_ReturnsRuntimeException(){
		
		//setup
		when(bookRepository.findById(anyInt())).thenReturn(
				Optional.empty()
		);
		
		//action & assertion
		bookService.deleteById(1);
		
	}
	

	
}
