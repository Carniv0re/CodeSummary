package model;

import java.io.File;
import java.util.ArrayList;

/**
 * The PackageSummary model class.
 * @author dkaiser
 *
 */
public class PackageSummary {

	String dirName;
	private ArrayList<FileSummary> fileSummaries = new ArrayList<FileSummary>();
	private ArrayList<PackageSummary> childPackageSummaries = new ArrayList<PackageSummary>();
	
	private int totalLinesOfCode = 0;
	private int totalLinesOfComment = 0;
	private int totalLinesEmpty = 0;
	
	private int totalCharsCode = 0;
	private int totalCharsComment = 0;
	
	private int totalSubpackages;
	private int totalClasses;
	
	/**
	 * Sets the name of the directory that the package represents.
	 * @param dir	The directory to represent.
	 */
	public PackageSummary(File dir) {
		this.dirName = dir.getName();
	}
	
	/**
	 * Returns the name of the PackageSummary.
	 * @return	The name of the PackageSummary.
	 */
	public String getName() {
		return dirName;
	}
	
	/**
	 * Returns the amount of lines of all files in the packages and in all subpackages.
	 * @return	The total amount of lines.
	 */
	public int getTotalLines() {
		return totalLinesOfCode + totalLinesOfComment + totalLinesEmpty;
	}
	
	/**
	 * Returns the amount of lines of code of all files in the packages and in all subpackages.
	 * @return	The total amount of code lines.
	 */
	public int getTotalLinesOfCode() {
		return totalLinesOfCode;
	}
	
	/**
	 * Adds an amount of lines of code to the existing amount.
	 * @param totalLinesOfCode	The amount of lines of code to add.
	 */
	public void addTotalLinesOfCode(int totalLinesOfCode) {
		this.totalLinesOfCode += totalLinesOfCode;
	}

	/**
	 * Returns the amount of lines of comment of all files in the packages and in all subpackages.
	 * @return	The total amount of comment lines.
	 */
	public int getTotalLinesOfComment() {
		return totalLinesOfComment;
	}

	/**
	 * Adds an amount of lines of comment to the existing amount.
	 * @param totalLinesOfCode	The amount of lines of comment to add.
	 */
	public void addTotalLinesOfComment(int totalLinesOfComment) {
		this.totalLinesOfComment += totalLinesOfComment;
	}

	/**
	 * Returns the amount of empty lines of all files in the packages and in all subpackages.
	 * @return	The total amount of empty lines.
	 */
	public int getTotalLinesEmpty() {
		return totalLinesEmpty;
	}

	/**
	 * Adds an amount of empty lines to the existing amount.
	 * @param totalLinesOfCode	The amount of empty lines to add.
	 */
	public void addTotalLinesEmpty(int totalLinesEmpty) {
		this.totalLinesEmpty += totalLinesEmpty;
	}
	

	/**
	 * Returns the amount of code characters of all files in the packages and in all subpackages.
	 * @return	The total amount of code characters.
	 */
	public int getTotalCharsCode() {
		return totalCharsCode;
	}
	
	/**
	 * Adds an amount of characters of code to the existing amount.
	 * @param totalLinesOfCode	The amount of characters of code to add.
	 */
	public void addTotalCharsCode(int chars) {
		this.totalCharsCode += chars;
	}
	
	/**
	 * Returns the amount of comment characters of all files in the packages and in all subpackages.
	 * @return	The total amount of code characters.
	 */
	public int getTotalCharsComment() {
		return totalCharsComment;
	}
	
	/**
	 * Adds an amount of characters of comment to the existing amount.
	 * @param totalLinesOfCode	The amount of characters of comment to add.
	 */
	public void addTotalCharsComment(int comment) {
		this.totalCharsComment += comment;
	}
	
	/**
	 * Adds a FileSummary to the ArrayList of FileSummaries that are in this package.
	 * @param totalLinesOfCode	The FileSummary to add.
	 */
	public void addFileSummary(FileSummary fs) {
		fileSummaries.add(fs);
	}
	
	/**
	 * Adds a PackageSummary to the ArrayList of subpackages that are in this package.
	 * @param totalLinesOfCode	The PackageSummary to add.
	 */
	public void addChildPackageSummary(PackageSummary ps) {
		childPackageSummaries.add(ps);
	}
	
	/**
	 * Increases the total amount of subpackages by 1.
	 */
	public void incTotalSubpackages(int packages) {
		totalSubpackages += packages;
	}
	
	/**
	 * Returns the total amount of subpackages.
	 * @return	The amount of subpackages.
	 */
	public int getTotalSubpackages() {
		return totalSubpackages;
	}
	
	/**
	 * Increases the total amount of classes in a PackageSummary.
	 */
	public void incTotalClasses(int classes) {
		totalClasses += classes;
	}
	
	/**
	 * Returns the total amount of classes in a PackageSummary.
	 * @return	The amount of classes.
	 */
	public int getTotalClasses() {
		return totalClasses;
	}
	
	/**
	 * Returns the ArrayList of all files in this package.
	 * @return	The ArrayList of files.
	 */
	public ArrayList<FileSummary> getFiles() {
		return fileSummaries;
	}
	
	/**
	 * Returns the ArrayList of all subpackages in this package.
	 * @return	The ArrayList of subpackages.
	 */
	public ArrayList<PackageSummary> getChildPackageSummaries() {
		return childPackageSummaries;
	}
	
	/**
	 * Returns the name of the object so that the JTree can display it correctly.
	 * @return	The name of the object that is in a JTree.
	 */
	public String toString() {
		return this.getName();
	}
}
