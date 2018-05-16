package view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import controller.CodeSummary;
import model.FileSummary;
import model.PackageSummary;

/**
 * Builds the JTree from the selected directory with the selected directory as root.
 * @author dkaiser
 *
 */
public class JTreePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTree tree;
	DefaultMutableTreeNode rootNode;
	CodeSummary cs = new CodeSummary();
	
	/**
	 * Constructor
	 * @param root	The PackageSummary object that represents the root directory.
	 */
	public JTreePanel(PackageSummary root) {
		buildTree(root);
	}
	
	/**
	 * Iterates through the PackageSummaries and their children, building the JTree.
	 * @param root	The PackageSummary object that represents the root directory.
	 */
	public void buildTree(PackageSummary root) {
		setBackground(Color.white);
		removeAll();
		rootNode = new DefaultMutableTreeNode(root.getName());
		rootNode.setUserObject(root);
		
		setModel(root.getChildPackageSummaries(), rootNode);
		
		for(FileSummary rootFile : root.getFiles()) {
			DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(rootFile.getName());
			fileNode.setUserObject(rootFile);
			rootNode.add(fileNode);
		}
		
		tree = new JTree(rootNode);
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				
				if(selectedNode == null) return;
				
				if(selectedNode.toString().contains(".java")) {
					FileSummary file = (FileSummary) selectedNode.getUserObject();
					setValuesFromFile(file);
				}
				
				else {
					PackageSummary thePackage = (PackageSummary) selectedNode.getUserObject();
					setValuesFromPackage(thePackage);
				}
			}
			
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(200, 530));
		scrollPane.setViewportView(tree);
		this.add(scrollPane);
	}
	
	/**
	 * Adds a child node to its parent and sets the UserObject that the node is pointing at.
	 * @param packages	The ArrayList of subpackages that the parent object has.
	 * @param parent	The parent node that the child nodes are added to.
	 */
	public void setModel(ArrayList<PackageSummary> packages, DefaultMutableTreeNode parent) {
		
		for(PackageSummary myPackage : packages) {
			DefaultMutableTreeNode tempNode = new DefaultMutableTreeNode(myPackage.getName());
			tempNode.setUserObject(myPackage);
			parent.add(tempNode);

			for(FileSummary childFile : myPackage.getFiles()) {
				DefaultMutableTreeNode file = new DefaultMutableTreeNode(childFile.getName());
				file.setUserObject(childFile);
				tempNode.add(file);
			}
			if(!myPackage.getChildPackageSummaries().isEmpty()) {
				setModel(myPackage.getChildPackageSummaries(), tempNode);
			}
			parent.add(tempNode);
		}
	}
	
	/**
	 * Changes the main GUI's statistics panel according to the data stored behind the selectted node.
	 * @param file	The selected FileSummary object.
	 */
	public void setValuesFromFile(FileSummary file) {
		cs.setValuesFromFile(file);
	}
	
	/**
	 * Changes the main GUI's statistics panel according to the data stored behind the selectted node.
	 * @param file	The selected PackageSummary object.
	 */
	public void setValuesFromPackage(PackageSummary thePackage) {
		cs.setValuesFromPackage(thePackage);
	}
}
