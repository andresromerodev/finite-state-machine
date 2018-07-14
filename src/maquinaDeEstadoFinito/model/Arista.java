package maquinaDeEstadoFinito.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


@XStreamAlias("arista")
public class Arista {
    
    @XStreamAsAttribute
    @XStreamAlias("estadoanterior")
    Estado estadoAnterior;
    
    @XStreamAsAttribute
    @XStreamAlias("estadosiguiente")
    Estado estadoSiguiente;
    

   public Arista(Estado a,Estado s){
       estadoAnterior=a;
       estadoSiguiente=s;
    }
   
    public Estado getAnterior(){
        return estadoAnterior;
    } 
   
   public Estado getSiguiente(){
       return estadoSiguiente;
   }
    
}