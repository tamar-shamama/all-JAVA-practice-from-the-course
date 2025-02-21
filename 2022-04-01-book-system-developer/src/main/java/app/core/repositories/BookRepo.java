package app.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Book;

public interface BookRepo extends JpaRepository<Book, Integer> {

}
