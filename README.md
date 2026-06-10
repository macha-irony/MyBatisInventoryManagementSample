MyBatisを用いた販売管理システムのポートフォリオの作成
PJName:MyBatisInventoryManagementSample

## 概要
本プロジェクトは、MyBatisを使用した販売管理システムを想定した簡易的なアプリケーションです。
Dockerを利用した開発環境を提供しており、Docker Desktopをインストールするだけで誰でも同じ環境を構築できます。

## 必須環境
以下のツールがインストールされていることを確認してください。

* **Docker Desktop**: 最新版を推奨
* **Git**: リポジトリのクローンに使用します
* **IDE**: Eclipse (推奨)

## セットアップ手順

### 1. リポジトリのクローン
ターミナルを開き、任意の開発用ディレクトリ（`C:\Users\[ユーザー名]\workspace` 等）で以下を実行してください。

```bash
git clone https://github.com/macha-irony/MyBatisInventoryManagementSample.git
cd MyBatisInventoryManagementSample
```

# クリーンビルドとコンテナの起動
```bash
./gradlew clean build -x test
docker compose up -d --build
```

コンテナの起動が完了したら、ブラウザで以下にアクセスしてください。
URL: http://localhost:8080

データ等の不整合が発生した場合や、環境を初期化したい場合は以下のコマンドを実行してください。
```bash
docker compose down -v
```
