package frontend;

import javax.swing.*;

import backend.Documentary;
import backend.Manager;
import backend.Media;

import java.awt.*;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

// Classe principal que cria a janela de inserção de registros
public class MostraDocumentarios extends JFrame {
    private JPanel panel;
    private List<Media> docs;
    private JLabel tituloLabel;
    private JLabel vazioLabel;

    Manager db;

    // Construtor da classe Inserir
    public MostraDocumentarios() {
        setTitle("Séries Cadastradas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);

        try {
            Manager db = Manager.getInstance();
            this.db = db;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Inicialização do painel principal com layout GridBag
        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);

        try {
            docs = db.get("documentaries");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Carrega a fonte personalizada ou usa a fonte padrão
        Font fonteBebas = loadFont("Bebas.ttf", 30f);
        Font fontePadrao = new Font("Arial", Font.BOLD, 100);

        // Rótulo do título da janela
        tituloLabel = new JLabel("Documentarios Cadastradas", SwingConstants.CENTER);
        if (fonteBebas != null) {
            tituloLabel.setFont(fonteBebas);
        } else {
            tituloLabel.setFont(fontePadrao);
        }
        tituloLabel.setForeground(Color.RED);
        addComponente(panel, tituloLabel, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

        // Rótulo exibido quando não há dados para inserir
        vazioLabel = new JLabel("Não há documentários cadastrados", SwingConstants.CENTER);
        vazioLabel.setForeground(Color.WHITE);
        vazioLabel.setVisible(true);

        // Adiciona um painel de rolagem para o painel principal
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));
        add(scrollPane, BorderLayout.CENTER);

        setSize(400, 600);
        setLocationRelativeTo(null);

        // Adiciona as series na tela
        int linha = 2;

        for (Media docsMedia : docs) {
            Documentary docs = (Documentary) docsMedia;
            JPanel panelDocs = new JPanel(new GridLayout(4, 1));
            panelDocs.setBackground(Color.RED);
            JLabel titulo = new JLabel("Título: " + docs.getTitle());
            JLabel genero = new JLabel("Gênero: " + docs.getGenre());
            JLabel duracao = new JLabel("Duração: " + docs.getDuration());
            JLabel ano = new JLabel("Ano: " + docs.getYear());
            titulo.setForeground(Color.WHITE);
            genero.setForeground(Color.WHITE);
            duracao.setForeground(Color.WHITE);
            ano.setForeground(Color.WHITE);
            panelDocs.add(titulo);
            panelDocs.add(genero);
            panelDocs.add(duracao);
            panelDocs.add(ano);
            addComponente(panel, panelDocs, 0, linha++, 1, 1, GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL);
        }

        if (docs.isEmpty()) {
            addComponente(panel, vazioLabel, 0, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
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
