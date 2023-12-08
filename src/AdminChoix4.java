// Ce class est pour l'option 4:
// Générer des bilans selon la catégorie des produits

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

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
        bilan.setBounds(154, 42, 385, 24);
        Font logoFontConn = new Font("Times New Roman", Font.BOLD, 26);
        bilan.setFont(logoFontConn);
        bilan.setForeground(Color.BLUE);

        Connection conn = DriverManager.getConnection(url, usernameDB, passwordDB);
        String query2 = "SELECT designation FROM categorie";
        StringBuilder resultat = new StringBuilder();
        PreparedStatement ps2 = conn.prepareStatement(query2);
        ResultSet resultSet2 = ps2.executeQuery();

        while (resultSet2.next()) {
            resultat.append(resultSet2.getString("designation")).append(", ");
        }

        resultat.append("...");

        JLabel resultCateg = new JLabel(String.valueOf(resultat));
        resultCateg.setBounds(100, 110, 600, 30);
        Font logoFontCat = new Font("Calibri", Font.BOLD, 17);
        resultCateg.setFont(logoFontCat);
        resultCateg.setForeground(Color.RED);
        this.panel.add(resultCateg);

        JLabel categorie = new JLabel("Saisir la catégorie: ");
        categorie.setBounds(90, 183, 150, 30);
        categorie.setFont(new Font("Arial", Font.BOLD, 16));
        categorie.setForeground(new Color(60, 160, 240));

        JTextField catTf = new JTextField();
        catTf.setBounds(260, 183, 150, 26);

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
        this.panel.add(catTf);
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
                String categorie = Character.toUpperCase(catTf.getText().charAt(0)) + catTf.getText().substring(1);

                JFrame bilan = new JFrame();
                bilan.setTitle("Bilan de vente selon la catégorie");
                bilan.setVisible(true);
                bilan.setBounds(380, 146, 800, 460);
                bilan.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel panelBilan = new JPanel();
                panelBilan.setLayout(null);

                JLabel bilanTitle = new JLabel("Bilan de vente de la catégorie " + categorie);
                bilanTitle.setBounds(150, 42, 550, 30);
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
                    table.setRowHeight(30);
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
                        model.addRow(new Object[]{
                                produit,
                                categorieDes,
                                date,
                                qttVendu,
                                user
                        });
                    }

                    JScrollPane tableJ = new JScrollPane(table);
                    tableJ.setBounds(100, 110, 600, 200);
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
