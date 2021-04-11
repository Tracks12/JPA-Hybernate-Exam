package io.rtx.data;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name="customers")
public class CustomerEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String lastName;
	
	@OneToOne
    @JoinColumn(name="library_id")
	private LibraryEntity library;
	
	@OneToMany
	@JoinColumn(name="customer_id")
	private List<BorrowingEntity> borrowingEntities;
	
	@ElementCollection
	@CollectionTable(name="customers_firstnames", joinColumns=@JoinColumn(name = "customer_id", referencedColumnName = "id"))
	@Column(name="name")
	private List<String> firstNames;
	
	public CustomerEntity() {}

	public CustomerEntity(LibraryEntity library, List<String> firstNames, String lastName) {
		this.library    = library;
		this.firstNames = firstNames;
		this.lastName   = lastName;
	}
	
	public List<BorrowingEntity> getBorrowingEntities() {
		return this.borrowingEntities;
	}

	public List<String> getFirstNames() {
		return this.firstNames;
	}

	public Long getId() {
		return this.id;
	}

	public String getLastName() {
		return this.lastName;
	}

	public LibraryEntity getLibrary() {
		return this.library;
	}

	public void setBorrowingEntities(List<BorrowingEntity> borrowingEntities) {
		this.borrowingEntities = borrowingEntities;
	}

	public void setFirstNames(List<String> firstNames) {
		this.firstNames = firstNames;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setLibrary(LibraryEntity library) {
		this.library = library;
	}
}
