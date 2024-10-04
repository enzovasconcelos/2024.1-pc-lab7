import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import exceptions.ItemsNotAvailableException;
import exceptions.ItemsNotFoundException;

public class Estoque implements Runnable{
    public ConcurrentHashMap<Long, AtomicLong> estoque; //id e quantidade de itens
    public BlockingQueue<Pedido> vendidos = new LinkedBlockingQueue<Pedido>();
    public BlockingQueue<Pedido> rejeitados = new LinkedBlockingQueue<Pedido>();

    public Estoque(){
        this.estoque = new ConcurrentHashMap<>();
    }

    public void adicionarProduto(Long id, Long quantidade){
        try{
            if(estoque.containsKey(id)){
                estoque.get(id).addAndGet(quantidade);
            }else{
                estoque.put(id, new AtomicLong(quantidade));
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    
    public void removerPedido(Pedido pedido) throws ItemsNotAvailableException, 
                                                    ItemsNotFoundException, InterruptedException {
        verificarDisponibilidadeItems(pedido);
        for (Item item : pedido.getItems()) {
            AtomicLong quantidade = estoque.get(item.getProduto().getId());
            Long quantidadeAtual = quantidade.get();
            quantidade.set(quantidadeAtual - item.getQuantidade());
        }
        try {

            vendidos.put(pedido);
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    
    private void verificarDisponibilidadeItems(Pedido pedido) throws ItemsNotAvailableException, 
                                                        ItemsNotFoundException, InterruptedException{

        for (Item item : pedido.getItems()) {
            AtomicLong quantidade = estoque.get(item.getProduto().getId());
            String nomeItem = item.getProduto().getNome();
            int idCliente = pedido.getIdCliente();
            int idPedido = pedido.getId();
            if (quantidade == null) {
                rejeitados.put(pedido);
                String message = String.format("Cliente %d teve pedido %d rejeitado. %s não cadastrado", 
                                                idCliente, idPedido, nomeItem);
                throw new ItemsNotFoundException(message);
            }
            if (quantidade.get() < item.getQuantidade()) {
                String msg = String.format("cliente %d teve pedido %d pendente. %s possui quantidade insuficiente", 
                                            idCliente, idPedido, nomeItem);
                throw new ItemsNotAvailableException(msg);
            }
        }
    }

    public void run(){
        if (estoque.isEmpty()) {
            for (long i = 1; i <= 10; i++) {
                adicionarProduto(i, 20L);
            }
        }
        for (Long item : estoque.keySet()) {
            adicionarProduto(item, 30L);
        }
        System.out.println("Estoque abastecido com 200 itens de 10 produtos");
    }
    
    public String gerarRelatorio() {
        int totalProcessados = vendidos.size() + rejeitados.size();
        String out = "--- Relatório ---\n";
        out += String.format("\tPedidos processados: %d\n", totalProcessados);
        float totalVendas = calcularTotalVendas();
        out += String.format("\tValor total vendas: R$ %.2f\n", totalVendas);
        out += String.format("\tPedidos rejeitados: %d\n", rejeitados.size());
        return out;
    }
    
    private float calcularTotalVendas() {
        float total = 0;
        for (Pedido pedido : vendidos) {
            total += pedido.calcularTotal();
        }
        return total;
    }
}
