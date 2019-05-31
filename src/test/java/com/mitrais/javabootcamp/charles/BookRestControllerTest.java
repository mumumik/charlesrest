package com.mitrais.javabootcamp.charles;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mitrais.javabootcamp.charles.entity.Book;
import com.mitrais.javabootcamp.charles.entity.Status;
import com.mitrais.javabootcamp.charles.repository.BookRepository;
import com.mitrais.javabootcamp.charles.rest.BookRestController;
import com.mitrais.javabootcamp.charles.service.BookServiceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(BookRestController.class)
public class BookRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private BookServiceImpl bookService;
	@MockBean
	private BookRepository bookRepository;
	
	@Test
	public void get_AllBooksOrderByStatus_ReturnsOk() throws Exception{
		
		//setup
		Set<Book> bookList = new HashSet<>();
		bookList.add(new Book(3, "978-979-2763-37-9","ROBIN HOOD 3",Status.NOT_SHELVED,"Paul Creswick"));
		bookList.add(new Book(1, "978-979-2763-37-5","ROBIN HOOD",Status.SHELVED,"Paul Creswick"));
		bookList.add(new Book(2, "978-979-2763-37-4","ROBIN HOOD 2",Status.SHELVED,"Paul Creswick"));
		when(bookService.findAll()).thenReturn(bookList);
		
		//action & assertion
		mockMvc.perform(
				MockMvcRequestBuilders.get("/books/"))
				.andExpect(content().json("[{id:3,isbn:'978-979-2763-37-9',title:'ROBIN HOOD 3',author:'Paul Creswick',status:'NOT_SHELVED'},"
						+ "{id:1,isbn:'978-979-2763-37-5',title:'ROBIN HOOD',author:'Paul Creswick',status:'SHELVED'},"
						+ "{id:2,isbn:'978-979-2763-37-4',title:'ROBIN HOOD 2',author:'Paul Creswick',status:'SHELVED'}]"));
		
	}
	
	@Test
	public void get_booksByTitle_ReturnsOk() throws Exception{
		
		//setup
		Set<Book> bookList = new HashSet<>();
		bookList.add(new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.NOT_SHELVED,"Paul Creswick"));
		when(bookService.findByTitle(anyString())).thenReturn(bookList);
		
		//action & assertion
		mockMvc.perform(
				MockMvcRequestBuilders.get("/books/?bookTitle=robin"))
				.andExpect(content().json("[{id:1,isbn:'978-979-2763-37-9',title:'ROBIN HOOD',author:'Paul Creswick',status:'NOT_SHELVED'}]"));
		
	}
	
	@Test
	public void get_booksByStatus_ReturnsOk() throws Exception{
		
		//setup
		Set<Book> bookListNotShelved = new HashSet<>();
		bookListNotShelved.add(new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.NOT_SHELVED,"Paul Creswick"));
		Set<Book> bookListShelved = new HashSet<>();
		bookListShelved.add(new Book(2, "978-979-2763-37-4","ROBIN HOOD 2",Status.SHELVED,"Paul Creswick"));
		when(bookService.findByStatus(Status.NOT_SHELVED)).thenReturn(bookListNotShelved);
		when(bookService.findByStatus(Status.SHELVED)).thenReturn(bookListShelved);
				
		//action & assertion
		mockMvc.perform(
				MockMvcRequestBuilders.get("/books/?bookStatus=NOT_SHELVED"))
				.andExpect(content().json("[{id:1,isbn:'978-979-2763-37-9',title:'ROBIN HOOD',author:'Paul Creswick',status:'NOT_SHELVED'}]"));
		
		mockMvc.perform(
				MockMvcRequestBuilders.get("/books/?bookStatus=SHELVED"))
				.andExpect(content().json("[{id:2,isbn:'978-979-2763-37-4',title:'ROBIN HOOD 2',author:'Paul Creswick',status:'SHELVED'}]"));
		
	}

}
