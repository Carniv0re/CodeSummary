package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class FileParser {
	
	private CodeParser codeParser = new CodeParser();
	private CommentParser commentParser = new CommentParser();
	
	private FileSummary fs;
	
	/**
	 * Uses the CodeParser and CommentParser in a state pattern to parse a file to a FileSummary object.
	 * @param file	The file to be parsed.
	 * @return		The FileSummary object that represents the file.
	 */
	public FileSummary parse(File file) {
		this.fs = new FileSummary(file);
		ArrayList<String> lines = readFile(file);
		codeParser.parse(lines, 0);		
		return fs;
	}
	
	/**
	 * Reads a file and returns an ArrayList with one line of the file per index.
	 * @param file	The file to be read
	 * @return		An ArrayList containing all lines of the file.
	 */
	public ArrayList<String> readFile(File file) {
		try (Scanner s = new Scanner(file)){
			ArrayList<String> lines = new ArrayList<String>();
			
			while(s.hasNextLine()) {
				lines.add(s.nextLine());
			}
			return lines;
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		
	}
	
	/**
	 * Reads code lines and empty lines from the ArrayList of lines. 
	 **/
	class CodeParser {
		private void parse(ArrayList<String> lines, int index) {
			
			while(index < lines.size()) {
				
				String line = lines.get(index).trim();
				
				//Leerzeile
				if(line.isEmpty()) {
					fs.incLinesEmpty();
					index++;
					continue;
				}
				
				//Kommentarzeile
				if(isComment(line)) {
					commentParser.parse(lines, index);
					return;
				}
				
				//Codezeile
				fs.incLinesOfCode();
				fs.addCharsCode(line.length());
				index++;
			}
		}
		
		private boolean isComment(String line) {
			if(line.startsWith("//")) return true;
			if(line.startsWith("/*")) return true;
			if(line.startsWith("/**")) return true;
			return false;
		}
	}
	
	/**
	 * Reads comment lines and empty lines from the ArrayList of lines. 
	 **/
	class CommentParser {
		
		
		private void parse(ArrayList<String> lines, int index) {
			
			while(index < lines.size()) {
				
				String line = lines.get(index).trim();
				
				//Leerzeile
				if(line.isEmpty()) {
					fs.incLinesEmpty();
					index++;
					continue;
				}
				
				//Einzeiliger Kommentar
				if(isSingleLineComment(line)) {
					fs.incLinesOfComment();
					fs.addCharsComment(line.length());
					index++;
					codeParser.parse(lines, index);
					return;
				}
				
				//Zeile beendet den Kommentar
				if(line.contains("*/")) {
					fs.incLinesOfComment();
					fs.addCharsComment(line.length());
					index++;
					codeParser.parse(lines, index);
					return;
				}
				
				//Normale Kommentarzeile
				fs.incLinesOfComment();
				fs.addCharsComment(line.length());
				index++;
			}
		}
		
		private boolean isSingleLineComment(String line) {
			if(line.startsWith("//")) return true;
			return false;
		}
	}
}
