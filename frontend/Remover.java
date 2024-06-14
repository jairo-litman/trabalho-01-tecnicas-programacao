package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import backend.Documentary;
import backend.Film;
import backend.Manager;
import backend.Media;
import backend.Series;

// Classe principal que cria a janela de remover do catálogo
public class Remover extends JFrame {
    private JComboBox<String> categoriaComboBox;
    private JComboBox<String> titleComboBox;
    private List<Media> media;
    Manager db;

    // Construtor da classe Remover
    public Remover() {
        setTitle("Remover itens");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        try {
            Manager db = Manager.getInstance();
            this.db = db;
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

        // ComboBox para os títulos
        titleComboBox = new JComboBox<>();

        // Carrega a fonte personalizada
        Font bebasFont = loadFont("Bebas.ttf", 30f);
        Font sansSerifBoldFont = new Font("SansSerif", Font.BOLD, 18);

        // cria as opções de categorias disponíveis
        String[] opcoes = { "Filme", "Série", "Documentário" };

        // cria o ComboBox para os títulos disponíveis dependendo da categoria
        categoriaComboBox = new JComboBox<>(opcoes);
        categoriaComboBox.setSelectedIndex(-1);
        categoriaComboBox.addActionListener(e -> {
            String selectedCategory = categoriaComboBox.getSelectedItem().toString();
            if ("Filme".equals(selectedCategory)) {
                titleComboBox.removeAllItems();

                try {
                    media = db.get("films");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                for (Media filmeMedia : media) {
                    Film filme = (Film) filmeMedia;
                    titleComboBox.addItem(filme.getTitle());
                }

            } else if ("Série".equals(selectedCategory)) {

                titleComboBox.removeAllItems();

                try {
                    media = db.get("series");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                for (Media seriesMedia : media) {
                    Series serie = (Series) seriesMedia;
                    titleComboBox.addItem(serie.getTitle());
                }

            } else if ("Documentário".equals(selectedCategory)) {

                titleComboBox.removeAllItems();

                try {
                    media = db.get("documentaries");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                for (Media docsMedia : media) {
                    Documentary doc = (Documentary) docsMedia;
                    titleComboBox.addItem(doc.getTitle());
                }

            }
        });
        ;

        // Cria e configura o rótulo do tipo
        JLabel titleLabel = new JLabel("Qual tipo deseja remover?", SwingConstants.CENTER);
        if (bebasFont != null) {
            titleLabel.setFont(bebasFont);
        } else {
            titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        }
        titleLabel.setForeground(Color.BLACK);

        // Cria e configura o rótulo para o título
        JLabel title = new JLabel("Título da obra que deseja excluir: ");
        title.setFont(sansSerifBoldFont);
        title.setForeground(Color.BLACK);

        // Botão para apagar registros selecionados com base na categoria
        JButton apagarButton = new JButton("Remover");
        apagarButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        apagarButton.setForeground(Color.WHITE);
        apagarButton.setBackground(Color.RED);
        apagarButton.setFocusPainted(false);
        apagarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String category = categoriaComboBox.getSelectedItem().toString();
                String tableName = "";
                String itemName = titleComboBox.getSelectedItem().toString();

                if (itemName.equals("")) {
                    JOptionPane.showMessageDialog(panel, "Insira o título da obra!", "Aviso",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                if ("Filme".equals(category)) {
                    tableName = "films";
                    try {
                        Film filme = (Film) db.get(tableName, itemName).get(0);
                        db.delete(filme);
                        System.out.println("Filme deletado!");
                        JOptionPane.showMessageDialog(panel, "Filme " + "'" + itemName + "'" + " deletado!", "Aviso",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e1) {
                        e1.getStackTrace();
                        JOptionPane.showMessageDialog(panel, "Erro ao deletar filme!", "Aviso",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                if ("Série".equals(category)) {
                    tableName = "series";
                    try {
                        Series serie = (Series) db.get(tableName, itemName).get(0);
                        db.delete(serie);
                        System.out.println("Série deletada!");
                        JOptionPane.showMessageDialog(panel, "Série " + "'" + itemName + "'" + " deletada!", "Aviso",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e1) {
                        e1.getStackTrace();
                        JOptionPane.showMessageDialog(panel, "Erro ao deletar série!", "Aviso",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                if ("Documentário".equals(category)) {
                    tableName = "documentaries";
                    try {
                        Documentary doc = (Documentary) db.get(tableName, itemName).get(0);
                        db.delete(doc);
                        System.out.println("Documentário deletado!");
                        JOptionPane.showMessageDialog(panel, "Documentário " + "'" + itemName + "'" + " deletado!",
                                "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e1) {
                        e1.getStackTrace();
                        JOptionPane.showMessageDialog(panel, "Erro ao deletar documentário!", "Aviso",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            }
        });

        panel.add(titleLabel, gbc);
        panel.add(categoriaComboBox, gbc);
        panel.add(title, gbc);
        panel.add(titleComboBox, gbc);
        panel.add(apagarButton, gbc);

        add(panel, BorderLayout.CENTER);

        pack();
        setSize(400, 600);
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
