package com.mitrais.javabootcamp.charles.rest;

import java.util.List;

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

import com.mitrais.javabootcamp.charles.entity.Shelf;
import com.mitrais.javabootcamp.charles.service.ShelfService;

@RestController
@RequestMapping("shelfs")
public class ShelfRestController {

	private ShelfService shelfService;
	
	@Autowired
	public ShelfRestController(ShelfService theShelfService) {
		shelfService = theShelfService;
	}
	
	@GetMapping
	public List<Shelf> findAll(){
		
		return shelfService.findAll();
		
	}
	
	@GetMapping(value = "{shelfId}")
	public Shelf getShelf(@PathVariable int shelfId) {
		
		return shelfService.findById(shelfId);
		
	}
	
	@PostMapping
	public Shelf saveShelf(@RequestBody Shelf theShelf) {
		
		theShelf.setShelfId(0);
		
		shelfService.save(theShelf);
		
		return theShelf;
		
	}
	
	@PutMapping
	public Shelf updateShelf(@RequestBody Shelf theShelf) {
		
		shelfService.save(theShelf);
		
		return theShelf;
		
	}
	
	@DeleteMapping(value = "{shelfId}")
	public String deleteShelf(@PathVariable int shelfId) {
		
		shelfService.deleteById(shelfId);
		
		return "Deleted Shelf Id - " + shelfId;
	}
	
	@PostMapping(params = {"shelfId", "bookId"})
	public Shelf addBook(@RequestParam int shelfId, @RequestParam int bookId) {
		
		return shelfService.addBook(shelfId, bookId);
		
	}
	
	@DeleteMapping(params = {"shelfId", "bookId"})
	public Shelf removeBook(@RequestParam int shelfId, @RequestParam int bookId) {
		
		return shelfService.removeBook(shelfId, bookId);
		
	}
	
}
