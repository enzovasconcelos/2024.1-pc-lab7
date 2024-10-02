import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class Trabalhador implements Runnable{
    public LinkedBlockingDeque<Pedido> pedidos;
    private Estoque estoque;

    public Trabalhador( LinkedBlockingDeque<Pedido> pedidos, Estoque estoque){
        this.pedidos = pedidos;
        this.estoque = estoque;
    }

    @Override
    public void run(){
        while (true) {
            try{
                Pedido pedidoAtual = pedidos.take();
                List<Item> listaItems = pedidoAtual.getItems();
                Boolean remocaoComSucesso = true;

                // removerItem(idItem, quantidade)
                for(Item item: listaItems){
                    remocaoComSucesso = this.estoque.removerProduto(item);

                    if(!remocaoComSucesso){
                        //TODO O pedido rejeitado pode ir eventualmente para uma lista de espera, caso não possa ser atendido.
                        System.out.println("Pedido rejeitado pois não temos quantidade em estoque suficiente para o produto " + item.getProduto().getId());
                        break;
                    }
                }
                if(remocaoComSucesso){
                    System.out.println("Pedido " + pedidoAtual.getId() + "do Cliente " + pedidoAtual.getIdCliente() + " Foi processado com sucesso!");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }
       
    }
}
