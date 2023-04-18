/**
 * 
 */
package helper;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


/**
 * @author Virgilio
 *
 */
@SuppressWarnings("serial")
public class DrawGraphPvaluesZK extends ApplicationFrame {
	public static void main(String[] args)  {
		
		
		double[] py= {1.0 ,2.0, 3.0, 4.0 };
		double[] px= {1.0 ,2.0, 3.0, 4.0 };
		disegna(px,py);		

	}
	
	public static void disegna(double[] px,double[] py){
		final DrawGraphPvaluesZK demo = new DrawGraphPvaluesZK("P values",px,py);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
	}
	
	  public DrawGraphPvaluesZK(final String title, double[] px, double[] py ) {

	        super(title);
	        final XYSeries series = new XYSeries("Random Data");
	        for (int i = 0; i < py.length; i++) {
	        	series.add(px[i], py[i]);
			}
	       
	        final XYSeriesCollection data = new XYSeriesCollection(series);
	        final JFreeChart chart = ChartFactory.createXYLineChart(
	            "P Values",
	            "X", 
	            "Y", 
	            data,
	            PlotOrientation.VERTICAL,
	            true,
	            true,
	            false
	        );

	        final ChartPanel chartPanel = new ChartPanel(chart);
	        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
	        setContentPane(chartPanel);

	    }//fine metodo
	
}
