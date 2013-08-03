package invaders; 

import java.util.ArrayList; 
import java.util.Random; 
import java.util.logging.Level; 
import java.util.logging.Logger; 

public class EscuadronUFO { 
    private ArrayList escuadronUFO=new ArrayList(); 
    
    public EscuadronUFO(Interfaz interfaz,int columnas,int filas){ 
        UFO enemigo=new UFO(interfaz); // Generamos un UFO de prueba para conocer calcular la distancia 
        // entre las naves 
        int espacioEntreNaves=(interfaz.getWidth()-((columnas+1)*enemigo.getWidth()))/(columnas+1); 
        interfaz.contenedorDeSprites().remove(enemigo); // Luego lo eliminamos 
        for (int f=0;f<filas;f++) 
            for (int c=0;c<columnas;c++) { 
                enemigo=new UFO(interfaz); 
                enemigo.setX((enemigo.getWidth()+espacioEntreNaves)*(c+1)); 
                enemigo.setY((enemigo.getHeight()+5)*(f+2)); 
                escuadronUFO.add(enemigo); 
            }        
        // Una vez creado el escuadron, este actua como uno solo y se encarga del comportamiento de las naves 
        new EscuadronUFO.Comportamiento(interfaz).start();          
    } 

    private class Comportamiento extends Thread { 
        Interfaz interfaz; 
        public Comportamiento(Interfaz interfaz) { 
            super(); 
            this.interfaz=interfaz; 
        } 

        @Override 
        public void run() { 
            Random rnd = new Random(System.currentTimeMillis()); 
            UFO naveAlienigena; 
            boolean paUnLao=true,gameOver=false,aux=paUnLao; 
            do { // Mientras exista algún UFO, existira la maldad XD 
                try { 
                    Thread.sleep(300); // Retardo 
                } catch (InterruptedException ex) { 
                    Logger.getLogger(Laser.class.getName()).log(Level.SEVERE, null, ex); 
                } 
                // Comprueba que naves alienigenas siguen con vida en el escuadron          
                for (int i=0;i<escuadronUFO.size();i++) 
                    if (!interfaz.contenedorDeSprites().contains(escuadronUFO.get(i))) 
                        escuadronUFO.remove(escuadronUFO.get(i)); 
                
                // Movemos el escuadron hacia un lado y luego hacia otro (y a la vez descendemos) 
                for (Object objeto: escuadronUFO) { 
                    naveAlienigena=(UFO)objeto; 
                    naveAlienigena.setY(naveAlienigena.getY()+1); 
                    // Si algún UFO llega a la tierra, acaba la partida :( 
                    if (naveAlienigena.getY()>(interfaz.getHeight()-(naveAlienigena.getHeight()*2))) gameOver=true; 
                    // Si alguna nave llega a los limites de la pantalla, indicamos al escuadron que cambie de direccion 
                    if (paUnLao) { 
                        naveAlienigena.setX(naveAlienigena.getX()+10); 
                        if (naveAlienigena.getX()>interfaz.getWidth()-naveAlienigena.getWidth()) aux=false; 
                    } else { 
                        naveAlienigena.setX(naveAlienigena.getX()-10); 
                        if (naveAlienigena.getX()<1) aux=true; 
                    }      
                    // Aleatoriamente abriran fuego contra la tierra 
                    if(rnd.nextInt(escuadronUFO.size())==1) new Misil(naveAlienigena); 
                } 
                paUnLao=aux; 
            } while(escuadronUFO.size()>0 && !gameOver); 
            interfaz.contenedorDeSprites().removeAll(escuadronUFO); 
            escuadronUFO.clear(); 
        } 
    } 
}