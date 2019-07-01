Create database ControleFluxoCaixa;

use ControleFluxoCaixa;


CREATE TABLE IF NOT EXISTS ContaPagar (
	ContaPagarID INT NOT NULL,

	PRIMARY KEY (ContaPagarID)
);

CREATE TABLE IF NOT EXISTS CateogriaConta (
  Codigo INT NOT NULL,
  Descricao VARCHAR(126) NULL,
  Positiva TINYINT NOT NULL,
  PRIMARY KEY (Codigo));


CREATE TABLE IF NOT EXISTS FluxoCaixa (
	FluxoCaixaID INT NOT NULL,
	DataOcorrencia VARCHAR(45) NOT NULL,
	Descricao VARCHAR(126) NULL,
	Valor DOUBLE NOT NULL,
	FormaPagamento INT NOT NULL,
	PRIMARY KEY (FluxoCaixaID)
);

CREATE TABLE IF NOT EXISTS ContaReceber (
	ContaReceberID INT NOT NULL,

	PRIMARY KEY (ContaReceberID)
);
