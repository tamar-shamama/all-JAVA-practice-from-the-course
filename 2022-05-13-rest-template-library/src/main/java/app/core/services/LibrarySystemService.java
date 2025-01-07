package app.core.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.core.entities.Book;
import app.core.entities.Library;
import app.core.repositories.BookRepo;
import app.core.repositories.LibraryRepo;

@Service
@Transactional
public class LibrarySystemService {
	
	@Autowired
	private BookRepo bookRepo;
	@Autowired
	private LibraryRepo libraryRepo;
	
	
	public int addLibrary (Library library) {
		return libraryRepo.save(library).getId();
	}
	
	public Library getLibrary (int id) {
		return this.libraryRepo.findById(id).orElseThrow();
	}
	
	public void updateLibrary(Library library) {
		if (libraryRepo.existsById(library.getId())) {
			libraryRepo.save(library);
		} else {
			throw new RuntimeException("updateLibrary failed - not found");
		}
	}
	
	public void deletLibrary(int id) {
		if (libraryRepo.existsById(id)) {
			this.libraryRepo.deleteById(id);
		}
	}
	
	public int addBookToLibrary(Book book, int libraryId) {
		
		book.setLibrary(getLibrary(libraryId));
		book = this.bookRepo.save(book);
		return book.getId();
		
//		Library library = getLibrary(libraryId);
//		library.addBook(book);
//		libraryRepo.save(library);
//		return book.getId();
	}
	
	
	public Book getBook (int id) {
		return this.bookRepo.findById(id).orElseThrow();
	}
	
	public void updateBook(Book book) {
		if (bookRepo.existsById(book.getId())) {
			bookRepo.save(book);
		} else {
			throw new RuntimeException("updateBook failed - not found");
		}
	}
	
	public void deletBook(int id) {
		if (bookRepo.existsById(id)) {
			this.bookRepo.deleteById(id);
		}else {
			throw new RuntimeException("deleteBook failed - not found");
		}
	}
	
	public List<Book> getAllBooks() {
		return bookRepo.findAll();
	}
	
	public List<Book> getAllBooksFromLibrary(int libraryId) {
		return bookRepo.findAllByLibraryId(libraryId);
	}
	
	
	

}
