package com.book.graphql.controller;

import com.book.graphql.model.Book;
import com.book.graphql.repository.BookRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Controller
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //Query: get all books
    @QueryMapping
    public List<Book> books() {
        return bookRepository.findAll();
    }

    //Query: get book by ID
    @QueryMapping
    public Book book(@Argument Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    //Mutation: add book
    @MutationMapping
    public Book addBook(@Argument String title,
                     @Argument String author,
                     @Argument Integer pageCount) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPageCount(pageCount);

        return bookRepository.save(book);
    }

    //Mutation: update Book
    @MutationMapping
    public Book updateBook(@Argument Long id,
                           @Argument String title,
                           @Argument String author,
                           @Argument Integer pageCount
                        ) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("book not fond"));
        if(title != null) book.setTitle(title);
        if(author != null) book.setAuthor(author);
        if(pageCount != null) book.setPageCount(pageCount);

        return bookRepository.save(book);
    }

    //Mutation: Delete a book
    @MutationMapping
    public Boolean deleteBook(@Argument Long id) {
        if(!bookRepository.existsById(id)) {
            throw new RuntimeException("No Book found");
        }

        bookRepository.deleteById(id);
        return true;
    }

}
