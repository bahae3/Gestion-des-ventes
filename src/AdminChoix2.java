// Ce class est pour l'option 2:
// Gérer les entités Produit et Catégorie (CRUD)

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminChoix2 extends JFrame {
    int id;
    String url = "jdbc:mysql://localhost:3306/itapp";
    String usernameDB = "root";
    String passwordDB = "";
    JPanel panel = new JPanel();

    public AdminChoix2(int id) throws SQLException {
        this.id = id;
        this.setTitle("Interface de l'administrateur - Choix 2");
        this.setVisible(false);
        this.setBounds(390, 70, 900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.panel.setLayout(null);

        JLabel ajouterSupprimer = new JLabel("Ajouter/Supprimer un produit");
        ajouterSupprimer.setBounds(307, 50, 400, 30);
        Font logoFontConn = new Font("Times New Roman", Font.BOLD, 22);
        ajouterSupprimer.setFont(logoFontConn);
        ajouterSupprimer.setForeground(new Color(60, 160, 240));

        JButton ajouter = new JButton("Ajouter");
        ajouter.setBounds(200, 132, 150, 30);
        ajouter.setBackground(Color.GREEN);

        JButton retourAuMenu = new JButton("Retour au menu");
        retourAuMenu.setBounds(376, 132, 150, 30);
        retourAuMenu.setBackground(new Color(60, 160, 240));

        JButton supprimer = new JButton("Supprimer");
        supprimer.setBounds(552, 132, 150, 30);
        supprimer.setBackground(Color.RED);

        venteJournee();

        this.panel.add(ajouterSupprimer);
        this.panel.add(ajouter);
        this.panel.add(retourAuMenu);
        this.panel.add(supprimer);

        this.setContentPane(this.panel);

        retourAuMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminInterface admin = new AdminInterface(id);
                admin.setVisible(true);
                setVisible(false);
            }
        });

        ajouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        supprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

    private void venteJournee() throws SQLException {
        Connection connection = DriverManager.getConnection(url, usernameDB, passwordDB);
        String query = "SELECT idProduit, produits.designation AS produit_designation, idCategorie, categorie.designation AS categorie_designation, prixUnitaire, quantiteStock FROM produits JOIN categorie ON produits.categorieId = categorie.idCategorie ORDER BY idCategorie ASC;";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet resultSet = ps.executeQuery();
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        table.setRowHeight(30); // Adjust row height
        table.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font for table cells

        // Add columns to the table
        model.addColumn("Id de produit");
        model.addColumn("Produit");
        model.addColumn("Id de catégorie");
        model.addColumn("Catégorie");
        model.addColumn("Prix");
        model.addColumn("Quantité");

        while (resultSet.next()) {
            int idP = resultSet.getInt("idProduit");
            String produitDesignation = resultSet.getString("produit_designation");
            int idC = resultSet.getInt("idCategorie");
            String categorieDesignation = resultSet.getString("categorie_designation");
            double prix = resultSet.getDouble("prixUnitaire");
            int quantiteStock = resultSet.getInt("quantiteStock");
            model.addRow(new Object[]{
                    idP,
                    produitDesignation,
                    idC,
                    categorieDesignation,
                    prix,
                    quantiteStock
            });
        }

        JScrollPane tableJ = new JScrollPane(table);
        tableJ.setBounds(104, 210, 700, 400);

        this.panel.add(tableJ);

        resultSet.close();
        ps.close();
        connection.close();
    }
}
