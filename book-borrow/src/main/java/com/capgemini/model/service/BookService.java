package com.capgemini.model.service;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.model.entity.Book;
import com.capgemini.model.repository.BookRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	// ADD BOOK
	public Map<String, Object> addBook(Book book) {
		Map<String, Object> response = new HashMap<>();

		book.setBorrowedCopies(0); // always reset to 0 on creation
		Book saved = bookRepository.save(book);

		response.put("message", "Book added successfully");
		response.put("bookId", saved.getId());
		return response;
	}

	// GET BY ID
	public Map<String, Object> getBookById(Integer id) {
		Map<String, Object> response = new HashMap<>();
		Optional<Book> optional = bookRepository.findById(id);

		if (optional.isEmpty()) {
			response.put("message", "Book not found");
			return response;
		}

		Book book = optional.get();
		response.put("id", book.getId());
		response.put("title", book.getTitle());
		response.put("author", book.getAuthor());
		response.put("availableCopies", book.getAvailableCopies());
		response.put("borrowedCopies", book.getBorrowedCopies());
		return response;
	}

	// GET ALL
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	// UPDATE BOOK
	public Map<String, Object> updateBook(Integer id, Book updatedData) {
		Map<String, Object> response = new HashMap<>();
		Optional<Book> optional = bookRepository.findById(id);

		if (optional.isEmpty()) {
			response.put("message", "Book not found");
			return response;
		}

		Book book = optional.get();
		book.setTitle(updatedData.getTitle());
		book.setAuthor(updatedData.getAuthor());
		book.setAvailableCopies(updatedData.getAvailableCopies());
		bookRepository.save(book);

		response.put("message", "Book updated successfully");
		return response;
	}

	// DELETE BOOK
	public Map<String, Object> deleteBook(Integer id) {
		Map<String, Object> response = new HashMap<>();
		Optional<Book> optional = bookRepository.findById(id);

		if (optional.isEmpty()) {
			response.put("message", "Book not found");
			return response;
		}

		bookRepository.deleteById(id);
		response.put("message", "Book deleted successfully");
		return response;
	}

	// BORROW BOOK
	public Map<String, Object> borrowBook(Integer id) {
		Map<String, Object> response = new HashMap<>();
		Optional<Book> optional = bookRepository.findById(id);

		if (optional.isEmpty()) {
			response.put("message", "Book not found");
			return response;
		}

		Book book = optional.get();

		if (book.getAvailableCopies() == 0) {
			response.put("message", "No copies available");
			return response;
		}
		book.setAvailableCopies(book.getAvailableCopies() - 1);
		book.setBorrowedCopies(book.getBorrowedCopies() + 1);
		bookRepository.save(book);

		response.put("message", "Book borrowed successfully");
		return response;
	}

	// RETURN BOOK
	public Map<String, Object> returnBook(Integer id) {
		Map<String, Object> response = new HashMap<>();
		Optional<Book> optional = bookRepository.findById(id);
		if (optional.isEmpty()) {
			response.put("message", "Book not found");
			return response;
		}

		Book book = optional.get();

		if (book.getBorrowedCopies() == 0) {
			response.put("message", "No borrowed books to return");
			return response;
		}

		book.setAvailableCopies(book.getAvailableCopies() + 1);
		book.setBorrowedCopies(book.getBorrowedCopies() - 1);
		bookRepository.save(book);

		response.put("message", "Book returned successfully");
		return response;
	}
}