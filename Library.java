import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

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
    static Book[] books = {
        new Book("Python"),
        new Book("Java"),
        new Book("English"),
        new Book("Done")
    };

    static Scanner sc = new Scanner(System.in);
    static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\nLibrary Menu:");
            System.out.println("1. View Books");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

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
        for (int i = 0; i < books.length; i++) {
            Book b = books[i];
            String status = b.isIssued ? "Issued on " + sdf.format(b.issueDate) : "Available";
            System.out.println((i + 1) + ". " + b.title + " - " + status);
        }
    }

    static void issueBook() {
        viewBooks();
        System.out.print("Enter book number to issue: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (id >= 1 && id <= books.length) {
            Book b = books[id - 1];
            if (!b.isIssued) {
                System.out.print("Enter issue date (dd-MM-yyyy): ");
                String dateStr = sc.nextLine();
                try {
                    Date issueDate = sdf.parse(dateStr);
                    b.isIssued = true;
                    b.issueDate = issueDate;
                    System.out.println("Book issued successfully.");
                } catch (ParseException e) {
                    System.out.println("Invalid date format.");
                }
            } else {
                System.out.println("Book is already issued.");
            }
        } else {
            System.out.println("Invalid book number.");
        }
    }

    static void returnBook() {
        System.out.print("Enter book number to return: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (id >= 1 && id <= books.length) {
            Book b = books[id - 1];
            if (b.isIssued) {
                System.out.print("Enter return date (dd-MM-yyyy): ");
                String returnDateStr = sc.nextLine();
                try {
                    Date returnDate = sdf.parse(returnDateStr);
                    long diff = Math.abs(returnDate.getTime() - b.issueDate.getTime());
                    long days = diff / (1000 * 60 * 60 * 24);

                    int fine = 0;
                    if (days > 7) {
                        long extra = days - 7;
                        long week = 1;
                        while (extra > 0) {
                            long fineDays = Math.min(extra, 7);
                            fine += fineDays * week;
                            extra -= fineDays;
                            week++;
                        }
                    }

                    b.isIssued = false;
                    b.issueDate = null;

                    System.out.println("Book returned successfully.");
                    System.out.println("Days borrowed: " + days);
                    System.out.println("Fine: ₹" + fine);
                } catch (ParseException e) {
                    System.out.println("Invalid date format.");
                }
            } else {
                System.out.println("Book is not currently issued.");
            }
        } else {
            System.out.println("Invalid book number.");
        }
    }
}
