package model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;

public class StringProcessor {
	private List<String> reviewsList;
	private String[] toneNames = { "Anger", "Disgust", "Sadness", "Joy", };
	
	
	public StringProcessor(List<String> theList) {
		reviewsList = new ArrayList<String>();
		reviewsList = theList;
	}
	
	public String analyzeText() {
		String result = "";

		  ToneAnalyzer service = new ToneAnalyzer(ToneAnalyzer.VERSION_DATE_2016_05_19);
		    service.setUsernameAndPassword("f0c57609-aff9-4fd6-a757-fdfddb20c09c", "VxWXMgHiNCZi");
		    
		    int numberOfReviews = reviewsList.size();
		    HashMap<String, Double> map = new HashMap<String, Double>();
		    
		    for (String line : reviewsList) {
		    	 // Call the service and get the tone
			    ToneAnalysis tone = service.getTone(line, null).execute();
			    JSONObject documentTone;
				try {
					documentTone = (JSONObject)((JSONObject) new JSONParser().parse(tone.toString())).get("document_tone");
					JSONObject tones = (JSONObject)((JSONArray) documentTone.get("tone_categories")).get(0);
					
					JSONArray currentTones = (JSONArray)tones.get("tones");
					
					for (int i = 0; i < currentTones.size(); i++) {
						JSONObject t = (JSONObject)currentTones.get(i);
						String toneName = ((String)t.get("tone_name")).trim();
						
						double total = (map.containsKey(toneName)) ? map.get(toneName): 0;
						total += ((Double)t.get("score"));
						
						// result = (toneName +" -> " + t.get("score"));
						map.put(toneName, total);
					}
					System.out.println("----------------------------------------------");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    for(Map.Entry<String, Double> entry : map.entrySet()) {
	            map.put(entry.getKey(),  entry.getValue()/numberOfReviews);
	        }

	        result += "<table style=\"width:100%\">";

	        result += "<tr><th>Sentiment</th> <th>Percentage</th></tr>";

		    // System.out.println(map.toString);
		    // result += map.toString() + "<br>";
			for(Map.Entry<String, Double> entry : map.entrySet()) {
				result += "<tr>";
				result += "<td>" + entry.getKey() + "</td>";
				result += "<td>" + ((((int) (entry.getValue() * 10000)) / 100.0f) + " % ") + "</td>";
				// result += entry.getKey() + ": " + entry.getValue() * 100 + " % " + "<br>";
				result += "</tr>";
			}

			result += "</table>";
		    return result;
	  }
}