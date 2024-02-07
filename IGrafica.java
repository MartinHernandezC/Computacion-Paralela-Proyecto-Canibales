package paralelascanivalesp1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class IGrafica extends JFrame implements Runnable{
    int cocinero;
    int[] canibales=new int[10];
    private Image fondo;
    private BufferedImage buffer;
    private Thread hilo;
    int pizza=0;
    
    public IGrafica(){
        setTitle("Ventana");
        setSize(1000,700);
        setLayout(null);
        hilo = new Thread((Runnable)this);
        hilo.start();  
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public synchronized void mostrarpizza(int p){
        pizza=p;
    }
    
    public synchronized void actualizarH(int id,int ide){
        canibales[id]=ide;
    }
    
    public synchronized void actualizarCoc(int coc){
    cocinero=coc;
    }
        
    public void paint(Graphics g){
        if(fondo==null){
        fondo=createImage(getWidth(),getHeight());
            Graphics gbuffer = fondo.getGraphics();    
        }
        update(g);
    }
    
    @Override
    public void update(Graphics g){   
        int puntoy=100;
        int puntox=0;
        g.setClip(0, 0,getWidth(),getHeight());   
        buffer=(BufferedImage) createImage(getWidth(),getHeight());
        Graphics gbuffer=buffer.getGraphics();
        gbuffer.setClip(0, 0,getWidth(),getHeight());
        gbuffer.drawImage(fondo,0,0,this);
         
        ImageIcon pizzona=new ImageIcon(getClass().getResource("pizza.png"));
        ImageIcon fondillo=new ImageIcon(getClass().getResource("mesa.jpg"));
        ImageIcon canibal1=new ImageIcon(getClass().getResource("canibal.png"));
        ImageIcon canibal2=new ImageIcon(getClass().getResource("canibald.png"));
        ImageIcon cociner=new ImageIcon(getClass().getResource("cocinero.png"));
        ImageIcon cocinerd=new ImageIcon(getClass().getResource("cocinerod.jpg"));
      
        gbuffer.drawImage(fondillo.getImage(), 0, 0, 1000, 700, this);       
        if(cocinero==1){
            gbuffer.drawImage(cociner.getImage(), 650, puntoy, 300, 400, this);
        }else if(cocinero==2){
            gbuffer.drawImage(cocinerd.getImage(), 650, 50, 300, 300, this);
            }      
        for(int i=0; i<10;i++){
            if(i>4){
            puntoy=200;
            puntox=5;
            }
            if(canibales[i]==1){
            gbuffer.drawImage(canibal1.getImage(), 10+((i-puntox)*100), puntoy, 80, 80, this);
            }else if(canibales[i]==2){
            gbuffer.drawImage(canibal2.getImage(), 10+((i-puntox)*100), puntoy, 80, 80, this);
            }
        }
        puntoy=400;
        puntox=0;
        
        for(int i=0;i<pizza;i++){
        if(i>19){
            puntoy=600;
            puntox=20;
        }else if(i>9){
            puntoy=500;
            puntox=10;
        }
        gbuffer.drawImage(pizzona.getImage(), 10+((i-puntox)*75), puntoy, 75, 50, this);
        } 
        g.drawImage(buffer, 0, 0, this);
        }
    
    @Override
    public void run() {
        while(true){
            try{
                repaint();
                Thread.sleep(16);
            }catch(InterruptedException ex){
                
            }
        }
    }
}

