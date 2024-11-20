CREATE TABLE Proprietario (
    CPF CHAR(11) PRIMARY KEY,
    Nome VARCHAR(100) NOT NULL,
    Telefone VARCHAR(15)
);

CREATE TABLE Veiculos (
    Placa CHAR(7) PRIMARY KEY,
    Modelo VARCHAR(50) NOT NULL,
    Cor VARCHAR(30),
    Tipo ENUM('carro', 'moto', 'caminhão') NOT NULL,
    CPF_Proprietario CHAR(11) NOT NULL,
    FOREIGN KEY (CPF_Proprietario) REFERENCES Proprietario(CPF)
);

CREATE TABLE Vagas (
    id INT PRIMARY KEY,
    Status BOOLEAN NOT NULL,
    DataHora_Entrada DATETIME,
    DataHora_Saida DATETIME,
    Placa_Veiculo CHAR(7),
    FOREIGN KEY (Placa_Veiculo) REFERENCES Veiculos(Placa)
);
