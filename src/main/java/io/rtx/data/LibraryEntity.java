package io.rtx.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity(name="libraries")
public class LibraryEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private LibraryType type;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="library_id")
	private List<BookEntity> books;
	
	public LibraryEntity() {}
	
	public LibraryEntity(String libraryName, LibraryType type) {
		this.name = libraryName;
		this.type = type;
	}
	
	public List<BookEntity> getBooks() {
		return this.books;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public LibraryType getType() {
		return this.type;
	}
	
	public void setBooks(List<BookEntity> books) {
		this.books = books;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setType(LibraryType type) {
		this.type = type;
	}
}