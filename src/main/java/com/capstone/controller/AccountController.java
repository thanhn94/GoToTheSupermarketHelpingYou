package com.capstone.controller;

import com.capstone.account.Account;
import com.capstone.account.Profile;
import com.capstone.msg.ErrorMsg;
import com.capstone.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController {

    @GetMapping("/account/{username}/{password}")
    public ResponseEntity<?> getApiAccountByUsername(@PathVariable("username") String username, @PathVariable("password") String password) {
        Account account = null;
        try {
            account = new AccountService().getAccountByUsername(username, password);
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, e.getMessage());
            return new ResponseEntity<>(new ErrorMsg(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    class AccountService {

        Account getAccountByUsername(String username, String password) throws SQLException, ClassNotFoundException {
            Connection con = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Account account = null;

            try {
                con = DBUtils.getConnection();
                if (con != null) {
                    String sql = "SELECT A.USERNAME, A.EMAIL, A.ROLE, A.STATUS,\n"
                            + "F.FIRST_NAME, F.MID_NAME, F.LAST_NAME, F.PHONE, F.DOB\n"
                            + "FROM ACCOUNT A\n"
                            + "JOIN PROFILE F\n"
                            + "ON A.ID = F.ACCOUNT\n"
                            + "WHERE A.USERNAME = ? AND A.PASSWORD = ? AND A.IS_ACTIVE = 1";
                    stmt = con.prepareStatement(sql);
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        return new Account(rs.getString("USERNAME"),
                                rs.getString("EMAIL"),
                                rs.getInt("ROLE"),
                                rs.getInt("STATUS"),
                                new Profile(rs.getString("FIRST_NAME"),
                                        rs.getString("MID_NAME"),
                                        rs.getString("LAST_NAME"),
                                        rs.getString("PHONE"),
                                        rs.getDate("DOB")));
                    }
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            return account;
        }
    }
}
