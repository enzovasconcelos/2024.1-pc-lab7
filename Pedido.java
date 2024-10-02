public class Pedido {

    private Long id;
    private Long idCliente;
    private Item[] itens;

    public Pedido(Long id, Long idCliente, Item[] itens){
        this.id = id;
        this.idCliente = idCliente;
        this.itens = itens;
    }

    public Long getId(){
        return this.id;
    }

    public Long getIdCliente(){
        return this.idCliente;
    }

    public Item[] getItems(){
        return this.itens;
    }
}
