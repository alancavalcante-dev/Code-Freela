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
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> findUserByUsernameAndEmail(String username,String email) {
        String sql = "SELECT id, name, email FROM \"user\" WHERE name = ? OR email = ?";
        return jdbcTemplate.queryForList(sql, username, email);
    }

    public List<Map<String, Object>> findRepository(String nameRepository) {
        String sql = "SELECT id, name FROM repository WHERE name = ?";
        return jdbcTemplate.queryForList(sql, nameRepository);
    }
}