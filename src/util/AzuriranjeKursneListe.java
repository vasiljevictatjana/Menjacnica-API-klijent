package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import jsonRates.JsonRatesAPIKomunikacija;
import domen.Valuta;

public class AzuriranjeKursneListe {
	
	final String putanjaDoFajlaKursnaLista = "data/kursnaLista.json";
	
	public LinkedList<Valuta> ucitajValute(){
		
		try {
			FileReader reader = new FileReader(putanjaDoFajlaKursnaLista);
			
			Gson gson = new GsonBuilder().create();
			JsonObject jobj = gson.fromJson(reader, JsonObject.class);
			JsonArray jarr = jobj.get("valute").getAsJsonArray();
			
			return parseValute(jarr);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
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
		
		String[] v=new String[valute.size()];
		
		for (int i = 0; i < v.length; i++) {
			v[i]=valute.get(i).getNaziv();
		}
		
		JsonRatesAPIKomunikacija j=new JsonRatesAPIKomunikacija();
		valute=j.vratiIznosKurseva(v);
		upisiValute(valute,new GregorianCalendar());
	}
}
