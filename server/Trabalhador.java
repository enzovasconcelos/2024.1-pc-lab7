import java.util.concurrent.BlockingQueue;
import exceptions.ItemsNotAvailableException;
import exceptions.ItemsNotFoundException;

public class Trabalhador implements Runnable{
    private BlockingQueue<Pedido> pedidos;
    private Estoque estoque;
    private BlockingQueue<Pedido> pendentes;

    public Trabalhador(BlockingQueue<Pedido> pedidos, Estoque estoque, BlockingQueue<Pedido> pendentes){
        this.pedidos = pedidos;
        this.estoque = estoque;
        this.pendentes = pendentes;
    }

    @Override
    public void run(){
        while (true) {
            try{
                Pedido pedidoAtual = pedidos.take();
                processarPedido(pedidoAtual);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            } 
        }
    }
    
    private void processarPedido(Pedido pedido) throws InterruptedException {
        try {
            this.estoque.removerPedido(pedido);
            int idCliente = pedido.getIdCliente();
            int idPedido = pedido.getId();
            String msg = String.format("pedido %d do cliente %d foi processado ", idPedido, idCliente);
            System.out.println(msg);
        } catch(ItemsNotAvailableException e) {
            pedido.incrementPrioridade();
            pendentes.put(pedido);
            System.out.println(e.getMessage());
        } catch(ItemsNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
