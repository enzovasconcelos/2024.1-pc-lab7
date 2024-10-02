import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;

public class Sistema {
    
    public static int DELAY_RELATORIO = 30;
    public static TimeUnit DELAY_RELATORIO_UNIT = TimeUnit.SECONDS;

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
        this.relatorioVendas = Executors.newSingleThreadScheduledExecutor();
    }

    public void run(){

        attEstoqueService.scheduleAtFixedRate(() -> {
            estoque.run();
        }, 0, 20, TimeUnit.SECONDS);

        for(int i = 0; i < 5; i++){
            this.trabalhadorService.execute(new Trabalhador(this.pedidos, this.estoque));
            this.clientService.execute(new Cliente(this.idCliente, this.pedidos, this.idPedido));
        }
        relatorioVendas.scheduleWithFixedDelay(() -> {
            System.out.println(estoque.gerarRelatorio());
        }, DELAY_RELATORIO, DELAY_RELATORIO, DELAY_RELATORIO_UNIT);
    }

    public static void main(String[] args){

        Sistema sistema = new Sistema();
        sistema.run();
    }
}
