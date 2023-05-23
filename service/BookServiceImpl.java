/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Book;
/**
 *
 * @author ningsih
 */
public class BookServiceImpl implements BookService {

    private Connection connection = Database.startConnection();
    private PreparedStatement statement;

    @Override
    public void addBook(Book book) {
        try {
            String query = "insert into book values (0, ?, ?)";
            statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthorName());
            statement.executeUpdate();

            System.out.println("Book has been added!\n");
            statement.close();
        } catch (SQLException exc) {
            System.out.println("Failed to add book " + exc.getMessage());
        }
    }

    @Override
    public List<Book> findBookList() {
        List<Book> books = new ArrayList<>();

        try {
            String query = "select * from book";
            statement = (PreparedStatement) connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Book book = new Book();
                book.setId(result.getInt("id"));
                book.setTitle(result.getString("title"));
                book.setAuthorName(result.getString("author_name"));

                books.add(book);
            }
            statement.close();
            return books;
        } catch (SQLException exc) {
            System.out.println("Failed to get book list: " + exc.getMessage());
        }
        return books;
    }

    @Override
    public Book findBookById(int bookId) {
        Book book = new Book();

        try {
            String query = "SELECT * FROM book WHERE id = ?";
            statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setInt(1, bookId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("title");
                String authorName = result.getString("author_name");

                book.setId(id);
                book.setTitle(title);
                book.setAuthorName(authorName);
            }
            statement.close();
            return book;
        } catch (SQLException exc) {
            System.out.println("Failed to get Book : " + exc.getMessage());
        }
        return book;
    }

    @Override
    public void updateBook(Book book) {
        try {
            String query = "update book set title = ?, author_name = ? where id = ?";
            statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthorName());
            statement.setInt(3, book.getId());
            statement.executeUpdate();

            System.out.println("Successfully updated the book with id = " + book.getId());
            System.out.println("\n");
            statement.close();
        } catch (SQLException exc) {
            System.out.println("Failed to update book data: " + exc.getMessage());
        }
    }

    @Override
    public void removeBook(int id) {
        try{
            String query = "delete from book where id = ?";
            statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            
            System.out.println("Successfully delete book!\n");
            statement.close();
        } catch (SQLException exc) {
            System.out.println("Failed to delete book data: " + exc.getMessage());
        }
    }
}
