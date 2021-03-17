import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import models.Country;
import models.Religion;

public class TpMondial {

	private Map<String,Religion> religions;
	private DocumentBuilderFactory documentBuilderFactory;
	private DocumentBuilder documentBuilder;
	public TpMondial()
	{
		solution();
	}
	private void solution()
	{
		religions=new HashMap<String, Religion>();
		try {
			documentBuilderFactory=DocumentBuilderFactory.newInstance();
			documentBuilder=documentBuilderFactory.newDocumentBuilder();
			Document document=documentBuilder.parse(
					new File("database/mondial-3.0.xml"));
			NodeList liste=document.getElementsByTagName("country");
			//System.out.println(liste.getLength());
			for (int i = 0; i < liste.getLength(); 
					i++) {
				Element element= (Element)liste.item(i);
				NodeList rs=element.getElementsByTagName("religions");
				float max=-1;
				String maxR="";
				for (int j = 0; j < rs.getLength(); 
						j++) 
				{
					
					float percentage =Float.parseFloat(((Element)rs.item(j)).getAttribute("percentage"));
					if(max<percentage)
					{
						max=percentage;
						maxR=rs.item(j).getTextContent().trim().toLowerCase();
					}
					Religion religion=new Religion();
					religion.setName(rs.item(j).getTextContent().trim().toLowerCase());
					if(religions.get(religion.getName())==null)
					religions.put(religion.getName(), religion);
				}
				if(religions.get(maxR)!=null)
				religions.get(maxR).getCountries().add(new Country(element.getAttribute("name")));
				
			}
			System.out.println(religions.size());
			for(Religion r:religions.values())
				System.out.println(r);
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {

		new TpMondial();
	}

}
