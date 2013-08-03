package invaders; 

import java.awt.Color; 
import java.awt.Graphics2D; 
import java.util.logging.Level; 
import java.util.logging.Logger; 

public class Misil extends Sprite{ 
    private UFO naveAlienigena; 
    
    // El misil sale del UFO 
    public Misil(UFO naveAlienigena){ 
        super(); 
        // Impedimos que el UFO lanze otro misil mientras exista uno lanzado 
        if (naveAlienigena.dispara()) {          
            // En caso de no haber un misil lanzado lo lanzamos por debajo del UFO 
            this.setY(naveAlienigena.getY()+11); 
            this.setX(naveAlienigena.getX()+7);        
            // Lo añadimos al contenedor de Sprites para poder visualizarlo y sirva de referencia 
            naveAlienigena.interfaz().contenedorDeSprites().add(this); 
            this.naveAlienigena=naveAlienigena; 
            // Comienza el cliclo de vida del misil 
            new Misil.Comportamiento(this).start();          
        }    
    } 
  
    // Definimos el comportamiento del misil en un hilo 
    private class Comportamiento extends Thread { 
        private Misil misil; 
        public Comportamiento(Misil misil) { 
            super(); 
            this.misil=misil; 
        } 

        @Override 
        public void run() { 
            // Mientras el misil no llegue hasta a la "tierra" existirá el misil 
            while(misil.getY()<naveAlienigena.interfaz().getHeight()){ 
                try { 
                    Thread.sleep(100); // retardo 
                } catch (InterruptedException ex) { 
                    Logger.getLogger(Laser.class.getName()).log(Level.SEVERE, null, ex); 
                } 
                misil.setY(misil.getY()+10);    
            } 
            // Finaliza el cliclo de vida del misil 
            naveAlienigena.interfaz().contenedorDeSprites().remove(misil); 
            naveAlienigena.recargaDisparo(); // indicamos al UFO que puede realizar otro disparo 
        } 
    } 
    
    // Definimos el grafico del misil 
    @Override 
    public void putSprite(Graphics2D grafico, int x, int y) { 
        grafico.setColor(Color.MAGENTA); 
        grafico.fillRect(x, y, 4, 10); 
    } 
    
    // Definimos el ancho 
    @Override 
    public int getWidth() { 
        return 4; 
    } 
}