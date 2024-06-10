package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

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
        filmesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre a janela "MostraFilmes" quando o botão "filmes" for clicado
                MostraFilmes mostraFilmes = new MostraFilmes();
                mostraFilmes.setVisible(true);
            }
        });

        // Botão "Séries"
        JButton seriesButton = new JButton("Séries");
        seriesButton.setFont(buttonFont);
        seriesButton.setBackground(Color.RED);
        seriesButton.setForeground(Color.WHITE);
        seriesButton.setMargin(new Insets(5, 5, 5, 5));
        seriesButton.setFocusPainted(false);
        panel.add(seriesButton, gbc);
        seriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre a janela "MostraSeries" quando o botão "series" for clicado
                MostraSeries mostraSeries = new MostraSeries();
                mostraSeries.setVisible(true);
            }
        });

        // Botão "Documentários"
        JButton documentariosButton = new JButton("Documentários");
        documentariosButton.setFont(buttonFont);
        documentariosButton.setBackground(Color.RED);
        documentariosButton.setForeground(Color.WHITE);
        documentariosButton.setMargin(new Insets(5, 5, 5, 5));
        documentariosButton.setFocusPainted(false);
        panel.add(documentariosButton, gbc);
        documentariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre a janela "MostraSeries" quando o botão "series" for clicado
                MostraDocumentarios mostraDocumentarios = new MostraDocumentarios();
                mostraDocumentarios.setVisible(true);
            }
        });

        add(panel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    // Método para carregar uma fonte personalizada
    private Font loadFont(String fontFileName, float size) {
        try (InputStream is = getClass().getResourceAsStream(fontFileName)) {
            if (is == null) {
                throw new IOException("Font resource not found: " + fontFileName);
            }
            return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}
