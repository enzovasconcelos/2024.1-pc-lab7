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
}
