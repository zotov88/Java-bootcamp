package edu.school21.chat.app;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.DataSourcePostgres;
import edu.school21.chat.repositories.messages.MessagesRepository;
import edu.school21.chat.repositories.messages.MessagesRepositoryJdbcImpl;
import edu.school21.chat.repositories.users.UsersRepository;
import edu.school21.chat.repositories.users.UsersRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = new DataSourcePostgres();
        startInit(dataSource, "schema.sql", "data.sql");
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);
        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(dataSource);
        Scanner sc = new Scanner(System.in);
        String str = "";

        try {
            System.out.println("Message ID:");
            while (!(str = sc.nextLine()).equals("save")) {
                long id = Long.parseLong(str);
                Optional<Message> message = messagesRepository.findById(id);
                if (message != null && message.isPresent()) {
                    System.out.println(message.get());
                } else {
                    System.out.println("Message not found");
                }
                System.out.println("\nMessage ID:");
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong id " + str);
            System.exit(-1);
        }

        saveMessage(messagesRepository);
        while (!(str = sc.nextLine()).equals("upd")) {
        }
        updMessage(messagesRepository);
        while (!(str = sc.nextLine()).equals("list")) {
        }
        printUsers(usersRepository.findAll(1, 3));
        sc.close();
    }

    private static void updMessage(MessagesRepository repository) {
        if (repository.findById(1L).isPresent()) {
            Message message = repository.findById(1L).get();
            message.setText("updated");
            message.setDate(null);
            message.setAuthor(new User(5L, "Elena", "password", new ArrayList<>(), new ArrayList<>()));
            message.setRoom(new Chatroom(4L, "room_4"));
            repository.update(message);
        }
    }

    private static void saveMessage(MessagesRepository repository) {
        User author = new User(3L, "Anton", "password", new ArrayList<>(), new ArrayList<>());
        Chatroom chatroom = new Chatroom(2L, "room_2",
                new User(2L, "Anna", "password", new ArrayList<>(), new ArrayList<>()),
                new ArrayList<>(), new ArrayList<>());
        repository.save(new Message(null, author, chatroom, "from main", LocalDateTime.now()));
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

    private static void printUsers(List<User> list) {
        for (User user : list) {
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println(user.getId() - 1 + " " + user.getLogin());
            System.out.println("Created rooms:");
            System.out.println(user.getCreatedRooms());
            System.out.println("Socializes rooms:");
            System.out.println(user.getSocializesRooms());
            System.out.println("--------------------------------------------------------------------------------------");
        }
    }
}