package utility;

import java.util.GregorianCalendar;
import java.util.LinkedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domen.Valuta;

public class ValutaJSonUtility {

	public JsonObject serializeValuta(LinkedList<Valuta> valute, GregorianCalendar datum){
		String dat = ""+datum.get(GregorianCalendar.DAY_OF_MONTH)+"."+(datum.get(GregorianCalendar.MONTH)+1)+"."+ datum.get(GregorianCalendar.YEAR)+".";
		
		JsonObject kurseviJson = new JsonObject();
		kurseviJson.addProperty("datum", dat);
		
		JsonArray jsarray = new JsonArray();
	for(int i=0;i<valute.size();i++){
		JsonObject job = new JsonObject();
		job.addProperty("naziv", valute.get(i).getNaziv());
		job.addProperty("kurs", Double.toString(valute.get(i).getKurs()));
		jsarray.add(job);
	}
	kurseviJson.add("valute", jsarray);
	return kurseviJson;
	}
}
