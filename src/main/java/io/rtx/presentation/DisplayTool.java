package io.rtx.presentation;

import java.util.Collection;
import java.util.List;

import io.rtx.data.BookEntity;
import io.rtx.data.BorrowingEntity;
import io.rtx.data.CustomerEntity;
import io.rtx.data.LibraryEntity;

public class DisplayTool {

	public void displayBook(BookEntity book) {
		System.out.println(" - " + book.getId() + " '" + book.getTitle() + "' ISBN=" + book.getIsbn());
	}

	public void displayBooks(Collection<BookEntity> books) {
		for (BookEntity book : books) {
			displayBook(book);
		}
	}

	public void displayBorrowing(BorrowingEntity borrowing, boolean showCustomer) {
		StringBuilder s = new StringBuilder();
		s.append(" - Book:");
		s.append(borrowing.getBook().getId());
		s.append(" '");
		s.append(borrowing.getBook().getTitle());
		s.append("' start:");
		s.append(borrowing.getStartDate());
		s.append(" end:");
		s.append(borrowing.getEndDate());

		System.out.println(s.toString());

		if (showCustomer) {
			CustomerEntity customer = borrowing.getCustomer();
			displayCustomer(customer, 3);
		}
	}

	public void displayBorrowings(Collection<BorrowingEntity> borrowings, boolean showCustomer) {
		for (BorrowingEntity borrowing : borrowings) {
			displayBorrowing(borrowing, false);
		}
	}

	private void displayCustomer(CustomerEntity customer, int spacing) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < spacing; i++) {
			s.append(" ");
		}
		s.append("Customer: ");
		s.append(customer.getId());
		s.append(" ");

		List<String> firstNames = customer.getFirstNames();
		if (firstNames != null) {
			firstNames.forEach(firstName -> {
				s.append(firstName);
				s.append(",");
			});
		}
		s.append(customer.getLastName());
		System.out.println(s.toString());
	}

	public void displayCustomer(CustomerEntity customer) {
		displayCustomer(customer, 1);
	}

	public void displayCustomers(Collection<CustomerEntity> customers) {
		for (CustomerEntity customer : customers) {
			displayCustomer(customer);
		}
	}

	public void displayLibraries(Collection<LibraryEntity> libraries) {
		for (LibraryEntity library : libraries) {
			displayLibrary(library);
		}

	}

	public void displayLibrary(LibraryEntity library) {
		System.out.println(" - Library: " + library.getId() + " " + library.getName());
	}

}
