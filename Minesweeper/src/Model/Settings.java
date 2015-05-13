package Model;

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
 * Class that holds all settings for Model.
 * @author Damian Mazurkiewicz
 */
public class Settings {
	
	public final static int SCORE_NUMBER = 10;
	public class Score
	{
		public String playerName_;
		public int time_;
		public Mode mode_;
		public Score(String playerName, Mode mode,  int time)
		{
			playerName_ = playerName;
			time_= time;
			mode_ = mode;
		}
		public Score(Score s)
		{
			playerName_ = s.playerName_;
			time_ = s.time_;
			mode_ = s.mode_;
		}
		
		
	}
	
	
	
	
	public boolean hints_;
	public int rows_;
	public int columns_; 
	public int mines_;
	public int xWindowPos_;
	public int yWindowPos_;
	public int customRows_;
	public int customColumns_;
	public int customMines_;
	public Score[] highScores_;
	public LinkedList<Score> scores_;

	
	
	
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
			
			
			hints_ = parseBool(rootElement.getElementsByTagName("hints").item(0) );
			
			/*Reading the amount of rows in game board.*/
			rows_ = parseInt( rootElement.getElementsByTagName("rows").item(0));
			columns_ = parseInt( rootElement.getElementsByTagName("columns").item(0));
			mines_ = parseInt( rootElement.getElementsByTagName("mines").item(0));
			xWindowPos_ = parseInt( rootElement.getElementsByTagName("xWindowPos").item(0));
			yWindowPos_ = parseInt( rootElement.getElementsByTagName("yWindowPos").item(0));
			
			highScores_ = new Score[SCORE_NUMBER];
			
			Element cursor = (Element) rootElement.getElementsByTagName("highScores").item(0);
			for(int i = 0; i<SCORE_NUMBER; ++i)
				highScores_[i] = parseScore(cursor.getElementsByTagName("place").item(i));
			
				
			
			customRows_ = parseInt( rootElement.getElementsByTagName("customRows").item(0));
			customColumns_ = parseInt( rootElement.getElementsByTagName("customColumns").item(0));
			customMines_ = parseInt( rootElement.getElementsByTagName("customMines").item(0));
			
		
			
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
		try
		{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("settings");
		doc.appendChild(rootElement);
		
		Element hints = doc.createElement("hints");
		hints.setTextContent(new Boolean(hints_).toString());
		rootElement.appendChild(hints);
		
		Element rows = doc.createElement("rows");
		rows.setTextContent(new Integer(rows_).toString());
		rootElement.appendChild(rows);
		Element columns = doc.createElement("columns");
		columns.setTextContent(new Integer(columns_).toString());
		rootElement.appendChild(columns);
		Element mines = doc.createElement("mines");
		mines.setTextContent(new Integer(mines_).toString());
		rootElement.appendChild(mines);
		
		Element xWindowPos = doc.createElement("xWindowPos");
		xWindowPos.setTextContent(new Integer(xWindowPos_).toString());
		rootElement.appendChild(xWindowPos);
		Element yWindowPos = doc.createElement("yWindowPos");
		yWindowPos.setTextContent(new Integer(yWindowPos_).toString());
		rootElement.appendChild(yWindowPos);
		
		Element customRows = doc.createElement("customRows");
		customRows.setTextContent(new Integer(customRows_).toString());
		rootElement.appendChild(customRows);
		Element customColumns = doc.createElement("customColumns");
		customColumns.setTextContent(new Integer(customColumns_).toString());
		rootElement.appendChild(customColumns);
		Element customMines = doc.createElement("customMines");
		customMines.setTextContent(new Integer(customMines_).toString());
		rootElement.appendChild(customMines);
		
		Element highSores = doc.createElement("highScores");
		for(int i = 0; i< highScores_.length; ++i)
		{
			Element place = doc.createElement("place");
			place.setAttribute("playerName", highScores_[i].playerName_);
			place.setAttribute("time", highScores_[i].mode_.toString());
			place.setAttribute("time", new Integer(highScores_[i].time_).toString());
			highSores.appendChild(place);
		}
		rootElement.appendChild(highSores);
		
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(filePath));
 
 
		transformer.transform(source, result);
 
		
		}
		catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } 
		catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }

	}
	public boolean canBeInHighScores(int time, Settings.Mode mode)
	{
	
		for(int i = 0; i<SCORE_NUMBER; ++i)
		{
			if(highScores_[i].time_>time && !highScores_[i].mode_.biggerThan(mode))
				return true;
		}
		return false;
	}
	
	public void addHighscore(String playerName, int time, Settings.Mode mode)
	{
		
		Score[] newScores = new Score[SCORE_NUMBER];
		boolean inserted = false;
		for(int i = 0; i<SCORE_NUMBER; ++i)
		{
			if(highScores_[i].time_>time&& !inserted && !highScores_[i].mode_.biggerThan(mode))
			{
				newScores[i] = new Score(playerName,mode,time);
				inserted = true;
				--i;
			}
			else
			{
				if(inserted == true && i+1 < SCORE_NUMBER)
				{
					newScores[i+1] = new Score(highScores_[i]);
				}
				else
				{
					newScores[i] = new Score(highScores_[i]);
				}
			}
		}
		highScores_ = newScores;
	}

	
	private Integer parseInt(Node n)
	{
		return Integer.parseInt(( (Element)n).getTextContent() );
	}
	private Boolean parseBool(Node n)
	{
		return Boolean.valueOf((n).getTextContent() );
	}
	
	private Score parseScore(Node n)
	{
		Element element = (Element) n;
		return new Score(element.getAttribute("playerName"), Mode.parseMode(element.getAttribute("mode")), Integer.parseInt(element.getAttribute("time")));
	}
	
	private Settings(){	}
	
	public Settings clone()
	{
		Settings retVal = new Settings();
		retVal.hints_ = hints_;
		retVal.rows_ = rows_;
		retVal.columns_ = columns_;
		retVal.mines_ = mines_;
		retVal.xWindowPos_ = xWindowPos_;
		retVal.yWindowPos_ = yWindowPos_;
		retVal.customRows_ = customRows_;
	
		retVal.customColumns_ = customColumns_;
		retVal.customMines_ = customMines_;
		retVal.highScores_ = new Score[SCORE_NUMBER];
		for(int i = 0; i<SCORE_NUMBER; i++)
			retVal.highScores_[i] = new Score(highScores_[i].playerName_,highScores_[i].mode_,highScores_[i].time_);
		
		return retVal;
	}
	
	public enum Mode {
		CUSTOM(0),BEGINNER(1), ADVANCED(2), EXPERT(3); 
		
		private int value_;
		private Mode(int value)
		{
			value_=value;
		}
		public static Mode getMode(int columns, int rows, int mines)
		{
			if(columns == 10 && rows == 10 && mines == 10)
				return BEGINNER;
			if(columns == 16 && rows == 16 && mines == 40)
				return ADVANCED;
			if(columns == 30 && rows == 16 && mines == 99)
				return EXPERT;
			return CUSTOM;
		}
		
		public static Mode parseMode(String s)
		{
			if(s=="BEGINNER")
				return Mode.BEGINNER;
			if(s=="ADVANCED")
				return Mode.ADVANCED;
			if(s=="EXPERT")
				return Mode.EXPERT;
			return Mode.CUSTOM;
		}
		
		public boolean biggerThan(Mode m)
		{
			if(value_>m.value_)
				return true;
			return false;
		}
	}

}
