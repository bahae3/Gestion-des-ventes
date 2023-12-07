// Ce class est pour l'option 4:
// Générer des bilans selon la catégorie des produits

import javax.swing.*;
import java.awt.*;

public class AdminChoix4 extends JFrame {
    int id;
    String url = "jdbc:mysql://localhost:3306/itapp";
    String usernameDB = "root";
    String passwordDB = "";
    JPanel panel = new JPanel();

    public AdminChoix4(int id){
        this.id = id;
        this.setTitle("Interface de l'administrateur - Choix 4");
        this.setVisible(false);
        this.setBounds(460, 200, 710, 424);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.panel.setLayout(null);

        JLabel bilan = new JLabel("Bilan des ventes selon la catégorie");
        bilan.setBounds(100, 135, 100, 25);
        Font logoFontConn = new Font("Times New Roman", Font.BOLD, 26);
        bilan.setFont(logoFontConn);
        bilan.setForeground(Color.BLUE);

        JLabel categorie = new JLabel("Saisir la catégorie: ");
        categorie.setBounds(142, 92, 300, 30);
        categorie.setFont(new Font("Arial", Font.BOLD, 16));
        categorie.setForeground(new Color(60, 160, 240));

        JTextField catTf = new JTextField();
        catTf.setBounds(160, 135, 150, 27);

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

    }
}
