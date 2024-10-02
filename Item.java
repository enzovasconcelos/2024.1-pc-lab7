public class Item {
    Produto produto;
    int quantidade;

    public Item(Produto produto, int quantidade){
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto(){
        return this.produto;
    }

    public int getQuantidade(){
        return this.quantidade;
    }

    public String toString(){
        return "Produto: " + this.produto.getNome() + " - Quantidade: " + this.quantidade + " - Preço unitario: " + this.produto.getPreco();
    }

}
