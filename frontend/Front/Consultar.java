import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

// Classe principal que cria a janela de consulta ao catálogo
public class Consultar extends JFrame {

    // Construtor da classe Consultar
    public Consultar() {
        setTitle("Consultar Catálogo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        // Painel principal com layout GridBag
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Carrega a fonte personalizada
        Font bebasFont = loadFont("Bebas.ttf", 30f);

        // Cria e configura o rótulo do título
        JLabel titleLabel = new JLabel("Qual catalogo deseja consultar?", SwingConstants.CENTER);
        if (bebasFont != null) {
            titleLabel.setFont(bebasFont);
        } else {
            titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        }
        titleLabel.setForeground(Color.BLACK);

        panel.add(titleLabel, gbc);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 25);

        // Botão "Filmes"
        JButton filmesButton = new JButton("Filmes");
        filmesButton.setFont(buttonFont);
        filmesButton.setBackground(Color.RED);
        filmesButton.setForeground(Color.WHITE);
        filmesButton.setMargin(new Insets(5, 5, 5, 5));
        filmesButton.setFocusPainted(false);
        panel.add(filmesButton, gbc);

        // Botão "Séries"
        JButton seriesButton = new JButton("Séries");
        seriesButton.setFont(buttonFont);
        seriesButton.setBackground(Color.RED);
        seriesButton.setForeground(Color.WHITE);
        seriesButton.setMargin(new Insets(5, 5, 5, 5));
        seriesButton.setFocusPainted(false);
        panel.add(seriesButton, gbc);

        // Botão "Documentários"
        JButton documentariosButton = new JButton("Documentários");
        documentariosButton.setFont(buttonFont);
        documentariosButton.setBackground(Color.RED);
        documentariosButton.setForeground(Color.WHITE);
        documentariosButton.setMargin(new Insets(5, 5, 5, 5));
        documentariosButton.setFocusPainted(false);
        panel.add(documentariosButton, gbc);

        add(panel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    // Método para carregar uma fonte personalizada
    private Font loadFont(String fontFileName, float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(fontFileName)).deriveFont(size);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método principal para executar o programa
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Consultar().setVisible(true);
        });
    }
}
