INSERT INTO cor (nome, codigohex)
VALUES ('Preto Fosco', '#111111'),
       ('Branco Neve', '#FFFFFF'),
       ('Inox (Aço)', '#C0C0C0'),
       ('Rose Gold', '#B76E79'),
       ('Azul Marinho', '#0B3C5D'),
       ('Azul Tiffany', '#0ABAB5'),
       ('Verde Oliva', '#556B2F'),
       ('Vermelho', '#E02020'),
       ('Laranja', '#FF7F11'),
       ('Roxo', '#6A0DAD');

INSERT INTO marca (nome)
VALUES ('ThermaX'),
       ('HydraPro'),
       ('PolarSteel'),
       ('AquaGuard'),
       ('FrostPeak'),
       ('EcoTherm'),
       ('ArcticWave'),
       ('SteelFlow'),
       ('HeatShield'),
       ('FreezeCool'),
       ('GlacierCup');

INSERT INTO modelo (id_marca, nome, anolancamento)
VALUES (7, 'Aqua Chill 500', 2023),
       (2, 'SteelCore 750', 2022),
       (11, 'SteelCore 750', 2022),
       (9, 'Urban Flip 600', 2024),
       (3, 'Adventure 1L', 2021),
       (11, 'Kids Pop 350', 2024),
       (1, 'Sport Pro 700', 2023),
       (8, 'Minimal 500', 2025),
       (5, 'Office Slim 400', 2022),
       (4, 'Trail Rugged 900', 2025),
       (6, 'Gourmet Infuser 600', 2023),
       (2, 'Glacier 650', 2020),
       (7, 'Summit 800', 2019),
       (3, 'Breeze 450', 2022),
       (1, 'Nova 550', 2025),
       (9, 'Terra 700', 2021),
       (4, 'Pulse 480', 2023),
       (8, 'Eclipse 520', 2024),
       (11, 'Horizon 750', 2020),
       (6, 'Quantum 900', 2025),
       (5, 'Orbit 620', 2022),
       (2, 'Vertex 680', 2023),
       (7, 'Canyon 1000', 2021),
       (3, 'Stream 400', 2024),
       (1, 'Atlas 850', 2019),
       (9, 'Vista 570', 2025);

INSERT INTO material (tipo, resistencia_temperatura)
VALUES ('Aço Inox 316L (corpo interno, alta resistência à corrosão)', 160),
       ('Aço Inox 304 (corpo externo escovado)', 150),
       ('Revestimento de Cobre eletrolítico (camada térmica)', 200),
       ('Silicone platina grau alimentício (anel de vedação)', 230),
       ('Tritan copoliéster BPA-free (tampa esportiva/biquinho)', 100);

INSERT INTO tipo_isolamento (descricao, eficienciatermica)
VALUES ('Vácuo duplo', 92),
       ('Espuma PU', 85),
       ('Vácuo triplo', 95),
       ('Gel Pack', 70),
       ('Aerogel híbrido', 96),
       ('Cerâmico interno', 80),
       ('Câmara de ar', 60),
       ('Parede simples', 40),
       ('Vácuo + cobre', 93),
       ('PU + alumínio', 88);

INSERT INTO tipo_tampa (descricao, material)
VALUES ('Flip-top', 'PP/Tritan'),
       ('Rosqueável', 'PP'),
       ('Pressão (push)', 'ABS/PP'),
       ('Bico retrátil', 'Tritan/Silicone'),
       ('Canudo', 'Tritan/Silicone'),
       ('Trava push c/ vedação', 'PP/Silicone'),
       ('Deslizante (slider)', 'ABS/Silicone'),
       ('Esportiva com alça', 'PP/TPE'),
       ('Anti-vazamento infantil', 'PP/Silicone'),
       ('Infusor twist', 'Aço/Tritan');

INSERT INTO produto
(nome, descricao, preco, capacidade, estoque, id_modelo, id_tipotampa, id_marca, id_tipoisolamento, id_material)
VALUES ('Frost 500 Inox', 'Parede dupla a vácuo, ótima para academia', 149, 0.50, 500, 1, 1, 1, 1, 1),
       ('Urban Flip 600', 'Tampa flip para uso urbano diário', 129, 0.60, 530,2, 1, 2, 2, 2),
       ('Adventure 1L', 'Trava reforçada para trilha/camping', 199, 1.00, 332, 3, 2, 3, 3, 1),
       ('Kids Pop 350', 'Bico macio e alça infantil', 89, 0.35, 442, 3, 9, 4, 4, 5),
       ('Office Slim 400', 'Perfil fino, cabe no porta-copo', 119, 0.40, 765, 5, 7, 5, 2, 3),
       ('Sport Pro 700', 'Alta retenção térmica e performance', 179, 0.70, 534, 6, 6, 6, 5, 1),
       ('Trail Rugged 900', 'Texturizada, super resistente para trilhas', 189, 0.90, 634, 7, 2, 7, 3, 2),
       ('Gourmet Infuser 600', 'Com infusor para chá e frutas, vedação premium', 159, 0.60, 512, 8, 10, 8, 9, 4),
       ('Minimal 500', 'Design minimalista com acabamento fosco', 139, 0.50, 523, 9, 3, 9, 1, 1),
       ('Hydra 750', 'Bico retrátil esportivo, alta durabilidade', 169, 0.75, 523, 11, 4, 10, 10, 2);

INSERT INTO produto_cor (id_cor, id_produto)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10);


INSERT INTO usuario (nome, username, senha, perfil)
VALUES
    -- 123
    ('Administrador Geral', 'admin.geral',
     '6wIlC05FYzn1zo5Nv2wzXDe3Z0EoCmZ9ww8SKmUoz7NU2l481ZGwuBpK1Sf3UfdNxlu+7w0uB+6ecWm9VB2lkQ==', 'ADM'),

    -- 234
    ('Administrador Operacional', 'admin.op',
     '66mD3S6UQnt5RcI3glEOdYFJFVk7j4cPWARbmAktY+OuWGwOTLtkcJXoj2mAr6NEtAKJ8m/bxUpATama8tS/Xg==', 'ADM'),

    -- 345
    ('Administrador Suporte', 'admin.suporte',
     'Iu6yCHd8x+Bf4A965tuVHho8cAwLexxcrcgo3m+EZxnOPIzg/+/ynfb88hnOe3pp99EXSt2FFpty+2sQ1FBg1g==', 'ADM'),

    -- 456
    ('João Silva', 'joao.silva',
     '8j0iO/fjc1dP7z0uIlRkVdR4MO5FIKecLgcsytg/Evo58XdZWgvxpk1SeQHAZn6YRfLEEWc+9zYcpyGOelpnqQ==', 'USER'),

    -- 567
    ('Maria Santos', 'maria.santos',
     'D7OMZ68p9EZQ2M0M64C2RlkzOFPnDv9sJVXD+2Yq+67o7yI2wA68xeMnelngne5RGR4wOJoW0oX0xDCZ/IbF0Q==', 'USER'),

    -- 678
    ('Ana Souza', 'ana.souza',
     '6oJqeA9Ce6ad7aAYP2AKrW+BFE+cDdqW7r6v+m8muzvekCgwcko8xVZXSuL+UPOsIeKqxKRdCZwsbkEgGLqGrQ==', 'USER');

-- Cria Cliente para USERS (joao, maria, ana)
INSERT INTO cliente (usuario_id, cpf, perfil)
SELECT u.id, '12345678900', 'USER' FROM usuario u WHERE u.username = 'joao.silva';

INSERT INTO cliente (usuario_id, cpf, perfil)
SELECT u.id, '98765432100', 'USER' FROM usuario u WHERE u.username = 'maria.santos';

INSERT INTO cliente (usuario_id, cpf, perfil)
SELECT u.id, '11122233344', 'USER' FROM usuario u WHERE u.username = 'ana.souza';

-- João: 2 endereços
INSERT INTO enderecoentrega (rua, numero, complemento, bairro, cidade, estado, cep, cliente_id)
SELECT 'Rua A', '10', 'Ap 02', 'Centro', 'Palmas', 'TO', '77000-000', c.id
FROM cliente c
         JOIN usuario u ON u.id = c.usuario_id
WHERE u.username = 'joao.silva';

INSERT INTO enderecoentrega (rua, numero, complemento, bairro, cidade, estado, cep, cliente_id)
SELECT 'Av. JK', '1500', 'Casa', 'Plano Diretor', 'Palmas', 'TO', '77001-111', c.id
FROM cliente c
         JOIN usuario u ON u.id = c.usuario_id
WHERE u.username = 'joao.silva';

-- Maria: 1 endereço
INSERT INTO enderecoentrega (rua, numero, complemento, bairro, cidade, estado, cep, cliente_id)
SELECT 'Rua das Flores', '55', 'Bloco B', 'Jardins', 'Palmas', 'TO', '77002-222', c.id
FROM cliente c
         JOIN usuario u ON u.id = c.usuario_id
WHERE u.username = 'maria.santos';

-- Ana: 1 endereço
INSERT INTO enderecoentrega (rua, numero, complemento, bairro, cidade, estado, cep, cliente_id)
SELECT 'Av. Teotônio', '800', 'Sala 12', 'Centro', 'Palmas', 'TO', '77003-333', c.id
FROM cliente c
         JOIN usuario u ON u.id = c.usuario_id
WHERE u.username = 'ana.souza';