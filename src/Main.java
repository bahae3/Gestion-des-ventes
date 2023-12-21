
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class Main {
    public static boolean adminLogin(String login, String pwd) {
        // Connecting to database
        String url = "jdbc:mysql://localhost:3306/itapp";
        String usernameDB = "root";
        String passwordDB = "";

        try {
            // Establishing the connection
            Connection connection = DriverManager.getConnection(url, usernameDB, passwordDB);
            // Fetching data from a table
            String users = "SELECT usernameAD, passwordAD FROM admin";
            PreparedStatement preparedStatement = connection.prepareStatement(users);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Process each row of the result set
                String usernameSQL = resultSet.getString("usernameAd");
                String passwordSQL = resultSet.getString("passwordAd");

                // Retrieve other columns similarly
                if (usernameSQL.equals(login) && passwordSQL.equals(pwd)) {
                    return true;
                }
            }

            // Close the connections
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public static boolean vendeurLogin(String login, String pwd) {
        // Connecting to database
        String url = "jdbc:mysql://localhost:3306/itapp";
        String usernameDB = "root";
        String passwordDB = "";

        try {
            // Establishing the connection
            Connection connection = DriverManager.getConnection(url, usernameDB, passwordDB);
            // Fetching data from a table
            String users = "SELECT login, mdp FROM utilisateurs";
            PreparedStatement preparedStatement = connection.prepareStatement(users);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Process each row of the result set
                String usernameSQL = resultSet.getString("login");
                String passwordSQL = resultSet.getString("mdp");

                // Retrieve other columns similarly
                if (usernameSQL.equals(login) && passwordSQL.equals(pwd)) {
                    return true;
                }
            }

            // Close the connections
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public static int vendeurIdFromDb(String login, String pwd) throws SQLException {
        // Connecting to database
        String url = "jdbc:mysql://localhost:3306/itapp";
        String usernameDB = "root";
        String passwordDB = "";

        // Establishing the connection
        Connection connection = DriverManager.getConnection(url, usernameDB, passwordDB);
        // Fetching data from a table
        String users = "SELECT idUser, login, mdp FROM utilisateurs WHERE login=? AND mdp=?";
        PreparedStatement preparedStatement = connection.prepareStatement(users);
        // these setString methods replace the "?" in var users query
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, pwd);
        ResultSet resultSet = preparedStatement.executeQuery();
        int idSQL = -1;
        if (resultSet.next()) {
            idSQL = resultSet.getInt("idUser");
        }

        // Close the connections
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return idSQL;
    }

    public static void main(String[] args) {
        JFrame loginPage = new JFrame();
        loginPage.setTitle("Info Technologies - Interface d'authentification");

        loginPage.setBounds(460, 200, 710, 424);
        loginPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel seConnecter = new JLabel("Veuillez vous connecter");
        seConnecter.setBounds(204, 50, 250, 25);
        Font logoFontConn = new Font("Times New Roman", Font.BOLD, 28);
        seConnecter.setFont(logoFontConn);
        seConnecter.setForeground(Color.red);

        JLabel login = new JLabel("Username");
        JTextField loginTf = new JTextField();
        login.setBounds(100, 135, 100, 20);
        loginTf.setBounds(180, 135, 150, 20);

        JLabel password = new JLabel("Password");
        JPasswordField passwordTf = new JPasswordField();
        password.setBounds(100, 195, 100, 20);
        passwordTf.setBounds(180, 195, 150, 20);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(180, 265, 100, 20);
        loginButton.setBackground(new Color(60, 160, 240));
        loginButton.setForeground(Color.WHITE);

        panel.add(seConnecter);
        panel.add(login);
        panel.add(loginTf);
        panel.add(password);
        panel.add(passwordTf);
        panel.add(loginButton);

        // Beautify the fonts, colors...
        JLabel logoLabel = new JLabel("IT");
        logoLabel.setBounds(544, 115, 100, 100);
        Font logoFont = new Font("Lucida Handwriting", Font.BOLD, 50);
        logoLabel.setFont(logoFont);
        logoLabel.setForeground(new Color(100, 100, 255));

        JLabel logoLabelLong = new JLabel("Info Technologies");
        logoLabelLong.setBounds(485, 160, 200, 100);
        Font logoFontLong = new Font("Segoe Script", Font.BOLD, 18);
        logoLabelLong.setFont(logoFontLong);
        logoLabelLong.setForeground(new Color(100, 100, 255));

        panel.add(logoLabel);
        panel.add(logoLabelLong);

        loginPage.setContentPane(panel);

        // ActionListener for the loginButton
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Retrieving username and pasword if it's an admin
                String username = loginTf.getText();
                String pwd = new String(passwordTf.getPassword());
                // affecting those data to Utilisateur class
                Utilisateur utilisateur = new Utilisateur(0, username, pwd);
                // checking if it's actually exists in db
                if (adminLogin(utilisateur.getLogin(), utilisateur.getPassword())) {
                    // Assuming admin id i always 1
                    utilisateur.id = 1;
                    // we close the login frame and open a new frame of "admin"
                    AdminInterface adminInt = new AdminInterface(utilisateur.id);
                    loginPage.setVisible(false);
                    adminInt.setVisible(true);
                } else if (vendeurLogin(utilisateur.getLogin(), utilisateur.getPassword())) {
                    // we change the id to the "vendeur" id in the db
                    try {
                        utilisateur.id = vendeurIdFromDb(username, pwd);
                        // we close the login frame and open a new frame of "vendeur"
                        VendeurInterface vendeurInt = new VendeurInterface(utilisateur.id);
                        loginPage.setVisible(false);
                        vendeurInt.setVisible(true);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Error handling for incorrect login
                    JLabel error = new JLabel("Username ou Password incorrect.");
                    error.setBounds(180, 342, 220, 20);
                    error.setForeground(Color.red);
                    panel.add(error);
                    loginPage.setContentPane(panel);
                }
            }
        });

        // Here I set the frame to be visible with all elements
        loginPage.setVisible(true);
    }
}
