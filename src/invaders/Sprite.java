package invaders; 

import java.awt.Graphics2D; 

// La clase base de cualquier objeto visual que se mueva por la interfaz 
public class Sprite{ 
    private int x,y; //coordenadas 
    private boolean visible; // si esta visible o no 

    public Sprite() //Constructor 
    { 
        visible=true; 
        x=y=0; 
    } 

    public boolean isVisible() 
    { 
        return visible; 
    } 

    public void setVisible(boolean estado) 
    { 
        visible=estado; 
    } 

    public int getX() // Obtenemos la coordenada horizontal actual del Sprite 
    { 
        return x; 
    } 

    public void setX(int valor) //Asignamos la coordenada horizontal actual del Sprite 
    { 
        x=valor; 
    } 

    public int getY() // Obtenemos la coordenada vertical actual del Sprite 
    { 
        return y; 
    } 

    public void setY(int valor) // Asignamos la coordenada vertical actual del Sprite 
    { 
        y=valor; 
    } 

    public int getWidth() // Ancho del Sprite 
    { 
        return 0; 
    } 

    public int getHeight() // Alto del Sprite 
    { 
        return 0; 
    } 

    public void putSprite(Graphics2D grafico,int coordenadaHorizontal,int coordenadaVertical)  
    { 
        // Pegamos el Sprite en la pantalla 
    } 

}