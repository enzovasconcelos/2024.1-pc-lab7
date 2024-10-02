import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Estoque implements Runnable{
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();
    public ConcurrentHashMap<Long, AtomicLong> estoque; //id e quantidade de itens

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

    public Boolean removerProduto(Long id, Long quantidade){
        writeLock.lock();
        try{
            if(estoque.containsKey(id)){
                if(estoque.get(id).get() < quantidade){
                    System.out.println("Quantidade insuficiente");
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
    }

    public void run(){
        for (Long item : estoque.keySet()) {
            adicionarProduto(item, 20L);
        }
    }
}
