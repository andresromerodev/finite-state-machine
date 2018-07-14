package maquinaDeEstadoFinito.model;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.extended.NamedMapConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.io.File;


public class Model extends Observable{
 
    public ArrayList <Estado> estados;
    
    public Model() {
        this.estados = new ArrayList();
    }  
    
    public void agregarEstado(String id, int tipo){
        estados.add(new Estado(id,tipo));//factory
        setChanged();
        notifyObservers(null);
    }
	
    @Override
    public void addObserver(java.util.Observer o) {
        super.addObserver(o);
        setChanged();
        notifyObservers(null);
    }
    
    public Estado encontrar(String c){
        for (int i = 0; i < estados.size(); i++) {
            if(estados.get(i).identificador.equals(c)){
                return estados.get(i);
            }
        }
        return null;
    }
    
    public void agregarArista(Estado s, Estado l,String c){    
        s.agregarArista(l,c);
    }
    
    public boolean verificar(String cadena){
        Estado aux = encontrar("I");
        for (int i = 0; i < cadena.length(); i++) {
            aux = aux.transiciona(String.valueOf(cadena.charAt(i)));
        }
            return aux.esEstadoFinal();
    }
    
    public void guardar() throws IOException{
        XStream xStream = new XStream(new DomDriver());
        xStream.processAnnotations(ArrayList.class);
        xStream.processAnnotations(Estado.class);
        xStream.processAnnotations(Arista.class);
        
        NamedMapConverter c = new NamedMapConverter(HashMap.class,xStream.getMapper(), "entry", "key", String.class, "value", Arista.class, Boolean.FALSE , Boolean.FALSE , xStream.getConverterLookup());
        xStream.registerConverter(c);
        xStream.toXML(this.estados,new FileWriter("test.xml"));
    }
    
    public void cargar(){
        
        XStream xStream = new XStream(new DomDriver());
        xStream.processAnnotations(ArrayList.class);
        xStream.processAnnotations(Estado.class);
        xStream.processAnnotations(Arista.class);
        
        NamedMapConverter c = new NamedMapConverter(HashMap.class,xStream.getMapper(), "entry", "key", String.class, "value", Arista.class, Boolean.FALSE , Boolean.FALSE , xStream.getConverterLookup());
        xStream.registerConverter(c);
        estados = (ArrayList<Estado>)xStream.fromXML(new File("test.xml"));
    }
    
}