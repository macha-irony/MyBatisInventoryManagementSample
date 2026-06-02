---- schema.sqlとしてファイルを作成する
---- productsテーブルを作成（存在しない場合のみ）
--CREATE TABLE IF NOT EXISTS products (
--    id SERIAL PRIMARY KEY,                       -- 主キー (自動採番されるシリアル型)
--    name VARCHAR(100) NOT NULL,                  -- 書籍タイトル (必須)
--    category_id INTEGER,                         -- 著者名
--    price INTEGER,                               -- 価格
--    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- データ登録日時
--);
--
--CREATE TABLE IF NOT EXISTS categories (
--    id SERIAL PRIMARY KEY,                       -- 主キー (自動採番されるシリアル型)
--    name VARCHAR(50) NOT NULL,                  -- 書籍タイトル (必須)
--);

-- 1. 在庫テーブル：現在の在庫数を管理
CREATE TABLE IF NOT EXISTS stocks (
    product_id INTEGER PRIMARY KEY REFERENCES products(id), -- 商品と1対1
    quantity INTEGER NOT NULL DEFAULT 0,                    -- 現在の在庫数
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. 入出庫履歴テーブル：在庫の変動を記録
CREATE TABLE IF NOT EXISTS order_histories (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL REFERENCES products(id),
    change_quantity INTEGER NOT NULL,          -- 増減数（入庫なら +5, 出庫なら -2）
    history_type VARCHAR(20) NOT NULL,         -- '入庫' や '出庫'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);