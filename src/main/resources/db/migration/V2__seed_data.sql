-- Dados de exemplo para demonstracao e evidencias de funcionamento

INSERT INTO categories (name, description) VALUES
    ('Racao',        'Alimentos secos e umidos para caes e gatos'),
    ('Brinquedos',   'Brinquedos para entretenimento e enriquecimento'),
    ('Higiene',      'Shampoos, tapetes higienicos e produtos de limpeza'),
    ('Acessorios',   'Coleiras, guias, comedouros e camas'),
    ('Medicamentos', 'Antipulgas, vermifugos e suplementos');

INSERT INTO products (name, description, price, stock_quantity, active, category_id) VALUES
    ('Racao Premium Caes Adultos 15kg', 'Racao super premium para caes adultos de porte medio', 189.90, 40, TRUE, 1),
    ('Racao Gatos Castrados 3kg',       'Racao para gatos castrados sabor frango',               74.50, 60, TRUE, 1),
    ('Bolinha Mordedor Resistente',     'Brinquedo de borracha atoxica para caes',               29.90, 120, TRUE, 2),
    ('Arranhador para Gatos',           'Arranhador com poste de sisal e plataforma',           139.00, 25, TRUE, 2),
    ('Shampoo Neutro 500ml',            'Shampoo neutro para banho de caes e gatos',             24.90, 80, TRUE, 3),
    ('Tapete Higienico 30un',           'Tapetes higienicos super absorventes',                  49.90, 150, TRUE, 3),
    ('Coleira Ajustavel M',             'Coleira de nylon ajustavel tamanho medio',              34.90, 70, TRUE, 4),
    ('Comedouro Duplo Inox',            'Comedouro e bebedouro duplo em aco inox',               59.90, 45, TRUE, 4),
    ('Antipulgas Caes 10-25kg',         'Antipulgas e carrapatos de uso topico',                 89.90, 35, TRUE, 5),
    ('Vermifugo Multi 4 comprimidos',   'Vermifugo de amplo espectro para caes',                 42.00, 50, TRUE, 5);

INSERT INTO customers (name, email, cpf, phone) VALUES
    ('Maria Oliveira', 'maria.oliveira@email.com', '123.456.789-09', '(11) 98888-1111'),
    ('Joao Santos',    'joao.santos@email.com',    '987.654.321-00', '(21) 97777-2222');
