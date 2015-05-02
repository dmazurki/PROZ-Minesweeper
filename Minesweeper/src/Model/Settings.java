package Model;

import java.io.File;



import java.io.IOException;



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Class that holds all settings for Model.
 * @author Damian Mazurkiewicz
 */
public class Settings {
	public enum Mode
	{
		BEGINNEER, ADVANCED, EXPERT, CUSTOM
	}
	public class Score
	{
		String playerName_;
		Mode mode_;
		int time_;
	}
	private Mode mode_;
	private int rows_;
	private int columns_; 
	private int mines_;
	private Score highScores_;
	
	private int xWindowPos;
	private int yWindowPos;
	
	/**
	 * Constructor that loads game settings from XML file.  
	 * @param filePath - path of the XML file with saved settings.
	 */
	Settings(String filePath) 
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document =  builder.parse(new File("settings.xml"));
			Node rootNode = document.getElementsByTagName("settings").item(0);
			Element rootElement = (Element) rootNode;
			
			/*Reading game mode  ( difficulty level )*/
			Element cursor = (Element) rootElement.getElementsByTagName("mode").item(0);
			String value = cursor.getTextContent();
			System.out.println(value);
			
			
			
			
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Method that saves settings in this object to specified XML file.
	 * @param filePath - path of the XML file.
	 */
	public void saveSettings(String filePath)
	{
		
	}
	
	
	

}
