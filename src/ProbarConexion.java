import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProbarConexion {
    public static void main(String[] args) {
        String url = "jdbc:mariadb://localhost:3306/repartidorya";
        String usuario = "root";
        String clave = "";

        String sql = "SELECT nombre, precio FROM tipo_producto";

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
