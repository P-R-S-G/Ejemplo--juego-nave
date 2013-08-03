package invaders; 

import java.awt.Color; 
import java.awt.Graphics2D; 
import java.util.logging.Level; 
import java.util.logging.Logger; 

public class Laser extends Sprite { 
    private Nave jugador; 
    private static int laseresEnPantalla=0; 
    
    public Laser(Nave jugador){ 
        super(); 
        if (laseresEnPantalla<3) { 
            laseresEnPantalla++;              
            this.setY(jugador.getY()-11); 
            this.setX(jugador.getX()+7);        
            jugador.interfaz().contenedorDeSprites().add(this); 
            this.jugador=jugador; 
            new Laser.Comportamiento(this).start();          
        } 
    } 

    private class Comportamiento extends Thread { 
        Laser laser; 
        public Comportamiento(Laser laser) { 
            super(); 
            this.laser=laser; 
        } 

        @Override 
        public void run() { 
            // El ciclo de vida del laser es desde que sale de la nave 
            // hasta que desaparece o sale de los limites 
            while(laser.getY()>15 && laser.isVisible()){ 
                try { 
                    Thread.sleep(100); 
                } catch (InterruptedException ex) { 
                    Logger.getLogger(Laser.class.getName()).log(Level.SEVERE, null, ex); 
                } 
                laser.setY(laser.getY()-10);    
            } 
            laseresEnPantalla--; 
            jugador.interfaz().contenedorDeSprites().remove(laser); 
        } 
    } 
    
    // Definimos el grafico del laser (el mismo que el misil) 
    @Override 
    public void putSprite(Graphics2D grafico, int x, int y) { 
        grafico.setColor(Color.BLUE); 
        grafico.fillRect(x, y, 4, 10); 
    } 
    
    // Definimos el ancho 
    @Override 
    public int getWidth() { 
        return 4; 
    }  
}