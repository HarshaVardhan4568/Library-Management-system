import java.util.*;

class Book {
    String title;
    boolean isIssued;
    Date issueDate;

    Book(String title) {
        this.title = title;
        this.isIssued = false;
        this.issueDate = null;
    }
}

public class Library {
    static HashMap<Integer, Book> books = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Pre-loaded books
        books.put(1, new Book("The Alchemist"));
        books.put(2, new Book("Clean Code"));
        books.put(3, new Book("The Pragmatic Programmer"));

        int choice;
        do {
            System.out.println("\nLibrary Menu:");
            System.out.println("1. View Books");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewBooks();
                    break;
                case 2:
                    issueBook();
                    break;
                case 3:
                    returnBook();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    static void viewBooks() {
        System.out.println("\nAvailable Books:");
        for (Map.Entry<Integer, Book> entry : books.entrySet()) {
            Book b = entry.getValue();
            String status = b.isIssued ? "Issued" : "Available";
            System.out.println(entry.getKey() + ". " + b.title + " - " + status);
        }
    }

    static void issueBook() {
        viewBooks();
        System.out.print("Enter book ID to issue: ");
        int id = sc.nextInt();
        if (books.containsKey(id)) {
            Book b = books.get(id);
            if (!b.isIssued) {
                b.isIssued = true;
                b.issueDate = new Date();
                System.out.println("Book issued successfully.");
            } else {
                System.out.println("Book is already issued.");
            }
        } else {
            System.out.println("Invalid book ID.");
        }
    }

    static void returnBook() {
        System.out.print("Enter book ID to return: ");
        int id = sc.nextInt();
        if (books.containsKey(id)) {
            Book b = books.get(id);
            if (b.isIssued) {
                long diffInMillies = Math.abs(new Date().getTime() - b.issueDate.getTime());
                long days = diffInMillies / (1000 * 60 * 60 * 24);

                int fine = 0;
                if (days > 7) {
                    long extraDays = days - 7;
                    long week = 1;
                    while (extraDays > 0) {
                        long fineDays = Math.min(extraDays, 7);
                        fine += fineDays * week;
                        extraDays -= fineDays;
                        week++;
                    }
                }

                b.isIssued = false;
                b.issueDate = null;
                System.out.println("Book returned successfully. Days borrowed: " + days);
                System.out.println("Fine: ₹" + fine);
            } else {
                System.out.println("Book is not currently issued.");
            }
        } else {
            System.out.println("Invalid book ID.");
        }
    }
}
