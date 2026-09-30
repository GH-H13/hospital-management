package com.hospital.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库连接池工具类（使用HikariCP）
 */
public class DBUtil {
    private static HikariDataSource dataSource;
    private static volatile boolean initialized = false;

    /**
     * 初始化数据库连接池（使用同步方法避免并发问题）
     */
    public static synchronized void init() {
        if (initialized) {
            return;
        }

        try {
            // 禁用 SLF4J 的 logback 初始化
            System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "warn");

            // 手动加载 MySQL 驱动
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("✅ MySQL 驱动加载成功");
            } catch (ClassNotFoundException e) {
                System.err.println("⚠️  警告：MySQL 驱动未找到，尝试继续初始化...");
                // 最后一次尝试：提示用户需要清理 Maven 缓存並重新下载依赖
                System.err.println("⚠️  请在 IDEA 中执行: File -> Invalidate Caches -> Invalidate and Restart");
            }

            // 加载数据库配置文件
            Properties props = new Properties();
            InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            if (is == null) {
                System.err.println("警告: 无法从 classpath 中找到 db.properties，尝试从 src/main/resources 加载...");
                java.nio.file.Path path = java.nio.file.Paths.get("src/main/resources/db.properties");
                if (java.nio.file.Files.exists(path)) {
                    is = java.nio.file.Files.newInputStream(path);
                } else {
                    throw new RuntimeException("找不到数据库配置文件 db.properties，请确认在 src/main/resources 目录下");
                }
            }
            props.load(is);
            is.close();

            System.out.println("=== 读取到的数据库配置 ===");
            System.out.println("db.url: " + props.getProperty("db.url"));
            System.out.println("db.username: " + props.getProperty("db.username"));
            System.out.println("db.driver: " + props.getProperty("db.driver"));
            System.out.println("========================");

            // 配置HikariCP连接池
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));
            // 注意：不设置 setDriverClassName，让 HikariCP 自动推断驱动
            // config.setDriverClassName(props.getProperty("db.driver"));

            // 连接池参数
            config.setMaximumPoolSize(Integer.parseInt(props.getProperty("db.pool.maximumPoolSize", "10")));
            config.setMinimumIdle(Integer.parseInt(props.getProperty("db.pool.minimumIdle", "5")));
            config.setConnectionTimeout(Long.parseLong(props.getProperty("db.pool.connectionTimeout", "30000")));
            config.setIdleTimeout(Long.parseLong(props.getProperty("db.pool.idleTimeout", "600000")));
            config.setMaxLifetime(Long.parseLong(props.getProperty("db.pool.maxLifetime", "1800000")));

            // 连接池名称
            config.setPoolName("HospitalDB-Pool");

            // 连接测试查询
            config.setConnectionTestQuery("SELECT 1");

            // 创建数据源
            dataSource = new HikariDataSource(config);

            // 测试连接
            try (Connection conn = dataSource.getConnection()) {
                System.out.println("✅ 数据库连接池初始化成功，且测试连接正常！");
            }

            initialized = true;

        } catch (IOException e) {
            System.err.println("❌ 配置文件加载失败：");
            e.printStackTrace();
            throw new RuntimeException("数据库配置文件加载失败", e);
        } catch (SQLException e) {
            System.err.println("❌ 数据库连接失败（URL/账号/密码/数据库不存在）：");
            e.printStackTrace();
            throw new RuntimeException("数据库连接失败", e);
        } catch (Exception e) {
            System.err.println("❌ 连接池初始化失败：");
            e.printStackTrace();
            throw new RuntimeException("数据库连接池初始化失败", e);
        }
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() throws SQLException {
        if (!initialized) {
            init();
        }
        if (dataSource == null) {
            throw new RuntimeException("数据库连接池未初始化");
        }
        return dataSource.getConnection();
    }

    /**
     * 关闭连接（归还到连接池）
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭数据源（应用退出时调用）
     */
    public static synchronized void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("✅ 数据库连接池已关闭！");
        }
        // 重置初始化标志，允许重新初始化
        initialized = false;
        dataSource = null;
    }
}
