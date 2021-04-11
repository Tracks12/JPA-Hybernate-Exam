package io.rtx.data;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class DaoManagerImpl implements DaoManager {

	private EntityManagerFactory emf;
	private EntityManager em;

	@Override
	public void startDAO() {
		this.emf = Persistence.createEntityManagerFactory("my-pu");
		this.em = this.emf.createEntityManager();
	}

	@Override
	public void stopDAO() {
		this.em.close();
		this.emf.close();
	}

	@Override
	public LibraryEntity createLibrary(String libraryName, LibraryType type) {
		LibraryEntity library = new LibraryEntity(libraryName, type);
		
		this.em.getTransaction().begin();
		this.em.persist(library);
		this.em.getTransaction().commit();
		
		return library;
	}

	@Override
	public BookEntity createBook(long libraryId, String title, ISBN isbn) {
		LibraryEntity library = this.em.find(LibraryEntity.class, libraryId);
		
		if(library == null)
			throw new IllegalArgumentException("No library found !");
		
		BookEntity book = new BookEntity(library, title, isbn);
		
		this.em.getTransaction().begin();
		this.em.persist(book);
		this.em.getTransaction().commit();
		
		return book;
	}

	@Override
	public CustomerEntity createCustomer(long libraryId, List<String> customerFirstNames, String customerLastName) {
		LibraryEntity library = this.em.find(LibraryEntity.class, libraryId);
		
		if(library == null)
			throw new IllegalArgumentException("No library found !");
		
		CustomerEntity customer = new CustomerEntity(library, customerFirstNames, customerLastName);
		
		this.em.getTransaction().begin();
		this.em.persist(customer);
		this.em.getTransaction().commit();
		
		return customer;
	}

	@Override
	public List<LibraryEntity> listLibraries() {
		TypedQuery<LibraryEntity> query = this.em.createQuery(
			"SELECT l FROM libraries l",
			LibraryEntity.class
		);
		
		return query.getResultList();
	}

	@Override
	public List<BookEntity> listBooksByLibrary(long libraryId) {
		LibraryEntity library = this.em.find(LibraryEntity.class, libraryId);
		
		if(library == null)
			throw new IllegalArgumentException("No library found !");
		
		return library != null ? library.getBooks() : Collections.emptyList();
	}

	@Override
	public List<CustomerEntity> listCustomerByLibrary(long libraryId) {
		LibraryEntity library = this.em.find(LibraryEntity.class, libraryId);
		
		if(library == null)
			throw new IllegalArgumentException("No library found !");
		
		TypedQuery<CustomerEntity> query = this.em.createQuery(
			"SELECT e FROM customers e WHERE e.library.id = :libraryId",
			CustomerEntity.class
		)
			.setParameter("libraryId", libraryId);
		
		return query.getResultList();
	}

	@Override
	public List<BorrowingEntity> listActiveBorrowingByLibrary(long libraryId) {
		LibraryEntity library = this.em.find(LibraryEntity.class, libraryId);
		
		if(library == null)
			throw new IllegalArgumentException("No library found !");
		
		TypedQuery<BorrowingEntity> query = this.em.createQuery(
			"SELECT e FROM borrowings e WHERE e.book.library.id = :libraryId AND e.endDate = NULL",
			BorrowingEntity.class
		)
			.setParameter("libraryId", libraryId);
		
		return query.getResultList();
	}

	@Override
	public List<BorrowingEntity> listBorrowingOfCustomer(long customerId) {
		CustomerEntity customer = this.em.find(CustomerEntity.class, customerId);
		
		if(customer == null)
			throw new IllegalArgumentException("No customer found !");
		
		TypedQuery<BorrowingEntity> query = this.em.createQuery(
			"SELECT e FROM borrowings e WHERE e.customer.id = :customerId",
			BorrowingEntity.class
		)
			.setParameter("customerId", customerId);
		
		return query.getResultList();
	}

	@Override
	public boolean updateBook(long bookId, String newTitle, ISBN newIsbn) {
		BookEntity book = this.em.find(BookEntity.class, bookId);
		
		if(book == null)
			throw new IllegalArgumentException("No book found !");
		
		this.em.getTransaction().begin();
		book.setTitle(newTitle);
		book.setIsbn(newIsbn);
		this.em.getTransaction().commit();
		
		return book != null;
	}

	@Override
	public boolean updateCustomer(long customerId, String newLastName, List<String> newFirstNames) {
		CustomerEntity customer = this.em.find(CustomerEntity.class, customerId);
		
		if(customer == null)
			throw new IllegalArgumentException("Invalid customerId");
		
		this.em.getTransaction().begin();
		customer.setLastName(newLastName);
		customer.setFirstNames(newFirstNames);
		this.em.getTransaction().commit();
		
		return customer != null;
	}

	@Override
	public boolean deleteBook(long bookId) {
		BookEntity book = this.em.find(BookEntity.class, bookId);
		
		if(book == null)
			throw new IllegalArgumentException("No book found !");
		
		this.em.getTransaction().begin();
		this.em.remove(book);
		this.em.getTransaction().commit();
		
		return book != null;
	}

	@Override
	public boolean deleteCustomer(long customerId) {
		CustomerEntity customer = this.em.find(CustomerEntity.class, customerId);
		
		if(customer == null)
			throw new IllegalArgumentException("No customer found !");
		
		this.em.getTransaction().begin();
		this.em.remove(customer);
		this.em.getTransaction().commit();
		
		return customer != null;
	}

	@Override
	public boolean deleteLibrary(long libraryId) {
		LibraryEntity library = this.em.find(LibraryEntity.class, libraryId);
		
		if(library == null)
			throw new IllegalArgumentException("No library found !");
		
		this.em.getTransaction().begin();
		this.em.remove(library);
		this.em.getTransaction().commit();
		
		return library != null;
	}

	@Override
	public boolean isBookAvailable(long bookId) {
		BookEntity book = this.em.find(BookEntity.class, bookId);
		
		if(book == null)
			throw new IllegalArgumentException("Invalid bookId");
		
		TypedQuery<BorrowingEntity> query = this.em.createQuery(
			"SELECT e FROM borrowings e WHERE e.book.id = :bookId AND e.endDate != NULL",
			BorrowingEntity.class
		)
			.setParameter("bookId", bookId);
		
		return query.getResultList().isEmpty();
	}

	@Override
	public String startBorrowing(long customerId, long bookId) {
		CustomerEntity customer = this.em.find(CustomerEntity.class, customerId);
		BookEntity book = this.em.find(BookEntity.class, bookId);

		if(customer == null)
			throw new IllegalArgumentException("No customer found !");
		
		if(book == null)
			throw new IllegalArgumentException("No book found !");
		
		if(!this.isBookAvailable(bookId))
			throw new IllegalStateException("No book available !");
		
		BorrowingEntity borrowing = new BorrowingEntity(customer, book);
		
		this.em.getTransaction().begin();
		this.em.persist(borrowing);
		this.em.getTransaction().commit();
		
		return borrowing.getStartDate().toString();
	}

	@Override
	public boolean stopBorrowing(long customerId, long bookId) {
		CustomerEntity customer = this.em.find(CustomerEntity.class, customerId);
		BookEntity book = this.em.find(BookEntity.class, bookId);
		
		if(customer == null)
			throw new IllegalArgumentException("No customer found !");

		if(book == null)
			throw new IllegalArgumentException("No book found !");
		
		TypedQuery<BorrowingEntity> query = this.em.createQuery(
			"SELECT e FROM borrowings e WHERE e.customer.id = :customerId AND e.book.id = :bookId AND e.endDate = NULL", 
			BorrowingEntity.class
		)
			.setParameter("customerId", customerId)
			.setParameter("bookId", bookId);

		BorrowingEntity borrowing = query.getSingleResult();
		
		if(borrowing == null)
			throw new IllegalStateException(MessageFormat.format("{0}: no active borrowing for {1}", book.getTitle(), customer.getLastName()));
		
		this.em.getTransaction().begin();
		borrowing.stop();
		this.em.getTransaction().commit();
		
		return borrowing != null;
	}
}
