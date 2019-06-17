CREATE TABLE IF NOT EXISTS `ControleFluxoCaixa`.`ContaPagar` (
)

CREATE TABLE IF NOT EXISTS `ControleFluxoCaixa`.`CateogriaConta` (
  `Codigo` INT NOT NULL,
  `Descricao` VARCHAR(126) NULL,
  `Positiva` TINYINT NOT NULL,
  PRIMARY KEY (`Codigo`))


CREATE TABLE IF NOT EXISTS `ControleFluxoCaixa`.`FluxoCaixa` (
  `FluxoCaixaID` INT NOT NULL,
  `DataOcorrencia` VARCHAR(45) NOT NULL,
  `Descricao` VARCHAR(126) NULL,
  `Valor` DOUBLE NOT NULL,
  `FormaPagamento` INT NOT NULL,
  PRIMARY KEY (`FluxoCaixaID`))

CREATE TABLE IF NOT EXISTS `ControleFluxoCaixa`.`ContaReceber` (

)

CREATE TABLE IF NOT EXISTS `ControleFluxoCaixa`.`ContaPagar` (

)
