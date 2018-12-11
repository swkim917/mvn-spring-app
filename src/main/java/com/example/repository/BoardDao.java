package com.example.repository;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.domain.Board;


@Repository
public class BoardDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    
    @PostConstruct
    public void init() {
        //simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) namedParameterJdbcTemplate.getJdbcOperations());
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("board");
        
        int count = count();
        if (count == 0) {
            insertDummyRecords();
        }
    }
    
    private void insertDummyRecords() {
        SqlParameterSource[] sources = new SqlParameterSource[1000];
        Board board = null;
        for (int i=0; i<1000; i++) {
            board = new Board();
            board.setNum(i+1);
            board.setName("글쓴이" + (i+1));
            board.setPasswd("1234");
            board.setSubject("글제목" + (i+1));
            board.setContent("글내용" + (i+1));
            board.setRe_ref(i+1);
            board.setRe_lev(0);
            board.setRe_seq(0);
            board.setReadcount(0);
            board.setReg_date(new Timestamp(System.currentTimeMillis()));
            
            sources[i] = new BeanPropertySqlParameterSource(board);
        }
        
        simpleJdbcInsert.executeBatch(sources);
    }
    
    public Integer getMaxNum() {
        try {
            Integer maxNum = jdbcTemplate.queryForObject("SELECT MAX(num) FROM board", Integer.class);
            return maxNum;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int count() {
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM board", Integer.class);
        return count;
    }

    public void add(Board board) {
        simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(board));
    } // add()의 끝

    public List<Board> getBoardList(int startRow, int pageSize) {
        // sql
        // select * from board order by re_ref desc, re_seq asc
        String sql = "select * from board order by re_ref desc, re_seq asc offset ? limit ?";

        List<Board> list = jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<Board>(Board.class),
                startRow - 1, pageSize);
        return list;
    }
    
    public Board getBoard(int num) {
        try {
            Board b = jdbcTemplate.queryForObject("SELECT * FROM board WHERE num = ?"
                    , new BeanPropertyRowMapper<Board>(Board.class), num);
            return b;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    public void updateReadcount(int num) {
        jdbcTemplate.update("UPDATE board SET readcount=readcount+1 WHERE num=?", num);
    }
    
    public int updateBoard(Board board) {
        int affected = namedParameterJdbcTemplate.update(
                "UPDATE board SET name = :name, subject = :subject, content = :content "
                + " WHERE num = :num"
                , new BeanPropertySqlParameterSource(board));
        return affected;
    }
    
    public void deleteBoard(int num) {
        jdbcTemplate.update("DELETE FROM board WHERE num = ?", num);
    }
    
    public void updateReSeq(int re_ref, int re_seq) {
        jdbcTemplate.update("UPDATE board SET re_seq=re_seq+1 WHERE re_ref=? AND re_seq>?", re_ref, re_seq);
    }
}
