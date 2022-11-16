
package inicio;

import sistemaDistribucion.*;

public class Main {
   public static void main(String[] args){
       IObligatorio obli = new Sistema();
       obli.agregarCamion("ABC1234", 5);
       obli.listarCamiones();
   } 
   
}

