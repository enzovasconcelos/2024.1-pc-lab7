import java.net.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class HandleClient implements Runnable {
    
    private Socket conn;
    private BlockingQueue<Pedido> pedidos;
    private AtomicInteger idPedido;
    private AtomicInteger idCliente;

    public HandleClient(Socket conn, BlockingQueue<Pedido> pedidos, AtomicInteger idCliente,
                        AtomicInteger idPedido) {
        this.conn = conn;
        this.pedidos = pedidos;
        this.idPedido = idPedido;
        this.idCliente = idCliente;
    }
    
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
            String[] request = in.readLine().split(" ");
            Integer idPedido = putOnPedidos(request);
            out.println(String.format("Pedido adicionado! Id: %d", idPedido));
        } catch(IOException e) {
            System.err.println(e);
        }
    }
    
    private Integer putOnPedidos(String[] request) {
        Integer idPedidoResult = null;
        int numberOfItems = request.length / 2;
        List<Item> itens = new ArrayList<Item>(numberOfItems);
        for (int i = 0; i < numberOfItems; i += 2) {
            String nome = request[i];
            Long quantidade = Long.parseLong(request[i + 1], 10);
            itens.add(new Item(new Produto(nome), quantidade));
        }
        try {
            idPedidoResult = idPedido.incrementAndGet();
            pedidos.put(new Pedido(idPedidoResult, idCliente.incrementAndGet(), itens));
        } catch (InterruptedException e) {
            System.err.println(e);
        }

        return idPedidoResult;
    }

}
