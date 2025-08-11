# Données de test pour l'application Tricount

# Insertion d'utilisateurs de test
INSERT INTO user_table (id, name, email, password, active) VALUES 
('550e8400-e29b-41d4-a716-446655440001', 'Alice Martin', 'alice@example.com', '$2a$10$rZ.1234567890abcdefghijklmnopqrstuvwxyz', true),
('550e8400-e29b-41d4-a716-446655440002', 'Bob Dupont', 'bob@example.com', '$2a$10$rZ.1234567890abcdefghijklmnopqrstuvwxyz', true),
('550e8400-e29b-41d4-a716-446655440003', 'Charlie Moreau', 'charlie@example.com', '$2a$10$rZ.1234567890abcdefghijklmnopqrstuvwxyz', true),
('550e8400-e29b-41d4-a716-446655440004', 'Diana Leroy', 'diana@example.com', '$2a$10$rZ.1234567890abcdefghijklmnopqrstuvwxyz', true);

# Insertion de groupes de test
INSERT INTO group_table (id, name) VALUES 
('660e8400-e29b-41d4-a716-446655440001', 'Vacances en Bretagne'),
('660e8400-e29b-41d4-a716-446655440002', 'Colocation appartement'),
('660e8400-e29b-41d4-a716-446655440003', 'Weekend ski');

# Association des utilisateurs aux groupes (table de jointure)
INSERT INTO group_table_users (group_id, users_id) VALUES 
('660e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440001'),
('660e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440002'),
('660e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440003'),

('660e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440001'),
('660e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440004'),

('660e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440002'),
('660e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440003'),
('660e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440004');

# Insertion de dépenses de test
INSERT INTO expense (id, description, amount, status, payer_id, group_id) VALUES 
('770e8400-e29b-41d4-a716-446655440001', 'Restaurant du premier soir', 85.50, 'APPROVED', '550e8400-e29b-41d4-a716-446655440001', '660e8400-e29b-41d4-a716-446655440001'),
('770e8400-e29b-41d4-a716-446655440002', 'Courses pour le petit-déjeuner', 24.30, 'APPROVED', '550e8400-e29b-41d4-a716-446655440002', '660e8400-e29b-41d4-a716-446655440001'),
('770e8400-e29b-41d4-a716-446655440003', 'Essence pour le trajet', 67.80, 'PENDING', '550e8400-e29b-41d4-a716-446655440003', '660e8400-e29b-41d4-a716-446655440001'),
('770e8400-e29b-41d4-a716-446655440004', 'Électricité mensuelle', 120.00, 'APPROVED', '550e8400-e29b-41d4-a716-446655440001', '660e8400-e29b-41d4-a716-446655440002'),
('770e8400-e29b-41d4-a716-446655440005', 'Internet mensuel', 45.99, 'APPROVED', '550e8400-e29b-41d4-a716-446655440004', '660e8400-e29b-41d4-a716-446655440002');

# Association des bénéficiaires aux dépenses (table de jointure)
INSERT INTO expense_beneficiary (expense_id, beneficiary_id) VALUES 
# Restaurant - tous les 3 participants aux vacances
('770e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440001'),
('770e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440002'),
('770e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440003'),

# Courses - tous les 3 participants aux vacances
('770e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440001'),
('770e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440002'),
('770e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440003'),

# Essence - tous les 3 participants aux vacances
('770e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440001'),
('770e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440002'),
('770e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440003'),

# Électricité - Alice et Diana (colocation)
('770e8400-e29b-41d4-a716-446655440004', '550e8400-e29b-41d4-a716-446655440001'),
('770e8400-e29b-41d4-a716-446655440004', '550e8400-e29b-41d4-a716-446655440004'),

# Internet - Alice et Diana (colocation)
('770e8400-e29b-41d4-a716-446655440005', '550e8400-e29b-41d4-a716-446655440001'),
('770e8400-e29b-41d4-a716-446655440005', '550e8400-e29b-41d4-a716-446655440004');
