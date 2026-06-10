-- ログイン用テストユーザーの挿入
-- パスワードは "password" を BCrypt で暗号化したものです
-- "12345678" をBCryptで暗号化したもの
INSERT INTO user_info (user_id, password, role_id) 
VALUES ('admin', '$2a$10$d7o3KipALaFfXj5R4dxbAuNoAgQYugEdUqE/EWHMsXZFng.PhvUFW', 1)
ON CONFLICT (user_id) DO UPDATE SET password = EXCLUDED.password;

-- ロールの初期データを挿入（重複時は何もしない）
INSERT INTO role (id, name) VALUES (1, 'ADMIN'), (2, 'USER') 
ON CONFLICT (id) DO NOTHING;

-- カテゴリの初期データを挿入（重複時は何もしない）
INSERT INTO categories (id, name) VALUES (1, '食品'), (2, '衣類'), (3, '雑貨')
ON CONFLICT (id) DO NOTHING;