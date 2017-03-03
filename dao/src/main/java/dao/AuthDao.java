package dao;

import model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@PropertySource("classpath:query.properties")
@ComponentScan("config")
public class AuthDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Value("${query.findUserByName}")
    private String findUserByName;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Users findByName(String userName){
        Map<String, String> map = new HashMap<>();
        map.put("userName", userName);
        return jdbcTemplate.queryForObject(findUserByName, map, (rs, rowNum) -> {
            Users users = new Users();
            users.setId(rs.getLong("id"));
            users.setUserName(rs.getString("userName"));
            users.setPassword(rs.getString("password"));
            users.setRole(rs.getString("role"));
            return users;
        });
    }
}
