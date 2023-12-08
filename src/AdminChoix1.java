// Ce class est pour l'option 1:
// Consulter les ventes réalisées dans la journée

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminChoix1 extends JFrame {
    int id;
    String url = "jdbc:mysql://localhost:3306/itapp";
    String usernameDB = "root";
    String passwordDB = "";
    JPanel panel = new JPanel();

    public AdminChoix1(int id) throws SQLException {
        this.id = id;
        this.setTitle("Interface de l'administrateur - Choix 1");
        this.setVisible(false);
        this.setBounds(390, 70, 900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.panel.setLayout(null);

        JLabel labelVentes = new JLabel("Les ventes d'aujourd'hui");
        labelVentes.setBounds(329, 59, 400, 30);
        Font logoFontConn = new Font("Times New Roman", Font.BOLD, 26);
        labelVentes.setFont(logoFontConn);
        labelVentes.setForeground(new Color(60, 160, 240));

        JButton retourAuMenu = new JButton("Retour au menu");
        retourAuMenu.setBounds(360, 130, 150, 30);
        retourAuMenu.setBackground(new Color(60, 160, 240));

        // I created the Table with this function (JTable)
        venteJournee();

        this.panel.add(labelVentes);
        this.panel.add(retourAuMenu);

        this.setContentPane(panel);

        retourAuMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminInterface admin = new AdminInterface(id);
                admin.setVisible(true);
                setVisible(false);
            }
        });


    }

    private void venteJournee() throws SQLException {
        Connection connection = DriverManager.getConnection(url, usernameDB, passwordDB);
        String query = "SELECT produits.designation AS produit_designation, categorie.designation AS categorie_designation, dateVente, quantiteVendu, utilisateurs.login FROM ventes JOIN produits ON ventes.produitVenduId = produits.idProduit JOIN categorie ON produits.categorieId = categorie.idCategorie JOIN utilisateurs ON ventes.idVendeur = utilisateurs.idUser ORDER BY utilisateurs.idUser ASC";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet resultSet = ps.executeQuery();
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        table.setRowHeight(30); // Adjust row height
        table.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font for table cells

        // Add columns to the table
        model.addColumn("Produit");
        model.addColumn("Catégorie");
        model.addColumn("Date de vente");
        model.addColumn("Quantité vendue");
        model.addColumn("Utilisateur");
        while (resultSet.next()) {
            String produitDesignation = resultSet.getString("produit_designation");
            String categorieDesignation = resultSet.getString("categorie_designation");
            Date dateVente = resultSet.getDate("dateVente");
            int quantiteVendu = resultSet.getInt("quantiteVendu");
            String utilisateurLogin = resultSet.getString("login");
            model.addRow(new Object[]{
                    produitDesignation,
                    categorieDesignation,
                    dateVente,
                    quantiteVendu,
                    utilisateurLogin
            });
        }

        JScrollPane tableJ = new JScrollPane(table);
        tableJ.setBounds(153, 214, 600, 400);

        this.panel.add(tableJ);

        resultSet.close();
        ps.close();
        connection.close();
    }


}
