import java.util.concurrent.ConcurrentHashMap;

public class Estoque {

    public ConcurrentHashMap<Long, Long> estoque; //id e quantidade de itens

    public Estoque(){
        this.estoque = new ConcurrentHashMap<>();
    }



}
