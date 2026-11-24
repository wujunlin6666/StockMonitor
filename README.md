# StockMonitor 股票监控系统

一个 Java 命令行课设项目。它用 MySQL 保存自选标的、板块和成分权重，用 Alpaca 获取美股行情。程序不再读写 `stock.txt`。

## 如何运行

需要 JDK 21、Maven 和 MySQL 8.4。可以用 IntelliJ IDEA 打开项目，用 DataGrip 或其他 MySQL 客户端执行 SQL。

### 1. 建库和建表

首次安装时，用 MySQL 管理员账号执行：

```sql
CREATE DATABASE IF NOT EXISTS stock_monitor CHARACTER SET utf8mb4;
USE stock_monitor;
```

在同一个 SQL 控制台执行 [01_schema.sql](src/test/java/training01/01_schema.sql)。脚本依次建立 `stocks`、`sectors` 和 `sector_component`。如果三张表已经建好，跳过建表脚本，不要在原库里重复执行。

### 2. 创建程序账号

首次配置时，在管理员连接中执行：

```sql
CREATE USER 'stock_app'@'127.0.0.1' IDENTIFIED BY RANDOM PASSWORD;

GRANT SELECT, INSERT, UPDATE, DELETE ON stock_monitor.stocks
TO 'stock_app'@'127.0.0.1';

GRANT SELECT ON stock_monitor.sectors
TO 'stock_app'@'127.0.0.1';

GRANT SELECT ON stock_monitor.sector_component
TO 'stock_app'@'127.0.0.1';
```

把生成的密码保存在自己的密码管理器里。账号已存在时，不要重复执行 `CREATE USER`，可用 `SHOW GRANTS FOR 'stock_app'@'127.0.0.1';` 核对权限。Java 只用 `stock_app` 登录；管理账号用于建表和维护板块。

### 3. 启动程序

1. 在 IDEA 中打开项目根目录，选择 JDK 21，让 Maven 下载项目依赖。
2. 运行 [`training01.Main`](src/main/java/training01/Main.java)。默认连接地址是 `jdbc:mysql://127.0.0.1:3306/stock_monitor`，用户名是 `stock_app`。
3. 在运行窗口输入该账号的密码。不要把密码写进代码、提交到 Git，或包含在分享的运行日志里。

菜单 `8`、`10`、`12` 会请求 Alpaca。要使用这三项，给 Java 运行进程设置环境变量 `ALPACA_API_KEY_ID` 和 `ALPACA_API_SECRET_KEY`（例如在 IDEA 的 `Main` 运行配置里设置）。密钥不应提交到 Git。只查看或手动维护 MySQL 中的数据，不需要 Alpaca 密钥。

## 快速演示（不需要 Alpaca）

建表脚本不包含演示数据。只在新建的空库里，用管理员在 DataGrip 执行下列 SQL：

```sql
USE stock_monitor;

INSERT INTO stocks (symbol, previous_close, current_price)
VALUES ('DEMOA', 10.0000, 11.0000),
       ('DEMOB', 20.0000, 19.0000);

INSERT INTO sectors (sector_id, sector_name)
VALUES (1, '演示板块');

INSERT INTO sector_component (sector_id, symbol, weight)
VALUES (1, 'DEMOA', 0.5000),
       (1, 'DEMOB', 0.5000);
```

启动程序后，选 `1` 可以看到两个标的，选 `11` 可以看到「演示板块」。选 `13` 输入「演示板块」，应显示两个成分各占 50%，板块总涨跌幅为 `+2.50%`。这两个代码是演示用的，不要拿它们请求 Alpaca。

## 菜单速查

| 菜单 | 功能 |
| --- | --- |
| `1–7` | 查看、查询、手动新增、删除和排序 |
| `8` / `10` | 从 Alpaca 添加新标的 / 刷新已保存的标的 |
| `11` / `13` | 查看板块列表 / 查看已保存的成分、权重和涨跌幅 |
| `12` | 刷新板块成分的行情，权重合计须为 100% |
| `14` | 手动更正已保存标的的当前价 |
| `9` | 退出 |

`sector_component` 用 `(sector_id, symbol)` 作联合主键，分别引用板块表和股票表。新增成分前，两边的记录必须都存在。要把标的移出板块，只删除成分表中对应的板块与代码组合；标的仍被任何板块引用时，菜单 `4` 不会删除它。移除成分后需要手动调整剩余权重。

## 运行测试

在项目根目录执行：

```sh
mvn test
```

现有单元测试不需要连接 MySQL，也不需要 Alpaca 密钥；真实数据库的启动和菜单验证可按上面的演示步骤单独完成。
