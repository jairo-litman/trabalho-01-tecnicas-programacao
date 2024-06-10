package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import backend.Documentary;
import backend.Film;
import backend.Manager;
import backend.Media;
import backend.Series;

// Classe principal que cria a janela do menu Xilften
public class XilftenMenu extends JFrame {
    Manager dbManager;

    // Construtor da classe XilftenMenu
    public XilftenMenu(Manager dbManager) {
        this.dbManager = dbManager;

        setTitle("XILFTEN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(229, 9, 14));
        setVisible(true);

        try {
            List<Media> filmes = dbManager.get("films");
            System.out.println("Filmes");
            for (Media m: filmes) {
                Film f = (Film) m;
                System.out.println(f);
            }
            List<Media> series = dbManager.get("series");
            System.out.println("Séries");
            for (Media s: series) {
                Series k = (Series) s;
                System.out.println(k);
            }
            List<Media> documentaries = dbManager.get("documentaries");
            System.out.println("Documentários");
            for (Media d: documentaries) {
                Documentary j = (Documentary) d;
                System.out.println(j);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
        Font bebasFont = loadFont("Bebas.ttf", 100f);

        // Cria e configura o rótulo do título
        JLabel titleLabel = new JLabel("XILFTEN", SwingConstants.CENTER);
        if (bebasFont != null) {
            titleLabel.setFont(bebasFont);
        } else {
            titleLabel.setFont(new Font("Arial", Font.BOLD, 100));
        }
        titleLabel.setForeground(Color.WHITE);

        panel.add(titleLabel, gbc);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        // Botão "Cadastrar"
        JButton cadastrarButton = new CustomButton("Cadastrar");
        cadastrarButton.setFont(buttonFont);
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre a janela "Cadastro" quando o botão "Cadastrar" for clicado
                Cadastro cadastro = new Cadastro();
                cadastro.setVisible(true);
            }
        });

        // Botão "Inserir"
        JButton inserirButton = new CustomButton("Inserir");
        inserirButton.setFont(buttonFont);
        inserirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre a janela "Inserir" quando o botão "Inserir" for clicado
                Inserir inserir = new Inserir();
                inserir.setVisible(true);
            }
        });

        // Botão "Consultar"
        JButton consultarButton = new CustomButton("Consultar");
        consultarButton.setFont(buttonFont);
        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre a janela "Consultar" quando o botão "Consultar" for clicado
                Consultar consultar = new Consultar();
                consultar.setVisible(true);
            }
        });

        // Botão "Remover"
        JButton removerButton = new CustomButton("Remover");
        removerButton.setFont(buttonFont);

        // Adiciona os botões ao painel
        panel.add(cadastrarButton, gbc);
        panel.add(inserirButton, gbc);
        panel.add(consultarButton, gbc);
        panel.add(removerButton, gbc);

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

    // Classe personalizada para criar botões com estilo específico
    private static class CustomButton extends JButton {
        public CustomButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setPreferredSize(new Dimension(150, 40));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.BLACK);
            g2.fillRect(5, 5, getWidth(), getHeight());
            g2.setColor(getBackground());
            g2.fillRect(0, 0, getWidth() - 5, getHeight() - 5);
            g2.setColor(getForeground());
            g2.drawRect(0, 0, getWidth() - 5, getHeight() - 5);
            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        public void updateUI() {
            setUI(new CustomButtonUI());
        }
    }

    // Classe personalizada para gerenciar a interface do usuário dos botões
    // personalizados
    private static class CustomButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
        @Override
        public void installUI(JComponent c) {
            super.installUI(c);
            JButton button = (JButton) c;
            button.setOpaque(false);
            button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            JButton button = (JButton) c;
            paintBackground(g, button);
            super.paint(g, c);
        }

        private void paintBackground(Graphics g, JButton button) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.BLACK);
            g2.fillRect(5, 5, button.getWidth(), button.getHeight());
            g2.setColor(button.getBackground());
            g2.fillRect(0, 0, button.getWidth() - 5, button.getHeight() - 5);
            g2.setColor(button.getForeground());
            g2.drawRect(0, 0, button.getWidth() - 5, button.getHeight() - 5);
            g2.dispose();
        }
    }
}
