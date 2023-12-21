import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdminInterface extends JFrame {
    int id;

    public AdminInterface(int id) {
        this.id = id;
        this.setTitle("Interface de l'administrateur");
        this.setVisible(false);
        this.setBounds(460, 170, 700, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel bienvenue = new JLabel("Bienvenue sur l'interface de l'admin");
        bienvenue.setBounds(110, 62, 450, 25);
        Font logoFontVen = new Font("Times New Roman", Font.BOLD, 27);
        bienvenue.setFont(logoFontVen);
        bienvenue.setForeground(Color.red);

        JLabel gestion = new JLabel("Que souhaitez-vous faire:");
        gestion.setBounds(148, 120, 250, 25);
        gestion.setFont(new Font("Arial", Font.BOLD, 16));
        gestion.setForeground(Color.BLUE);

        JRadioButton choix1 = new JRadioButton("Consulter les ventes réalisées dans les journées.");
        choix1.setBounds(180, 160, 400, 25);
        choix1.setFont(new Font("Arial", Font.PLAIN, 14));
        choix1.setForeground(Color.BLACK);

        JRadioButton choix2 = new JRadioButton("Gérer les produits.");
        choix2.setBounds(180, 190, 250, 25);
        choix2.setFont(new Font("Arial", Font.PLAIN, 14));
        choix2.setForeground(Color.BLACK);

        JRadioButton choix3 = new JRadioButton("Générer le bilan des ventes entre deux dates.");
        choix3.setBounds(180, 220, 400, 25);
        choix3.setFont(new Font("Arial", Font.PLAIN, 14));
        choix3.setForeground(Color.BLACK);

        JRadioButton choix4 = new JRadioButton("Générer des bilans selon la catégorie des produits.");
        choix4.setBounds(180, 250, 400, 25);
        choix4.setFont(new Font("Arial", Font.PLAIN, 14));
        choix4.setForeground(Color.BLACK);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(choix1);
        buttonGroup.add(choix2);
        buttonGroup.add(choix3);
        buttonGroup.add(choix4);

        JButton button = new JButton("Valider");
        button.setBounds(195, 310, 100, 25);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLUE);

        JButton quitterBtn = new JButton("Quitter");
        quitterBtn.setBounds(335, 310, 100, 25);
        quitterBtn.setFont(new Font("Arial", Font.BOLD, 12));
        quitterBtn.setForeground(Color.WHITE);
        quitterBtn.setBackground(Color.RED);

        JLabel logoLabel = new JLabel("IT");
        logoLabel.setBounds(485, 335, 100, 100);
        Font logoFont = new Font("Lucida Handwriting", Font.BOLD, 40);
        logoLabel.setFont(logoFont);
        logoLabel.setForeground(new Color(100, 100, 255));

        JLabel logoLabelLong = new JLabel("Info Technologies");
        logoLabelLong.setBounds(400, 375, 200, 100);
        Font logoFontLong = new Font("Segoe Script", Font.BOLD, 14);
        logoLabelLong.setFont(logoFontLong);
        logoLabelLong.setForeground(new Color(100, 100, 255));

        panel.add(bienvenue);
        panel.add(gestion);
        panel.add(choix1);
        panel.add(choix2);
        panel.add(choix3);
        panel.add(choix4);
        panel.add(button);
        panel.add(quitterBtn);
        panel.add(logoLabel);
        panel.add(logoLabelLong);

        this.setContentPane(panel);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (choix1.isSelected()) {
                    AdminChoix1 choix1Int;
                    try {
                        choix1Int = new AdminChoix1(id);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    choix1Int.setVisible(true);
                    setVisible(false);
                } else if (choix2.isSelected()) {
                    AdminChoix2 choix2Int;
                    try {
                        choix2Int = new AdminChoix2(id);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    choix2Int.setVisible(true);
                    setVisible(false);
                } else if (choix3.isSelected()) {
                    AdminChoix3 choix3Int = new AdminChoix3(id);
                    choix3Int.setVisible(true);
                    setVisible(false);

                } else if (choix4.isSelected()) {
                    AdminChoix4 choix4Int;
                    try {
                        choix4Int = new AdminChoix4(id);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    choix4Int.setVisible(true);
                    setVisible(false);

                }


            }
        });

        quitterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


    }
}
