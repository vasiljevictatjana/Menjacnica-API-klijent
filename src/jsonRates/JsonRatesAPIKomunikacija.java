package jsonRates;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import domen.Valuta;

public class JsonRatesAPIKomunikacija {
	
	public LinkedList<Valuta> vratiIznosKurseva (String[] valute){
		
		LinkedList<Valuta> val = new LinkedList<Valuta>();
		
		try {
			for(int i=0;i<valute.length;i++){
				if(valute[i]==null) 
					break;
				
				String url = "http://jsonrates.com/get/?from="+valute[i]+"&to=RSD&apiKey=jr-ba8999934fc5a7ab64a4872fb4ed9af7";
				String rezultat = sendGet(url); 
				
				Gson gson = new GsonBuilder().create();
				JsonObject kurs = gson.fromJson(rezultat, JsonObject.class);
				
				Valuta valuta = new Valuta();
				valuta.setNaziv(valute[i]);
				valuta.setKurs(Double.parseDouble(kurs.get("rate").getAsString()));
				val.add(valuta);
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return val;
		
	}
	private static String sendGet(String url) throws IOException {
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("GET");
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		
		boolean endReading = false;
		String response = "";
		
		while (!endReading) {
			String s = in.readLine();
			
			if (s != null) {
				response += s;
			} else {
				endReading = true;
			}
		}
		in.close();
 
		return response.toString();
	}
}

