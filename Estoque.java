import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.List;
import java.util.LinkedList;
import exceptions.ItemsNotAvailableException;
import exceptions.ItemsNotFoundException;

public class Estoque implements Runnable{
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();
    public ConcurrentHashMap<Long, AtomicLong> estoque; //id e quantidade de itens
    public List<Pedido> vendidos = new LinkedList<Pedido>();
    public List<Pedido> rejeitados = new LinkedList<Pedido>();

    public Estoque(){
        this.estoque = new ConcurrentHashMap<>();
    }

    public void adicionarProduto(Long id, Long quantidade){
        writeLock.lock();
        try{
            if(estoque.containsKey(id)){
                estoque.get(id).addAndGet(quantidade);
            }else{
                estoque.put(id, new AtomicLong(quantidade));
            }
        }finally{
            writeLock.unlock();
        }
    }
    
    public void removerPedido(Pedido pedido) throws ItemsNotAvailableException, 
                                                    ItemsNotFoundException {
        verificarDisponibilidadeItems(pedido);
        writeLock.lock();
        for (Item item : pedido.getItems()) {
            estoque.remove(item.getProduto().getId());
        }
        writeLock.unlock();
        vendidos.add(pedido);
    }
    
    private void verificarDisponibilidadeItems(Pedido pedido) throws ItemsNotAvailableException, 
                                                        ItemsNotFoundException{

        for (Item item : pedido.getItems()) {
            AtomicLong quantidade = estoque.get(item.getProduto().getId());
            String nomeItem = item.getProduto().getNome();
            if (quantidade == null) {
                String message = String.format("Pedido rejeitado. %s não cadastrado", nomeItem);
                throw new ItemsNotFoundException(message);
            }
            if (quantidade.get() < item.getQuantidade()) {
                rejeitados.add(pedido);
                String message = String.format("Pedido rejeitado. %s não disponível", nomeItem);
                throw new ItemsNotAvailableException(message);
            }
        }
    }

    // TODO: Remover
    /*public Boolean removerProduto(Item item){
        writeLock.lock();
        try{
            Long id = item.getProduto().getId();
            Long quantidade = item.getQuantidade();
            if(estoque.containsKey(id)){
                if(estoque.get(id).get() < quantidade){
                    return false;
                }else{
                    estoque.get(id).addAndGet(-quantidade);
                    return true;
                }
            }else{
                System.out.println("Item não encontrado");
                return false;
            }
        }finally{
            writeLock.unlock();
        }
    }*/

    public void run(){
        if (estoque.isEmpty()) {
            for (long i = 1; i <= 10; i++) {
                adicionarProduto(i, 20L);
            }
        }
        for (Long item : estoque.keySet()) {
            adicionarProduto(item, 20L);
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
