package frontend;

import javax.swing.*;

import backend.Documentary;
import backend.Film;
import backend.Manager;
import backend.Series;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

// Classe principal que cria a janela de cadastro
public class Cadastro extends JFrame {
    private JPanel panel;
    private GridBagConstraints gbc;
    private JLabel duracaoLabel;
    private JTextField duracaoTextField;
    private JLabel numTemporadasLabel;
    private JTextField numTemporadasTextField;
    private JLabel generoLabel;
    private JComboBox<String> generoComboBox;
    private JLabel dataLancamentoLabel;
    private JTextField dataLancamentoTextField;
    private JTextField titleTextField;
    private JComboBox<String> categoriaComboBox;

    Manager db;

    // Construtor da classe Cadastro
    public Cadastro() {
        setTitle("Cadastro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);

        try {
            Manager db = Manager.getInstance();
            this.db = db;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Painel principal com layout GridBag
        panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Carrega a fonte personalizada
        Font bebasFont = loadFont("Bebas.ttf", 60f);
        Font sansSerifBoldFont = new Font("SansSerif", Font.BOLD, 18);

        // Cria e configura o rótulo do título de cadastro
        JLabel cadastroLabel = new JLabel("CADASTRO", SwingConstants.CENTER);
        cadastroLabel.setForeground(Color.RED);
        if (bebasFont != null) {
            cadastroLabel.setFont(bebasFont);
        } else {
            cadastroLabel.setFont(new Font("Arial", Font.BOLD, 24));
        }

        // Cria e configura o rótulo e campo de texto para o título
        JLabel titleLabel = new JLabel("Título");
        titleLabel.setFont(sansSerifBoldFont);
        titleLabel.setForeground(Color.WHITE);

        titleTextField = new JTextField(20);

        // Cria e configura o rótulo e combo box para a categoria
        JLabel categoriaLabel = new JLabel("Categoria");
        categoriaLabel.setFont(sansSerifBoldFont);
        categoriaLabel.setForeground(Color.WHITE);

        String[] opcoes = { "Filme", "Série", "Documentário" };
        categoriaComboBox = new JComboBox<>(opcoes);
        categoriaComboBox.setSelectedIndex(-1);
        categoriaComboBox.addActionListener(e -> {
            // Define a visibilidade dos campos baseados na categoria selecionada
            String selectedCategory = (String) categoriaComboBox.getSelectedItem();
            if ("Filme".equals(selectedCategory) || "Documentário".equals(selectedCategory)) {
                duracaoLabel.setVisible(true);
                duracaoTextField.setVisible(true);
                numTemporadasLabel.setVisible(false);
                numTemporadasTextField.setVisible(false);
                generoLabel.setVisible(true);
                generoComboBox.setVisible(true);
                generoComboBox.removeAllItems();
                if ("Filme".equals(selectedCategory)) {
                    generoComboBox.addItem("Ação");
                    generoComboBox.addItem("Aventura");
                    generoComboBox.addItem("Comédia");
                    generoComboBox.addItem("Drama");
                    generoComboBox.addItem("Terror");
                    generoComboBox.addItem("Ficção Científica");
                    generoComboBox.addItem("Fantasia");
                } else if ("Documentário".equals(selectedCategory)) {
                    generoComboBox.addItem("Histórico");
                    generoComboBox.addItem("Natureza e Ecologia");
                    generoComboBox.addItem("Ciência");
                    generoComboBox.addItem("True Crime");
                    generoComboBox.addItem("Biografia");
                    generoComboBox.addItem("Política");
                    generoComboBox.addItem("Sociocultural");
                }
                generoComboBox.setSelectedIndex(-1);
                pack();
            } else if ("Série".equals(selectedCategory)) {
                duracaoLabel.setVisible(false);
                duracaoTextField.setVisible(false);
                numTemporadasLabel.setVisible(true);
                numTemporadasTextField.setVisible(true);
                generoLabel.setVisible(true);
                generoComboBox.setVisible(true);
                generoComboBox.removeAllItems();
                generoComboBox.addItem("Ação");
                generoComboBox.addItem("Aventura");
                generoComboBox.addItem("Comédia");
                generoComboBox.addItem("Drama");
                generoComboBox.addItem("Terror");
                generoComboBox.addItem("Ficção Científica");
                generoComboBox.addItem("Fantasia");
                generoComboBox.setSelectedIndex(-1);
                pack();
            } else {
                duracaoLabel.setVisible(false);
                duracaoTextField.setVisible(false);
                numTemporadasLabel.setVisible(false);
                numTemporadasTextField.setVisible(false);
                generoLabel.setVisible(false);
                generoComboBox.setVisible(false);
                pack();
            }
        });

        // Cria e configura o rótulo e campo de texto para a duração
        duracaoLabel = new JLabel("Duração");
        duracaoLabel.setFont(sansSerifBoldFont);
        duracaoLabel.setForeground(Color.WHITE);
        duracaoTextField = new JTextField(20);
        duracaoLabel.setVisible(false);
        duracaoTextField.setVisible(false);

        // Cria e configura o rótulo e campo de texto para o número de temporadas
        numTemporadasLabel = new JLabel("Número de Temporadas");
        numTemporadasLabel.setFont(sansSerifBoldFont);
        numTemporadasLabel.setForeground(Color.WHITE);
        numTemporadasTextField = new JTextField(20);
        numTemporadasLabel.setVisible(false);
        numTemporadasTextField.setVisible(false);

        // Cria e configura o rótulo e combo box para o gênero
        generoLabel = new JLabel("Gênero");
        generoLabel.setFont(sansSerifBoldFont);
        generoLabel.setForeground(Color.WHITE);
        generoLabel.setVisible(false);

        generoComboBox = new JComboBox<>();
        generoComboBox.addItem("");
        generoComboBox.addItem("Ação");
        generoComboBox.addItem("Aventura");
        generoComboBox.addItem("Comédia");
        generoComboBox.addItem("Drama");
        generoComboBox.addItem("Terror");
        generoComboBox.addItem("Ficção Científica");
        generoComboBox.addItem("Fantasia");
        generoComboBox.setSelectedIndex(-1);
        generoComboBox.setVisible(false);

        // Cria e configura o rótulo e campo de texto para o ano de lançamento
        dataLancamentoLabel = new JLabel("Ano de Lançamento");
        dataLancamentoLabel.setFont(sansSerifBoldFont);
        dataLancamentoLabel.setForeground(Color.WHITE);
        dataLancamentoTextField = new JTextField(20);

        // Cria e configura o botão "Cadastrar"
        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setFont(sansSerifBoldFont);
        cadastrarButton.setForeground(Color.WHITE);
        cadastrarButton.setBackground(Color.RED);
        cadastrarButton.setFocusPainted(false); // Remove a borda de foco
        cadastrarButton.addActionListener(e -> {
            // Ação executada ao clicar no botão "Cadastrar"
            String titulo = titleTextField.getText();
            String categoria = categoriaComboBox.getSelectedItem().toString();
            String genero = generoComboBox.getSelectedItem().toString();
            int dataLancamento = Integer.parseInt(dataLancamentoTextField.getText());

            // Grava os dados no banco de dados
            try {
                System.out.println(categoria);
                if ("Filme".equals(categoria)) {
                    int duracao = Integer.parseInt(duracaoTextField.getText());
                    Film filme = new Film(titulo, dataLancamento, genero, duracao);
                    db.save(filme);
                    System.out.println("Filme cadastrado!");
                }
                if ("Série".equals(categoria)) {
                    int numTemporadas = Integer.parseInt(numTemporadasTextField.getText());
                    Series serie = new Series(titulo, dataLancamento, genero, numTemporadas);
                    db.save(serie);
                    System.out.println("Série cadastrada!");
                }
                if ("Documentário".equals(categoria)) {
                    int duracao = Integer.parseInt(duracaoTextField.getText());
                    Documentary documentary = new Documentary(titulo, dataLancamento, genero, duracao);
                    db.save(documentary);
                    System.out.println("Documentário cadastrado!");
                }

            } catch (Exception e2) {
                e2.getStackTrace();
            }
        });

        // Adiciona os componentes ao painel
        panel.add(cadastroLabel, gbc);
        gbc.insets = new Insets(30, 0, 2, 0);
        panel.add(titleLabel, gbc);
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(titleTextField, gbc);
        gbc.insets = new Insets(10, 0, 2, 0);
        panel.add(categoriaLabel, gbc);
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(categoriaComboBox, gbc);
        gbc.insets = new Insets(10, 0, 2, 0);
        panel.add(duracaoLabel, gbc);
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(duracaoTextField, gbc);
        gbc.insets = new Insets(10, 0, 2, 0);
        panel.add(numTemporadasLabel, gbc);
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(numTemporadasTextField, gbc);
        gbc.insets = new Insets(10, 0, 2, 0);
        panel.add(generoLabel, gbc);
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(generoComboBox, gbc);
        gbc.insets = new Insets(10, 0, 2, 0);
        panel.add(dataLancamentoLabel, gbc);
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(dataLancamentoTextField, gbc);

        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 0, 0, 0);
        panel.add(cadastrarButton, gbc);

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
