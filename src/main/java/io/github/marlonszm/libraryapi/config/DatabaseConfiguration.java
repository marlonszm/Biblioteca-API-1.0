package io.github.marlonszm.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String user;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    //Configuração através do DriveManagerDataSource
    //@Bean
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        return ds;
    }

    //Conexão através do Hikari
    @Bean
    public DataSource hikariDataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.setDriverClassName(driver);

        //CONFIGURAÇÕES PRINCIPAIS
        // Pool máximo de conexões liberadas (usuários simultâneos!)
        config.setMaximumPoolSize(10);
        // Tamanho inicial do pool (número mínimo de conexão ao iniciar a aplicação)
        config.setMinimumIdle(1);
        // Nome
        config.setPoolName("library-db-pool");
        // 600 mil ms (tempo de vida da conexão [no máximo 10 min nesse caso])
        config.setMaxLifetime(600000);
        // Tempo de tentativa de conexão inicial (1,66 min nesse caso)
        config.setConnectionTimeout(100000);
        //CONFIGURAÇÕES SECUNDÁRIAS
        // Verificar se o banco está conectado (query de teste)
        config.setConnectionTestQuery("select 1");


        return new HikariDataSource(config);
    }

}

