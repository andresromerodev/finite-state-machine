package maquinaDeEstadoFinito.controller;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import maquinaDeEstadoFinito.model.Estado;
import maquinaDeEstadoFinito.model.Model;
import maquinaDeEstadoFinito.view.View;
import javax.swing.JOptionPane;
import java.awt.Point;
import java.awt.event.*;
import java.util.Map.Entry;
import java.util.logging.*;
import objectdraw.*;
import java.io.*;


public class Controller extends MouseAdapter implements ActionListener, MouseMotionListener{
    
    Model model;
    View view;
   
    FilledOval estado; //Estado actual seleccionado
    Line arista; //Arista actual seleccionada
    
    int mouseClick = 0;//Cant. de Clicks
    boolean arrastrando = false; //Si se esta arrastrando el estado
    
    //Se encargan de almacenar el punto del estado actual//
    int coordX; 
    int coordY;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        view.setModel(model);
        view.setController(this);
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        mouseClick++;
        if(dentro(e) && mouseClick==1){
            Location l = new Location(estado.getX()+20,estado.getY()+20);
            arista = new Line(l,l, view.panel);
            arrastrando = true;
        }
        if (dentro(e) && mouseClick==2) {
            mouseClick=0;  
            arista.setEnd(estado.getX()+20,estado.getY()+20);
            String cadena = JOptionPane.showInputDialog("Digite la cadena:");
            view.crearArista(arista, cadena, arista.getStart(), arista.getEnd());
            
            for (int i = 0; i < cadena.length(); i++) {
                this.agregarArista (cadena.charAt(i), arista);
            }
        }
    }
    
    
    public boolean dentro(MouseEvent e){ //Si el mouse se encuentra dentro de un estado
     JDrawingCanvas panel =(JDrawingCanvas)e.getSource(); 
     Location mousePosition = new Location(panel.getMousePosition()); 
                                                        
     for(FilledOval it: view.estados.values()){
            estado = it;
            if(it.contains(mousePosition)){
                return true;
            }
        }
            return false;        
    }
    

    @Override
    public void mouseReleased(MouseEvent e) {
        arrastrando = false;
    }
    

    @Override
    public void actionPerformed(java.awt.event.ActionEvent evt) {
       String info;
       switch(evt.getActionCommand()){
            case "Inicial":
                model.agregarEstado("I",0);//Agrega el estado al modelo.
                view.crearEstado("Inicial",'I');//Crea el estado visual.
                break;
            case "Intermedio":
                info = view.inputMs("Digite el ID (Solo un caracter)");
                model.agregarEstado(info,1);
                view.crearEstado("Intermedio",info.charAt(0));
                break;
            case "Final":
                info = view.inputMs("Digite el ID (Solo un caracter)");
                model.agregarEstado(info,2);
                view.crearEstado("Final",info.charAt(0));
                break;
            case "Hilera":
                info = view.inputMs("Digite la hilera");
                    if( model.verificar(info)){
                         view.message("Hilera Aceptada");
                         break;
                      }
                        view.message("Hilera NO Aceptada");
        }   
    }

    
    @Override
    public void mouseDragged(MouseEvent e) {
        Point mousePosition = ((JDrawingCanvas)e.getSource()).getMousePosition();
        
        if(!arrastrando){ //NO arrastrando.
            if(dentro(e)){ 
                coordX = mousePosition.x; 
                coordY = mousePosition.y; 
                arrastrando=true; 
            }
        }
        else{ //Arrastrando.
            for(Entry<Text,FilledOval> it: view.estados.entrySet()){
                if(estado == it.getValue()){
                    estado.moveTo((estado.getX() + mousePosition.x) - coordX,(estado.getY() + mousePosition.y) - coordY);
                    moverArista();
                    coordX = mousePosition.x;
                    coordY = mousePosition.y;
                    it.getKey().moveTo(estado.getX()+20, estado.getY()+20);
                }
            }  
        }
    }
    

    @Override
    public void mouseMoved(MouseEvent e) {
     Point mousePosition = ((JDrawingCanvas)e.getSource()).getMousePosition();
        if(arrastrando){
         arista.setEnd(mousePosition.getX()+20,mousePosition.getY()+20);
        }
    }
    
    
    public void moverArista(){
        Line l;
        Text t;
        for(Entry<Text,Line> ent: view.aristas.entrySet()){
            l = ent.getValue();
            t = ent.getKey();
            if(estado.contains(l.getEnd())){
                l.setEnd(estado.getX()+20, estado.getY()+20);
                t.moveTo(View.puntoMedioX(l.getStart(), l.getEnd()),View.puntoMedioY(l.getStart(), l.getEnd()));
            }
            else if (estado.contains(l.getStart())) {
                l.setStart(estado.getX()+20, estado.getY()+20);
                t.moveTo(View.puntoMedioX(l.getStart(), l.getEnd()),View.puntoMedioY(l.getStart(), l.getEnd()));
            }
        }
    }
    
    
    public void agregarArista(char t, Line l){
        Estado inicio = null;
        Estado siguiente = null;

            for(Entry<Text,FilledOval> it: view.estados.entrySet()){
                if(it.getValue().contains(l.getStart())){
                    inicio = model.encontrar(it.getKey().getText());       
                }
                else if(it.getValue().contains(l.getEnd())){
                    siguiente = model.encontrar(it.getKey().getText());
                }
        } 
           model.agregarArista(inicio, siguiente, String.valueOf(t));
    }
    
    
    public void guardarVista() throws IOException{
        XStream xStream = new XStream(new DomDriver());
        xStream.processAnnotations(JDrawingCanvas.class);
        xStream.toXML(view.panel,new FileWriter("view.xml"));
    }
    
    
    public void cargarVista(){
        XStream xStream = new XStream(new DomDriver());
        xStream.processAnnotations(JDrawingCanvas.class);
        view.panel = (JDrawingCanvas)xStream.fromXML(new File("view.xml"));
    }
    
}
