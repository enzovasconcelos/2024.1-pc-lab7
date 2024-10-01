import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class teste {
    
    public static void main(String[] args) {
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
        itens.add("batata");

        List<String> itensSelecionados = selecionarItensAleatorios(itens);

        System.out.println("Itens selecionados:");
        for (String item : itensSelecionados) {
            System.out.println(item);
        }
    }

    public static List<String> selecionarItensAleatorios(List<String> lista) {
        Random random = new Random();
        List<String> itensSelecionados = new ArrayList<>();

        // Gera um número aleatório entre 1 e o tamanho da lista
        int quantidadeParaSelecionar = random.nextInt(lista.size()) + 1;

        // Copia a lista original para uma lista temporária para seleção
        List<String> tempList = new ArrayList<>(lista);

        // Seleciona itens de forma aleatória e remove da lista temporária
        for (int i = 0; i < quantidadeParaSelecionar; i++) {
            int index = random.nextInt(tempList.size());
            itensSelecionados.add(tempList.get(index));
            tempList.remove(index); // Remove o item selecionado da lista temporária
        }

        return itensSelecionados;
    }

}
