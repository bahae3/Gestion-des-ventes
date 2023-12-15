// Ce class est pour le vendeur

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DeclarationSuccess extends JFrame {
    int id;
    public DeclarationSuccess(int id){
        this.id = id;
        this.setTitle("Interface du vendeur - Success");
        this.setVisible(false);
        this.setBounds(460, 200, 700, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Add the new elements and components
        JLabel success = new JLabel("Votre vente a été bien declarée.");
        success.setBounds(166, 100, 450, 25);
        Font logoFontConn = new Font("Times New Roman", Font.BOLD, 26);
        success.setFont(logoFontConn);
        success.setForeground(Color.red);

        JButton nouvelleVente = new JButton("Ajouter une vente");
        nouvelleVente.setBounds(130, 230, 150, 30);
        nouvelleVente.setBackground(Color.GREEN);

        // Si l'utilisateur clique sur cette boutton, le programme s'arrete
        JButton deconnecter = new JButton("Se déconnecter");
        deconnecter.setBounds(420, 230, 150, 30);
        deconnecter.setBackground(Color.RED);


        panel.add(success);
        panel.add(nouvelleVente);
        panel.add(deconnecter);

        this.setContentPane(panel);

        nouvelleVente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VendeurInterface vendeurInt = null;
                try {
                    vendeurInt = new VendeurInterface(id);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                setVisible(false);
                vendeurInt.setVisible(true);
            }
        });

        // if the users clicks "se deconnecter", the programm will stop
        deconnecter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });
    }
}
