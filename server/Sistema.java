import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.net.*;
import java.io.IOException;

public class Sistema {
    
    public static int DELAY_RELATORIO = 30;
    public static TimeUnit DELAY_RELATORIO_UNIT = TimeUnit.SECONDS;
    public static int port = 5050;
    public static int numberOfClients = 5;

    public ExecutorService clientService;
    public ExecutorService handleClientService;
    public ExecutorService trabalhadorService;
    public BlockingQueue<Pedido> pedidos;
    public BlockingQueue<Pedido> pendentes;
    public Estoque estoque;
    public AtomicInteger idCliente = new AtomicInteger(0);
    public AtomicInteger idPedido = new AtomicInteger(0);
    public ScheduledExecutorService attEstoqueService;
    public ScheduledExecutorService relatorioVendas;

    public Sistema(){
        this.estoque = new Estoque();
        this.clientService = Executors.newFixedThreadPool(5);
        this.trabalhadorService = Executors.newFixedThreadPool(5);
        this.handleClientService = Executors.newFixedThreadPool(numberOfClients);
        this.pedidos = new PriorityBlockingQueue<>();
        this.pendentes = new LinkedBlockingQueue<Pedido>();
        this.attEstoqueService = Executors.newSingleThreadScheduledExecutor();
        this.relatorioVendas = Executors.newSingleThreadScheduledExecutor();
    }

    public void run(){

        attEstoqueService.scheduleAtFixedRate(() -> {
            estoque.run();
            recolocarPedidosPendentes();
        }, 0, 20, TimeUnit.SECONDS);

        for(int i = 0; i < 5; i++){
            this.trabalhadorService.execute(new Trabalhador(this.pedidos, this.estoque, this.pendentes));
            this.clientService.execute(new Cliente(this.idCliente, this.pedidos, this.idPedido));
        }
        relatorioVendas.scheduleWithFixedDelay(() -> {
            System.out.println(estoque.gerarRelatorio());
        }, DELAY_RELATORIO, DELAY_RELATORIO, DELAY_RELATORIO_UNIT);
        
        listenConn();
    }
    
    private void listenConn() {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println(String.format("Running on port %s", port));
            for (;;) {
                Socket conn = server.accept();
                handleClientService.submit(new HandleClient(conn, pedidos, idCliente, idPedido));
            }
        } catch(IOException e) {
            System.err.println(e);
        }
    }
    
    private void recolocarPedidosPendentes() {
        while (!pendentes.isEmpty()) {
            try {
                pedidos.put(pendentes.take());
            } catch (InterruptedException e) {}
        }
    }

    public static void main(String[] args){

        Sistema sistema = new Sistema();
        sistema.run();
    }
}
