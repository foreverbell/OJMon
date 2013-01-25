package config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import oj.OJ;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import user.Account;
import user.AccountManager;
import util.Logger;

public class Config {
	private static Config _instance = new Config();
	
	public static Config getInstance() {
		return _instance;
	}
	
	private String getComment() throws UnsupportedEncodingException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(Config.class.getResource("/resource/xml_comment.txt").openStream(), "utf-8"));
		String textLine;
		StringBuffer strBuf = new StringBuffer();
		while ((textLine = reader.readLine()) != null) {
			strBuf.append(textLine);
			strBuf.append('\n');
		}
		reader.close();
		return strBuf.toString();
	}
	
	private void createEmptyConfigFile() {
		try {
			DocumentBuilderFactory XMLFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder XMLBuilder = XMLFactory.newDocumentBuilder();
			Document XMLDoc = XMLBuilder.newDocument();
			
			XMLDoc.appendChild(XMLDoc.createComment(getComment()));
			
			Element rootElement = XMLDoc.createElement("ojmon");
			XMLDoc.appendChild(rootElement);
			
			Element settingElement = XMLDoc.createElement("setting");
			rootElement.appendChild(settingElement);
			
			Element updateIntervalElement = XMLDoc.createElement("interval");
			updateIntervalElement.appendChild(XMLDoc.createTextNode("120000"));
			settingElement.appendChild(updateIntervalElement);

			Element accountsElement = XMLDoc.createElement("accounts");
			rootElement.appendChild(accountsElement);
	
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			try {
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			} catch(Exception e) { }
			transformer.transform(new DOMSource(XMLDoc), new StreamResult(new File("config.xml")));
		} catch (Exception e) { 
			Logger.printlnError(e);
			e.printStackTrace();
		}
	}

	public void initializeConfig() {
		for (;;) {
			try {
				File XMLFile = new File("config.xml");
				DocumentBuilderFactory XMLFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder XMLBuilder = XMLFactory.newDocumentBuilder();
				Document XMLDoc = XMLBuilder.parse(XMLFile);
				
				XMLDoc.getDocumentElement().normalize();
				
				Element rootElement = (Element) XMLDoc.getElementsByTagName("ojmon").item(0);
				Element settingElement = (Element) rootElement.getElementsByTagName("setting").item(0);
				Element accountsElement = (Element) rootElement.getElementsByTagName("accounts").item(0);
				NodeList accountNodeList = accountsElement.getElementsByTagName("account");
				
				AccountManager.getInstance().setUpdateInterval(Integer.parseInt(settingElement.getElementsByTagName("interval").item(0).getTextContent()));
				
				for (int index = 0; index < accountNodeList.getLength(); ++ index) {
					Element accountElement = (Element) accountNodeList.item(index);
					NodeList userNodeList = accountElement.getElementsByTagName("user");
					Account accountToAdd = AccountManager.getInstance().createAccount(accountElement.getAttribute("name"));
					
					for (int index2 = 0; index2 < userNodeList.getLength(); ++ index2) {
						Element userElement = (Element) userNodeList.item(index2);
						OJ OJInstance = OJ.createOJFromXMLElement(userElement);
						String userID = userElement.getAttribute("id");		
						
						if (userID.length() == 0) {
							accountToAdd.createNullUser(OJInstance);
						} else {
							accountToAdd.createUser(OJInstance, userID);
						}
					}
				}
				break;
			} catch (FileNotFoundException e) {
				createEmptyConfigFile();
				// Retry to open XML file
			} catch (Exception e) {
				e.printStackTrace();
				Logger.printlnError(e);
				System.exit(0);
			}
		}
	}
	
	private Config() { }
}
