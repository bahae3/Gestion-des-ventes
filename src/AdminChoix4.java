// Ce class est pour l'option 4:
// Générer des bilans selon la catégorie des produits

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;

public class AdminChoix4 extends JFrame {
    int id;
    String url = "jdbc:mysql://localhost:3306/itapp";
    String usernameDB = "root";
    String passwordDB = "";
    JPanel panel = new JPanel();

    public AdminChoix4(int id) throws SQLException {
        this.id = id;
        this.setTitle("Interface de l'administrateur - Choix 4");
        this.setVisible(false);
        this.setBounds(460, 200, 710, 424);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.panel.setLayout(null);

        JLabel bilan = new JLabel("Bilan des ventes selon la catégorie");
        bilan.setBounds(154, 42, 385, 30);
        Font logoFontConn = new Font("Times New Roman", Font.BOLD, 26);
        bilan.setFont(logoFontConn);
        bilan.setForeground(Color.BLUE);

        Connection conn = DriverManager.getConnection(url, usernameDB, passwordDB);
        String query2 = "SELECT designation FROM categorie";
        PreparedStatement ps2 = conn.prepareStatement(query2);
        ResultSet resultSet2 = ps2.executeQuery();

        JComboBox<String> catCombo = new JComboBox<>();
        while (resultSet2.next()) {
            String desi = resultSet2.getString("designation");
            catCombo.addItem(desi);
        }


        JLabel resultCateg = new JLabel("Sélectionnez la catégorie");
        resultCateg.setBounds(100, 110, 600, 30);
        Font logoFontCat = new Font("Calibri", Font.BOLD, 21);
        resultCateg.setFont(logoFontCat);
        resultCateg.setForeground(Color.RED);
        this.panel.add(resultCateg);

        JLabel categorie = new JLabel("La catégorie: ");
        categorie.setBounds(90, 181, 150, 30);
        categorie.setFont(new Font("Arial", Font.BOLD, 16));
        categorie.setForeground(new Color(60, 160, 240));

        catCombo.setBounds(225, 183, 150, 26);

        JButton valider = new JButton("Générer");
        valider.setBounds(140, 268, 100, 25);
        valider.setFont(new Font("Arial", Font.BOLD, 12));
        valider.setForeground(Color.WHITE);
        valider.setBackground(new Color(60, 160, 240));

        JButton menu = new JButton("Menu");
        menu.setBounds(260, 268, 100, 25);
        menu.setFont(new Font("Arial", Font.BOLD, 12));
        menu.setForeground(Color.WHITE);
        menu.setBackground(new Color(60, 160, 240));

        this.panel.add(bilan);
        this.panel.add(categorie);
        this.panel.add(catCombo);
        this.panel.add(valider);
        this.panel.add(menu);

        this.setContentPane(this.panel);

        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminInterface admin = new AdminInterface(id);
                admin.setVisible(true);
                setVisible(false);
            }
        });

        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                String categorie = (String) catCombo.getSelectedItem();

                JFrame bilan = new JFrame();
                bilan.setTitle("Bilan de vente selon la catégorie");
                bilan.setVisible(true);
                bilan.setBounds(380, 146, 800, 460);
                bilan.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel panelBilan = new JPanel();
                panelBilan.setLayout(null);

                JLabel bilanTitle = new JLabel("Bilan de vente de la catégorie " + categorie);
                bilanTitle.setBounds(165, 42, 540, 34);
                Font logoFontConn = new Font("Times New Roman", Font.BOLD, 26);
                bilanTitle.setFont(logoFontConn);
                bilanTitle.setForeground(new Color(60, 160, 240));
                panelBilan.add(bilanTitle);
                try {
                    Connection conn = DriverManager.getConnection(url, usernameDB, passwordDB);
                    String query1 = "SELECT produits.designation AS produit_designation, categorie.designation AS categorie_designation, dateVente, quantiteVendu, utilisateurs.login FROM ventes JOIN produits ON ventes.produitVenduId = produits.idProduit JOIN categorie ON produits.categorieId = categorie.idCategorie JOIN utilisateurs ON ventes.idVendeur = utilisateurs.idUser WHERE categorie.designation=?";
                    PreparedStatement ps1 = conn.prepareStatement(query1);
                    ps1.setString(1, categorie);
                    ResultSet resultSet = ps1.executeQuery();


                    DefaultTableModel model = new DefaultTableModel();
                    JTable table = new JTable(model);
                    table.setRowHeight(40); // Increase row height for better readability
                    table.setFont(new Font("Arial", Font.PLAIN, 14));

                    model.addColumn("Produit");
                    model.addColumn("Categorie");
                    model.addColumn("Date de vente");
                    model.addColumn("Quantité vendue");
                    model.addColumn("Utilisateur");

                    while (resultSet.next()) {
                        String produit = resultSet.getString("produit_designation");
                        String categorieDes = resultSet.getString("categorie_designation");
                        Date date = resultSet.getDate("dateVente");
                        int qttVendu = resultSet.getInt("quantiteVendu");
                        String user = resultSet.getString("login");

                        // Format the date to display in a specific format (e.g., dd/MM/yyyy)
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String formattedDate = dateFormat.format(date);

                        model.addRow(new Object[]{
                                produit,
                                categorieDes,
                                formattedDate, // Use formatted date string
                                qttVendu,
                                user
                        });
                    }

                    JScrollPane tableJ = new JScrollPane(table);
                    tableJ.setBounds(100, 110, 600, 200);

                    table.setGridColor(Color.LIGHT_GRAY); // Set grid color
                    table.setSelectionBackground(new Color(135, 206, 235)); // Set selection background color
                    table.setSelectionForeground(Color.BLACK); // Set selection text color
                    table.setShowVerticalLines(false); // Hide vertical grid lines

                    // Customize table header appearance
                    JTableHeader header = table.getTableHeader();
                    header.setFont(new Font("Arial", Font.BOLD, 12)); // Set header font
                    header.setBackground(Color.WHITE); // Set header background color
                    header.setForeground(Color.BLACK); // Set header text color
                    header.setReorderingAllowed(true); // Disable column reordering

                    panelBilan.add(tableJ);

                    resultSet.close();
                    ps1.close();
                    conn.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                JButton retour = new JButton("Retour en avant");
                retour.setBounds(327, 351, 145, 30);
                panelBilan.add(retour);

                bilan.setContentPane(panelBilan);

                retour.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        bilan.setVisible(false);
                        setVisible(true);
                    }
                });

            }
        });

    }
}
