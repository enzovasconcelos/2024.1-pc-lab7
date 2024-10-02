import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Sistema {

    // Constantes
    public final int PERIODORELATORIO = 30;
    public final TimeUnit PERIODORELATORIOUNIT = TimeUnit.SECONDS;

    public ExecutorService clientService;
    public ExecutorService trabalhadorService;
    public LinkedBlockingDeque<Pedido> pedidos;
    public Estoque estoque;
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

        for(int i = 0; i < 5; i++){
            this.clientService.execute(new Cliente());
            this.trabalhadorService.execute(new Trabalhador());
        }

        relatorioVendas.scheduleWithFixedDelay(() -> {
            System.out.println(estoque.gerarRelatorio());
        }, PERIODORELATORIO, PERIODORELATORIO, PERIODORELATORIOUNIT);

    }

    public static void main(String[] args){

        Sistema sistema = new Sistema();
        sistema.run();
    }
}
