package br.com.bse.tags;

public class Tag {
	public static final byte GET = 0;
	public static final byte POST = 1;
	public static final byte TIPO_NUMERO = 2;
	public static final byte FALSE = 0;
	public static final byte TRUE = 1;
	public static final String SUCCESS = "success";
	public static final String SIM = "Sim";
	public static final String NAO = "Não";
	
	// UF_ATENDIMENTO
	//public static final String URL_UF_DISPONIVEIS = "http://10.0.2.2/pizzaria/uf_disponiveis.php";
	public static final String URL_UF_DISPONIVEIS = "http://www.bsetechnology.com.br/mobile_pizzaria/uf_disponiveis.php";
	public static final String ID_UF = "Id_Uf";
	public static final String SIGLA_UF = "Sigla_Uf";
	public static final String UF_ATENDIMENTO = "Tb_Uf";
	
	// CIDADES_ATENDIMENTO
	//public static final String URL_CIDADES_DISPONIVEIS = "http://10.0.2.2/pizzaria/cidades_disponiveis.php";
	public static final String URL_CIDADES_DISPONIVEIS = "http://www.bsetechnology.com.br/mobile_pizzaria/cidades_disponiveis.php";
	public static final String ID_CIDADE = "Id_Cidade";
	public static final String ID_UF_ATENDIMENTO = "Id_Uf";
	public static final String CIDADE_ATENDIMENTO = "Tb_Cidade";
	public static final String DESCRICAO_CIDADE = "Nome_Cidade";
	
	//CATEGORIA DE PRODUTOS
	//public static final String URL_CATEGORIAS = "http://10.0.2.2/pizzaria/categorias.php";
	public static final String URL_CATEGORIAS = "http://www.bsetechnology.com.br/mobile_pizzaria/categorias.php";
	public static final String ID_CATEGORIA = "Id_Categ";
	public static final String DESCRICAO_CATEGORIA = "Descricao_Categ";
	public static final String CATEGORIA_PRODUTO = "Categoria_Prod";
	
	//PRODUTO
	//public static final String URL_PRODUTOS_CATEGORIA = "http://10.0.2.2/pizzaria/produtos_categoria.php";
	public static final String URL_PRODUTOS_CATEGORIA = "http://www.bsetechnology.com.br/mobile_pizzaria/produtos_categoria.php";
	public static final String ID_PRODUTO = "Id_Prod";
	public static final String DESCRICAO_PRODUTO = "Descricao_Prod";
	public static final String DETALHES_PRODUTO = "Detalhes_Prod";
	public static final String PRECO_PRODUTO = "Preco_Prod";
	public static final String IMAGEM_PRODUTO = "Imagem_Prod";
	public static final String PRODUTO = "produto";
	
	//ITEM ADICIONAL
	//public static final String URL_ITENS_ADICIONAIS = "http://10.0.2.2/pizzaria/itens_adicionais.php";
	public static final String URL_ITENS_ADICIONAIS = "http://www.bsetechnology.com.br/mobile_pizzaria/itens_adicionais.php";
	public static final String ID_ITEM_ADICIONAL = "Id_Item";
	public static final String ITEM_ADICIONAL = "Tb_Item_Adicional";
	public static final String DESCRICAO_ITEM_ADICIONAL = "Descricao_Item";
	public static final String PRECO_ITEM_ADICIONAL = "Preco_Item";
	
	//PEDIDO
	//public static final String URL_ADD_PEDIDO = "http://10.0.2.2/pizzaria/add_pedido.php";
	public static final String URL_ADD_PEDIDO = "http://www.bsetechnology.com.br/mobile_pizzaria/add_pedido.php";
	public static final String URL_MAX_ID_PEDIDO = "http://www.bsetechnology.com.br/mobile_pizzaria/get_max_id_pedido.php";
	public static final boolean PREENCHIDOS = true;
	public static final String TB_PED = "Tb_Pedido";
	public static final String ID_PED = "Id_Ped";
	public static final String NOME_CLI_PED = "Nome_Cli_Ped";
	public static final String TEL_PED = "Telefone_Cli_Ped";
	public static final String VALOR_TOTAL_PED = "Valor_Total_Ped";
	public static final String FORMA_PAG_PED = "Forma_Pag_Ped";
	public static final String TROCO_PED = "Valor_Troco_Ped";
	public static final String ENDERECO_PED = "Endereco_Ped";
	public static final String NUM_END_PED = "Num_End_Ped";
	public static final String BAIRRO_PED = "Bairro_Ped";
	public static final String CIDADE_PED = "Cidade_Ped";
	public static final String UF_PED = "Uf_Ped";
	public static final String STATUS_PED = "Status_Ped";
	public static final String DATA_PED = "Data_Ped";
	
	//PAGAMENTO
	//public static final String URL_FORMA_PAG = "http://10.0.2.2/pizzaria/forma_pag.php";
	public static final String URL_FORMA_PAG = "http://www.bsetechnology.com.br/mobile_pizzaria/forma_pag.php";
	public static final String FORMA_PAG = "Tb_Forma_Pag";
	public static final String ID_FORMA_PAG = "Id_Forma_Pag";
	public static final String DESC_FORMA_PAG = "Desc_Forma_Pag";
	public static final byte PEDIDO_FINALIZADO = 2;
	public static final String NULL = "NULL";
	
	//ITENS PEDIDO
	//public static final String URL_ADD_ITENS_PED = "http://10.0.2.2/pizzaria/add_itens_pedido.php";
	public static final String URL_ADD_ITENS_PED = "http://www.bsetechnology.com.br/mobile_pizzaria/add_itens_pedido.php";
	public static final String VALOR_SQL = "Valor_Sql";
	public static final String TB_ITEM_PEDIDO = "Tb_Item_Pedido";
	public static final byte QTD_CAMPOS_ITENS = 8;
	
	//INFO PEDIDO
	//public static final String URL_CANC_PED = "http://10.0.2.2/pizzaria/cancelar_pedido.php";
	public static final String URL_CANC_PED = "http://www.bsetechnology.com.br/mobile_pizzaria/cancelar_pedido.php";
}
