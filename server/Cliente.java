import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Cliente implements Runnable {
    private int idCliente;
    private BlockingQueue<Pedido> pedidos;
    private AtomicInteger idPedido;

    public Cliente(AtomicInteger id, BlockingQueue<Pedido> pedidos, AtomicInteger idPedido){
        this.idCliente = id.incrementAndGet();
        this.pedidos = pedidos;
        this.idPedido = idPedido;
    }

    public void run(){
        while (true){
            try{
                List<Item> itens = gerarItens();
                Pedido pedido = new Pedido(idPedido.incrementAndGet(), idCliente, itens);
                this.pedidos.put(pedido);
                Thread.sleep(5000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static List<Item> gerarItens(){
        List<String> itens = new ArrayList<>();
        itens.add("pizza");
        itens.add("mamao");
        itens.add("goiaba");
        itens.add("cafe");
        itens.add("pao");
        itens.add("leite");
        itens.add("acucar");
        itens.add("miojo");
        itens.add("lasanha");

        List<Item> itensSelecionados = selecionarItensAleatorios(itens);

        return itensSelecionados;
    }

    public static List<Item> selecionarItensAleatorios(List<String> lista) {
        Random random = new Random();
        //inicializando a lista que vai conter os itens aleatorios no final
        List<Item> itensSelecionados = new ArrayList<>();
        // Gera um número aleatório entre 1 e o tamanho da lista
        int quantidadeParaSelecionar = random.nextInt(lista.size()) + 1;
        // Copia a lista original para uma lista temporária para seleção
        List<String> tempList = new ArrayList<>(lista);
        // Seleciona itens de forma aleatória e remove da lista temporária
        for (int i = 0; i < quantidadeParaSelecionar; i++) {
            int index = random.nextInt(tempList.size());
            String nomeProduto = tempList.get(index);
            Produto produto = new Produto(nomeProduto);
            Long quantidade = random.nextLong(6) + 1; //gera numeros aleatorios de 1 a 4
            itensSelecionados.add(new Item(produto, quantidade));
            tempList.remove(index); // Remove o item selecionado da lista temporária
        }

        return itensSelecionados;
    }


}
