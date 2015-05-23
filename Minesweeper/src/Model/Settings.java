package Model;

import java.io.File;

import java.io.IOException;

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
	/**Number of best scores in settings object.*/
	public final static int SCORE_NUMBER = 10;
	
	/**Class that represents single registration in the HighScores.*/
	public class Score
	{
		/**Name of the player in the registration.*/
		final public String playerName_;
		/**How fast did player finish the game.*/
		final public int time_;
		/**In which difficulty mode player finished the game.*/
		final public Mode mode_;
		
		/**
		 * @param playerName    Name of the player in the registration.
		 * @param mode 			How fast did player finish the game.
		 * @param time 			In which difficulty mode player finished the game.
		 */
		public Score(String playerName, Mode mode,  int time)
		{
			playerName_ = playerName;
			time_= time;
			mode_ = mode;
		}
		
		/**
		 * @param s 	Original Score object on which base we want create new Score.
		 */
		public Score(Score s)
		{
			playerName_ = s.playerName_;
			time_ = s.time_;
			mode_ = s.mode_;
		}
	}
	/**True if hints are enabled, false otherwise.*/
	public boolean hints_;
	/**Number of rows in the game board.*/
	public int rows_;
	/**Number of columns in the game board.*/
	public int columns_; 
	/**Number of mines on the game board.*/
	public int mines_;
	/**Table of best scores in the game.*/
	public Score[] highScores_;
	
	/**
	 * Constructor that loads game settings from XML file. If there is no file, or file is incorrect, 
	 * it sets default settings.
	 * @param filePath - path of the XML file with saved settings.
	 */
	public  Settings(String filePath) 
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try 
		{
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document =  builder.parse(new File(filePath));
			Node rootNode = document.getElementsByTagName("settings").item(0);
			Element rootElement = (Element) rootNode;
			
			hints_ = parseBool(rootElement.getElementsByTagName("hints").item(0) );
			
			rows_ = parseInt( rootElement.getElementsByTagName("rows").item(0));
			columns_ = parseInt( rootElement.getElementsByTagName("columns").item(0));
			mines_ = parseInt( rootElement.getElementsByTagName("mines").item(0));
		
			highScores_ = new Score[SCORE_NUMBER];
			Element cursor = (Element) rootElement.getElementsByTagName("highScores").item(0);
			for(int i = 0; i<SCORE_NUMBER; ++i)
			{
				highScores_[i] = parseScore(cursor.getElementsByTagName("place").item(i));
			}
		} 
		catch (ParserConfigurationException e) 
		{
			setDefaultSettings();
		} 
		catch (SAXException e) 
		{
			setDefaultSettings();
		} 
		catch (IOException e) 
		{
			setDefaultSettings();
		}
		catch (NumberFormatException e)
		{
			setDefaultSettings();
		}
		catch( NullPointerException e)
		{
			setDefaultSettings();
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
		catch (ParserConfigurationException pce) 
		{
			pce.printStackTrace();
		} 
		catch (TransformerException tfe) 
		{
			tfe.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param time 	How fast did player finished the game.
	 * @param mode 	On which difficulty mode did player play.
	 * @return true if that result is good enough to be in highscores, false otherwise.
	 */
	public boolean canBeInHighScores(int time, Settings.Mode mode)
	{
		for(int i = 0; i<SCORE_NUMBER; ++i)
		{
			if(highScores_[i].time_>time && !highScores_[i].mode_.biggerThan(mode))
				return true;
		}
		return false;
	}
	
	/**
	 * Add new score to the HighScores table.
	 * @param playerName 	Name of the player in the registration.
	 * @param time			How fast did player finish the game.
	 * @param mode			On which difficulty mode did player play.
	 */
	public void addHighscore(String playerName, int time, Settings.Mode mode)
	{
		
		Score[] newScores = new Score[SCORE_NUMBER];
		boolean inserted = false;
		for(int i = 0; i<SCORE_NUMBER; ++i)
		{
			if( (mode.biggerThan(highScores_[i].mode_) && !inserted ) ||
			    (highScores_[i].time_>time&& !inserted && !highScores_[i].mode_.biggerThan(mode)) )
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

	/**
	 * Get an Integer out of Node text content.
	 * @param n from which we wan to get Integer
	 * @return  Integer from Node text content.
	 */
	private Integer parseInt(Node n)
	{
		return Integer.parseInt(( (Element)n).getTextContent() );
	}
	
	/**
	 * Get an Boolean out of Node text content.
	 * @param n Node from which we wan to get Boolean.
	 * @return  Boolean from Node text content.
	 */
	private Boolean parseBool(Node n)
	{
		return Boolean.valueOf((n).getTextContent() );
	}
	
	/**
	 * Get an Score out of Node content.
	 * @param n Node from which we wan to get Score.
	 * @return 	Score from Node content.
	 */
	private Score parseScore(Node n)
	{
		Element element = (Element) n;
		return new Score(element.getAttribute("playerName"),
						Mode.parseMode(element.getAttribute("mode")),
						Integer.parseInt(element.getAttribute("time")));
	}
	
	
	/**
	 * @return Deep copy of the HighScores table.
	 */
	public Score[] getHighScores()
	{
		Score[] retVal = new Score[SCORE_NUMBER];
		for(int i = 0; i<SCORE_NUMBER; i++)
			retVal[i] = new Score(highScores_[i].playerName_,highScores_[i].mode_,highScores_[i].time_);
		return retVal;
	}
	
	/**Mode of a game, it is derivative of the settings of the board.*/
	public enum Mode {
		CUSTOM(0),BEGINNER(1), ADVANCED(2), EXPERT(3); 
		
		/**Value assigned to Mode is used to sort Scores in a HighScores table.*/
		private int value_;
		
		/**@param value "Weight" of a mode.*/
		private Mode(int value)
		{
			value_=value;
		}
		
		/**
		 * @param columns how many columns the board has.
		 * @param rows how many rows the board has.
		 * @param mines how many mines there are on the board.
		 * @return  Game Mode corresponding to given board parameters.
		 */
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
		
		/**
		 * @param s String with the name of game mode.
		 * @return Mode enum corresponding to the value of the string.
		 */
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
		
		/**
		 * @param m Mode with which we want co compare this Mode. 
		 * @return true is this Mode has bigger value that given m Mode.
		 */
		public boolean biggerThan(Mode m)
		{
			if(value_>m.value_)
				return true;
			return false;
		}
	}
	
	/**
	 * Set all values in settings to default.
	 */
	private void setDefaultSettings()
	{
		hints_ = false;
		rows_ = 10;
		columns_ = 10; 
		mines_ = 10;
		highScores_ = new Score[SCORE_NUMBER];
		for(int i = 0; i<SCORE_NUMBER; ++i)
		{
			highScores_[i] = new Score("---",Mode.CUSTOM,999);
		}
	}

}
