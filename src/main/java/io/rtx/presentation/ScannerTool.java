package io.rtx.presentation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import io.rtx.data.ISBN;
import io.rtx.data.LibraryType;

public class ScannerTool {

	private static final String DATE_FORMAT = "dd-MM-yyyy";

	private SimpleDateFormat dateFormat;
	private Scanner scanner;

	public ScannerTool() {
		this.scanner = new Scanner(System.in);
		this.dateFormat = new SimpleDateFormat(DATE_FORMAT);
	}

	public Date scanDate() {
		while (true) {
			try {
				String date = scanner.nextLine();
				return dateFormat.parse(date);
			} catch (ParseException e) {
				System.out.println(" - Invalid date format. Expected: " + DATE_FORMAT);
			}
		}
	}

	public LibraryType scanLibraryType() {
		while (true) {
			int type = scanInt("# Library type (1-CENTRAL, 2-SECONDARY): ");
			if (type == 1) {
				return LibraryType.CENTRAL;
			} else if (type == 2) {
				return LibraryType.SECONDARY;
			}
		}
	}

	public int scanInt(String message) {
		message(message);
		
		while (true) {
			try {
				if (!this.scanner.hasNextInt() && this.scanner.hasNextLine()) {
					System.out.println("<! not a 'int' value' !>");
					this.scanner.nextLine();
				}
				int value = this.scanner.nextInt();
				this.scanner.nextLine();
				return value;
			} catch (InputMismatchException e) {
			}
		}
	}

	public ISBN scanIsbn() {
		int group = scanInt("# Book ISBN[1 -Group      ]: ");
		int registrant = scanInt("# Book ISBN[2 -Registrant ]: ");
		int publication = scanInt("# Book ISBN[3 -Publication]: ");
		int checksum = scanInt("# Book ISBN[4 -Checksum   ]: ");

		ISBN isbn = new ISBN();
		isbn.setChecksum(checksum);
		isbn.setGroup(group);
		isbn.setPublication(publication);
		isbn.setRegistrant(registrant);
		return isbn;
	}

	public long scanLong(String message) {
		message(message);
		
		while (true) {
			try {
				if (!this.scanner.hasNextLong() && this.scanner.hasNextLine()) {
					System.out.println("<! not a 'long' value' !>");
					this.scanner.nextLine();
				}
				long value = this.scanner.nextLong();
				this.scanner.nextLine();
				return value;
			} catch (InputMismatchException e) {
			}
		}
	}

	public String scanString(String message) {
		message(message);
		
		return this.scanner.nextLine();
	}

	public List<String> scanStringList(String message, String itemSeparator) {
		message(message);
		
		String input = this.scanner.nextLine();

		return Arrays.stream(input.split(itemSeparator))//
				.map(String::trim)//
				.collect(Collectors.toList());
	}

	public void wait(String message) {
		message(message);
		scanner.nextLine();
	}

	private void message(String message) {
		if (message != null) {
			System.out.println(message);
		}
	}
}
