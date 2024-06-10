package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

// Classe principal que cria a janela de inserção de registros
public class Inserir extends JFrame {
    private JPanel panel;
    private ArrayList<JCheckBox> checkBoxes;
    private ArrayList<String> registros;
    private String nomeArquivo = "cadastro.txt";
    private JLabel vazioLabel;
    private JLabel tituloLabel;
    private JButton inserirButton;
    private JButton apagarButton;
    private JComboBox<String> categoriaComboBox;

    // Construtor da classe Inserir
    public Inserir() {
        setTitle("Inserir no banco de dados");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);

        // Carrega a fonte personalizada
        Font bebasFont = loadFont("Bebas.ttf", 60f);
        Font sansSerifBoldFont = new Font("SansSerif", Font.BOLD, 18);

        JLabel categoriaLabel = new JLabel("Categoria");
        categoriaLabel.setFont(sansSerifBoldFont);
        categoriaLabel.setForeground(Color.WHITE);

        String[] opcoes = { "Filme", "Série", "Documentário" };
        categoriaComboBox = new JComboBox<>(opcoes);
        categoriaComboBox.setSelectedIndex(-1);

        // Inicialização do painel principal com layout GridBag
        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);

        checkBoxes = new ArrayList<>();
        registros = new ArrayList<>();

        // Rótulo exibido quando não há dados para inserir
        vazioLabel = new JLabel("Sem dados para serem inseridos", SwingConstants.CENTER);
        vazioLabel.setForeground(Color.WHITE);
        vazioLabel.setVisible(false);
        addComponente(panel, vazioLabel, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

        // Carrega a fonte personalizada ou usa a fonte padrão
        Font fonteBebas = loadFont("Bebas.ttf", 30f);
        Font fontePadrao = new Font("Arial", Font.BOLD, 100);

        // Rótulo do título da janela
        tituloLabel = new JLabel("Editar", SwingConstants.CENTER);
        if (fonteBebas != null) {
            tituloLabel.setFont(fonteBebas);
            vazioLabel.setFont(fonteBebas);
        } else {
            tituloLabel.setFont(fontePadrao);
            vazioLabel.setFont(new Font("Arial", Font.BOLD, 30));
        }
        tituloLabel.setForeground(Color.RED);
        addComponente(panel, tituloLabel, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

        // Carrega os registros do arquivo de cadastro
        carregarRegistrosDoArquivo();

        int linha = 2;

        // Cria checkboxes para cada registro carregado
        for (String registro : registros) {
            JPanel painelCheckBox = new JPanel(new BorderLayout());
            painelCheckBox.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            painelCheckBox.setBackground(Color.DARK_GRAY);

            JCheckBox checkBox = new JCheckBox();
            checkBox.setForeground(Color.WHITE);
            checkBox.setFont(checkBox.getFont().deriveFont(Font.BOLD));
            checkBox.setBackground(Color.DARK_GRAY);
            painelCheckBox.add(checkBox, BorderLayout.WEST);

            JLabel rotuloCheckBox = new JLabel(formatarRegistro(registro));
            rotuloCheckBox.setForeground(Color.WHITE);
            rotuloCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
            painelCheckBox.add(rotuloCheckBox, BorderLayout.CENTER);

            checkBoxes.add(checkBox);
            addComponente(panel, painelCheckBox, 0, linha++, 1, 1, GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL);
        }

        addComponente(panel, categoriaLabel, 0, linha++, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
        addComponente(panel, categoriaComboBox, 0, linha++, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

        // Botão para inserir registros selecionados
        inserirButton = new JButton("Inserir Selecionados");
        inserirButton.setFont(fonteBebas);
        inserirButton.setForeground(Color.WHITE);
        inserirButton.setBackground(Color.RED);
        inserirButton.setFocusPainted(false);
        inserirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inserirRegistrosSelecionados();
            }
        });
        addComponente(panel, inserirButton, 0, linha++, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

        // Botão para apagar registros selecionados
        apagarButton = new JButton("Apagar Selecionados");
        apagarButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        apagarButton.setForeground(Color.BLACK);
        apagarButton.setBackground(Color.WHITE);
        apagarButton.setFocusPainted(false);
        apagarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                apagarRegistrosSelecionados();
            }
        });
        addComponente(panel, apagarButton, 0, linha++, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
 

        // Adiciona um painel de rolagem para o painel principal
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));
        add(scrollPane, BorderLayout.CENTER);

        setSize(400, 600);
        setLocationRelativeTo(null);
    }

    // Método para carregar registros do arquivo de cadastro
    private void carregarRegistrosDoArquivo() {
        try (BufferedReader leitor = new BufferedReader(new FileReader(new File(nomeArquivo)))) {
            StringBuilder construtorRegistro = new StringBuilder();
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    if (construtorRegistro.length() > 0) {
                        registros.add(construtorRegistro.toString().trim());
                        construtorRegistro.setLength(0);
                    }
                } else {
                    construtorRegistro.append(linha).append("\n");
                }
            }
            if (construtorRegistro.length() > 0) {
                registros.add(construtorRegistro.toString().trim());
            }

            if (registros.isEmpty()) {
                vazioLabel.setVisible(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para formatar registros para exibição no JLabel
    private String formatarRegistro(String registro) {
        return "<html>" + registro.replace("\n", "<br>") + "</html>";
    }

    // Método para inserir registros selecionados (a implementar)
    private void inserirRegistrosSelecionados() {
        // Implementar a lógica de inserção de registros selecionados
    }

    // Método para apagar registros selecionados
    private void apagarRegistrosSelecionados() {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(new File(nomeArquivo)))) {
            for (int i = checkBoxes.size() - 1; i >= 0; i--) {
                if (checkBoxes.get(i).isSelected()) {
                    panel.remove(checkBoxes.get(i).getParent());
                    checkBoxes.remove(i);
                    registros.remove(i);
                }
            }
            panel.revalidate();
            panel.repaint();

            for (String registro : registros) {
                escritor.write(registro);
                escritor.newLine();
            }

            if (registros.isEmpty()) {
                vazioLabel.setVisible(true);
                tituloLabel.setVisible(false);
                inserirButton.setEnabled(false);
                apagarButton.setEnabled(false);
            }

            setSize(400, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
