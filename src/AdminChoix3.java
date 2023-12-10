// Ce class est pour l'option 3:
// Générer le bilan des ventes entre deux dates

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AdminChoix3 extends JFrame {
    int id;
    String url = "jdbc:mysql://localhost:3306/itapp";
    String usernameDB = "root";
    String passwordDB = "";
    JPanel panel = new JPanel();

    public AdminChoix3(int id) {
        this.id = id;
        this.setTitle("Interface de l'administrateur - Choix 3");
        this.setVisible(false);
        this.setBounds(460, 200, 710, 424);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.panel.setLayout(null);

        JLabel bilan = new JLabel("Bilan des ventes entre 2 dates");
        bilan.setBounds(180, 42, 400, 30);
        Font logoFontConn = new Font("Times New Roman", Font.BOLD, 26);
        bilan.setFont(logoFontConn);
        bilan.setForeground(Color.BLUE);

        JLabel dates = new JLabel("Entrez les dates souhaitées: ");
        dates.setBounds(142, 92, 300, 30);
        dates.setFont(new Font("Arial", Font.BOLD, 16)); // Setting font style and size
        dates.setForeground(new Color(60, 160, 240));

        JLabel dateVente1 = new JLabel("Date 1: ");
        dateVente1.setBounds(100, 135, 100, 25);

        PlaceholderTextField dateVente1Tf = new PlaceholderTextField("yyyy-mm-dd");
        dateVente1Tf.setBounds(160, 135, 150, 27);

        JLabel dateVente2 = new JLabel("Date 2: ");
        dateVente2.setBounds(100, 195, 100, 25);

        PlaceholderTextField dateVente2Tf = new PlaceholderTextField("yyyy-mm-dd");
        dateVente2Tf.setBounds(160, 195, 150, 27);

        JLabel nb = new JLabel("* Veuillez saisir une Date 1 antérieure à la Date 2 *");
        nb.setBounds(44, 240, 400, 30);
        Font logoFontNb = new Font("Calibri", Font.ITALIC, 16);
        nb.setFont(logoFontNb);
        nb.setForeground(Color.RED);

        JButton valider = new JButton("Générer");
        valider.setBounds(130, 290, 100, 25);
        valider.setFont(new Font("Arial", Font.BOLD, 12));
        valider.setForeground(Color.WHITE);
        valider.setBackground(Color.BLUE);

        JButton menu = new JButton("Menu");
        menu.setBounds(250, 290, 100, 25);
        menu.setFont(new Font("Arial", Font.BOLD, 12));
        menu.setForeground(Color.WHITE);
        menu.setBackground(Color.BLUE);

        this.panel.add(bilan);
        this.panel.add(dates);
        this.panel.add(dateVente1);
        this.panel.add(dateVente1Tf);
        this.panel.add(dateVente2);
        this.panel.add(dateVente2Tf);
        this.panel.add(nb);
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
            public void actionPerformed(ActionEvent evt) {
                setVisible(false);
                String date1 = dateVente1Tf.getText();
                String date2 = dateVente2Tf.getText();

                JFrame bilan = new JFrame();
                bilan.setTitle("Bilan de vente entre 2 dates");
                bilan.setVisible(true);
                bilan.setBounds(380, 146, 800, 460);
                bilan.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel panelBilan = new JPanel();
                panelBilan.setLayout(null);

                JLabel bilanTitle = new JLabel("Bilan de vente entre " + date1 + " et " + date2);
                bilanTitle.setBounds(145, 42, 550, 30);
                Font logoFontConn = new Font("Times New Roman", Font.BOLD, 26);
                bilanTitle.setFont(logoFontConn);
                bilanTitle.setForeground(new Color(60, 160, 240));
                panelBilan.add(bilanTitle);
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedDate1 = sdf.parse(date1);
                    java.util.Date parsedDate2 = sdf.parse(date2);

                    // Convert java.util.Date to java.sql.Date
                    java.sql.Date sqlDate1 = new java.sql.Date(parsedDate1.getTime());
                    java.sql.Date sqlDate2 = new java.sql.Date(parsedDate2.getTime());

                    Connection conn = DriverManager.getConnection(url, usernameDB, passwordDB);
                    String query = "SELECT produits.designation AS produit_designation, categorie.designation AS categorie_designation, dateVente, quantiteVendu, utilisateurs.login FROM ventes JOIN produits ON ventes.produitVenduId = produits.idProduit JOIN categorie ON produits.categorieId = categorie.idCategorie JOIN utilisateurs ON ventes.idVendeur = utilisateurs.idUser WHERE dateVente BETWEEN ? AND ? ORDER BY dateVente DESC;";

                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setDate(1, sqlDate1);
                    ps.setDate(2, sqlDate2);

                    ResultSet resultSet = ps.executeQuery();

                    DefaultTableModel model = new DefaultTableModel();
                    JTable table = new JTable(model);
                    table.setRowHeight(30);
                    table.setFont(new Font("Arial", Font.PLAIN, 14));

                    model.addColumn("Produit");
                    model.addColumn("Catégorie");
                    model.addColumn("Date de vente");
                    model.addColumn("Quantité vendue");
                    model.addColumn("Utilisateur");

                    // Process the retrieved data from resultSet
                    while (resultSet.next()) {
                        String produit = resultSet.getString("produit_designation");
                        String categorie = resultSet.getString("categorie_designation");
                        Date date = resultSet.getDate("dateVente");
                        int qttVendu = resultSet.getInt("quantiteVendu");
                        String user = resultSet.getString("login");
                        model.addRow(new Object[]{
                                produit,
                                categorie,
                                date,
                                qttVendu,
                                user
                        });
                    }

                    JScrollPane tableJ = new JScrollPane(table);
                    tableJ.setBounds(100, 110, 600, 200);
                    panelBilan.add(tableJ);

                    resultSet.close();
                    ps.close();
                    conn.close();
                } catch (ParseException | SQLException ex) {
                    throw new RuntimeException(ex);
                }

                JButton retour = new JButton("Retour en avant");
                retour.setBounds(310, 351, 145, 30);
                retour.setBackground(new Color(60, 160, 240));

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
