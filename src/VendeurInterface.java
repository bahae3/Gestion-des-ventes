import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class VendeurInterface extends JFrame {
    int userId;
    String url = "jdbc:mysql://localhost:3306/itapp";
    String usernameDB = "root";
    String passwordDB = "";

    public VendeurInterface(int id) throws SQLException {
        this.userId = id;
        this.setTitle("Interface du vendeur");
        this.setVisible(false);
        this.setBounds(460, 200, 700, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel bienvenueVenteur = new JLabel("Bienvenue sur IT!");
        bienvenueVenteur.setBounds(225, 50, 250, 25);
        Font logoFontVen = new Font("Times New Roman", Font.BOLD, 28);
        bienvenueVenteur.setFont(logoFontVen);
        bienvenueVenteur.setForeground(Color.red);

        JLabel declarationVente = new JLabel("Déclarer vos ventes réalisées durant la journée:");
        declarationVente.setBounds(90, 105, 500, 25);
        declarationVente.setFont(new Font("Times New Roman", Font.BOLD, 22));
        declarationVente.setForeground(new Color(60, 160, 240));

        JLabel produitVendu = new JLabel("Nom du produit vendu: ");
        produitVendu.setBounds(110, 160, 150, 25);

        Connection conn = DriverManager.getConnection(url, usernameDB, passwordDB);
        String query = "SELECT designation FROM produits";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        JComboBox<String> produits = new JComboBox<>();

        while (rs.next()) {
            String produit = rs.getString("designation");
            produits.addItem(produit);
        }
        rs.close();
        ps.close();
        conn.close();

        produits.setBounds(255, 160, 160, 25);

        JLabel dateVente = new JLabel("Date de la vente: ");
        dateVente.setBounds(110, 220, 160, 25);
        PlaceholderTextField dateVenteTf = new PlaceholderTextField("yyyy-mm-dd");
        dateVenteTf.setBounds(255, 220, 160, 25);


        JLabel quantiteVendue = new JLabel("Quantité vendue: ");
        quantiteVendue.setBounds(110, 280, 150, 25);
        SpinnerNumberModel spinnerModelQV = new SpinnerNumberModel(1, 1, 250, 1);
        JSpinner quantiteVendueTf = new JSpinner(spinnerModelQV);
        quantiteVendueTf.setBounds(255, 280, 160, 25);

        JButton declarer = new JButton("Déclarer");
        declarer.setBounds(200, 360, 100, 25);
        declarer.setBackground(Color.GREEN);

        JButton quitter = new JButton("Quitter");
        quitter.setBounds(325, 360, 100, 25);
        quitter.setBackground(Color.RED);


        panel.add(bienvenueVenteur);
        panel.add(declarationVente);
        panel.add(produitVendu);
        panel.add(produits);
        panel.add(dateVente);
        panel.add(dateVenteTf);
        panel.add(quantiteVendue);
        panel.add(quantiteVendueTf);
        panel.add(declarer);
        panel.add(quitter);

        this.setContentPane(panel);

        declarer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String url = "jdbc:mysql://localhost:3306/itapp";
                String usernameDB = "root";
                String passwordDB = "";
                int idProduit = 0;

                try {
                    Connection conn = DriverManager.getConnection(url, usernameDB, passwordDB);
                    String query = "SELECT idProduit FROM produits WHERE designation=?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, (String) produits.getSelectedItem());
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()){
                        idProduit = rs.getInt("idProduit");
                    }

                    ps.close();
                    rs.close();
                    conn.close();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                String dateVente = dateVenteTf.getText();

                // Converting dateVente from String to Date (java type)
                java.util.Date parsedDate = null;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    parsedDate = sdf.parse(dateVente);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Converting the java.util.Date to java.sql.Date (from java type to sql type)
                java.sql.Date sqlDate = null;
                if (parsedDate != null) {
                    sqlDate = new java.sql.Date(parsedDate.getTime());
                }

                int qttVendue = (int) quantiteVendueTf.getValue();

                Vente vente = new Vente(idProduit, sqlDate, qttVendue);

                try (Connection connection = DriverManager.getConnection(url, usernameDB, passwordDB);
                     PreparedStatement preparedStatement = connection.prepareStatement(
                             "INSERT INTO ventes (produitVenduId, dateVente, quantiteVendu, idVendeur) VALUES (?, ? ,?, ?)"
                     )) {

                    preparedStatement.setInt(1, vente.getProduitVenduId());
                    preparedStatement.setDate(2, vente.getDateVente());
                    preparedStatement.setInt(3, vente.getQuantiteVendue());
                    preparedStatement.setInt(4, id);

                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Insert successful!");
                        PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE produits SET quantiteStock = quantiteStock - ? WHERE idProduit = ?");
                        preparedStatement1.setInt(1, qttVendue);
                        preparedStatement1.setInt(2, idProduit);
                        preparedStatement1.executeUpdate();
                        preparedStatement1.close();

                        // here i will change the frame to
                        // if the user wants to add a new "vente" or no
                        // if he chooses no, we will sign out and go to login page
                        DeclarationSuccess success = new DeclarationSuccess(id);
                        setVisible(false);
                        success.setVisible(true);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }
}
