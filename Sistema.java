import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class Sistema {

    public ExecutorService clientService;
    public ExecutorService trabalhadorService;
    public LinkedBlockingDeque<Pedido> pedidos;
    public Estoque estoque;
    public AtomicInteger idCliente = new AtomicInteger(0);
    public AtomicInteger idPedido = new AtomicInteger(0);
    public ScheduledExecutorService attEstoqueService;
    public ScheduledExecutorService relatorioVendas;

    public Sistema(){
        this.estoque = new Estoque();
        this.clientService = Executors.newFixedThreadPool(5);
        this.trabalhadorService = Executors.newFixedThreadPool(5);
        this.pedidos = new LinkedBlockingDeque<>();
        this.attEstoqueService = Executors.newSingleThreadScheduledExecutor();

    }

    public void run(){
        for(int i = 0; i < 5; i++){
            this.clientService.execute(new Cliente(this.idCliente, this.pedidos, this.idPedido));
            this.trabalhadorService.execute(new Trabalhador(this.pedidos, this.estoque));
        }

    }

    public static void main(String[] args){

        Sistema sistema = new Sistema();
        sistema.run();
    }
}
