-- V1__init.sql

-- 1. Tabela de Usuários (para autenticação JWT)
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER',
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT NOW()
);

-- 2. Tabela de Veículos
CREATE TABLE veiculos (
    id SERIAL PRIMARY KEY,
    placa VARCHAR(10) UNIQUE NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    tipo VARCHAR(20) CHECK (tipo IN ('LEVE', 'PESADO')),
    ano INTEGER
);

-- 3. Tabela de Viagens
CREATE TABLE viagens (
    id SERIAL PRIMARY KEY,
    veiculo_id INTEGER REFERENCES veiculos(id) ON DELETE CASCADE,
    data_saida TIMESTAMP NOT NULL,
    data_chegada TIMESTAMP,
    origem VARCHAR(100),
    destino VARCHAR(100),
    km_percorrida DECIMAL(10,2)
);

-- 4. Tabela de Manutenções
CREATE TABLE manutencoes (
    id SERIAL PRIMARY KEY,
    veiculo_id INTEGER REFERENCES veiculos(id) ON DELETE CASCADE,
    data_inicio DATE NOT NULL,
    data_finalizacao DATE,
    tipo_servico VARCHAR(100),
    custo_estimado DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'PENDENTE'
);

-- Usuário admin padrão (senha: admin123)
INSERT INTO usuarios (nome, email, senha, role) VALUES
('Administrador', 'admin@logitrack.com', '$2a$12$QeC6mz.Iy3FJqhWRqJ0SB.F8S3/H6WjIHksFOL1sFrUdUDkIMVAPa', 'ADMIN'),
('Gestor', 'gestor@logitrack.com', '$2a$12$QeC6mz.Iy3FJqhWRqJ0SB.F8S3/H6WjIHksFOL1sFrUdUDkIMVAPa', 'USER');

-- Veículos
INSERT INTO veiculos (placa, modelo, tipo, ano) VALUES
('ABC-1234', 'Fiorino', 'LEVE', 2022),
('XYZ-9876', 'Volvo FH', 'PESADO', 2021),
('KJG-1122', 'Mercedes Sprinter', 'LEVE', 2020),
('LMN-4455', 'Scania R500', 'PESADO', 2023);

-- Viagens
INSERT INTO viagens (veiculo_id, data_saida, data_chegada, origem, destino, km_percorrida) VALUES
(1, '2024-05-01 08:00:00', '2024-05-01 18:00:00', 'São Paulo', 'Rio de Janeiro', 435.00),
(1, '2024-05-05 09:00:00', '2024-05-05 12:00:00', 'Rio de Janeiro', 'Niterói', 20.50),
(2, '2024-05-02 05:00:00', '2024-05-03 20:00:00', 'Curitiba', 'Belo Horizonte', 1000.00),
(3, '2024-05-10 07:00:00', '2024-05-10 15:00:00', 'Salvador', 'Aracaju', 350.00),
(4, '2024-05-15 06:00:00', '2024-05-16 18:00:00', 'Recife', 'Fortaleza', 800.00);

-- Manutenções
INSERT INTO manutencoes (veiculo_id, data_inicio, data_finalizacao, tipo_servico, custo_estimado, status) VALUES
(1, '2024-06-10', '2024-06-11', 'Troca de Óleo', 350.00, 'PENDENTE'),
(2, '2024-06-15', '2024-06-17', 'Revisão de Freios', 1500.00, 'PENDENTE'),
(3, '2024-05-20', '2024-05-20', 'Troca de Pneus', 2200.00, 'CONCLUIDA'),
(4, '2024-06-20', '2024-06-22', 'Revisão Geral', 3500.00, 'PENDENTE'),
(1, '2024-07-01', '2024-07-02', 'Troca de Filtros', 450.00, 'PENDENTE');
