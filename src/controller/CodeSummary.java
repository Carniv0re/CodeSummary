package controller;

import java.io.File;
import javax.swing.JTree;

import model.FileParser;
import model.FileSummary;
import model.PackageSummary;
import view.CodeSummaryGUI;

/**
 * The controller that builds the GUI and changes it according to the selected fields.
 * @author dkaiser
 *
 */
public class CodeSummary {

	private PackageSummary rootPackage;
	public JTree tree;
	static CodeSummaryGUI gui;
	
	/**
	 * Fetches all subdirectories and Java files in a directory.
	 * @param dir	The directory to be iterated through.
	 */
	public void fetchData(File dir) {
		
		if(! dir.isDirectory()) {
			return;
		}
		
		rootPackage = new PackageSummary(dir);
		
		for(File f : dir.listFiles()) {
			appendChild(rootPackage, f);
		}
	}
	
	/**
	 * Adds a child PackageSummary or a FileSummary to a PackageSummary.
	 * @param parent	The PackageSummary to which a child should be added.
	 * @param child		The child to be file to be identified as a file or a directory and added to the parent accordingly.
	 */
	private void appendChild(PackageSummary parent, File child) {
		
		if(! child.isDirectory()) {
			
			if(child.getName().contains(".java")) {
				FileSummary fs = parseFile(child);
				parent.addFileSummary(fs);
				parent.addTotalLinesOfCode(fs.getLinesOfCode());
				parent.addTotalLinesOfComment(fs.getLinesOfComment());
				parent.addTotalLinesEmpty(fs.getLinesEmpty());
				parent.addTotalCharsCode(fs.getCharsCode());
				parent.addTotalCharsComment(fs.getCharsComment());
				
				parent.incTotalClasses(1);
			}
			return;
		}
		
		PackageSummary pchild = new PackageSummary(child);
		parent.addChildPackageSummary(pchild);
		
		for(File f : child.listFiles()) {
			appendChild(pchild, f);
		}
		
		parent.incTotalSubpackages(1);
		parent.incTotalSubpackages(pchild.getTotalSubpackages());
		parent.incTotalClasses(pchild.getTotalClasses());
		
		parent.addTotalLinesOfCode(pchild.getTotalLinesOfCode());
		parent.addTotalLinesOfComment(pchild.getTotalLinesOfComment());
		parent.addTotalLinesEmpty(pchild.getTotalLinesEmpty());
		parent.addTotalCharsCode(pchild.getTotalCharsCode());
		parent.addTotalCharsComment(pchild.getTotalCharsComment());
	}
	
	/**
	 * Uses the FileParser to create a FileSummary object from a file.
	 * @param file	The file to be parsed.
	 * @return		The according FileSummary object.
	 */
	public static FileSummary parseFile(File file) {
		FileParser parser = new FileParser();
		return parser.parse(file);
	}
	
	/**
	 * Takes a FileSummary object and changes the statistics Panel according to the statistics in the object.
	 * @param file	The FileSummary object to be displayed on the GUI.
	 */
	public void setValuesFromFile(FileSummary file) {
		gui.setValues(file.getLinesOfCode(), file.getLinesOfComment(), file.getLinesEmpty(), file.getCharsCode(), file.getCharsComment(), null, null);
	}
	
	/**
	 * Takes a PackageSummary object and changes the statistics Panel according to the statistics in the object.
	 * @param file	The PackageSummary object to be displayed on the GUI.
	 */
	public void setValuesFromPackage(PackageSummary thePackage) {
		gui.setValues(thePackage.getTotalLinesOfCode(), thePackage.getTotalLinesOfComment(), thePackage.getTotalLinesEmpty(), 
					  thePackage.getTotalCharsCode(), thePackage.getTotalCharsComment(), thePackage.getTotalSubpackages(),
					  thePackage.getTotalClasses());
	}
	
	/**
	 * Returns the root object which contains the whole directory's representing objects.
	 * @return	The root object.
	 */
	public PackageSummary getFullTree() {
		return rootPackage;
	}
	
	/**
	 * Initializes the GUI.
	 * @param args	Main arguments
	 */
	public static void main(String[] args) {
		gui = new CodeSummaryGUI();
		gui.setVisible(true);		
	}
}
