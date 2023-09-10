import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.*;



public class LMS2 {

    public static class Library {
        private ArrayList <LMS2.Book> collection;  //Collection of all the books
        private ArrayList <LMS2.Book> checkedOut; //Collection of books checked out/borrowed

        /*
         * Creates a new Collection Arraylist for all the books
         * and collection of books currently checked out
         * */
        public Library(ArrayList <LMS2.Book> collection) {
            //collection = new ArrayList<LMS2.Book>();
            this.collection = collection;

            checkedOut = new ArrayList<LMS2.Book>();
        }
        /*
         * Adds a new book to the collection*/
        public void addBook(LMS2.Book book) {
            collection.add(book);
        }

        public void addBorrowed(LMS2.Book book) {
            checkedOut.add(book);
        }
        /*
         * Removes a book from the collection
         */
        public void removeBook(LMS2.Book book){
            collection.remove(book);
        }

        /*
         * Retrieves the collection of books
         */
        public ArrayList <LMS2.Book> getBooks() {
            return collection;
        }

        public ArrayList <LMS2.Book> getBorrowed() {
            return checkedOut;
        }
    }

    public static class Book {

        private String title;           // Title of the book
        private String author;          // Author of the book
        private int isbn;               // ISBN/ID # of the book

        /*
         * Constructs a new Book object with the given title, author, and isbn.
         *
         * @param isbn The ISBN/ID # of the book.
         * @param title The tile of the book.
         * @param author The author of the book.
         */
        public Book(int isbn, String title, String author) {  //add this back in--, String title, String author
            this.isbn = isbn;
            this.title = title;
            this.author = author;
        }

        /*
         * Receives the ISBN of the book
         */
        public int getISBN() {
            return isbn;
        }

        /*
         * Sets the ISBN of the book
         */
        public void setISBN(int isbn) {
            this.isbn = isbn;
        }

        /*
         * Receives the title of the book
         */
        public String getTitle() {
            return title;
        }

        /*
         * Sets the title of the book
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /*
         * Receives the author of the book.
         */
        public String getAuthor() {
            return author;
        }

        /*
         * Sets the author of the book.
         */
        public void setAuthor(String author) {
            this.author = author;
        }
    }

    public static class ISBN {
        private static int nextID = 0;
        private int currentISBN;

        ArrayList<Integer> idList = new ArrayList<>();
        private int checkTaken;
        boolean found = true;


        public void textInt(int isbn) {
            //idList.add(isbn);
            nextID = isbn;
            while(found) {
                found = false;
                while (idList.contains(nextID)) {
                    found = true;
                    System.out.println("Id already taken, choose another one.");
                    nextID++;
                    if (!found) {
                        break;
                    }
                }
            }

            if(!found) {

                idList.add(nextID);
                currentISBN = nextID;

                //nextID++;
            }
        }
        public int getCurrentISBN() {
            currentISBN = nextID++;
            return currentISBN;
        }
    }

    public static class fileReader {
        private final Library library;
        Scanner scanner = new Scanner(System.in);
        private ArrayList <LMS2.Book> collection;

        LMS2.ISBN generator = new LMS2.ISBN();

        private String filePath;

        public fileReader(String filePath, ArrayList<LMS2.Book> collection, LMS2.Library library) {
          this.filePath = filePath;
          this.collection = collection;
          this.library = library;
        }

        public void readPrintFile() {
            try {
                /*
                 * Attempts to read data from a text file and catch
                 * any exceptions that might occur from that.
                 */
                File fileBooks = new File("Books.txt");
                Scanner myReader = new Scanner(fileBooks);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    String[] parts = data.split(",");
                    if(parts.length == 3) {
                        String id = parts[0];
                        String title = parts[1];
                        String author = parts[2];
                        int id1 = Integer.parseInt(id);


                        generator.textInt(id1);
                        int isbn = generator.getCurrentISBN();

                        LMS2.Book book = new LMS2.Book(isbn, title, author);

                        library.addBook(book);
                    }
                }
                myReader.close();
                System.out.println("Updated library collection.");
            }   catch (FileNotFoundException e) {
                //Handles file not found error
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            for (Book book: library.getBooks()) {
                System.out.println(book.getISBN() + " " + book.getTitle() + " by " + book.getAuthor());
            }
        }
    }

    public static class fileWriter {
        private Library library;
        private ArrayList <LMS2.Book> collection;
        private String fileUpdate;


        public fileWriter(String fileUpdate, ArrayList<LMS2.Book> collection, LMS2.Library library){
            this.fileUpdate = fileUpdate;
            this. collection = collection;
            this.library = library;
        }

        public void writeToFile() {
            try (FileWriter myWriter = new FileWriter(fileUpdate)) {
                for (Book book : library.getBooks()) {
                    System.out.println(book.getISBN() + " " + book.getTitle() + " by " + book.getAuthor());
                    String line = book.getISBN() + "," + book.getTitle() + "," + book.getAuthor();
                    myWriter.write(line + "\n");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

    }


        /*
        * Main Method for a Library Management System Consoled Based Application.
        * This program will allow users to manage a collection of books in a Library.
        *
        * Displays the front end menu for user interaction
        *
        * Creates instances for ISBN/ID # and the library.
        * Creates a arraylist for the books.
        *
        * Path for reading and writing from a text file.
        * Calls the readPrintFile() method to read and display the books
        * from a text file.
        */

        public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList <LMS2.Book> collection = new ArrayList<>();

        LMS2.Library library = new LMS2.Library(collection);
        LMS2.ISBN generator = new LMS2.ISBN();

        int choice;

        fileReader fileReader = new fileReader("Books.txt", collection, library);
        fileWriter fileWriter = new fileWriter("Books.txt", collection, library);

        fileReader.readPrintFile();

        while(true) {
            System.out.println("Menu");
            System.out.println("1. Update text file");
            System.out.println("2. Add Book to Collection");
            System.out.println("3. Remove A Book from Collection");
            System.out.println("4. Display Library Collection");
            System.out.println("5. Display Checked-out Books");
            System.out.println("6. Exit");
            System.out.println("Enter Your Choice: ");

            //Read user's choice
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    /*
                    * Option 1
                    * Updates text file with current collection of books.
                    * calls the writeToFile method to write collection
                    * of books to a text file.
                    */
                    fileWriter.writeToFile();
                    System.out.println("Your text file has been updated.");
                    break;

                case 2:
                    /*
                    * Option 2
                    * Adds a new book to the collection.
                    * generates an ISBN
                    * Input and scans given title and author of book.
                    *
                    * Creates a book instance of new information
                    * Adds book to the library collection
                    * Ability to add another book or exit to main menu.
                    */
                    System.out.println("Add a new book to the collection.");
                    boolean askAdd;
                    while(true) {
                        int isbn = generator.getCurrentISBN();
                        scanner.nextLine();
                        System.out.println("Please enter the book's Title:");
                        String title = scanner.nextLine();
                        System.out.println("Please enter the book's Author:");
                        String author = scanner.nextLine();

                        Book book = new Book(isbn, title, author);
                        library.addBook(book);

                        System.out.println("Would you like to add another book? Yes or No?");
                        String option = scanner.nextLine();
                        if (option.equalsIgnoreCase("Yes")) {
                            askAdd = true;
                        } else {
                            askAdd = false;
                            //System.out.println("These are the new books that you added to the collection.");
                            break;
                        }
                    }
                    /*for (Book book: library.getBooks()) {
                        System.out.println(book.getISBN() + " " + book.getTitle() + " by " + book.getAuthor());
                    }*/
                    break;

                case 3:
                    /*
                     * Option 3
                     * Removes a book from the collection via ISBN/ID #
                     * compares entered ISBN to ISBN in the collection
                     * If ISBN is in the collection, then removes that book
                     * from the collection and adds it to a borrowed collection.
                     * Displays if book was successfully removed with information.
                     *
                     * Used an iterator as I kept getting an ConcurrentModificationException
                     */

                    int borrowedISBN;
                    boolean askRemoved;

                    System.out.println("Which book would you like to remove?");

                    while(true) {

                        System.out.println("To begin checking out, please provide the book's ISBN or ID number: ");
                        borrowedISBN = scanner.nextInt();
                        Iterator<Book> iterator = library.getBooks().iterator();
                        while (iterator.hasNext()) {
                            Book book = iterator.next();
                            if(book.getISBN() == borrowedISBN) {
                                iterator.remove();
                                library.addBorrowed(book);
                                askRemoved = true;
                            } else {
                                askRemoved = false;

                            }

                        }
                        break;
                    }

                    for (Book book: library.getBorrowed()) {
                        System.out.println("Book successfully checked out.");
                        System.out.println(book.getISBN() + " " + book.getTitle() + " by " + book.getAuthor());
                    }

                   break;

                case 4:
                    /*
                     * Option 4
                     * Displays a list of all the books in the collection at the moment
                     * loops through library collection and returns each book to be printed
                    */
                    System.out.println("Here is a list of all the books currently in our library at the moment.");
                    for (Book book: library.getBooks()) {
                        System.out.println(book.getISBN() + " " + book.getTitle() + " by " + book.getAuthor());
                    }

                    break;

                case 5:
                    /*
                     * Option 5
                     * Displays all books that were removed from the collection at the moment.
                     * loops through borrowed collection and returns each book to be printed
                    */
                    System.out.println("Here is a list of all the books currently checked out of the library.");
                    for (Book book: library.getBorrowed()) {
                        System.out.println(book.getISBN() + " " + book.getTitle() + " by " + book.getAuthor());
                    }
                    break;

                case 6:
                    //Option 6
                    //Displays goodbye message and exits application
                    System.out.println("Goodbye!");
                    System.exit(0);
            }
        }

    }
}
