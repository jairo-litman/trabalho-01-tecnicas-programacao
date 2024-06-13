package frontend;

import javax.swing.*;

import backend.Documentary;
import backend.Film;
import backend.Manager;
import backend.Media;
import backend.Series;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

// Classe principal que cria a janela de inserção de registros
public class Inserir extends JFrame {
    private JPanel panel;
    private JLabel tituloLabel;
    private JComboBox<String> categoriaComboBox;
    private JComboBox<String> opcoesComboBox;
    private JComboBox<String> titleComboBox;
    private List<Media> media;
    Manager db;

    // Construtor da classe Inserir
    public Inserir() {
        setTitle("Inserir no banco de dados");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);

        // instância do banco
        try {
            Manager db = Manager.getInstance();
            this.db = db;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Carrega a fonte personalizada
        Font sansSerifBoldFont = new Font("SansSerif", Font.BOLD, 18);

        // cria label para categoria
        JLabel categoriaLabel = new JLabel("Categoria");
        categoriaLabel.setFont(sansSerifBoldFont);
        categoriaLabel.setForeground(Color.WHITE);

        // cria ComboBox para as categorias
        String[] opcoes = { "Filme", "Série", "Documentário" };
        categoriaComboBox = new JComboBox<>(opcoes);
        categoriaComboBox.setSelectedIndex(-1);

        // cria ComboBox para opções
        opcoesComboBox = new JComboBox<>();

        // Inicialização do painel principal com layout GridBag
        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);

        // Carrega a fonte personalizada ou usa a fonte padrão
        Font fonteBebas = loadFont("Bebas.ttf", 30f);
        Font fontePadrao = new Font("Arial", Font.BOLD, 100);

        // Rótulo do título da janela
        tituloLabel = new JLabel("Editar", SwingConstants.CENTER);
        if (fonteBebas != null) {
            tituloLabel.setFont(fonteBebas);
        } else {
            tituloLabel.setFont(fontePadrao);
        }
        tituloLabel.setForeground(Color.RED);
        addComponente(panel, tituloLabel, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

        // linha para manter controle da edição de componentes
        int linha = 2;

        // adicionar categoriaLabel e categoriaComboBox
        addComponente(panel, categoriaLabel, 0, linha++, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL);
        addComponente(panel, categoriaComboBox, 0, linha++, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL);

        // cria label para título da obra
        JLabel title = new JLabel("Título da obra que deseja editar: ");
        title.setFont(sansSerifBoldFont);
        title.setForeground(Color.WHITE);

        // ComboBox para os títulos existentes
        titleComboBox = new JComboBox<>();

        addComponente(panel, title, 0, linha++, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL);
        addComponente(panel, titleComboBox, 0, linha++, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL);

        // label para característica a ser editada
        JLabel caracteristica = new JLabel("Característica a ser editada: ");
        caracteristica.setFont(sansSerifBoldFont);
        caracteristica.setForeground(Color.WHITE);
        addComponente(panel, caracteristica, 0, linha++, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL);

        // adicionar opcoesComboBox
        addComponente(panel, opcoesComboBox, 0, linha++, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL);

        //  e cria um Listener no categoriaComboBox para trazer as opções de obra dependendo da categoria escolhida
        categoriaComboBox.addActionListener(e -> {
            String selectedCategory = categoriaComboBox.getSelectedItem().toString();
            if ("Filme".equals(selectedCategory)) {
                opcoesComboBox.removeAllItems();
                opcoesComboBox.addItem("Título");
                opcoesComboBox.addItem("Duração");
                opcoesComboBox.addItem("Gênero");
                opcoesComboBox.addItem("Ano");

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
                opcoesComboBox.removeAllItems();
                opcoesComboBox.addItem("Título");
                opcoesComboBox.addItem("Número de temporadas");
                opcoesComboBox.addItem("Gênero");
                opcoesComboBox.addItem("Ano");

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
                opcoesComboBox.removeAllItems();
                opcoesComboBox.addItem("Título");
                opcoesComboBox.addItem("Duração");
                opcoesComboBox.addItem("Gênero");
                opcoesComboBox.addItem("Ano");

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
            opcoesComboBox.setSelectedIndex(-1);
        });

        // label para a nova informação que será atualizada
        JLabel novaInformacaoLabel = new JLabel("Nova informação: ");
        novaInformacaoLabel.setFont(sansSerifBoldFont);
        novaInformacaoLabel.setForeground(Color.WHITE);

        JTextField novaInformacaoTextField = new JTextField(20);

        // Botão para editar registros selecionados na tabela correspondente dependendo da escolha
        JButton editarButton = new JButton("Editar");
        editarButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        editarButton.setForeground(Color.WHITE);
        editarButton.setBackground(Color.RED);
        editarButton.setFocusPainted(false);
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String category = categoriaComboBox.getSelectedItem().toString();
                String tableName = "";
                String itemName = titleComboBox.getSelectedItem().toString();
                String novaInformacao = novaInformacaoTextField.getText();
                String option = opcoesComboBox.getSelectedItem().toString();

                if (itemName.equals("")) {
                    JOptionPane.showMessageDialog(panel, "Insira o título da obra!", "Aviso",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                if ("Filme".equals(category)) {
                    tableName = "films";

                    if ("Título".equals(option)) {
                        try {
                            Film filme = (Film) db.get(tableName, itemName).get(0);
                            filme.setTitle(novaInformacao);
                            db.update(filme);
                            System.out.println("Filme editado!");
                            JOptionPane.showMessageDialog(panel,
                                    "Filme " + "'" + itemName + "'" + " editado! " + option + ": " + novaInformacao,
                                    "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception e1) {
                            e1.getStackTrace();
                            JOptionPane.showMessageDialog(panel, "Erro ao editar filme!", "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else if ("Duração".equals(option)) {
                        try {
                            Film filme = (Film) db.get(tableName, itemName).get(0);
                            filme.setDuration(Integer.parseInt(novaInformacao));
                            db.update(filme);
                            System.out.println("Filme editado!");
                            JOptionPane.showMessageDialog(panel,
                                    "Filme " + "'" + itemName + "'" + " editado! " + option + ": " + novaInformacao,
                                    "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception e1) {
                            e1.getStackTrace();
                            JOptionPane.showMessageDialog(panel, "Erro ao editar filme!", "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else if ("Gênero".equals(option)) {
                        try {
                            Film filme = (Film) db.get(tableName, itemName).get(0);
                            filme.setGenre(novaInformacao);
                            db.update(filme);
                            System.out.println("Filme editado!");
                            JOptionPane.showMessageDialog(panel,
                                    "Filme " + "'" + itemName + "'" + " editado! " + option + ": " + novaInformacao,
                                    "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception e1) {
                            e1.getStackTrace();
                            JOptionPane.showMessageDialog(panel, "Erro ao editar filme!", "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else if ("Ano".equals(option)) {
                        try {
                            Film filme = (Film) db.get(tableName, itemName).get(0);
                            filme.setYear(Integer.parseInt(novaInformacao));
                            db.update(filme);
                            System.out.println("Filme editado!");
                            JOptionPane.showMessageDialog(panel,
                                    "Filme " + "'" + itemName + "'" + " editado! " + option + ": " + novaInformacao,
                                    "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception e1) {
                            e1.getStackTrace();
                            JOptionPane.showMessageDialog(panel, "Erro ao editar filme!", "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

                }
                if ("Série".equals(category)) {
                    tableName = "series";

                    if ("Título".equals(option)) {
                        try {
                            Series serie = (Series) db.get(tableName, itemName).get(0);
                            serie.setTitle(novaInformacao);
                            db.update(serie);
                            System.out.println("Serie editada!");
                            JOptionPane.showMessageDialog(panel,
                                    "Serie " + "'" + itemName + "'" + " editada! " + option + ": " + novaInformacao,
                                    "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception e1) {
                            e1.getStackTrace();
                            JOptionPane.showMessageDialog(panel, "Erro ao editar serie!", "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else if ("Número de temporadas".equals(option)) {
                        try {
                            Series serie = (Series) db.get(tableName, itemName).get(0);
                            serie.setSeasons(Integer.parseInt(novaInformacao));
                            db.update(serie);
                            System.out.println("Serie editada!");
                            JOptionPane.showMessageDialog(panel,
                                    "Serie " + "'" + itemName + "'" + " editada! " + option + ": " + novaInformacao,
                                    "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception e1) {
                            e1.getStackTrace();
                            JOptionPane.showMessageDialog(panel, "Erro ao editar serie!", "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else if ("Gênero".equals(option)) {
                        try {
                            Series serie = (Series) db.get(tableName, itemName).get(0);
                            serie.setGenre(novaInformacao);
                            db.update(serie);
                            System.out.println("Serie editada!");
                            JOptionPane.showMessageDialog(panel,
                                    "Serie " + "'" + itemName + "'" + " editada! " + option + ": " + novaInformacao,
                                    "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception e1) {
                            e1.getStackTrace();
                            JOptionPane.showMessageDialog(panel, "Erro ao editar serie!", "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else if ("Ano".equals(option)) {
                        try {
                            Series serie = (Series) db.get(tableName, itemName).get(0);
                            serie.setYear(Integer.parseInt(novaInformacao));
                            db.update(serie);
                            System.out.println("Serie editada!");
                            JOptionPane.showMessageDialog(panel,
                                    "Serie " + "'" + itemName + "'" + " editada! " + option + ": " + novaInformacao,
                                    "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception e1) {
                            e1.getStackTrace();
                            JOptionPane.showMessageDialog(panel, "Erro ao editar serie!", "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

                }
                if ("Documentário".equals(category)) {
                    tableName = "documentaries";

                    if ("Título".equals(option)) {
                        try {
                            Documentary doc = (Documentary) db.get(tableName, itemName).get(0);
                            doc.setTitle(novaInformacao);
                            db.update(doc);
                            System.out.println("Documentario editado!");
                            JOptionPane.showMessageDialog(panel,
                                    "Documentario " + "'" + itemName + "'" + " editado! " + option + ": "
                                            + novaInformacao,
                                    "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception e1) {
                            e1.getStackTrace();
                            JOptionPane.showMessageDialog(panel, "Erro ao editar documentario!", "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else if ("Duração".equals(option)) {
                        try {
                            Documentary doc = (Documentary) db.get(tableName, itemName).get(0);
                            doc.setDuration(Integer.parseInt(novaInformacao));
                            db.update(doc);
                            System.out.println("Documentario editado!");
                            JOptionPane.showMessageDialog(panel,
                                    "Documentario " + "'" + itemName + "'" + " editado! " + option + ": "
                                            + novaInformacao,
                                    "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception e1) {
                            e1.getStackTrace();
                            JOptionPane.showMessageDialog(panel, "Erro ao editar documentario!", "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else if ("Gênero".equals(option)) {
                        try {
                            Documentary doc = (Documentary) db.get(tableName, itemName).get(0);
                            doc.setGenre(novaInformacao);
                            db.update(doc);
                            System.out.println("Documentario editado!");
                            JOptionPane.showMessageDialog(panel,
                                    "Documentario " + "'" + itemName + "'" + " editado! " + option + ": "
                                            + novaInformacao,
                                    "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception e1) {
                            e1.getStackTrace();
                            JOptionPane.showMessageDialog(panel, "Erro ao editar documentario!", "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else if ("Ano".equals(option)) {
                        try {
                            Documentary doc = (Documentary) db.get(tableName, itemName).get(0);
                            doc.setYear(Integer.parseInt(novaInformacao));
                            db.update(doc);
                            System.out.println("Documentario editado!");
                            JOptionPane.showMessageDialog(panel,
                                    "Documentario " + "'" + itemName + "'" + " editado! " + option + ": "
                                            + novaInformacao,
                                    "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception e1) {
                            e1.getStackTrace();
                            JOptionPane.showMessageDialog(panel, "Erro ao editar documentario!", "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });

        // acidiona componente de novaInformacao e botão de editar
        addComponente(panel, novaInformacaoLabel, 0, linha++, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL);
        addComponente(panel, novaInformacaoTextField, 0, linha++, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL);
        addComponente(panel, editarButton, 0, linha++, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL);

        // Adiciona um painel de rolagem para o painel principal
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));
        add(scrollPane, BorderLayout.CENTER);

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

    // Método auxiliar para adicionar componentes ao painel com GridBagConstraints
    private void addComponente(Container container, Component componente, int gridx, int gridy, int gridwidth,
            int gridheight, int anchor, int fill) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.insets = new Insets(5, 5, 5, 5);
        container.add(componente, gbc);
    }
}
