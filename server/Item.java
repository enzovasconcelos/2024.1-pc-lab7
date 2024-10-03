public class Item {
    Produto produto;
    Long quantidade;

    public Item(Produto produto, Long quantidade){
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto(){
        return this.produto;
    }

    public Long getQuantidade(){
        return this.quantidade;
    }

    public String toString(){
        return "Produto: " + this.produto.getNome() + " - Quantidade: " + this.quantidade + " - Preço unitario: " + this.produto.getPreco();
    }

}
