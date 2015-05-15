package View;

import java.io.File;



import java.io.IOException;








import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Class that holds all settings for View.
 * @author Damian Mazurkiewicz
 */
public class ViewSettings {
	
	public int xWindowPosition_;
	public int yWindowPosition_;
	public int customColumns_; 
	public int customRows_;
	public int customMines_;
	public String lastGivenPlayerName_; 
	/**
	 * Constructor that loads game settings from XML file.  
	 * @param filePath - path of the XML file with saved settings.
	 */
	public  ViewSettings(String filePath) 
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try 
		{
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document =  builder.parse(new File(filePath));
			Node rootNode = document.getElementsByTagName("guiSettings").item(0);
			Element rootElement = (Element) rootNode;
			
			xWindowPosition_ = parseInt( rootElement.getElementsByTagName("xWindowPosition").item(0));
			yWindowPosition_ = parseInt( rootElement.getElementsByTagName("yWindowPosition").item(0));
			
			/*Reading the amount of rows in game board.*/
			customRows_ = parseInt( rootElement.getElementsByTagName("customRows").item(0));
			customColumns_ = parseInt( rootElement.getElementsByTagName("customColumns").item(0));
			customMines_ = parseInt( rootElement.getElementsByTagName("customMines").item(0));
		
			lastGivenPlayerName_ = rootElement.getElementsByTagName("lastGivenPlayerName").item(0).toString();

		} 
		catch (ParserConfigurationException e) 
		{
			e.printStackTrace();
		} 
		catch (SAXException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that saves settings in this object to specified XML file.
	 * @param filePath - path of the XML file.
	 */
	public void saveSettings(String filePath)
	{
		try
		{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("guiSettings");
		doc.appendChild(rootElement);
		
		Element xWindowPosition = doc.createElement("xWindowPosition");
		xWindowPosition.setTextContent(new Integer(xWindowPosition_).toString());
		rootElement.appendChild(xWindowPosition);
		Element yWindowPosition = doc.createElement("yWindowPosition");
		yWindowPosition.setTextContent(new Integer(yWindowPosition_).toString());
		rootElement.appendChild(yWindowPosition);
		
		Element customRows = doc.createElement("customRows");
		customRows.setTextContent(new Integer(customRows_).toString());
		rootElement.appendChild(customRows);
		Element customColumns = doc.createElement("customColumns");
		customColumns.setTextContent(new Integer(customColumns_).toString());
		rootElement.appendChild(customColumns);
		Element customMines = doc.createElement("customMines");
		customMines.setTextContent(new Integer(customMines_).toString());
		rootElement.appendChild(customMines);
		
		Element lastGivenPlayerName = doc.createElement("lastGivenPlayerName");
		lastGivenPlayerName.setTextContent(lastGivenPlayerName_);
		rootElement.appendChild(lastGivenPlayerName);
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(filePath));
 
		transformer.transform(source, result);
		}
		catch (ParserConfigurationException pce) 
		{
			pce.printStackTrace();
		} 
		catch (TransformerException tfe) 
		{
			tfe.printStackTrace();
	    }

	}
	private Integer parseInt(Node n)
	{
		return Integer.parseInt(( (Element)n).getTextContent() );
	}
}
