package com.example.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.domain.Member;

@Repository
public class MemberDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    
    @PostConstruct
    public void init() {
        //simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) namedParameterJdbcTemplate.getJdbcOperations());
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("member");
        
    }

    public void add(Member member) {
//        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
//        String sql = "INSERT INTO member(id, passwd, name, reg_date, age, gender, email)"
//                + " VALUES(:id, :passwd, :name, :reg_date, :age, :gender, :email)";
//        namedParameterJdbcTemplate.update(sql, param);
        
        // SimpleJdbcInsert의 활용
        simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(member));
    } // add()의 끝

    public int update(Member member) {
        int affected = namedParameterJdbcTemplate.update(
                "UPDATE member SET name=:name, age=:age, gender=:gender, email=:email" + " WHERE id=:id",
                new BeanPropertySqlParameterSource(member));
        return affected;
    }

    public void delete(String id) {
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update("DELETE FROM member WHERE id=:id", param);
        // jdbcTemplate.update("DELETE FROM mymember WHERE id = ?", id);
    }

    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM member");
    }

    public Member get(String id) {
        try {
//            Member m = jdbcTemplate.queryForObject("SELECT * FROM member WHERE id = ?",
//                    new BeanPropertyRowMapper<Member>(Member.class), id);

            Member m = namedParameterJdbcTemplate.queryForObject("SELECT * FROM member WHERE id = :id",
                    new MapSqlParameterSource("id", id), new BeanPropertyRowMapper<Member>(Member.class));
            return m;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    public List<Member> search(String name) {
        List<Member> members = jdbcTemplate.query("SELECT * FROM member WHERE name LIKE ?"
                , new BeanPropertyRowMapper<Member>(Member.class), "%" + name + "%");
        return members;
    }
    
    public List<Member> getAll() {
        List<Member> members = jdbcTemplate.query("SELECT * FROM member ORDER BY name ASC"
                , new BeanPropertyRowMapper<Member>(Member.class));
        return members;
    }
    
    public int count() {
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM member", Integer.class);
        return count;
    }
    
    public int countById(String id) {
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM member WHERE id = ?", Integer.class, id);
        return count;
    }

}
