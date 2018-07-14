package maquina.de.estado.finito;

import maquinaDeEstadoFinito.controller.Controller;
import maquinaDeEstadoFinito.model.Model;
import maquinaDeEstadoFinito.view.View;


public class Application {
    
    public static void main(String[] args) throws Exception{
        
        Model model=new Model() ;
        View view = new View();
        Controller controller = new Controller(model,view);
        view.setVisible(true);
    }
    
}

