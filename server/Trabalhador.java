import java.util.List;
import java.util.concurrent.BlockingQueue;
import exceptions.ItemsNotAvailableException;
import exceptions.ItemsNotFoundException;

public class Trabalhador implements Runnable{
    private BlockingQueue<Pedido> pedidos;
    private Estoque estoque;
    private List<Pedido> pendentes;

    public Trabalhador(BlockingQueue<Pedido> pedidos, Estoque estoque, List<Pedido> pendentes){
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
    
    private void processarPedido(Pedido pedido) {
        try {
            this.estoque.removerPedido(pedido);
            int idCliente = pedido.getIdCliente();
            int idPedido = pedido.getId();
            String msg = String.format("cliente %d processou pedido %d", idCliente, idPedido);
            System.out.println(msg);
        } catch(ItemsNotAvailableException e) {
            System.out.println(e.getMessage());
        } catch(ItemsNotFoundException e){
            pedido.incrementPrioridade();
            pendentes.add(pedido);
            System.out.println(e.getMessage());
        }
    }
}
