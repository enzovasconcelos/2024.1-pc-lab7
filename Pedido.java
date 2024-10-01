public class Pedido {

    private Long id;
    private Item[] itens;

    public Pedido(Item[] itens){
        this.itens = itens;
    }
}
