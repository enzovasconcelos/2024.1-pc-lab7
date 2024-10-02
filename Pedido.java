import java.util.List;

public class Pedido {
    private int idCliente;
    private int id;
    private List<Item> itens;

    public Pedido(int id, int idCliente, List<Item> itens){
        this.id = id;
        this.idCliente = idCliente;
        this.itens = itens;
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
    
    public float calcularTotal() {
        return itens.stream().map(Item::getProduto).map(Produto::getPreco)
            .reduce(0f, (a, b) -> a + b);
    }
}
