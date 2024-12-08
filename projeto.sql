CREATE TABLE vagas (
    id SERIAL PRIMARY KEY,
    status BOOLEAN NOT NULL
);

CREATE TABLE veiculo (
    placa VARCHAR(10) PRIMARY KEY,
    modelo VARCHAR(50) NOT NULL,
    cor VARCHAR(20),
    proprietario VARCHAR(100),
    tipo VARCHAR(10) CHECK (tipo IN ('carro', 'moto', 'caminhao')) NOT NULL
);

CREATE TABLE registro (
    id SERIAL PRIMARY KEY,
    id_vaga INT NOT NULL,
    placa_veiculo VARCHAR(10) NOT NULL,
    data_entrada TIMESTAMP NOT NULL,
    data_saida TIMESTAMP,
    CONSTRAINT fk_vaga FOREIGN KEY (id_vaga) REFERENCES vagas (id) ON DELETE CASCADE,
    CONSTRAINT fk_veiculo FOREIGN KEY (placa_veiculo) REFERENCES veiculo (placa) ON DELETE CASCADE
);
