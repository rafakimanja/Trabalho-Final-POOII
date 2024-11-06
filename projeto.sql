--tabela de pessoas
CREATE TABLE pessoas (
    CPF CHAR(11) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(15)
);

--tabela de veículos
CREATE TABLE veiculos (
    placa CHAR(7) PRIMARY KEY,
    modelo VARCHAR(50) NOT NULL,
    cor VARCHAR(20) NOT NULL,
    tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('carro', 'moto', 'caminhão')),
    CPF_proprietario CHAR(11),
    CONSTRAINT FK_pessoas_veiculos FOREIGN KEY (CPF_proprietario) REFERENCES pessoas(CPF) ON DELETE CASCADE
);
