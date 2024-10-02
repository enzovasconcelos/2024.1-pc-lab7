import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import exceptions.ItemsNotAvailableException;
import exceptions.ItemsNotFoundException;

public class Trabalhador implements Runnable{
    public LinkedBlockingDeque<Pedido> pedidos;
    private Estoque estoque;

    public Trabalhador( LinkedBlockingDeque<Pedido> pedidos, Estoque estoque){
        this.pedidos = pedidos;
        this.estoque = estoque;
    }

    @Override
    public void run(){
        while (true) {
            try{
                Pedido pedidoAtual = pedidos.take();
                this.estoque.removerPedido(pedidoAtual);
                System.out.println("Pedido processado com sucesso!");
            } catch(ItemsNotAvailableException | ItemsNotFoundException  e) {
                System.out.println(e.getMessage());
            } catch(Exception e){
                e.printStackTrace();
            }
            
        }
       
    }
}
