import javax.swing.*;

public class AdminInterface extends JFrame{
    int id;
    public AdminInterface(int id){
        this.id = id;
        this.setTitle("Interface de l'administrateur");
        this.setVisible(false);
        this.setBounds(460, 200, 700, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



    }
}
