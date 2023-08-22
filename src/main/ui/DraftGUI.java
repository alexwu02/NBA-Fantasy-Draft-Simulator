package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.stream.Collectors;
import java.lang.Runtime;
import java.lang.Thread;



public class DraftGUI extends JFrame {
    private static final String JSON_STORE = "./data/league.json";
    private League league;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JTable teamTable;
    private DraftPool pool;
    private JTable draftBoardTable;
    private JTable teamRosterTable;
    private JLabel currentTeamLabel;
    private int currentTeamIndex;

    // EFFECTS: runs the DraftGUI application
    public DraftGUI() {
        initGUI();
    }

    // MODIFIES: this
    // EFFECTS: conducts the entire draft process
    private void initGUI() {
        init();
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openMenuItem = new JMenuItem("Load");
        openMenuItem.addActionListener(e -> {
            openMenuItemCall();
        });
        fileMenu.add(openMenuItem);
        menuBar.add(fileMenu);
        JMenuItem createTeamMenuItem = new JMenuItem("Create Team");
        createTeamMenuItem.addActionListener(e -> {
            createTeamMenuItemCall();
        });
        menuBar.add(createTeamMenuItem);
        JMenuItem startDraftMenuItem = new JMenuItem("Start Draft");
        startDraftMenuItem.addActionListener(e -> {
            initGuiDraft();
        });
        menuBar.add(startDraftMenuItem);
        setJMenuBar(menuBar);
        panel();
    }

    // MODIFIES: this
    // EFFECTS: creates the panel in the beginning that displays the teams playing
    private void panel() {
        JPanel draftPanel = new JPanel(new BorderLayout());
        draftPanel.setBorder(BorderFactory.createTitledBorder("League"));
        teamTable = new JTable();
        JScrollPane teamTableScrollPane = new JScrollPane(teamTable);
        draftPanel.add(teamTableScrollPane, BorderLayout.CENTER);

        add(draftPanel);

    }

    // MODIFES: this
    // EFFECTS: loads the last saved league
    private void openMenuItemCall() {
        loadLeagueGUI();
        updateTeamTable();
    }

    // MODIFIES: this
    // EFFECTS: creates the add team feature and adds it to the team table
    private void createTeamMenuItemCall() {
        String teamName = JOptionPane.showInputDialog(this, "Enter the name of the new team:");
        JOptionPane.showMessageDialog(this, "Team " + teamName + " has entered the league!");
        createTeam(teamName);
        updateTeamTable();
    }

    // MODIFIES: this
    // EFFECTS: initializes the scanner and changes the Delimiter to a newline character
    private void init() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        setTitle("NBA Fantasy Draft Simulator");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        league = new League("Alex's Fantasy League");
        pool = new DraftPool();
    }

    // MODIFIES: this
    // EFFECTS: creates an object of type Team based on user's input
    private void createTeam(String name) {
        Team team = new Team(name);
        league.addTeam(team);
        int draftNo = team.getPosition();
    }


    // MODIFIES: this
    // EFFECTS: goes through all the teams and updates them in the following team table
    private void updateTeamTable() {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Teams:");
        for (Team team : league.getLeague()) {
            Object[] rowData = new Object[]{team.getTeamName()};
            tableModel.addRow(rowData);
        }
        teamTable.setModel(tableModel);
    }


    // EFFECTS: creates the main display after the draft is started
    private void initGuiDraft() {
        setJMenuBar(null);

        JMenuBar newMenuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(e -> {
            saveLeagueGUI();
        });
        fileMenu.add(saveMenuItem);
        newMenuBar.add(fileMenu);
        newMenuBar.add(filterMenu());
        JMenu viewMenu = new JMenu("View");
        JMenuItem currentTeam = new JMenuItem("Current Teams");
        JMenuItem viewPlayerStats = new JMenuItem("View Player");
        viewMenu.add(currentTeam);
        viewMenu.add(viewPlayerStats);
        newMenuBar.add(viewMenu);
        setJMenuBar(newMenuBar);
        JPanel draftPanel = new JPanel(new BorderLayout());
        draftPanel.setBorder(BorderFactory.createTitledBorder("Current Available Players"));
        add(draftPanel);
        setContentPane(draftPanel);
        revalidate();
        showDraftBoard();
    }


    // EFFECTS: creates the menu after pressing the filter option
    private JMenu filterMenu() {
        JMenu filterMenu = new JMenu("Filter");
        JMenuItem filterMenuPoints = new JMenuItem("Points");
        JMenuItem filterMenuFG = new JMenuItem("FG%");
        JMenuItem filterMenuFT = new JMenuItem("FT%");
        JMenuItem filterMenu3PT = new JMenuItem("3PT");
        JMenuItem filterMenuRebounds = new JMenuItem("Rebounds");
        JMenuItem filterMenuAssists = new JMenuItem("Assists");
        JMenuItem filterMenuSteals = new JMenuItem("Steals");
        JMenuItem filterMenuBlocks = new JMenuItem("Blocks");
        JMenuItem filterMenuTurnovers = new JMenuItem("Turnovers");

        filterMenuPoints.addActionListener(e -> {
            filterPoints();
        });

        filterMenu.add(filterMenuPoints);
        filterMenu.add(filterMenuFG);
        filterMenu.add(filterMenuFT);
        filterMenu.add(filterMenu3PT);
        filterMenu.add(filterMenuRebounds);
        filterMenu.add(filterMenuAssists);
        filterMenu.add(filterMenuSteals);
        filterMenu.add(filterMenuBlocks);
        filterMenu.add(filterMenuTurnovers);

        return filterMenu;
    }


    // MODIFIES: this
    // EFFECTS: updates the draft pool based on the respective call to this method
    private void updateDraftPool() {
        DefaultTableModel model = (DefaultTableModel) draftBoardTable.getModel();
        model.setRowCount(0);
        for (Player player : pool.getPool()) {
            String[] rowData = {player.getName(), player.getPosition(), String.valueOf(player.getPoints()),
                    String.valueOf(player.getFg()), String.valueOf(player.getFt()), String.valueOf(player.getThrees()),
                    String.valueOf(player.getRebounds()), String.valueOf(player.getAssists()), String.valueOf(player
                    .getSteals()), String.valueOf(player.getBlocks()), String.valueOf(player.getTurnovers())};
            model.addRow(rowData);
        }
    }


    // EFFECTS: creates the display for the draft pool
    private void showDraftBoard() {
        JPanel draftPanel = new JPanel(new BorderLayout());
        JPanel draftBoardPanel = new JPanel(new BorderLayout());
        draftBoardPanel.setBorder(BorderFactory.createTitledBorder("Draft Pool"));
        currentTeamLabel = new JLabel("Current team: " + league.getLeague().get(currentTeamIndex).getTeamName());
        draftBoardPanel.add(currentTeamLabel, BorderLayout.SOUTH);
        String[] columnNames = {"Name", "Position", "Points", "FG%", "FT%", "3PT", "Rebounds", "Assists", "Steals",
                "Blocks", "Turnovers"};
        DefaultTableModel draftBoardModel = new DefaultTableModel(columnNames, 0);
        draftBoardTable = new JTable(draftBoardModel);
        draftBoardTableTwo();
        JScrollPane scrollPane = new JScrollPane(draftBoardTable);
        draftBoardPanel.add(scrollPane, BorderLayout.CENTER);
        draftPanel.add(draftBoardPanel, BorderLayout.CENTER);
        draftPlayerUI();
        JPanel teamRosterPanel = new JPanel(new BorderLayout());
        teamRosterPanel.setBorder(BorderFactory.createTitledBorder("Teams"));
        String[] teamRosterColumnNames = {"Team", "Player"};
        DefaultTableModel teamRosterModel = new DefaultTableModel(teamRosterColumnNames, 0);
        teamRosterTable = new JTable(teamRosterModel);
        JScrollPane teamRosterScrollPane = new JScrollPane(teamRosterTable);
        teamRosterPanel.add(teamRosterScrollPane, BorderLayout.CENTER);
        draftPanel.add(teamRosterPanel, BorderLayout.SOUTH);
        showDraftBoardTwo(draftPanel);
    }

    // EFFECTS: assists with instantiating the draftboard
    private void draftBoardTableTwo() {
        draftBoardTable.setDefaultEditor(Object.class, null);
        draftBoardTable.getColumnModel().getColumn(0).setCellEditor(null);
        draftBoardTable.getColumnModel().getColumn(1).setCellEditor(null);
    }

    // MODDIFIES: this
    // EFFECTS: assists with instantiating the display of the draftboard
    private void showDraftBoardTwo(JPanel draftPanel) {
        updateDraftPool();
        updateTeamRoster();

        setContentPane(draftPanel);
        revalidate();
    }

    // EFFECTS: modifies each team in the league based on the respective call
    private void updateTeamRoster() {
        DefaultTableModel model = (DefaultTableModel) teamRosterTable.getModel();
        model.setRowCount(0);

        for (Team team : league.getLeague()) {
            String teamName = team.getTeamName();
            for (Player player : team.getRoster()) {
                String[] rowData = {teamName, player.getName()};
                model.addRow(rowData);
            }
        }
    }

    // EFFECTS: allows users to conduct the draft option through double-clicking and having the respective players
    // image return
    private void draftPlayerUI() {
        draftBoardTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    try {
                        int selectedRow = draftBoardTable.getSelectedRow();
                        Player selectedPlayer = pool.getPool().get(selectedRow);
                        URL imageURL = new URL(selectedPlayer.getPicture());
                        ImageIcon playerIcon = new ImageIcon(ImageIO.read(imageURL));
                        ImageIcon scaledIcon = new ImageIcon(convertImage(playerIcon));
                        JPanel panel = new JPanel();
                        panel.add(new JLabel("Are you sure you want to draft " + selectedPlayer.getName() + "?"));
                        panel.add(new JLabel(scaledIcon));
                        int option = JOptionPane.showConfirmDialog(null, panel, "Draft Player",
                                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                        if (option == JOptionPane.YES_OPTION) {
                            optionYes(selectedPlayer);
                        }
                    } catch (Exception f) {
                        System.out.println("Exception was caught, An Error Occured");
                    }
                }
            }
        });
    }

    // REQUIRES: playerIcon is not null
    // MODIFIES: playerIcon
    // EFFECTS: converts the image into a smaller size
    private Image convertImage(ImageIcon playerIcon) {
        Image image = playerIcon.getImage();
        return image.getScaledInstance(135, 100, Image.SCALE_SMOOTH);
    }

    // REQUIRES: selectedPlayer is not null
    // MODIFIES: this
    // EFFECTS: drafts the player to the respective team and adjusts the draft pool
    private void optionYes(Player selectedPlayer) {
        Team currentTeam = league.getLeague().get(currentTeamIndex);
        currentTeam.draftPlayer(selectedPlayer.getName());
        updateTeamRoster();
        updateDraftPool();
        currentTeamIndex++;
        if (currentTeamIndex >= league.getLeague().size()) {
            currentTeamIndex = 0;
        }
        currentTeamLabel.setText("Current team: " + league.getLeague()
                .get(currentTeamIndex).getTeamName());
    }

    // EFFECTS: saves the league to file
    private void saveLeagueGUI() {
        try {
            jsonWriter.open();
            jsonWriter.write(league);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Saved " + league.getLeagueName() + " to "
                    + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads league from file
    private void loadLeagueGUI() {
        try {
            league = jsonReader.read();
            JOptionPane.showMessageDialog(this, "Loaded " + league.getLeagueName() + " "
                    + "from " + JSON_STORE);
        } catch (IOException f) {
            JOptionPane.showMessageDialog(this, "Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: conducts the filter process based on points
    private void filterPoints() {
        ArrayList<Player> fgPool = new ArrayList<>(this.pool.getPool().stream()
                .sorted(Comparator.comparingDouble(Player::getPoints).reversed())
                .collect(Collectors.toList()));
        this.pool.getPool().clear();
        this.pool.getPool().addAll(fgPool);
        updateDraftPool();
        JOptionPane.showMessageDialog(this, "Filtered by Points in descending order");
    }

}
