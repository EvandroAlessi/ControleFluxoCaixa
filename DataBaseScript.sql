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
	   ON UPDATE CASCADE
	   ON DELETE RESTRICT
);

INSERT INTO CategoriaConta(Descricao, Positiva) VALUES('Categoria1',1);

INSERT INTO CategoriaConta(Descricao, Positiva) VALUES('Categoria2',0);

INSERT INTO CategoriaConta(Descricao, Positiva) VALUES('Categoria3',0);

INSERT INTO CategoriaConta(Descricao, Positiva) VALUES('Categoria4',0);

INSERT INTO CategoriaConta(Descricao, Positiva) VALUES('Categoria5',1);

INSERT INTO CategoriaConta(Descricao, Positiva) VALUES('Categoria6',1);

INSERT INTO CategoriaConta(Descricao, Positiva) VALUES('Categoria7',1);


Select * from CategoriaConta;


INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(1, 'SubCategoria1');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(2, 'SubCategoria2');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(3, 'SubCategoria3');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(4, 'SubCategoria4');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(5, 'SubCategoria5');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(5, 'SubCategoria6');

INSERT INTO SubCategoria(CategoriaContaID, Descricao) VALUES(5, 'SubCategoria7');


Select * from Movimentacao;


INSERT INTO Movimentacao(SubCategoriaID, DataOcorrencia, Descricao, Valor, FormaPagamento) VALUES(1, '2019/07/01', 'Mov001', 1000, 4);

INSERT INTO Movimentacao(SubCategoriaID, DataOcorrencia, Descricao, Valor, FormaPagamento) VALUES(2, '2019/07/01', 'Mov002', 1000, 2);

INSERT INTO Movimentacao(SubCategoriaID, DataOcorrencia, Descricao, Valor, FormaPagamento) VALUES(1, '2019/07/01', 'Mov003', 1000, 1);

INSERT INTO Movimentacao(SubCategoriaID, DataOcorrencia, Descricao, Valor, FormaPagamento) VALUES(4, '2019/07/01', 'Mov004', 1000, 1);

INSERT INTO Movimentacao(SubCategoriaID, DataOcorrencia, Descricao, Valor, FormaPagamento) VALUES(7, '2019/07/01', 'Mov005', 1000, 4);

INSERT INTO Movimentacao(SubCategoriaID, DataOcorrencia, Descricao, Valor, FormaPagamento) VALUES(3, '2019/07/01', 'Mov006', 1000, 3);

INSERT INTO Movimentacao(SubCategoriaID, DataOcorrencia, Descricao, Valor, FormaPagamento) VALUES(6, '2019/07/01', 'Mov007', 1000, 2);