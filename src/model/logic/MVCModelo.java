package model.logic;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import model.data_structures.LinearProbingHashST;
import model.data_structures.Queue;
import model.data_structures.SeparateChainingHashST;

/**
 * Definicion del modelo del mundo
 *
 */
public class MVCModelo {
	/**
	 * Atributos del modelo del mundo
	 */
	private LinearProbingHashST<String, Viaje> datosHashLinear;
	private SeparateChainingHashST<String, Viaje> datosHashSeparate;

	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public MVCModelo()
	{
		datosHashLinear= new LinearProbingHashST<>();
		datosHashSeparate= new SeparateChainingHashST<>();
	}

	/**
	 * Carga las dos Tablas de hash
	 * @throws IOException 
	 */
	public void cargar() throws IOException
	{
			int i=1;
			String primera="";
			String key="";
			while(i<5)
			{
				CSVReader reader=null;
				reader = new CSVReader(new FileReader("./data/bogota-cadastral-2018-"+i+"-WeeklyAggregate.csv"));
				int contador=0;
				Viaje val=null;
				String [] nextLine=reader.readNext();
				while ((nextLine = reader.readNext()) != null) {
					// nextLine[] is an array of values from the line
					nextLine=reader.readNext();
					val= new Viaje(nextLine[0],nextLine[1],nextLine[2],nextLine[3],nextLine[4]);
					key=i+nextLine[0]+nextLine[1];
					datosHashLinear.put(key, val);
					datosHashSeparate.put(key, val);
					if(datosHashLinear.size()==1)
					{
						primera=key;
					}

				}
				i++;
			}
			System.out.println("Numero actual de elementos es" + datosHashLinear.size() + "\n---------");	
			System.out.println("Primer viaje: zona de origen " + datosHashLinear.get(primera).getSourceid()+", zona de destino "+datosHashLinear.get(primera).getDstid()+", tiempor promedio "+datosHashLinear.get(primera).getMean_travel_time() + "\n---------");	
			System.out.println("Primer viaje: zona de origen " + datosHashLinear.get(key).getSourceid()+", zona de destino "+datosHashLinear.get(key).getDstid()+", tiempor promedio "+datosHashLinear.get(key).getMean_travel_time() + "\n---------");

		}
	

	/**
	 * Buscar tiempos promedios por trimestre, zona de origen y zona de destino
	 * @param trimestre trimestre del ano por el que se desea buscar
	 * @param zonaOrigen zona de origen del viaje
	 * @param zonaDestino zona de destino del viaje
	 * post: muestra los viajes encontrados por dia de la semana
	 */
	public void buscarTiemposLinear(String trimestre, String zonaOrigen, String zonaDestino) {
		// TODO Auto-generated method stub
		ArrayList<Viaje> aux=new ArrayList<Viaje>();
		for(int i=0; i<datosHashLinear.size();i++)
		{
			if(datosHashLinear.get(trimestre+zonaOrigen+zonaDestino)!=null){
				aux.add(datosHashLinear.get(trimestre+zonaOrigen+zonaDestino));
			}
		}
		int i=1;
		while(i<=7)
		{
			for(int j=0; j<aux.size(); j++)
			{
				if(aux.get(j).getDow()==i)
				{
					System.out.println("Viaje "+ j+": "+ trimestre+", "+aux.get(j).getSourceid()+", "+aux.get(j).getDstid()+", "+aux.get(j).getDow()+", "+aux.get(j).getMean_travel_time()+ "\n---------");	
				}
			}
			i++;
		}
	}

	public void buscarTiemposSeparate(String trimestre, String zonaOrigen, String zonaDestino) {
		// TODO Auto-generated method stub
		ArrayList<Viaje> aux=new ArrayList<Viaje>();
		for(int i=0; i<datosHashSeparate.size();i++)
		{
			if(datosHashSeparate.get(trimestre+zonaOrigen+zonaDestino)!=null){
				aux.add(datosHashSeparate.get(trimestre+zonaOrigen+zonaDestino));
			}
		}
		int i=1;
		while(i<=7)
		{
			for(int j=0; j<aux.size(); j++)
			{
				if(aux.get(j).getDow()==i)
				{
					System.out.println("Viaje "+ j+": "+ trimestre+", "+aux.get(j).getSourceid()+", "+aux.get(j).getDstid()+", "+aux.get(j).getDow()+", "+aux.get(j).getMean_travel_time()+ "\n---------");	
				}
			}
			i++;
		}
	}




}
