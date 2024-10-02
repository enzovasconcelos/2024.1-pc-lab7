import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.List;
import java.util.LinkedList;

public class Estoque implements Runnable{
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();
    public ConcurrentHashMap<Long, AtomicLong> estoque; //id e quantidade de itens
    public List<Item> vendidos = new LinkedList<Item>();
    public List<Item> rejeitados = new LinkedList<Item>();

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

    public Boolean removerProduto(Item item){
        writeLock.lock();
        try{
            Long id = item.getProduto().getId();
            Long quantidade = item.getQuantidade();
            if(estoque.containsKey(id)){
                if(estoque.get(id).get() < quantidade){
                    rejeitados.add(item);
                    return false;
                }else{
                    estoque.get(id).addAndGet(-quantidade);
                    vendidos.add(item);
                    return true;
                }
            }else{
                System.out.println("Item não encontrado");
                return false;
            }
        }finally{
            writeLock.unlock();
        }
    }

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
        out += String.format("\tValor total vendas: R$ %f\n", totalVendas);
        out += String.format("\tPedidos rejeitados: %d\n", rejeitados.size());
        return out;
    }
    
    private float calcularTotalVendas() {
        float total = 0;
        for (Item item : vendidos) {
            total += item.getProduto().getPreco() * item.getQuantidade();
        }
        return total;
    }
}
