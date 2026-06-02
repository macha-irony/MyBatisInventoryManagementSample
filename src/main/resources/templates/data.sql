-- productsテーブルに存在する商品IDに対して、stocksにも初期レコードを作る
INSERT INTO stocks (product_id, quantity)
SELECT id, 0 FROM products 
ON CONFLICT (product_id) DO NOTHING;