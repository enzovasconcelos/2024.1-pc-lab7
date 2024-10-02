import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;

public class Sistema {

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

    }

    public void run(){

        for(int i = 0; i < 5; i++){
            this.clientService.execute(new Cliente());
            this.trabalhadorService.execute(new Trabalhador(this.pedidos, this.estoque));
        }

    }

    public static void main(String[] args){

        Sistema sistema = new Sistema();
        sistema.run();
    }
}
