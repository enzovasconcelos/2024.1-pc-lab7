# 2024-pc-lab7

Antes de tudo, é necessário fazer build do projeto.
Basta executar o script de build no diretório raiz do projeto.

```bash
./build.sh
```

O sistema funciona com clientes virtuais criados através de Threads
e também com a parte extra de clientes de outros processos.
O Sistema fica escutando por requisições tcp.

Para executar o sistema, usar o script run.sh.

```bash
./run.sh
```

O cliente lê os itens de um arquivo chamado 'items.txt' e 
cria um pedido com um número de itens aleatório e suas quantidades
aleatórias também.

Para executar algum cliente, usar o script run_client.sh.

```bash
./run_client.sh
```

## Processamento de pedidos e prioridade

O sistema verifica se existe os itens do pedido estão cadastrados no 
estoque, caso algum não exista, o sistema rejeita o pedido que é ignorado.
Caso os todos itens do pedido estão cadastrados, porém algum possui
quantidade insuficiente, o pedido ficará pendente até o estoque ser
reabastecido. Esse pedido pendente terá sua prioridade aumentada. Dessa
forma, ele será atendido prioritariamente quando o estoque for reabastecido.
Sendo todos os itens do pedido disponíveis e o pedido é processado.
