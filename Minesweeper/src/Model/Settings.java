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
	public class Score
	{
		public String playerName_;
		public int time_;
		public Score(String playerName, int time)
		{
			playerName_ = playerName;
			time_= time;
		}
		public Score(Score s)
		{
			playerName_ = s.playerName_;
			time_ = s.time_;
		}
	}
	public final static int SCORES_NUMBER = 5;
	
	
	
	public boolean hints_;
	public int rows_;
	public int columns_; 
	public int mines_;
	public int xWindowPos_;
	public int yWindowPos_;
	public int customRows_;
	public int customColumns_;
	public int customMines_;
	public Score[] beginnerHighScores_;
	public Score[] advancedHighScores_;
	public Score[] expertHighScores_;
	
	
	
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
			
			beginnerHighScores_ = new Score[5];
			advancedHighScores_ = new Score[5];
			expertHighScores_ = new Score[5];
			
			Element beginnerCursor = (Element) rootElement.getElementsByTagName("beginnerHighScores").item(0);
			Element advancedCursor = (Element) rootElement.getElementsByTagName("advancedHighScores").item(0);
			Element expertCursor = (Element) rootElement.getElementsByTagName("expertHighScores").item(0);
			for(int i = 0; i<5; ++i)
			{
				beginnerHighScores_[i] = parseScore(beginnerCursor.getElementsByTagName("place").item(i));
				advancedHighScores_[i] = parseScore(advancedCursor.getElementsByTagName("place").item(i));
				expertHighScores_[i] = parseScore(expertCursor.getElementsByTagName("place").item(i));
				
			}
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
		
	}
	public boolean canBeInHighScores(int time, Settings.Mode mode)
	{
		Score[] scores;
		switch (mode)
		{
			case BEGINNER: scores = beginnerHighScores_; break;
			case ADVANCED: scores = advancedHighScores_; break;
			case EXPERT: scores = expertHighScores_; break;
			default: return false;
		}
		for(int i = 0; i<SCORES_NUMBER; ++i)
		{
			if(scores[i].time_>time)
				return true;
		}
		return false;
	}
	
	public void addHighscore(String playerName, int time, Settings.Mode mode)
	{
		Score[] scores;
		switch (mode)
		{
			case BEGINNER: scores = beginnerHighScores_; break;
			case ADVANCED: scores = advancedHighScores_; break;
			case EXPERT: scores = expertHighScores_; break;
			default: return;
		}
		Score[] newScores = new Score[SCORES_NUMBER];
		boolean inserted = false;
		for(int i = 0; i<SCORES_NUMBER; ++i)
		{
			if(scores[i].time_>time&&!inserted)
			{
				newScores[i] = new Score(playerName,time);
				inserted = true;
				--i;
			}
			else
			{
				if(inserted == true && i+1 < SCORES_NUMBER)
				{
					newScores[i+1] = new Score(scores[i]);
				}
				else
				{
					newScores[i] = new Score(scores[i]);
				}
			}
		}
		switch (mode)
		{
			case BEGINNER: beginnerHighScores_ = newScores; break;
			case ADVANCED: advancedHighScores_ = newScores; break;
			case EXPERT: expertHighScores_ = newScores; break;
			default: return;
		}
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
		return new Score(element.getAttribute("playerName"), Integer.parseInt(element.getAttribute("time")));
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
		retVal.beginnerHighScores_ = new Score[5];
		retVal.advancedHighScores_ = new Score[5];
		retVal.expertHighScores_ = new Score[5];
		for(int i = 0; i<5; i++)
		{
			retVal.beginnerHighScores_[i] = new Score(beginnerHighScores_[i].playerName_,beginnerHighScores_[i].time_);
			retVal.advancedHighScores_[i] = new Score(advancedHighScores_[i].playerName_,advancedHighScores_[i].time_);
			retVal.expertHighScores_[i] = new Score(expertHighScores_[i].playerName_,expertHighScores_[i].time_);
		}
		
		return retVal;
	}
	
	public enum Mode {
		BEGINNER, ADVANCED, EXPERT, CUSTOM;
		
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
	}

}
