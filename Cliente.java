import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cliente implements Runnable {

    public Cliente(){
        
    }
    public void run(){
        while (true){
            System.out.println("Cliente chegou");
            try{
                Random random = new Random();
                
                //List<String> itens = new ArrayList<>();
                //int qnt = random.nextInt(itens.size()) + 1;
                
                //Pedido pedido = new Pedido();
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }


}
