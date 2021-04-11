package io.rtx.data;

import java.text.MessageFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ISBN {

	@Column(name="isbn_checksum")
	private int checksum;
	
	@Column(name="isbn_group")
	private int group;
	
	@Column(name="isbn_publication")
	private int publication;
	
	@Column(name="isbn_registrant")
	private int registrant;
	
	public int getChecksum() {
		return this.checksum;
	}
	
	public int getGroup() {
		return this.group;
	}
	
	public int getPublication() {
		return this.publication;
	}
	
	public int getRegistrant() {
		return this.registrant;
	}
	
	public void setChecksum(int checksum) {
		this.checksum = checksum;
	}
	
	public void setGroup(int group) {
		this.group = group;
	}
	
	public void setPublication(int publication) {
		this.publication = publication;
	}
	
	public void setRegistrant(int registrant) {
		this.registrant = registrant;
	}
	
	@Override
	public String toString() {
		return MessageFormat.format("{0}-{1}-{2}-{3}", this.group, this.registrant, this.publication, this.checksum);
	}
}