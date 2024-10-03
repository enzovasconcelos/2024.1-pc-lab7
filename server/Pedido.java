import java.util.List;

public class Pedido implements Comparable<Pedido> {
    private int idCliente;
    private int id;
    private List<Item> itens;
    private int prioridade;

    public Pedido(int id, int idCliente, List<Item> itens){
        this.id = id;
        this.idCliente = idCliente;
        this.itens = itens;
        this.prioridade = 0;
    }

    public int getId(){
        return this.id;
    }

    public int getIdCliente(){
        return this.idCliente;
    }

    public List<Item> getItems(){
        return this.itens;
    }
    
    public int incrementPrioridade() {
        this.prioridade++;
        return this.prioridade;
    }
    
    public int getPrioridade() {
        return this.prioridade;
    }
    
    public float calcularTotal() {
        return itens.stream().map(Item::getProduto).map(Produto::getPreco)
            .reduce(0f, (a, b) -> a + b);
    }
    
    public int compareTo(Pedido other) {
        if (other == null) {
            throw new NullPointerException();
        }
        return this.prioridade - other.getPrioridade();
    }
}
