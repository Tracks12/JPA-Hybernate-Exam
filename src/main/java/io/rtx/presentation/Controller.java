package io.rtx.presentation;

import java.util.List;

import io.rtx.data.BookEntity;
import io.rtx.data.BorrowingEntity;
import io.rtx.data.CustomerEntity;
import io.rtx.data.DaoManager;
import io.rtx.data.ISBN;
import io.rtx.data.LibraryEntity;
import io.rtx.data.LibraryType;

public class Controller {

	private DaoManager daoManager;
	private DisplayTool display;
	private ScannerTool scanner;

	public Controller(DaoManager daoManager) {
		this.daoManager = daoManager;
		this.scanner = new ScannerTool();
		this.display = new DisplayTool();
	}

	private void checkBookAvaibility() {
		long bookId = scanner.scanLong("# Id Book: ");

		boolean available = daoManager.isBookAvailable(bookId);

		if (available) {
			System.out.println("Book is available");
		} else {
			System.out.println("Book not available or invalid Book Id");
		}
	}

	private void createBook() {
		long libraryId = scanner.scanLong("# Library Id: ");
		String title = scanner.scanString("# Book title: ");
		ISBN isbn = scanner.scanIsbn();

		BookEntity book = daoManager.createBook(libraryId, title, isbn);

		if (book != null) {
			System.out.println("Created - Id:" + book.getId());
		} else {
			System.out.println("Not created");
		}
	}

	private void createCustomer() {
		long libraryId = scanner.scanLong("# Id Library: ");
		List<String> customerFirstNames = scanner
				.scanStringList("# Customer First Names separated by comme (ex: John,Williams): ", ",");
		String customerLastName = scanner.scanString("# Customer Last Name: ");

		CustomerEntity customer = daoManager.createCustomer(libraryId, customerFirstNames, customerLastName);

		if (customer != null) {
			System.out.println("Created - Id:" + customer.getId());
		} else {
			System.out.println("Not created");
		}
	}

	private void createLibrary() {
		String libraryName = scanner.scanString("# Library Name: ");
		LibraryType type = scanner.scanLibraryType();

		LibraryEntity library = daoManager.createLibrary(libraryName, type);

		if (library != null) {
			System.out.println("Created - Id:" + library.getId());
		} else {
			System.out.println("Not created");
		}
	}

	private void deleteBook() {
		long bookId = scanner.scanLong("# Id Book: ");
		boolean success = daoManager.deleteBook(bookId);
		if (success) {
			System.out.println("Deleted");
		} else {
			System.out.println("Invalid id");
		}
	}

	private void deleteCustomer() {
		long customerId = scanner.scanLong("# Id Customer: ");
		boolean success = daoManager.deleteCustomer(customerId);
		if (success) {
			System.out.println("Deleted");
		} else {
			System.out.println("Invalid id");
		}
	}

	private void deleteLibrary() {
		long libraryId = scanner.scanLong("# Id Library: ");
		boolean success = daoManager.deleteLibrary(libraryId);
		if (success) {
			System.out.println("Deleted");
		} else {
			System.out.println("Invalid id");
		}
	}

	private void listBooksByLibrary() {
		long libraryId = scanner.scanLong("# Id Library: ");

		List<BookEntity> books = daoManager.listBooksByLibrary(libraryId);

		if (books == null) {
			System.out.println("Invalid Library id");
		} else {
			this.display.displayBooks(books);
		}
	}

	private void listBorrowingOfCustomer() {
		long customerId = scanner.scanLong("# Id Customer: ");

		List<BorrowingEntity> borrowings = daoManager.listBorrowingOfCustomer(customerId);

		if (borrowings == null) {
			System.out.println("Invalid Customer id");
		} else {
			this.display.displayBorrowings(borrowings, false);
		}
	}

	private void listCustomerByLibrary() {
		long libraryId = scanner.scanLong("# Id Library: ");

		List<CustomerEntity> customers = daoManager.listCustomerByLibrary(libraryId);

		if (customers == null) {
			System.out.println("Invalid Library id");
		} else {
			this.display.displayCustomers(customers);
		}
	}

	private void listActiveBorrowingByLibrary() {
		long libraryId = scanner.scanLong("# Id Library: ");

		List<BorrowingEntity> borrowings = daoManager.listActiveBorrowingByLibrary(libraryId);

		if (borrowings == null) {
			System.out.println("Invalid Library id");
		} else {
			this.display.displayBorrowings(borrowings, true);
		}
	}

	private void listLibraries() {
		List<LibraryEntity> libraries = daoManager.listLibraries();

		if (libraries == null || libraries.isEmpty()) {
			System.out.println(" - No library");
		} else {
			this.display.displayLibraries(libraries);
		}
	}

	public void start() {
		System.out.println("## Welcome in LibraryManager v1.0");

		daoManager.startDAO();

		boolean exit = false;

		while (!exit) {
			try {
				// Get Input
				System.out.println();
				System.out.println("# Select an option :");
				System.out.println(" Library ");
				System.out.println(" 11 - List Libraries");
				System.out.println(" 12 - Create a new Library");
				System.out.println(" 13 - Delete a Library");
				System.out.println(" Book ");
				System.out.println(" 21 - Create a new book");
				System.out.println(" 22 - Update a book");
				System.out.println(" 23 - Delete a book");
				System.out.println(" 24 - List books by library");
				System.out.println(" Customer ");
				System.out.println(" 31 - List customer by library");
				System.out.println(" 32 - Create a new customer");
				System.out.println(" 33 - Update a customer");
				System.out.println(" 34 - Delete a customer");
				System.out.println(" Borrowing ");
				System.out.println(" 41 - Check book availability");
				System.out.println(" 42 - List borrowing of a customer");
				System.out.println(" 43 - Start a borrowing");
				System.out.println(" 44 - End a borrowing");
				System.out.println(" 45 - List active borrowings by library");
				System.out.println();
				System.out.println(" 99 - exit");
				int choice = scanner.scanInt("# Select an option: ");

				// Treate choice
				switch (choice) {
				case 11:
					listLibraries();
					break;
				case 12:
					createLibrary();
					break;
				case 13:
					deleteLibrary();
					break;
				case 14:
					break;
				case 21:
					createBook();
					break;
				case 22:
					updateBook();
					break;
				case 23:
					deleteBook();
					break;
				case 24:
					listBooksByLibrary();
					break;
				case 31:
					listCustomerByLibrary();
					break;
				case 32:
					createCustomer();
					break;
				case 33:
					updateCustomer();
					break;
				case 34:
					deleteCustomer();
					break;
				case 41:
					checkBookAvaibility();
					break;
				case 42:
					listBorrowingOfCustomer();
					break;
				case 43:
					startBorrowing();
					break;
				case 44:
					stopBorrowing();
					break;
				case 45:
					listActiveBorrowingByLibrary();
					break;
				case 99:
					exit = true;
					continue;
				default:
					System.out.println("Invalid choice: " + choice);
					break;
				}

			} catch (Exception e) {
				System.out.println("### Unexpected Exception :");
				e.printStackTrace();
			}
			scanner.wait("-> type enter to continue...");
		}
		
		System.out.println("## Closing...");
		daoManager.stopDAO();
		System.out.println("## See you soon.");
	}

	private void startBorrowing() {
		long bookId = scanner.scanLong("# Id Book: ");
		long customerId = scanner.scanLong("# Id Customer: ");

		String message = daoManager.startBorrowing(customerId, bookId);
		System.out.println(message);
	}

	private void stopBorrowing() {
		long bookId = scanner.scanLong("# Id Book: ");
		long customerId = scanner.scanLong("# Id Customer: ");

		boolean success = daoManager.stopBorrowing(customerId, bookId);
		if (success) {
			System.out.println("OK");
		} else {
			System.out.println("No active borrowing found with this Customer and Book");
		}
	}

	private void updateBook() {
		long bookId = scanner.scanLong("# Id Book: ");
		String newTitle = scanner.scanString("# New Title: ");
		ISBN isbn = scanner.scanIsbn();

		boolean success = daoManager.updateBook(bookId, newTitle, isbn);
		if (success) {
			System.out.println("Updated");
		} else {
			System.out.println("Invalid Book Id");
		}
	}

	private void updateCustomer() {
		long customerId = scanner.scanLong("# Id Customer: ");

		List<String> newFirstNames = scanner
				.scanStringList("# New First Names ; Separated by a comma (ex: John,Williams): ", ",");
		String newLastName = scanner.scanString("# New Last Name: ");

		boolean success = daoManager.updateCustomer(customerId, newLastName, newFirstNames);
		if (success) {
			System.out.println("Updated");
		} else {
			System.out.println("Invalid Customer Id");
		}
	}
}
