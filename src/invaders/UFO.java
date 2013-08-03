package invaders; 

import java.awt.Color; 
import java.awt.Graphics2D; 

public class UFO extends Sprite { 
    private Interfaz interfaz; 
    private int disparo=1; // Inidcamos el numero de disparos que puede realizar el UFO 
    
    public UFO(Interfaz interfaz){ 
        super(); 
        this.interfaz=interfaz; 
        interfaz.contenedorDeSprites().add(this); 
    } 
    
    @Override 
    public void putSprite(Graphics2D grafico, int x, int y) { 
        grafico.setColor(Color.RED); 
        grafico.fillRect(x+6, y, 6, 5);        
        grafico.fillRect(x, y+5, 18, 5); 
    } 
    
    @Override 
    public int getWidth() { 
        return 18; 
    } 

    @Override 
    public int getHeight() { 
        return 11; 
    } 
    
    public Interfaz interfaz() { 
        return interfaz; 
    } 
    
    // Reiniciamos los disparos 
    public void recargaDisparo(){ 
        disparo=1; 
    } 
    
    // Indica si puede seguir disparando 
    public boolean dispara(){ 
        if (disparo>0) { 
            disparo--; 
            return true; 
        } else 
            return false; 
    } 
}