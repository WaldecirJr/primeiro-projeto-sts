CREATE TABLE IF NOT EXISTS `empresa` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cnpj` VARCHAR(255) NOT NULL,
  `data_atualizacao` DATETIME NOT NULL,
  `data_criacao` DATETIME NOT NULL,
  `razao_social` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `funcionario` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cpf` VARCHAR(255) NOT NULL,
  `data_atualizacao` DATETIME NOT NULL,
  `data_criacao` DATETIME NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `perfil` VARCHAR(255) NOT NULL,
  `qtd_horas_almoco` FLOAT DEFAULT NULL,
  `qtd_horas_trabalho_dia` FLOAT DEFAULT NULL,
  `senha` VARCHAR(255) NOT NULL,
  `valor_hora` DECIMAL(19,2) DEFAULT NULL,
  `empresa_id` BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4cm1kg523jlopyexjbmi6y54j` (`empresa_id`),
  CONSTRAINT `FK4cm1kg523jlopyexjbmi6y54j` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `lancamento` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `data` DATETIME NOT NULL,
  `data_atualizacao` DATETIME NOT NULL,
  `data_criacao` DATETIME NOT NULL,
  `descricao` VARCHAR(255) DEFAULT NULL,
  `localizacao` VARCHAR(255) DEFAULT NULL,
  `tipo` VARCHAR(255) NOT NULL,
  `funcionario_id` BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK46i4k5vl8wah7feutye9kbpi4` (`funcionario_id`),
  CONSTRAINT `FK46i4k5vl8wah7feutye9kbpi4` FOREIGN KEY (`funcionario_id`) REFERENCES `funcionario` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
