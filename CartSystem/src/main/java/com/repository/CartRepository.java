package com.repository;


import com.enums.UserMembership;
import com.model.CartItem;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CartRepository {

    //DI using Constructor
    private final JdbcTemplate jdbcTemplate;
    public CartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CartItem> fetchAllItems() {
        String sql = """
            SELECT c.id, c.name, c.price, c.qty,
                   u.id AS user_id, u.name AS user_name, u.membership
            FROM cart_item c
            JOIN users u ON c.user_id = u.id
        """;
        return jdbcTemplate.query(sql, new RowMapper<CartItem>() {

            @Override
            public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        UserMembership.valueOf(rs.getString("membership"))
                );

                return new CartItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("qty"),
                        user
                        );
            }
        });
    }

    public List<CartItem> getitemsByUsername(String username) {
        String sql = """
            SELECT c.*, u.id as user_id, u.name as user_name, u.membership
            FROM cart_item c
            JOIN users u ON c.user_id = u.id
            WHERE u.name = ?
        """;
        return jdbcTemplate.query(sql,new Object[]{username}, new RowMapper<CartItem>() {
            @Override
            public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        UserMembership.valueOf(rs.getString("membership"))
                );
                return new CartItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("qty"),
                        user
                );
            }
        });
    }

    public int delete(int id) {
        String sql="delete from cart_item where id=?";
        return jdbcTemplate.update(sql,id);
    }
}
