package com.github.startzyp.mc.Dao;

import java.sql.*;

public class DaoTool {

    private static Connection connection = null;
    private static Statement statement = null;
    public DaoTool(String DatabasePath){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:"+DatabasePath+".db");
            statement = connection.createStatement();
            String SuperGroupTable = "CREATE TABLE IF NOT EXISTS 'myTable'( ID INTEGER PRIMARY KEY  AUTOINCREMENT,PlayerName VARCHAR(100), PlayerDay INTEGER, ValueDay INTEGER, CreateTime DATETIME)";
            statement.executeUpdate(SuperGroupTable);
            statement.close();
        } catch ( Exception e ) {
        }
    }


    public static void AddData(String PlayerName,String PlayerValue,String ValueDay){
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "INSERT INTO myTable (PlayerName,ValueDay,PlayerDay,CreateTime)values('"+PlayerName+"','"+ValueDay+"','"+PlayerValue+"',datetime('now','localtime'))";
            statement.executeUpdate(sql);
            connection.commit();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void Updata(String PlayerName,String PlayerValue,String ValueDay){
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "update myTable set PlayerDay='"+PlayerValue+"',ValueDay='"+ValueDay+"' where PlayerName='"+PlayerName+"'";
            statement.executeUpdate(sql);
            connection.commit();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public static int GetPlayerDay(String PlayerName){
        int Count = -1;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String GetSuperGroupSql = "select * from myTable where PlayerName='"+PlayerName+"'";
            ResultSet rs = statement.executeQuery(GetSuperGroupSql);
            while (rs.next()){
                Count = rs.getInt("PlayerDay");
            }
            rs.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Count;
    }

    public static String GetPlayerTop(int TopNum){
        String Top ="";
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String GetSuperGroupSql = "select * from myTable ORDER BY PlayerDay DESC LIMIT "+TopNum;
            ResultSet rs = statement.executeQuery(GetSuperGroupSql);
            Top += "§f-------------------------§a☆§b成就榜§a☆§f-------------------------,";
            while (rs.next()){
                Top += "§6玩家：§3"+rs.getString("PlayerName")+" §8||| §6成就值：§9"+rs.getString("PlayerDay")+" §8||| §6徽章数：§d"+rs.getString("ValueDay")+",";
            }
            Top += "§f-------------------------§a☆§b前十名§a☆§f-------------------------";
            rs.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Top;
    }



}
