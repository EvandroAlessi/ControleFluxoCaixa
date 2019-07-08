Create database ControleFluxoCaixa;

use ControleFluxoCaixa;

CREATE TABLE IF NOT EXISTS CategoriaConta (
	CategoriaContaID INT NOT NULL auto_increment,
	Descricao VARCHAR(126) NULL,
	Positiva TINYINT(1) NOT NULL,
  
	PRIMARY KEY (CategoriaContaID)
);

CREATE TABLE IF NOT EXISTS SubCategoria (
	SubCategoriaID INT NOT NULL auto_increment,
    CategoriaContaID INT NOT NULL,
	Descricao VARCHAR(126) NULL,
    
	PRIMARY KEY (SubCategoriaID),
    FOREIGN KEY fk_categoria(CategoriaContaID)
	   REFERENCES CategoriaConta(CategoriaContaID)
	   ON UPDATE CASCADE
	   ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS Movimentacao (
	MovimentacaoID INT NOT NULL auto_increment,
    SubCategoriaID INT NOT NULL,
	DataOcorrencia Date NOT NULL,
	Descricao VARCHAR(126) NULL,
	Valor DOUBLE NOT NULL,
	FormaPagamento INT NOT NULL,
    
	PRIMARY KEY (MovimentacaoID),
    FOREIGN KEY fk_subcategoria(SubCategoriaID)
	   REFERENCES SubCategoria(SubCategoriaID)
	   ON UPDATE RESTRICT
	   ON DELETE RESTRICT
);

INSERT INTO CategoriaConta(Descricao, Positiva) VALUES('Moradia',0);

INSERT INTO CategoriaConta(Descricao, Positiva) VALUES('Transporte',0);

INSERT INTO CategoriaConta(Descricao, Positiva) VALUES('Vestuario',0);

INSERT INTO CategoriaConta(Descricao, Positiva) VALUES('Lazer',0);

INSERT INTO CategoriaConta(Descricao, Positiva) VALUES('Emprego',1);

INSERT INTO CategoriaConta(Descricao, Positiva) VALUES('Investimento',1);

INSERT INTO CategoriaConta(Descricao, Positiva) VALUES('Freelancer',1);


Select * from CategoriaConta;


INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(1, 'Água');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(1, 'Energia');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(1, 'Gás');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(1, 'Condominio');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(1, 'Internet');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(2, 'Carro');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(2, 'Onibus');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(2, 'Bicicleta');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(3, 'Camisas');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(3, 'Sapatos');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(3, 'Blazers');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(3, 'Calças');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(4, 'Salário');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(4, 'Horas extras');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(4, 'bonus');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(5, 'Habitação');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(5, 'Bolsa de valores');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(5, 'Selic');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(6, 'Programação');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(6, 'Design');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(6, 'Consultoria');


Select * from Movimentacao;


INSERT INTO Movimentacao(SubCategoriaID, DataOcorrencia, Descricao, Valor, FormaPagamento) VALUES(1, '2019/03/06', 'mov001', 100, 4);

INSERT INTO Movimentacao(SubCategoriaID, DataOcorrencia, Descricao, Valor, FormaPagamento) VALUES(2, '2019/07/01', 'Mov002', 120, 2);

INSERT INTO Movimentacao(SubCategoriaID, DataOcorrencia, Descricao, Valor, FormaPagamento) VALUES(1, '2019/09/08', 'Mov003', 360, 1);

INSERT INTO Movimentacao(SubCategoriaID, DataOcorrencia, Descricao, Valor, FormaPagamento) VALUES(4, '2019/06/07', 'Mov004', 1475, 1);

INSERT INTO Movimentacao(SubCategoriaID, DataOcorrencia, Descricao, Valor, FormaPagamento) VALUES(7, '2019/07/15', 'Mov005', 22.35, 4);

INSERT INTO Movimentacao(SubCategoriaID, DataOcorrencia, Descricao, Valor, FormaPagamento) VALUES(3, '2019/04/22', 'Mov006', 295, 3);

INSERT INTO Movimentacao(SubCategoriaID, DataOcorrencia, Descricao, Valor, FormaPagamento) VALUES(6, '2019/08/11', 'Mov007', 785, 2);