package io.rtx.data;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name="books")
public class BookEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Embedded
	private ISBN isbn;
	
	private String title;
	
	@OneToOne
	@JoinColumn(name="library_id")
	private LibraryEntity library;
	
	public BookEntity() {}
	
	public BookEntity(LibraryEntity library, String title, ISBN isbn) {
		this.title   = title;
		this.library = library;
		this.isbn    = isbn;
	}
	
	public Long getId() {
		return id;
	}
	
	public ISBN getIsbn() {
		return isbn;
	}
	
	public String getTitle() {
		return title;
	}
	
	public LibraryEntity getLibrary() {
		return this.library;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setIsbn(ISBN isbn) {
		this.isbn = isbn;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setLibrary(LibraryEntity library) {
		this.library = library;
	}
}