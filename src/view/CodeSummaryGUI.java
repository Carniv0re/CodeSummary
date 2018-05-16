package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import controller.CodeSummary;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;

/**
 * The main GUI to be used for the project.
 * @author dkaiser
 *
 */
public class CodeSummaryGUI extends JFrame {

	/**
	 * Serialization UID
	 */
	private static final long serialVersionUID = 1L;
	
	private static JPanel contentPane;
	public static CodeSummary cs = new CodeSummary();
	static CodeSummaryGUI frame;
	public File chosenDir;
	private static JTreePanel jtree;
	private static JPanel statisticsPanel;
	private JPanel chartPanel;
	
	private JLabel linesOfCodeNumber;
	private JLabel linesOfCommentNumber;
	private JLabel emptyLinesNumber;
	private JLabel totalLinesNumber;
	private JLabel pagesTotalNumber;
	private JLabel pagesCodeNumber;
	private JLabel pagesCommentNumber;
	
	private JLabel subpackages;
	private JLabel subpackagesNumber = new JLabel();
	private JLabel classes;
	private JLabel classesNumber = new JLabel();
	
	Task task = new Task();
	Thread thread = new Thread(task);

	/**
	 * Create the frame.
	 */
	public CodeSummaryGUI() {
		setTitle("CodeSummary");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 600);
		//setResizable(false);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);
		
		JMenuItem dirOeffnen = new JMenuItem("Verzeichnis \u00F6ffnen");
		menu.add(dirOeffnen);
		
		JMenuItem beenden = new JMenuItem("Beenden");
		menu.add(beenden);
		
		beenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackground(Color.white);
		setContentPane(contentPane);
		
		statisticsPanel = new JPanel();
		statisticsPanel.setBackground(Color.white);
		
		statisticsPanel.setLayout(null);
		
		//Lines of Code
		JLabel linesOfCode = new JLabel("Zeilen Code:");
		linesOfCode.setBounds(30, 55, 82, 14);
		statisticsPanel.add(linesOfCode);
		
		linesOfCodeNumber = new JLabel("0");
		linesOfCodeNumber.setBounds(200, 55, 82, 14);
		statisticsPanel.add(linesOfCodeNumber);
		
		//Lines of Comment
		JLabel linesOfComment = new JLabel("Zeilen Kommentar:");
		linesOfComment.setBounds(30, 110, 111, 14);
		statisticsPanel.add(linesOfComment);
		
		linesOfCommentNumber = new JLabel("0");
		linesOfCommentNumber.setBounds(200, 110, 111, 14);
		statisticsPanel.add(linesOfCommentNumber);
		
		//Empty lines
		JLabel emptyLines = new JLabel("Leere Zeilen:");
		emptyLines.setBounds(30, 165, 82, 14);
		statisticsPanel.add(emptyLines);
		
		emptyLinesNumber = new JLabel("0");
		emptyLinesNumber.setBounds(200, 165, 82, 14);
		statisticsPanel.add(emptyLinesNumber);
		
		//Total lines
		JLabel totalLines = new JLabel("Zeilen insgesamt:");
		totalLines.setBounds(30, 225, 100, 14);
		statisticsPanel.add(totalLines);
		
		totalLinesNumber = new JLabel("0");
		totalLinesNumber.setBounds(200, 225, 50, 14);
		statisticsPanel.add(totalLinesNumber);
		
		//Normpages of code
		JLabel pagesCode = new JLabel("Normseiten Code:");
		pagesCode.setBounds(30, 300, 130, 14);
		statisticsPanel.add(pagesCode);
		
		pagesCodeNumber = new JLabel("0");
		pagesCodeNumber.setBounds(200, 300, 50, 14);
		statisticsPanel.add(pagesCodeNumber);
		
		//Normpages of comment
		JLabel pagesComment = new JLabel("Normseiten Kommentar:");
		pagesComment.setBounds(30, 340, 140, 14);
		statisticsPanel.add(pagesComment);
		
		pagesCommentNumber = new JLabel("0");
		pagesCommentNumber.setBounds(200, 340, 111, 14);
		statisticsPanel.add(pagesCommentNumber);
		
		//Total normpages
		JLabel pagesTotal = new JLabel("Normseiten insgesamt:");
		pagesTotal.setBounds(30, 385, 140, 14);
		statisticsPanel.add(pagesTotal);
		
		pagesTotalNumber = new JLabel("0");
		pagesTotalNumber.setBounds(200, 385, 111, 14);
		statisticsPanel.add(pagesTotalNumber);
		
		
		dirOeffnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);
			    
				int returnVal = chooser.showOpenDialog(contentPane);
				
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					
					if(chooser.getSelectedFile().isDirectory()) {
						if(contentPane.isAncestorOf(jtree) ) {
							contentPane.remove(jtree);
						}
						if(contentPane.isAncestorOf(statisticsPanel)) {
							contentPane.remove(statisticsPanel);
						}
						if(contentPane.isAncestorOf(chartPanel)) {
							contentPane.remove(chartPanel);
						}
						
						chosenDir = chooser.getSelectedFile();
						
						//cs.fetchData(chosenDir);
						task.setDir(chosenDir);
						thread.start();
					}
					else {
						JOptionPane.showMessageDialog(contentPane, "Das war kein gültiges Verzeichnis. Bitte versuchen Sie es erneut.");
						actionPerformed(e);
					}
				}
			}
		});
	}
	
	/**
	 * Changes the Statistics Panel according to the data from the object that the constructor received.
	 * @param code				The amount of lines of code that is stored in the object.
	 * @param comment			The amount of lines of comment that is stored in the object.
	 * @param empty				The amount of empty lines that is stored in the object.
	 * @param charsCode			The amount of characters of code that is stored in the object.
	 * @param charsComment		The amount of characters of comment that is stored in the object.
	 */
	public void setValues(int code, int comment, int empty, int charsCode, int charsComment, Object subpackagesInt, Object classesInt) {
		
		if(contentPane.isAncestorOf(chartPanel)) {
			contentPane.remove(chartPanel);
		}
		
		chartPanel = new PieChartPanel(code, comment, empty, charsCode, charsComment);
		contentPane.add(chartPanel, BorderLayout.EAST);
		
		linesOfCodeNumber.setText(Integer.toString(code));
		linesOfCommentNumber.setText(Integer.toString(comment));
		emptyLinesNumber.setText(Integer.toString(empty));
		totalLinesNumber.setText(Integer.toString(code + comment + empty));
		
		double pagesCode = (charsCode * 1.1) / 1350;
		double doubled = pagesCode * 2;
		double rounded = Math.round(doubled);
		double half = rounded / 2;
		pagesCodeNumber.setText(Double.toString(half));
		
		double pagesComment = (charsComment * 1.1 / 1350);
		doubled = pagesComment * 2;
		rounded = Math.round(doubled);
		half = rounded / 2;
		pagesCommentNumber.setText(Double.toString(half));
		
		double pagesTotal = (charsCode + charsComment) * 1.1 / 1350;
		doubled = pagesTotal * 2;
		rounded = Math.round(doubled);
		half = rounded / 2;
		pagesTotalNumber.setText(Double.toString(half));
		
		if(statisticsPanel.isAncestorOf(subpackagesNumber)) {
			statisticsPanel.remove(subpackages);
			statisticsPanel.remove(subpackagesNumber);
			statisticsPanel.remove(classes);
			statisticsPanel.remove(classesNumber);
		}
		
		if(subpackagesInt != null && classesInt != null) {
			subpackages = new JLabel("Subpackages:");
			subpackages.setBounds(30, 440, 110, 14);
			statisticsPanel.add(subpackages);
			
			subpackagesNumber.setText(Integer.toString((int)subpackagesInt));
			subpackagesNumber.setBounds(200, 440, 50, 14);
			statisticsPanel.add(subpackagesNumber);
			
			classes = new JLabel("Subklassen:");
			classes.setBounds(30, 460, 110, 14);
			statisticsPanel.add(classes);
			
			classesNumber.setText(Integer.toString((int)classesInt));
			classesNumber.setBounds(200, 460, 50, 14);
			statisticsPanel.add(classesNumber);
		}
		
		contentPane.repaint();
		contentPane.revalidate();
	}
	
	static class Task implements Runnable {
		private File file;
		
		@Override
		public void run() {
				cs.fetchData(file);
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						jtree = new JTreePanel(cs.getFullTree());
						
						contentPane.add(jtree, BorderLayout.WEST);
						
						contentPane.add(statisticsPanel, BorderLayout.CENTER);
						
						contentPane.revalidate();
					}
				});
		}
		
		public void setDir(File dir) {
			this.file = dir;
		}
	}
}
