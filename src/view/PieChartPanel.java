package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * Builds the JFreeChart pie charts from the selected PackageSummary or FileSummary object.
 * @author dkaiser
 *
 */
public class PieChartPanel extends JPanel{

	/**
	 * Serialization UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Builds the first chart from empty lines, code lines and comment lines and the second chart from code and comment character amount.
	 * @param codeLines			The lines of code contained in the selected object.
	 * @param commentLines		The lines of comment contained in the selected object.
	 * @param emptyLines		The empty lines contained in the selected object.
	 * @param codeChars			The amount of code characters contained in the selected object.
	 * @param commentChars		The amount of comment characters contained in the selected object.
	 */
	public PieChartPanel(int codeLines, int commentLines, int emptyLines, int codeChars, int commentChars) {
			if(!(codeLines == 0 && commentLines == 0 && emptyLines == 0 && codeChars == 0 && commentChars == 0)) {
				setBackground(Color.white);
				setLayout(new GridBagLayout());
				
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = 0;
				gbc.gridy = 0;
				
				PieDataset datasetLines = createDataset(codeLines, commentLines, emptyLines);
				JFreeChart chartLines = createChart(datasetLines, "Zeilen");
				
				ChartPanel linesPanel = new ChartPanel(chartLines);
				linesPanel.setPreferredSize(new java.awt.Dimension(350, 240));
								
				this.add(linesPanel, gbc);
				
				gbc.gridy = 1;
				
				PieDataset datasetChars = createDatasetPages(codeChars, commentChars);
				JFreeChart chartChars = createChart(datasetChars, "Normseiten");

				ChartPanel charsPanel = new ChartPanel(chartChars);
				charsPanel.setBackground(Color.darkGray);
				charsPanel.setPreferredSize(new java.awt.Dimension(350, 240));
								
				this.add(charsPanel, gbc);
			
				this.repaint();
		}
	}
	
	/**
	 * Creates and returns the dataset to be used by the first pie chart.
	 * @param codeLines			The lines of code contained in the selected object.
	 * @param commentLines		The lines of comment contained in the selected object.
	 * @param emptyLines		The empty lines contained in the selected object.
	 * @return 					The dataset to be used.
	 */
	public PieDataset createDataset(int codeLines, int commentLines, int emptyLines) {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Code", codeLines);
		result.setValue("Kommentar", commentLines);
		result.setValue("Leer", emptyLines);
		return result;
	}
	
	/**
	 * Creates and returns the dataset to be used by the first pie chart.
	 * @param codeChars			The amount of code characters contained in the selected object.
	 * @param commentChars		The amount of comment characters contained in the selected object.
	 * @return 					The dataset to be used.
	 */
	public PieDataset createDatasetPages(int codeChars, int commentChars) {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Code", codeChars);
		result.setValue("Kommentar", commentChars);
		return result;
	}
	
	/**
	 * Creates a pie chart from a dataset with the given title.
	 * @param dataset	The dataset to be used to create the pie chart.
	 * @param title		The title to be used for the chart.
	 * @return			The pie chart.
	 */
	public JFreeChart createChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
		return chart;
	}
}