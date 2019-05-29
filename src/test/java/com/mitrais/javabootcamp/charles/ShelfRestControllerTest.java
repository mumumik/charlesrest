package com.mitrais.javabootcamp.charles;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mitrais.javabootcamp.charles.entity.Book;
import com.mitrais.javabootcamp.charles.entity.Shelf;
import com.mitrais.javabootcamp.charles.entity.Status;
import com.mitrais.javabootcamp.charles.repository.BookRepository;
import com.mitrais.javabootcamp.charles.repository.ShelfRepository;
import com.mitrais.javabootcamp.charles.rest.ShelfRestController;
import com.mitrais.javabootcamp.charles.service.ShelfServiceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@WebMvcTest(ShelfRestController.class)
public class ShelfRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ShelfServiceImpl shelfService;
	@MockBean
	private BookRepository bookRepository;
	@MockBean
	private ShelfRepository shelfRepository;
	
	@Test
	public void get_ShelfById_ReturnsOk() throws Exception{
		
		//setup
		Set<Book> bookList = new HashSet<>();
		Shelf theShelf = new Shelf(1, 10, 0, bookList);
		when(shelfService.findById(anyInt())).thenReturn(theShelf);
		
		//action & assertion
		mockMvc.perform(MockMvcRequestBuilders.get("/shelfs/1"))				
				.andExpect(content().json("{'shelfId':1,'maxCapacity':10,'currentCapacity':0,'books':[]}"));
		
	}
	
	@Test
	public void addBook_ReturnsOk() throws Exception{
		
		//setup
		Book theBook = new Book(1, "978-979-2763-37-9","ROBIN HOOD",Status.SHELVED,"Paul Creswick");
		Set<Book> bookList = new HashSet<>();
		bookList.add(theBook);
		Shelf theShelf = new Shelf(1, 10, 1, bookList);
		when(shelfService.addBook(anyInt(), anyInt())).thenReturn(theShelf);
		
		//action & assertion
		mockMvc.perform(MockMvcRequestBuilders.post("/shelfs/?shelfId=1&bookId=1"))
				.andExpect(content().json("{'shelfId':1,'maxCapacity':10,'currentCapacity':1,'books':[{'id':1,'isbn':'978-979-2763-37-9','title':'ROBIN HOOD','author':'Paul Creswick','status':'SHELVED'}]}"));
		
	}
	
	@Test
	public void removeBook_ReturnsOk() throws Exception{
		
		//setup	
		Set<Book> bookList = new HashSet<>();
		Shelf theShelf = new Shelf(1, 10, 0, bookList);
		when(shelfService.removeBook(anyInt(), anyInt())).thenReturn(theShelf);
		
		//action & assertion
		mockMvc.perform(MockMvcRequestBuilders.delete("/shelfs/?shelfId=1&bookId=1"))
		.andExpect(content().json("{'shelfId':1,'maxCapacity':10,'currentCapacity':0,'books':[]}"));
		
	}
	

}
