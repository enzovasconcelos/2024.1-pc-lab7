import java.util.HashMap;

public class Produto {
    Integer id;
    String nome;
    float preco;

    public Produto(String nome){
        this.id = this.mapId(nome);
        this.nome = nome;
        this.preco = this.mapPreco(this.id);
    }

    private float mapPreco(Integer idproduto) {
        HashMap<Integer, Float> mapa = new HashMap<>();

        mapa.put(1, 15.00f); // lasanha
        mapa.put(2, 1.50f);  // miojo
        mapa.put(3, 3.50f);  // acucar
        mapa.put(4, 4.00f);  // leite
        mapa.put(5, 0.50f);  // pao
        mapa.put(6, 8.00f);  // cafe
        mapa.put(7, 2.00f);  // goiaba
        mapa.put(8, 1.20f);  // banana
        mapa.put(9, 3.00f);  // mamao
        mapa.put(10, 20.00f); // pizza

        return mapa.get(idproduto);
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

    public int mapId(String nome){
        HashMap<String, Integer> mapa = new HashMap<>();
        mapa.put("lasanha", 1);
        mapa.put("miojo", 2);
        mapa.put("acucar", 3);
        mapa.put("leite", 4);
        mapa.put("pao", 5);
        mapa.put("cafe", 6);
        mapa.put("goiaba", 7);
        mapa.put("banana", 8);
        mapa.put("mamao", 9);
        mapa.put("pizza", 10);

        return mapa.get(nome);

    }
}
