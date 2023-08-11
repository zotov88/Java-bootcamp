package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.DataSourcePostgres;
import edu.school21.chat.repositories.messages.MessagesRepository;
import edu.school21.chat.repositories.messages.MessagesRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = new DataSourcePostgres();
        startInit(dataSource, "schema.sql", "data.sql");
        MessagesRepository repository = new MessagesRepositoryJdbcImpl(dataSource);
        Scanner sc = new Scanner(System.in);
        String str = "";

        try {
            System.out.println("Message ID:");
            while (!(str = sc.nextLine()).equals("stop")) {
                long id = Long.parseLong(str);
                Optional<Message> message = repository.findById(id);
                if (message != null && message.isPresent()) {
                    System.out.println(message.get());
                } else {
                    System.out.println("Message not found");
                }
                System.out.println("\nMessage ID:");
            }
        } catch (NumberFormatException e) {
            System.out.print("Wrong id " + str);
        }
    }

    private static void startInit(DataSource dataSource, String... files) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            for (String file : files) {
                InputStream input = Program.class.getClassLoader().getResourceAsStream(file);
                Scanner scanner = new Scanner(input).useDelimiter(";");
                while (scanner.hasNext()) {
                    statement.executeUpdate(scanner.next().trim());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}