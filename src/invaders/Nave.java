package invaders; 

import java.awt.Color; 
import java.awt.Graphics2D; 
import java.awt.event.KeyEvent; 
import java.awt.event.KeyListener; 

public class Nave extends Sprite implements KeyListener { 
    private Interfaz interfaz; 
    private boolean izquierda, derecha, disparo; 

    public Nave(Interfaz interfaz){ 
        super(); 
        this.setY(interfaz.getHeight()-15); 
        this.interfaz=interfaz; 
        interfaz.contenedorDeSprites().add(this); 
    } 
    
    @Override 
    public void putSprite(Graphics2D grafico, int x, int y) { 
        grafico.setColor(Color.GREEN); 
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

    // Definimos el comportamiento en funcion de las teclas pulsadas 
    public void actualiza() { 
        if(izquierda) { 
            this.setX(this.getX()-5); 
            if (this.getX()<1) this.setX(1); 
        } else if(derecha) { 
            this.setX(this.getX()+5); 
            if (this.getX()>interfaz.getWidth()-this.getWidth()) this.setX(interfaz.getWidth()-this.getWidth()); 
        } 
        
        if (disparo) { 
            disparo=false; 
            new Laser(this); 
        } 
    } 

    // tecla soltada 
    @Override 
    public void keyReleased(KeyEvent e) { 
        switch (e.getKeyCode()) { 
            case KeyEvent.VK_LEFT: 
                izquierda = false; 
                break; 
            case KeyEvent.VK_RIGHT: 
                derecha = false; 
                break; 
            case KeyEvent.VK_SPACE: 
                disparo = false; 
                break; 
        } 
    } 

    //tecla presionada 
    @Override 
    public void keyPressed(KeyEvent e) { 
        switch (e.getKeyCode()) { 
            case KeyEvent.VK_LEFT: 
                izquierda = true; 
                break; 
            case KeyEvent.VK_RIGHT: 
                derecha = true; 
                break; 
            case KeyEvent.VK_SPACE: 
                disparo = true; 
                break; 
        } 
    } 

    @Override 
    public void keyTyped(KeyEvent e) { } 

    // Permite conocer la interfaz donde se ejecuta el jugador 
    public Interfaz interfaz() { 
        return interfaz; 
    } 
} 