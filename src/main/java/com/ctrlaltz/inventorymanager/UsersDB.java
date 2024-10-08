package com.ctrlaltz.inventorymanager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDB {
    private Connection connection;

    public UsersDB() {
        connection = DatabaseConnection.getInstance();
    }

    public void initializeTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS Users ("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "userName VARCHAR NOT NULL,"
                            + "hashedPass VARCHAR NOT NULL,"
                            + "firstName VARCHAR NOT NULL,"
                            + "lastName VARCHAR NOT NULL,"
                            + "dateRegistered DATETIME NOT NULL"
                            + ")"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void insert(Users user) {
        try {
            PreparedStatement insertUser = connection.prepareStatement(
                    "INSERT INTO Users (userName, hashedPass, firstName, lastName, dateRegistered) VALUES (?, ?, ?, ?, ?)"
            );
            insertUser.setString(1, user.getUserName());
            insertUser.setString(2, user.getHashedPass());
            insertUser.setString(3, user.getFirstName());
            insertUser.setString(4, user.getLastName());
            insertUser.setString(5, user.getdateRegistered());
            insertUser.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void update(Users user) {
        try {
            PreparedStatement updateUser = connection.prepareStatement(
                    "UPDATE Users userName = ?, hashedPass = ?, firstName = ?, lastName = ?, dateRegistered = ? WHERE id = ?"
            );
            updateUser.setString(1, user.getUserName());
            updateUser.setString(2, user.getHashedPass());
            updateUser.setString(3, user.getFirstName());
            updateUser.setString(4, user.getLastName());
            updateUser.setString(5, user.getdateRegistered());
            updateUser.setInt(6, user.getId());
            updateUser.execute();
        } catch(SQLException ex) {
            System.err.println(ex);
        }
    }

    public void delete (int id) {
        try {
            PreparedStatement deleteUser = connection.prepareStatement(
                    "DELETE FROM Users WHERE id = ?"
            );
            deleteUser.setInt(1, id);
            deleteUser.execute();
        } catch(SQLException ex) {
            System.err.println(ex);
        }
    }

    public List<Users> getAll() {
        List<Users> users = new ArrayList<>();
        return users;
    }

    public Users getByUserName(String userName) {
        try {
            PreparedStatement getAccount = connection.prepareStatement("SELECT * FROM Users WHERE userName = ?");
            getAccount.setString(1, userName);
            ResultSet rs = getAccount.executeQuery();
            if (rs.next()) {
                return new Users(
                        rs.getInt("id"),
                        rs.getString("userName"),
                        rs.getString("hashedPass"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("dateRegistered")
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
}
