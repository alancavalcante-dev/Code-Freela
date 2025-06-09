package io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class GiteaRepository {


    @Autowired
    private JdbcTemplate jdbcTemplate;



    // Retorna um único objeto personalizado (User)
    public Object findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
             new User(
                    rs.getLong("id"),
                    rs.getString("name"),
            );
        });
    }
}
