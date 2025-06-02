CREATE DATABASE ProjetoSQL;
USE ProjetoSQL;

CREATE TABLE Marca (
    id_marca INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE Carro (
    id_carro INT PRIMARY KEY AUTO_INCREMENT,
    id_marca INT,
    nome VARCHAR(100) NOT NULL,
    FOREIGN KEY (id_marca) REFERENCES Marca(id_marca)
);

CREATE TABLE Piloto (
    id_piloto INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE Carro_Piloto (
    id_carro INT,
    id_piloto INT,
    PRIMARY KEY (id_carro, id_piloto),
    FOREIGN KEY (id_carro) REFERENCES Carro(id_carro),
    FOREIGN KEY (id_piloto) REFERENCES Piloto(id_piloto)
);

CREATE TABLE Equipamento_Seguranca (
    id_equipamento INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE Piloto_Equipamento (
    id_piloto INT,
    id_equipamento INT,
    PRIMARY KEY (id_piloto, id_equipamento),
    FOREIGN KEY (id_piloto) REFERENCES Piloto(id_piloto),
    FOREIGN KEY (id_equipamento) REFERENCES Equipamento_Seguranca(id_equipamento)
);

CREATE TABLE Patrocinador (
    id_patrocinador INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE Marca_Patrocinador (
    id_marca INT,
    id_patrocinador INT,
    PRIMARY KEY (id_marca, id_patrocinador),
    FOREIGN KEY (id_marca) REFERENCES Marca(id_marca),
    FOREIGN KEY (id_patrocinador) REFERENCES Patrocinador(id_patrocinador)
);

-- Inserções
INSERT INTO Marca (nome) VALUES ('Marca A');
INSERT INTO Marca (nome) VALUES ('Marca B');

INSERT INTO Carro (id_marca, nome) VALUES (1, 'Carro 1');
INSERT INTO Carro (id_marca, nome) VALUES (2, 'Carro 2');

INSERT INTO Piloto (nome) VALUES ('Piloto 1');
INSERT INTO Piloto (nome) VALUES ('Piloto 2');

INSERT INTO Equipamento_Seguranca (nome) VALUES ('Capacete');
INSERT INTO Equipamento_Seguranca (nome) VALUES ('Luvas');

INSERT INTO Patrocinador (nome) VALUES ('Patrocinador X');
INSERT INTO Patrocinador (nome) VALUES ('Patrocinador Y');

-- Relacionamentos de muitos para muitos
INSERT INTO Carro_Piloto (id_carro, id_piloto) VALUES (1, 1);
INSERT INTO Carro_Piloto (id_carro, id_piloto) VALUES (2, 2);

INSERT INTO Piloto_Equipamento (id_piloto, id_equipamento) VALUES (1, 1);
INSERT INTO Piloto_Equipamento (id_piloto, id_equipamento) VALUES (2, 2);

INSERT INTO Marca_Patrocinador (id_marca, id_patrocinador) VALUES (1, 1);
INSERT INTO Marca_Patrocinador (id_marca, id_patrocinador) VALUES (2, 2);

-- Atualizações
UPDATE Carro SET nome = 'Carro Atualizado' WHERE id_carro = 1;

UPDATE Piloto SET nome = 'Piloto Atualizado' WHERE id_piloto = 1;

-- Excluindo
DELETE FROM Carro_piloto WHERE id_carro = 2; /*deletando antes para conseguir deletar em Carro */
DELETE FROM Carro WHERE id_carro = 2;
DELETE FROM piloto_equipamento WHERE id_piloto = 2;  /mesma coisa no de cima, deletando em baixo para conseguir deletar em Piloto/
DELETE FROM Piloto WHERE id_piloto = 2;

-- Consultas (com JOIN)
SELECT Carro.nome AS Carro, Marca.nome AS Marca, Piloto.nome AS Piloto FROM Carro
JOIN Marca ON Carro.id_marca = Marca.id_marca
JOIN Carro_Piloto ON Carro.id_carro = Carro_Piloto.id_carro
JOIN Piloto ON Carro_Piloto.id_piloto = Piloto.id_piloto;

SELECT Marca.nome AS Marca, Patrocinador.nome AS Patrocinador FROM Marca
JOIN Marca_Patrocinador ON Marca.id_marca = Marca_Patrocinador.id_marca
JOIN Patrocinador ON Marca_Patrocinador.id_patrocinador = Patrocinador.id_patrocinador;

SELECT Piloto.nome AS Piloto, Equipamento_Seguranca.nome AS Equipamento FROM Piloto
JOIN Piloto_Equipamento ON Piloto.id_piloto = Piloto_Equipamento.id_piloto
JOIN Equipamento_Seguranca ON Piloto_Equipamento.id_equipamento = Equipamento_Seguranca.id_equipamento;

-- Views
CREATE VIEW CarrosComPilotos AS
SELECT Carro.nome AS Carro, Marca.nome AS Marca, GROUP_CONCAT(Piloto.nome) AS Pilotos FROM Carro
JOIN Marca ON Carro.id_marca = Marca.id_marca
JOIN Carro_Piloto ON Carro.id_carro = Carro_Piloto.id_carro
JOIN Piloto ON Carro_Piloto.id_piloto = Piloto.id_piloto
GROUP BY Carro.id_carro;

CREATE VIEW PatrocinadoresPorMarca AS SELECT Marca.nome AS Marca, GROUP_CONCAT(Patrocinador.nome) AS Patrocinadores FROM Marca
JOIN Marca_Patrocinador ON Marca.id_marca = Marca_Patrocinador.id_marca
JOIN Patrocinador ON Marca_Patrocinador.id_patrocinador = Patrocinador.id_patrocinador GROUP BY Marca.id_marca;

-- Triggers
DELIMITER //
CREATE TRIGGER before_insert_piloto BEFORE INSERT ON Piloto
FOR EACH ROW
BEGIN
    DECLARE count_pilotos INT;
    SELECT COUNT(*) INTO count_pilotos FROM Piloto WHERE nome = NEW.nome;
    IF count_pilotos > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Piloto já existe';
    END IF;
END; //
DELIMITER ;

DELIMITER //
CREATE TRIGGER after_delete_carro AFTER DELETE ON Carro
FOR EACH ROW
BEGIN
    INSERT INTO Log (acao, descricao) VALUES ('DELETE', CONCAT('Carro ', OLD.nome, ' foi excluído.'));
END; //
DELIMITER ;
Select * from Marca;
SHOW DATABASES;
USE ProjetoSQL;
SHOW TABLES;