package io.rtx.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name="borrowings")
public class BorrowingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="customer_id")
	private CustomerEntity customer;

	@OneToOne
	@JoinColumn(name="book_id")
	private BookEntity book;

	@Column(name="start_date")
	private Date startDate;

	@Column(name="end_date")
	private Date endDate;
	
	public BorrowingEntity() {}
	
	public BorrowingEntity(CustomerEntity customer, BookEntity book) {
		this.customer = customer;
		this.book     = book;
	}

	public BookEntity getBook() {
		return book;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public Date getEndDate() {
		return endDate;
	}

	public Long getId() {
		return id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setBook(BookEntity book) {
		this.book = book;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void stop() {
		this.endDate = new Date();
	}

}
