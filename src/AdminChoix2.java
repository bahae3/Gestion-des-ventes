// Ce class est pour l'option 2:
// Gérer les entités Produit et Catégorie (CRUD)

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
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
        this.setTitle("Interface de l'administrateur - Ajouter/Supprimer un produit");
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

        // This is the beginning of the table
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

        // Iterate through columns to set their preferred widths
        int[] columnWidths = {100, 200, 100, 150, 100, 100}; // Adjust the widths as needed

        for (int i = 0; i < columnWidths.length; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

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
        tableJ.setBounds(104, 210, 700, 318);

        // Apply modern table appearance
        table.setGridColor(Color.LIGHT_GRAY);
        table.setSelectionBackground(new Color(135, 206, 250));
        table.setSelectionForeground(Color.WHITE);
        table.setShowVerticalLines(false);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);


        this.panel.add(tableJ);

        resultSet.close();
        ps.close();
        connection.close();
        // This is the end of the table

        JLabel pourSupp = new JLabel("Pour  supprimer  un  produit:");
        pourSupp.setBounds(110, 575, 350, 30);
        Font logoFontPourSupp = new Font("Helvetica", Font.BOLD, 15);
        pourSupp.setFont(logoFontPourSupp);
        pourSupp.setForeground(Color.RED);

        JLabel suppMsg = new JLabel("**Cliquez sur le nom du produit puis cliquez sur le boutton rouge 'Supprimer'");
        suppMsg.setBounds(110, 600, 550, 30);
        Font logoFontSupp = new Font("Helvetica", Font.BOLD, 14);
        suppMsg.setFont(logoFontSupp);
        suppMsg.setForeground(Color.RED);

        this.panel.add(pourSupp);
        this.panel.add(suppMsg);
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
                JFrame ajoutFrame = new JFrame();
                ajoutFrame.setTitle("Ajouter un produit");
                ajoutFrame.setVisible(true);
                ajoutFrame.setBounds(600, 280, 450, 320);
                ajoutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel ajoutPanel = new JPanel();
                ajoutPanel.setLayout(null);

                JLabel remplirLabel = new JLabel("Remplir les informations du produit");
                remplirLabel.setBounds(65, 20, 310, 30);
                Font logoFontConn = new Font("Times New Roman", Font.BOLD, 20);
                remplirLabel.setFont(logoFontConn);
                remplirLabel.setForeground(new Color(60, 160, 240));

                JLabel designation = new JLabel("Désignation:");
                designation.setBounds(20, 70, 100, 25);
                JTextField designationTf = new JTextField();
                designationTf.setBounds(140, 70, 130, 25);

                JLabel prix = new JLabel("Prix:");
                prix.setBounds(20, 105, 100, 25);
                JTextField prixTf = new JTextField();
                prixTf.setBounds(140, 105, 130, 25);

                JLabel qtt = new JLabel("Quantité:");
                qtt.setBounds(20, 140, 100, 25);
                SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 1000, 1);
                JSpinner qttSp = new JSpinner(spinnerModel);
                qttSp.setBounds(140, 140, 130, 25);

                JLabel categorie = new JLabel("Catégorie:");
                categorie.setBounds(20, 175, 100, 25);

                JComboBox<String> catCombo;
                try {
                    Connection conn = DriverManager.getConnection(url, usernameDB, passwordDB);
                    String query = "SELECT designation FROM categorie";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ResultSet rs = ps.executeQuery();

                    catCombo = new JComboBox<>();

                    while (rs.next()) {
                        String desi = rs.getString("designation");
                        catCombo.addItem(desi);
                    }

                    rs.close();
                    ps.close();
                    conn.close();

                    catCombo.setBounds(140, 175, 130, 25);

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                JButton valider = new JButton("Ajouter");
                valider.setBounds(100, 225, 100, 25);
                valider.setBackground(Color.GREEN);

                ajoutPanel.add(remplirLabel);
                ajoutPanel.add(designation);
                ajoutPanel.add(designationTf);
                ajoutPanel.add(prix);
                ajoutPanel.add(prixTf);
                ajoutPanel.add(qtt);
                ajoutPanel.add(qttSp);
                ajoutPanel.add(categorie);
                ajoutPanel.add(catCombo);
                ajoutPanel.add(valider);

                ajoutFrame.setContentPane(ajoutPanel);

                valider.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String designationValue = designationTf.getText();
                        double prixValue = Double.parseDouble(prixTf.getText());
                        int qttValue = (int) qttSp.getValue();
                        String categorieValue = (String) catCombo.getSelectedItem();

                        try {
                            Connection conn = DriverManager.getConnection(url, usernameDB, passwordDB);
                            String queryGetCategorie = "SELECT idCategorie FROM categorie WHERE designation=?";
                            PreparedStatement ps1 = conn.prepareStatement(queryGetCategorie);
                            ps1.setString(1, categorieValue);
                            ResultSet rs1 = ps1.executeQuery();
                            int categoryId = 0; // Default value in case no category ID is found
                            if (rs1.next()) {
                                categoryId = rs1.getInt("idCategorie");
                            }

                            rs1.close();
                            ps1.close();

                            String queryInsert = "INSERT INTO produits(designation, prixUnitaire, quantiteStock, categorieId)VALUES(?, ?, ?, ?)";
                            PreparedStatement ps2 = conn.prepareStatement(queryInsert);
                            ps2.setString(1, designationValue);
                            ps2.setDouble(2, prixValue);
                            ps2.setInt(3, qttValue);
                            ps2.setInt(4, categoryId);
                            ps2.executeUpdate();

                            ps2.close();
                            conn.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                        ajoutFrame.setVisible(false);
                        setVisible(false);
                        try {
                            AdminChoix2 adminChoix2Ajout = new AdminChoix2(id);
                            adminChoix2Ajout.setVisible(true);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }


                    }
                });

            }
        });

        supprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                int selectedColumn = table.getSelectedColumn();

                Object selectedValueObject = table.getValueAt(selectedRow, selectedColumn);
                String selectedValue = String.valueOf(selectedValueObject);

                JFrame sureteFrame = new JFrame();
                sureteFrame.setTitle("Etes-vous sur?");
                sureteFrame.setVisible(true);
                sureteFrame.setBounds(700, 350, 350, 180);
                sureteFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel suretePanel = new JPanel();
                suretePanel.setLayout(null);

                JLabel suppLabel = new JLabel("Voulez-vous vraiment le supprimer?");
                suppLabel.setBounds(30, 30, 290, 30);
                Font logoFontConn = new Font("Times New Roman", Font.BOLD, 17);
                suppLabel.setFont(logoFontConn);
                suppLabel.setForeground(new Color(60, 160, 240));

                JButton oui = new JButton("Oui");
                oui.setBounds(70, 90, 80, 25);
                oui.setBackground(Color.RED);

                JButton non = new JButton("Non");
                non.setBounds(190, 90, 80, 25);
                non.setBackground(Color.GREEN);

                suretePanel.add(suppLabel);
                suretePanel.add(oui);
                suretePanel.add(non);

                sureteFrame.setContentPane(suretePanel);

                oui.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Connection conn = DriverManager.getConnection(url, usernameDB, passwordDB);
                            String query = "DELETE FROM produits WHERE designation=?";
                            PreparedStatement ps = conn.prepareStatement(query);
                            ps.setString(1, selectedValue);
                            ps.executeUpdate();

                            ps.close();
                            conn.close();

                            sureteFrame.setVisible(false);
                            setVisible(false);
                            AdminChoix2 newAdmin2 = new AdminChoix2(id);
                            newAdmin2.setVisible(true);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                });

                non.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        sureteFrame.setVisible(false);
                    }
                });

            }
        });

    }
}
