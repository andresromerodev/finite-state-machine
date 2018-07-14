package maquinaDeEstadoFinito.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import java.util.HashMap;


@XStreamAlias("estado")
public class Estado {
    
    @XStreamAsAttribute
    @XStreamAlias("identificador")
    protected String identificador;    
    
    @XStreamAsAttribute
    public HashMap<String,Arista> aristas;
    
    @XStreamAsAttribute
    @XStreamAlias("tipo")
    int tipo;
    
    public final static int INICIAL = 0;
    public final static int INTERMEDIO = 1;
    public final static int FINAL = 2;
    
    
    public Estado(String id, int tipo){
       this.aristas = new HashMap<>(); 
       this.identificador = id;
       this.tipo = tipo;
    }
    
    public Estado(){
        
    }

    public Estado transiciona(String valor){
        Arista aux = aristas.get(valor);
        if(aux == null){
            return this;
        }
        return aux.estadoSiguiente;
    }

    public String getIdentificador() {
        return identificador;
    }
    
    public int getTipo(){
        return tipo;
    }
    
    public void agregarArista(Estado llegada, String cadena){
       aristas.put(cadena, new Arista(this,llegada));
    }
     
    public boolean esEstadoFinal(){
        return (tipo == 2);
    }
}
