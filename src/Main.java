import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Vue.ParametrageSimulation;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		new ParametrageSimulation();
	}
}
