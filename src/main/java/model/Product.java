package model;

import org.json.simple.JSONObject;

import java.util.Objects;

public class Product{
	private final String asin;
	private String title;
	// private final double price;
	private final String imageURL;


	// Sample Product:
// 	{
//   "asin": "0000031852",
//   "title": "Girls Ballet Tutu Zebra Hot Pink",
//   "price": 3.17,
//   "imUrl": "http://ecx.images-amazon.com/images/I/51fAmVkTbyL._SY300_.jpg",
//   "related":
//   {
//     "also_bought": ["B00JHONN1S", "B002BZX8Z6", "B00D2K1M3O", "0000031909", "B00613WDTQ", "B00D0WDS9A", "B00D0GCI8S", "0000031895", "B003AVKOP2", "B003AVEU6G", "B003IEDM9Q", "B002R0FA24", "B00D23MC6W", "B00D2K0PA0", "B00538F5OK", "B00CEV86I6", "B002R0FABA", "B00D10CLVW", "B003AVNY6I", "B002GZGI4E", "B001T9NUFS", "B002R0F7FE", "B00E1YRI4C", "B008UBQZKU", "B00D103F8U", "B007R2RM8W"],
//     "also_viewed": ["B002BZX8Z6", "B00JHONN1S", "B008F0SU0Y", "B00D23MC6W", "B00AFDOPDA", "B00E1YRI4C", "B002GZGI4E", "B003AVKOP2", "B00D9C1WBM", "B00CEV8366", "B00CEUX0D8", "B0079ME3KU", "B00CEUWY8K", "B004FOEEHC", "0000031895", "B00BC4GY9Y", "B003XRKA7A", "B00K18LKX2", "B00EM7KAG6", "B00AMQ17JA", "B00D9C32NI", "B002C3Y6WG", "B00JLL4L5Y", "B003AVNY6I", "B008UBQZKU", "B00D0WDS9A", "B00613WDTQ", "B00538F5OK", "B005C4Y4F6", "B004LHZ1NY", "B00CPHX76U", "B00CEUWUZC", "B00IJVASUE", "B00GOR07RE", "B00J2GTM0W", "B00JHNSNSM", "B003IEDM9Q", "B00CYBU84G", "B008VV8NSQ", "B00CYBULSO", "B00I2UHSZA", "B005F50FXC", "B007LCQI3S", "B00DP68AVW", "B009RXWNSI", "B003AVEU6G", "B00HSOJB9M", "B00EHAGZNA", "B0046W9T8C", "B00E79VW6Q", "B00D10CLVW", "B00B0AVO54", "B00E95LC8Q", "B00GOR92SO", "B007ZN5Y56", "B00AL2569W", "B00B608000", "B008F0SMUC", "B00BFXLZ8M"],
//     "bought_together": ["B002BZX8Z6"]
//   },
//   "salesRank": {"Toys & Games": 211836},
//   "brand": "Coxlures",
//   "categories": [["Sports & Outdoors", "Other Sports", "Dance"]]
// }
	public Product(JSONObject jsonObject){
		asin = (String) jsonObject.get("asin");
		title = (String) jsonObject.get("title");
		// price = (Double) jsonObject.get("price");
		imageURL = (String) jsonObject.get("imUrl");
		if(title == null)
			title = "no title";
	}

    public String getAsin(){
		return asin;
	}

	public String getTitle(){
		return title;
	}

	// public double getPrice(){
	// 	return price;
	// }

	public String getImageURL(){
    	return imageURL;
    }

    public String getHtml(){
    	String output = "";
    	// output = "Item is funny words<br>";
    	output += "Item name: " + title + "<br>";
    	output += "<img src=\"" + imageURL + "\" alt = \"" + title + "\">" + "<br>";
        // output += "Price: " + String.valueOf(price) + "<br>";
        return output;
    }

    @Override
    public boolean equals(final Object theOther)
    {
        boolean result = false;
        if (theOther != null && getClass() == theOther.getClass())
        {
            final Product other = (Product) theOther;
            result = asin.equals(other.asin);
        }
        return result;
    }



	@Override
	public String toString(){
		return "this came from Review.java";
	}	
}