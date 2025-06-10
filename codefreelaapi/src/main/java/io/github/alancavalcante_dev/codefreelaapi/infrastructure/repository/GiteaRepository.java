package io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class GiteaRepository {

    @Autowired
    @Qualifier("giteaJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> listarUsuarios() {
        String sql = "SELECT id, name, email FROM \"user\"";
        return jdbcTemplate.queryForList(sql);
    }
}