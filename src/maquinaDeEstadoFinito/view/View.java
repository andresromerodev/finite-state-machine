package maquinaDeEstadoFinito.view;


import java.awt.*;
import java.util.*;
import objectdraw.*;
import javax.swing.JOptionPane;
import maquinaDeEstadoFinito.controller.Controller;
import maquinaDeEstadoFinito.model.Model;

public class View extends javax.swing.JFrame implements Observer{

    public JDrawingCanvas panel;
    public HashMap <Text, FilledOval> estados;
    public HashMap <Text, Line> aristas;

    public View() {
        initComponents();
        initPanel();
    }  
    
    public void initPanel(){
        panel = new JDrawingCanvas();
        panel.setSize(735, 426);
        panel.setBackground(new Color(247,247,247));
        
        estados = new HashMap<>();
        aristas = new HashMap<>();
        
        this.add(panel);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuGuardar = new javax.swing.JMenuItem();
        jMenuCargar = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItemInicial = new javax.swing.JMenuItem();
        jMenuIntermedio = new javax.swing.JMenuItem();
        jMenuItemFinal = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuHilera = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("Archivo");

        jMenuGuardar.setText("Guardar");
        jMenu1.add(jMenuGuardar);

        jMenuCargar.setText("Cargar");
        jMenu1.add(jMenuCargar);

        jMenuItem3.setText("Limpiar");
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Estados");

        jMenuItemInicial.setText("Inicial");
        jMenu2.add(jMenuItemInicial);

        jMenuIntermedio.setText("Intermedio");
        jMenu2.add(jMenuIntermedio);

        jMenuItemFinal.setText("Final");
        jMenu2.add(jMenuItemFinal);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Verificar");

        jMenuHilera.setText("Hilera");
        jMenu3.add(jMenuHilera);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 735, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 403, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new View().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuCargar;
    private javax.swing.JMenuItem jMenuGuardar;
    private javax.swing.JMenuItem jMenuHilera;
    private javax.swing.JMenuItem jMenuIntermedio;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItemFinal;
    private javax.swing.JMenuItem jMenuItemInicial;
    // End of variables declaration//GEN-END:variables

    Model model;
    Controller controller;
    
public void setModel(Model model){
    this.model=model;
    model.addObserver(this); 
}    

public void setController(Controller controller){
     this.controller = controller;
     panel.addMouseMotionListener(controller);
     panel.addMouseListener(controller);
     jMenuItemInicial.addActionListener(controller);
     jMenuIntermedio.addActionListener(controller);
     jMenuItemFinal.addActionListener(controller);
     jMenuGuardar.addActionListener(controller);
     jMenuCargar.addActionListener(controller);
     jMenuHilera.addActionListener(controller);
}

public void crearEstado(String  tipo, char id){ //Crea el estado visual en la vista.
    
    FilledOval nuevo = new FilledOval(120,120,50,50,panel);
    Location location = new Location(nuevo.getX()+20,nuevo.getY()+20);
    
    switch(tipo){
        case "Inicial":
        nuevo.setColor(new Color(2,176,232));
        break;
        case "Intermedio":
        nuevo.setColor(new Color(13,255,81));
        break;
        case "Final":
        nuevo.setColor(new Color(255,13,14));
        break;
    }
    
    //Modificacion del texto
    Text t = new Text(id,location,panel);
    t.setFontSize(18);
    t.setBold(rootPaneCheckingEnabled);
    t.setItalic(rootPaneCheckingEnabled);
    t.setColor(Color.WHITE);
    
    estados.put(t, nuevo);
}

public void crearArista(Line arista, String cadena, Location start, Location end){ //Crea la arista grafica
    String cadena1 = cadena;
    Text t = new Text(cadena1,puntoMedioX(start,end) , puntoMedioY(start,end),panel);
    aristas.put(t, arista);
    arista.sendToBack();
    
    if(end.getX()<start.getX()){
       t.setText("<-  "+t.getText());
    }else if(end.getX()>start.getX()){
       t.setText(t.getText()+"  ->");
    }
}

public static double puntoMedioX(Location start, Location end){ //Permiten ubicar el punto medio en la vista.
    double xstart= start.getX();
    double xend=end.getX();
        
        return (xstart+xend)/2;
}
    
public static double puntoMedioY(Location start, Location end){
    double ystart=start.getY();
    double yend=end.getY();
        
        return (ystart+yend)/2;
}

public String inputMs(String mensaje){
    return JOptionPane.showInputDialog(mensaje);
}

public void message(String mensaje){
    JOptionPane.showMessageDialog(null, mensaje);
}

@Override
    public void update(Observable updatedModel,Object param){
        panel.repaint();
    }

}