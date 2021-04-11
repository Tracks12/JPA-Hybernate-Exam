package io.rtx.data;

import java.util.List;

public interface DaoManager {

	/**
	 * Create a new Book and associate this Book to a Library.
	 * 
	 * @param libraryId id of the Library
	 * @param title     title of the new Book
	 * @param isbn      ISBN of the new Book
	 * 
	 * @return created Book or <code>null</code> if the id does not match a Library
	 */
	BookEntity createBook(long libraryId, String title, ISBN isbn);

	/**
	 * Create a new Customer and associate this customer to a library.
	 * 
	 * @param libraryId          id of the library
	 * @param customerFirstNames List of first names of the new Customer
	 * @param customerLastName   last name of the new Customer
	 * 
	 * @return created Customer or <code>null</code> if the id does not match a
	 *         Library
	 */
	CustomerEntity createCustomer(long libraryId, List<String> customerFirstNames, String customerLastName);

	/**
	 * Create a new Library
	 * 
	 * @param libraryName name of the Library
	 * @param type        type of the Library {@literal}
	 * 
	 * @return created Library or <code>null</code> if an error occurred
	 * @see LibraryType
	 */
	LibraryEntity createLibrary(String libraryName, LibraryType type);

	/**
	 * Delete a Book identified by its id
	 * 
	 * @param bookId id of the book
	 *
	 * @return <code>true</code> if deleted, <code>false</code> if the id does not
	 *         match a Book
	 */
	boolean deleteBook(long bookId);

	/**
	 * Delete a Customer identified by its id
	 * 
	 * @param customerId id of the customer
	 * 
	 * @return <code>true</code> if deleted, <code>false</code> if the id does not
	 *         match a Customer
	 */
	boolean deleteCustomer(long customerId);

	/**
	 * Delete a Library identified by its id
	 * 
	 * @param libraryId id of the library
	 * 
	 * @return <code>true</code> if deleted, <code>false</code> the id does not
	 *         match a Library
	 */
	boolean deleteLibrary(long libraryId);

	/**
	 * Determine if a Book is available, i.e. the Book exists and there is no active
	 * Borrowing associate to this Book. A borrowing is active if its field
	 * <code>endDate</code> is not filled.
	 * 
	 * @param bookId id of the Book
	 * 
	 * @return <code>true</code> if there is no active Borrowing associate to the
	 *         book. <code>false</code> if the id does not match a Book or if a
	 *         Borrowing is active.
	 */
	boolean isBookAvailable(long bookId);

	/**
	 * Return list of all Books in a given Library
	 * 
	 * @param libraryId id of the Library
	 * 
	 * @return a list of Book or <code>null</code> the id does not match a Library
	 */
	List<BookEntity> listBooksByLibrary(long libraryId);

	/**
	 * Return all Borrowings of the Customer identified by the given id.
	 * 
	 * @param customerId id of the Customer
	 * 
	 * @return a list of Borrowing or <code>null</code> if the id does not match a
	 *         Customer
	 */
	List<BorrowingEntity> listBorrowingOfCustomer(long customerId);

	/**
	 * Return all Customers associated to the Library identified by the given id
	 * 
	 * @param libraryId id of the Library
	 * 
	 * @return a list of customer or <code>null</code> if the id does not match a
	 *         Library
	 */
	List<CustomerEntity> listCustomerByLibrary(long libraryId);

	/**
	 * Return active borrowing, i.e. borrowing without 'endDate' for a given Library
	 * 
	 * @param libraryId id of the library
	 * 
	 * @return a list of Borrowing or <code>null</code> if the id does not match a
	 *         Library
	 */
	List<BorrowingEntity> listActiveBorrowingByLibrary(long libraryId);

	/**
	 * Return all Libraries
	 * 
	 * @return a list of Libraries
	 */
	List<LibraryEntity> listLibraries();

	/**
	 * Start the borrowing of a Book by a Customer. This Book and this Customer must
	 * exists and the Book must be available (as define in
	 * {@link DaoManager#isBookAvailable(long)})
	 * 
	 * Create a Borrowing entity with <code>startDate</code> filled and
	 * <code>endDate</code> empty.
	 * 
	 * @param customerId id of the customer
	 * @param bookId     id of the book
	 * 
	 * @return <code>"Invalid Customer Id"</code>, <code>"Invalid Book Id"</code>,
	 *         <code>"Book not available"</code> or <code>"OK"</code>
	 */
	String startBorrowing(long customerId, long bookId);

	/**
	 * Start the DaoManager : Create EntityManagerFactory
	 */
	void startDAO();

	/**
	 * Retrieve an active Borrowing with the given Customer and Book id and stop
	 * this borrowing by setting the field <code>endDate</code>.
	 * 
	 * 
	 * @param customerId id of the Customer
	 * @param bookId     id of the Book
	 * 
	 * @return <code>true</code> if an active Borrowing is found and stopped,
	 *         <code>false</code> otherwise.
	 */
	boolean stopBorrowing(long customerId, long bookId);

	/**
	 * Stop the DaoManager : Close EntityManagerFactory
	 */
	void stopDAO();

	/**
	 * Update title and ISBN of a Book identified by the given id
	 * 
	 * @param bookId   id of the Book
	 * @param newTitle new title
	 * @param newIsbn  new ISBN
	 * 
	 * @return <code>true</code> if success, <code>false</code> if the id does not
	 *         match a Book
	 */
	boolean updateBook(long bookId, String newTitle, ISBN newIsbn);

	/**
	 * Update first and last names of a Customer identified by the given id
	 * 
	 * @param customerId    id of the Customer
	 * @param newLastName   new value of the lastName
	 * @param newFirstNames new values of the firstName
	 * 
	 * @return <code>true</code> if success, <code>false</code> if the id does not
	 *         match a Customer
	 */
	boolean updateCustomer(long customerId, String newLastName, List<String> newFirstNames);

}
