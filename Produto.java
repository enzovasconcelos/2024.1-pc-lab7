public class Produto {
    long id;
    String nome;
    float preco;

    public Produto(long id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public long getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public float getPreco(){
        return this.preco;
    }
}
