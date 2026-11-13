package se.chalmers.group21.spa.model.Database;
import java.sql.*;

public class Database {
    private static final String URL = "jdbc:postgresql://aws-0-eu-west-2.pooler.supabase.com:5432/postgres";
    private static final String USER = "postgres.utofoymoupoupdemmpbo";
    private static final String PASSWORD  = System.getenv("DB_PASSWORD");

    public static Connection connect() throws SQLException{
        return DriverManager.getConnection(URL, USER, "EUu64vHE3GaXTbb5");
    }
}
