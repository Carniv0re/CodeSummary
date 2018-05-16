package model;

import java.io.File;

/**
 * The FileSummary model class.
 * @author dkaiser
 *
 */
public class FileSummary {

	private int linesOfCode = 0;
	private int linesOfComment = 0;
	private int linesEmpty = 0;
	
	private int charsCode = 0;
	private int charsComment = 0;
	
	File file;
	
	/**
	 * Sets the file to be used for the object.
	 * @param file	The file to be used
	 */
	public FileSummary(File file) {
		this.file = file;
	}

	
	/**
	 * Increases the amount of code lines by 1.
	 */
	public void incLinesOfCode() {
		linesOfCode++;
	}
	
	/**
	 * Increases the amount of comment lines by 1.
	 */
	public void incLinesOfComment() {
		linesOfComment++;
	}
	
	/**
	 * Increases the amount of empty lines by 1.
	 */
	public void incLinesEmpty() {
		linesEmpty++;
	}
	
	/**
	 * Adds a certain amount of code characters to the existing amount.
	 * @param code	The amount to add.
	 */
	public void addCharsCode(int code) {
		this.charsCode += code;
	}
	
	/**
	 * Adds a certain amount of comment characters to the existing amount.
	 * @param comment	The amount to add.
	 */
	public void addCharsComment(int comment) {
		this.charsComment += comment;
	}
	
	/**
	 * Returns the amount of code lines in a file.
	 * @return The amount of code lines.
	 */
	public int getLinesOfCode() {
		return linesOfCode;
	}
	
	/**
	 * Returns the amount of comment lines in a file.
	 * @return	The amount of comment lines.
	 */
	public int getLinesOfComment() {
		return linesOfComment;
	}
	
	/**
	 * Returns the amount of empty lines in a file.
	 * @return	The amount of empty lines.
	 */
	public int getLinesEmpty() {
		return linesEmpty;
	}
	
	/**
	 * Returns the total amount of lines in a file.
	 * @return	The total amount of lines.
	 */
	public int getTotalLines() {
		return linesOfCode + linesOfComment + linesEmpty;
	}
	
	/**
	 * Returns the amount of code characters in a file.
	 * @return	The amount of characters in a file that are part of the code.
	 */
	public int getCharsCode() {
		return charsCode;
	}
	
	/**
	 * Gibt die Anzahl der Kommentar-Zeichen zurück.
	 * @return	The amount of characters in a file that are part of a comment.
	 */
	public int getCharsComment() {
		return charsComment;
	}
	
	/**
	 * Returns the name.
	 * @return	The name of the FileSummary object
	 */
	public String getName() {
		return file.getName();
	}
	
	/**
	 * Returns the name of the object so that the JTree can display it correctly.
	 * @return	The name of the object that is in a JTree.
	 */
	@Override
	public String toString() {
		return this.getName();
	}
}
