package com.gilang.demo.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {

    @Autowired
    private Environment env;

    @Value("${db.datasource.username}")
    private String username;

    @Value("")
    private String password;

    @Value("${db.datasource.maximumPoolSize:10}")
    private String maxPoolSize;

    @Value("${db.datasource.cachePrepStmts:true}")
    private String cachePrepStmts;

    @Value("${db.datasource.prepStmtCacheSize:250}")
    private String prepStmtCacheSize;

    @Value("${db.datasource.prepStmtCacheSqlLimit:2048}")
    private String prepStmtCacheSqlLimit;

    @Bean
    public DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(env.getProperty("db.datasource.driver-class-name"));
        config.setJdbcUrl(env.getProperty("db.datasource.url"));

        config.setUsername(username);
        config.setPassword(password);

        config.setMaximumPoolSize(Integer.parseInt(maxPoolSize));
        config.addDataSourceProperty("cachePrepStmts", cachePrepStmts);
        config.addDataSourceProperty("prepStmtCacheSize", prepStmtCacheSize);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", prepStmtCacheSqlLimit);

        return new HikariDataSource(config);
    }

}
