//package io.github.alancavalcante_dev.codefreelaapi.config;
//
//import lombok.Getter;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import javax.sql.DataSource;
//
//
//@Getter
//@Configuration
//public class GiteaConfiguration {
//
//    @Bean(name = "giteaDataSource")
//    @ConfigurationProperties(prefix = "gitea.datasource")
//    public DataSource giteaDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "giteaJdbcTemplate")
//    public JdbcTemplate giteaJdbcTemplate(@Qualifier("giteaDataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//}
