import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class VendeurInterface extends JFrame {
    int userId;

    public VendeurInterface(int id) {
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
        declarationVente.setBounds(90, 110, 300, 25);

        JLabel produitVendu = new JLabel("Id du produit vendu: ");
        produitVendu.setBounds(110, 160, 150, 25);
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 10, 1);
        JSpinner produitVenduId = new JSpinner(spinnerModel);
        produitVenduId.setBounds(255, 160, 150, 25);

        JLabel dateVente = new JLabel("Date de la vente: ");
        dateVente.setBounds(110, 220, 160, 25);
        PlaceholderTextField dateVenteTf = new PlaceholderTextField("yyyy-mm-dd");
        dateVenteTf.setBounds(255, 220, 150, 25);


        JLabel quantiteVendue = new JLabel("Quantité vendue: ");
        quantiteVendue.setBounds(110, 280, 150, 25);
        SpinnerNumberModel spinnerModelQV = new SpinnerNumberModel(1, 1, 250, 1);
        JSpinner quantiteVendueTf = new JSpinner(spinnerModelQV);
        quantiteVendueTf.setBounds(255, 280, 150, 25);

        JButton declarer = new JButton("Declarer");
        declarer.setBounds(215, 350, 100, 25);


        panel.add(bienvenueVenteur);
        panel.add(declarationVente);
        panel.add(produitVendu);
        panel.add(produitVenduId);
        panel.add(dateVente);
        panel.add(dateVenteTf);
        panel.add(quantiteVendue);
        panel.add(quantiteVendueTf);
        panel.add(declarer);

        this.setContentPane(panel);

        declarer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String url = "jdbc:mysql://localhost:3306/itapp";
                String usernameDB = "root";
                String passwordDB = "";

                int idProduit = (int) produitVenduId.getValue();
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
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });

    }
}
