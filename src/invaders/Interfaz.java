package invaders; 

import java.awt.Frame; 
import java.awt.Graphics2D; 
import java.awt.event.WindowAdapter; 
import java.awt.event.WindowEvent; 
import java.awt.image.BufferedImage; 
import java.util.ArrayList; 
import java.util.logging.Level; 
import java.util.logging.Logger; 

public class Interfaz extends Frame implements Runnable { 
    // Creamos el contenedor de los sprites, que permitira a la interfaz visualizarlos 
    // y gestionarlos, así como también creamos un método público que permita 
    // compartir dicho contenedor con el fin de que pueda ser gestionado 
    private ArrayList sprites=new ArrayList(); 
    public ArrayList contenedorDeSprites(){ 
        return sprites; 
    } 
    
    // Definimos la interfaz, que es el marco gráfico donde se ejecuta el juego 
    public Interfaz(){ 
        // Tamaño de la ventana 
        this.setBounds(0, 0, 520, 310); 
        // Centramos la ventana 
        this.setLocation((java.awt.Toolkit.getDefaultToolkit().getScreenSize().width/2)-(this.getWidth()/2) 
        , (java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/2)-(this.getHeight()/2)); 
        // Impedimos que se altere el tamaño de la ventana 
        this.setResizable(false); 
        this.setTitle("Space Invaders"); 
        this.setVisible(true); 
        // Todos los eventos de la ventana recogidos en los metodos de esta clase 
        this.addWindowListener(new WindowAdapter() { 
            @Override 
            public void windowClosing(WindowEvent e) { 
                System.out.println("Cerrando"); 
                System.exit(0); 
            } 
        }); 
    } 
    
    // Hilo del juego 
    @Override 
    public void run() { 
        // Creamos la nave del jugador y le pasamos los eventos del teclado para 
        // que el jugador pueda interactura con ella 
        Nave jugador=new Nave(this); 
        this.requestFocus(); // Focalizamos hacia nuestro objeto 
        this.addKeyListener(jugador); // Direccionamos la captura de teclas al Jugaodr 
        
        // Generamos un escuadron de UFOs, el primer argumento toma la interfaz 
        // el segundo las columnas y el tercero las filas, así podemos personalizar la partida 
        new EscuadronUFO(this,7,4); 
        
        // Bucle principal, donde trasncurre la partida 
        while(actualizar()) {// Actualizar gestiona la partida        
            try { 
                Thread.sleep(25); // Retardo, permite ejecutarse igual en cualquier equipo 
            } catch (InterruptedException ex) { 
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex); 
            } 
        } 
        // GameOver 
        System.exit(0); 
    } 
    
    // Gestor de la partida que controla el pintado de los sprites, los impactos y 
    // determina segun los casos si la partida finaliza. 
    private boolean actualizar(){ 
         // Buffer que permite evitar el parpadeo cada vez que se limpia la pantalla, 
         // además es el objeto donde se intaran los sprites 
         BufferedImage pantalla=new BufferedImage(this.getWidth(),this.getHeight(), BufferedImage.TYPE_INT_RGB); 
         pantalla.getGraphics().fillRect(0, 0, pantalla.getWidth(), pantalla.getHeight()); 
         // banderas del control de la partida 
         boolean gameOver=true,jugadorVivo=false; 
          
         // Gestionamos TODOS los sprites 
         for (int i=0;i<sprites.size();i++) { 
             try {// Pintamos el sprite 
                ((Sprite)sprites.get(i)).putSprite((Graphics2D)pantalla.getGraphics(),((Sprite)sprites.get(i)).getX(), ((Sprite)sprites.get(i)).getY()); 

                // En caso de que el sprite sea la NAVE del jugador 
                if (sprites.get(i).getClass()==Nave.class) { 
                    ((Nave)sprites.get(i)).actualiza(); // Mueve la nave acorde a las teclas pulsadas 
                    // Comprueba si hay algun MISIL que impacte con la nave 
                    jugadorVivo=compruebaImpactoMisil(((Nave)sprites.get(i))); 
                // En caso de que el sprite sea un LASER disparado por el jugador 
                } else if (sprites.get(i).getClass()==Laser.class) { 
                    // Comprobamos si hay impacto del LASER con un UFO 
                    if (((Laser)sprites.get(i)).isVisible()) compruebaImpactoDelLaser((Laser)sprites.get(i)); 
                    // En caso de impacto eliminamos el LASER 
                    else sprites.remove(i); 
                // En caso de que el sprite sea un UFO o una nave alienigena 
                } else if (sprites.get(i).getClass()==UFO.class) { 
                    // Significará que la partida todavía no ha acabado y qeu faltan UFOs por destruir 
                    gameOver=false; 
                    // Si el UFO fue impactado por el LASER (invisible) lo eliminamos 
                    if (!((UFO)sprites.get(i)).isVisible()) sprites.remove(i); 
                } 
             } catch(IndexOutOfBoundsException ioex){ 
              
             } 
         } 
         // Volcamos el buffer visual donde se dibujan los sprites a la ventana 
         this.getGraphics().drawImage(pantalla, 0, 0, this); 
         return (!gameOver && jugadorVivo); 
    } 
    
    // En caso de impacto hacemos invisble el LASER y el UFO 
    private void compruebaImpactoDelLaser(Laser laserJugador){ 
        for (int i=0;i<sprites.size();i++) 
            if (sprites.get(i).getClass()==UFO.class) if (((UFO)sprites.get(i)).isVisible()) { 
                UFO enemigo=(UFO)sprites.get(i); 
                if ((laserJugador.getX()>enemigo.getX() && laserJugador.getX()+laserJugador.getWidth()<enemigo.getX()+enemigo.getWidth()) && 
                   (laserJugador.getY()<enemigo.getY()+enemigo.getHeight() && laserJugador.getY()>enemigo.getY())) 
                {    enemigo.setVisible(false); 
                     laserJugador.setVisible(false); 
                     break; 
                } 
            } 
    } 
    
    // En caso de ser alcanzado por un MISIL lo notificamos devolviendo FALSE 
    private boolean compruebaImpactoMisil(Nave jugador){ 
        for (int i=0;i<sprites.size();i++) 
            if (sprites.get(i).getClass()==Misil.class) { 
                Misil laserEnemigo=(Misil)sprites.get(i); 
                if ((laserEnemigo.getX()>jugador.getX() && laserEnemigo.getX()+laserEnemigo.getWidth()<jugador.getX()+jugador.getWidth()) && 
                   (laserEnemigo.getY()<jugador.getY()+jugador.getHeight() && laserEnemigo.getY()>jugador.getY())) 
                    return false; 
             } 
        return true; 
    } 
}