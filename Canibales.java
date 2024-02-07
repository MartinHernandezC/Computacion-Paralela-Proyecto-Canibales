package paralelascanivalesp1;

import java.util.Random;

public class Canibales implements Runnable{
    
    public static IGrafica grafica;
    public static Random rand = new Random(System.nanoTime());    
    private static int acumulado=0;
    private int id;
    public static int numHilos=2+rand.nextInt(10);
    private static int pizza=0;
    private boolean consumidor;
    private int estado; //estado 1 despierto, y estado 2 es dormido
    private static Object lock=new Object();
      
    public Canibales(boolean consumidor, int id, int estado){
        this.consumidor=consumidor;
        this.id=id;
        this.estado=estado;
    }

    public void run(){
        while(true){
            if(consumidor){
                comer(id);
            }else{
                cocinar();
            }
        }
    }
  
    private void comer(int id) {
        synchronized(lock){
            if(pizza>0){
                pizza--;
                acumulado++;
                grafica.mostrarpizza(pizza);
                grafica.actualizarH(id,2);
                System.out.println("El canival "+ (id+1) +" se comió una pizzona y ya nomas sobran "+pizza+" pizzas.");
                try {
                    Thread.sleep(1000);
                    if(acumulado==(numHilos-1)){//Cuando el último hilo (canibal) va a consumir les notifica a todos y les cambia el estado a todos para que puedan consumir otra vez                 
                    lock.notifyAll();          
                    for(int i=0;i<numHilos-1;i++){
                    grafica.actualizarH(i,1);
                    }
                    acumulado=0;
                    }else{
                    lock.wait();
                    }
                }catch (InterruptedException ex){
                }
            }else{
                lock.notifyAll();
                for(int i=0;i<numHilos-1;i++){
                grafica.actualizarH(i,1);
                }
                acumulado=0;        
            }     
        }
    }
    
    private void cocinar() {
        synchronized(lock){
            if(pizza==0){
                pizza=5+rand.nextInt(21);
                grafica.mostrarpizza(pizza);
                grafica.actualizarCoc(1);
                System.out.println("Ratatouille: Cómanse una pizza, hice "+pizza);
                lock.notifyAll();
            }
            try{
                Thread.sleep(3000);
                grafica.actualizarCoc(2);
                lock.wait();
            }catch(Exception ex){    
            }
        }
    }
     
    public static void main(String[] args) {
        System.out.println(numHilos-1+" Canivales vinieron a comerse unas pizzas, dense");
        grafica =new IGrafica();
        Thread[] hilos = new Thread[numHilos];
        for(int i=0; i < hilos.length;i++){
            Runnable runnable = null; 
            if(i!= (numHilos-1)){
                runnable = new Canibales(true,i,0);
            }else{
                runnable = new Canibales(false,i,1);
            }
            hilos[i] = new Thread(runnable);
            hilos[i].start();
        }   
        for(int i =0;i<hilos.length;i++){
            try{
                hilos[i].join();
            }catch(Exception ex){             
            }
        }   
    }  
}