// Ce class est pour l'option 1:
// Consulter les ventes réalisées dans la journée

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;

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
        labelVentes.setBounds(300, 50, 300, 30);
        Font logoFontConn = new Font("Times New Roman", Font.BOLD, 26);
        labelVentes.setFont(logoFontConn);
        labelVentes.setForeground(new Color(60, 160, 240));

        JButton retourAuMenu = new JButton("Retour au menu");
        retourAuMenu.setBounds(378, 130, 144, 30);
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        while (resultSet.next()) {
            String produitDesignation = resultSet.getString("produit_designation");
            String categorieDesignation = resultSet.getString("categorie_designation");
            Date dateVente = resultSet.getDate("dateVente");
            int quantiteVendu = resultSet.getInt("quantiteVendu");
            String utilisateurLogin = resultSet.getString("login");

            String formattedDate = dateFormat.format(dateVente);

            model.addRow(new Object[]{
                    produitDesignation,
                    categorieDesignation,
                    formattedDate,
                    quantiteVendu,
                    utilisateurLogin
            });
        }

        JScrollPane tableJ = new JScrollPane(table);
        tableJ.setBounds(150, 210, 600, 387);

        // Apply modern table appearance
        table.setGridColor(new Color(200, 200, 200)); // Adjust grid color
        table.setSelectionBackground(new Color(135, 206, 250)); // Change selection background color
        table.setSelectionForeground(Color.WHITE); // Change selection text color
        table.setShowVerticalLines(false); // Hide vertical grid lines

        // Customize table header appearance
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12)); // Set header font
        header.setBackground(Color.WHITE); // Set header background color
        header.setForeground(Color.BLACK); // Set header text color
        header.setReorderingAllowed(false);

        this.panel.add(tableJ);

        resultSet.close();
        ps.close();
        connection.close();
    }


}
