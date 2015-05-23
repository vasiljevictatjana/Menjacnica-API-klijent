package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import jsonRates.JsonRatesAPIKomunikacija;
import domen.Valuta;

public class AzuriranjeKursneListe {
	
	final String putanjaDoFajlaKursnaLista = "data/kursnaLista.json";


	public LinkedList<Valuta> ucitajValute(){
		
		try {
			FileReader in = new FileReader(putanjaDoFajlaKursnaLista);
			
			Gson gson = new GsonBuilder().create();
			JsonObject jsobject = gson.fromJson(in, JsonObject.class);
			JsonArray jsarray = jsobject.get("valute").getAsJsonArray();
			
			return parseValute(jsarray);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public LinkedList<Valuta> parseValute(JsonArray valuteJsArray) {
		LinkedList<Valuta> valute = new LinkedList<Valuta>();
		
		for (int i = 0; i < valuteJsArray.size(); i++) {
			JsonObject valutaJs = (JsonObject) valuteJsArray.get(i);
			
			Valuta valuta = new Valuta();
			valuta.setNaziv(valutaJs.get("naziv").getAsString());
			valuta.setKurs(valutaJs.get("kurs").getAsDouble());
			
			valute.add(valuta);
		}
		
		return valute;
	}

	public void upisiValute(LinkedList<Valuta> valute, GregorianCalendar datum) {
		
		try {
			FileWriter out =new FileWriter(putanjaDoFajlaKursnaLista);
			
			Gson gson=new GsonBuilder().setPrettyPrinting().create();
			JsonObject jobj = gson.toJson(out, JsonObject.class);
			out.write(gson.toJson());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void azurirajValute(){
		
		LinkedList<Valuta> valute=ucitajValute();
		
		String[] valuta=new String[valute.size()];
		
		for (int i = 0; i < valuta.length; i++) {
			valuta[i]=valute.get(i).getNaziv();
		}
		
		valute = JsonRatesAPIKomunikacija.vratiIznosKurseva(valuta);
		upisiValute(valute, new GregorianCalendar());
	}
}
