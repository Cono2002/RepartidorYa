import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ProbarConexion {
    public static void main(String[] args) {
        Properties config = new Properties();
        try (InputStream in = Files.newInputStream(Path.of("config/db.properties"))) {
            config.load(in);
        } catch (IOException e) {
            System.out.println("No pude leer config/db.properties: " + e.getMessage());
            System.out.println("Carpeta de trabajo actual: " + Path.of("").toAbsolutePath());
            return;
        }

        String url = config.getProperty("db.url");
        String usuario = config.getProperty("db.usuario");
        String clave = config.getProperty("db.clave", "");

        String sql = "SELECT nombre, precio FROM tipo_producto ORDER BY nombre";

        try (Connection con = DriverManager.getConnection(url, usuario, clave);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("Conectado a la base.");
            while (rs.next()) {
                System.out.println(rs.getString("nombre") + " - $" + rs.getBigDecimal("precio"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
