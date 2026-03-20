package com.capgemini.controller;



import com.capgemini.model.entity.Book;
import com.capgemini.model.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping
	public Map<String, Object> addBook(@RequestBody Book book) {
		return bookService.addBook(book);
	}

	@GetMapping("/{id}")
	public Map<String, Object> getBook(@PathVariable Integer id) {
		return bookService.getBookById(id);
	}

	@GetMapping
	public List<Book> getAllBooks() {
		return bookService.getAllBooks();
	}

	@PutMapping("/{id}")
	public Map<String, Object> updateBook(@PathVariable Integer id, @RequestBody Book book) {
		return bookService.updateBook(id, book);
	}

	@DeleteMapping("/{id}")
	public Map<String, Object> deleteBook(@PathVariable Integer id) {
		return bookService.deleteBook(id);
	}

	// POST /books/borrow/{id}
	@PostMapping("/borrow/{id}")
	public Map<String, Object> borrowBook(@PathVariable Integer id) {
		return bookService.borrowBook(id);
	}

	// POST /books/return/{id}
	@PostMapping("/return/{id}")
	public Map<String, Object> returnBook(@PathVariable Integer id) {
		return bookService.returnBook(id);
	}
}