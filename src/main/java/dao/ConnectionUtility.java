package dao;

public class ConnectionUtility {
    public static String url = "jdbc:postgresql://" + System.getenv("DB_URL") + "/project_one";
    public static String dbUsername = System.getenv("DB_USERNAME");
    public static String dbPassword = System.getenv("DB_PASSWORD");
}
