package ui;

import model.DraftPool;
import model.League;
import model.Player;
import model.Team;

import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

// Fantasy Draft App
public class DraftApp extends DraftPool {
    private static final String JSON_STORE = "./data/league.json";
    private Scanner input;
    private int maxRosterSize = 2;                 // usually 13 but adjusted due to draft pool (size >= 1)
    private League league = new League("Alex's Fantasy League");
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: runs the draft application
    public DraftApp() {
        startDraft();
    }

    // MODIFIES: this
    // EFFECTS: conducts entire draft process
    private void startDraft() {
        String command = null;

        init();

        while (true) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("c")) {
                createTeam();
            } else if (command.equals("load")) {
                loadLeague();
            } else if (command.equals("s")) {
                beginDraft();
                break;
            }

        }
        endDraftMessage();
        for (Team team : league.getLeague()) {
            System.out.println(" ");
            System.out.println(team.getTeamName() + " Team:");
            for (Player player : team.getRoster()) {
                System.out.println(player.getName());
            }
        }
    }

    // EFFECTS: displays a message at the end of the draft
    private void endDraftMessage() {
        System.out.println("The Draft is now over!");
        System.out.println("Here are the results:");
    }


    // MODIFIES: this
    // EFFECTS: initializes the scanner and changes the Delimiter to a newline character
    private void init() {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input.useDelimiter("\n");

    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("Welcome to the NBA Fantasy Draft Simulator!");
        System.out.println("Select from:");
        System.out.println("c -> Create Teams");
        System.out.println("s -> Start Draft");
        System.out.println("load -> Load League");
        System.out.println(" ");
        System.out.println("Teams so far: ");
        for (Team team : league.getLeague()) {
            System.out.println(team.getTeamName());
        }
    }

    // EFFECTS: displays menu of stats for to user to filter
    private void displayStatMenu() {
        System.out.println(" ");
        System.out.println("Filter by:");
        System.out.println("pos -> Position");
        System.out.println("fg -> Field Goal %");
        System.out.println("ft -> Free Throw %");
        System.out.println("3 -> 3pts Made");
        System.out.println("p -> Points");
        System.out.println("r -> Rebounds");
        System.out.println("a -> Assists");
        System.out.println("s -> Steals");
        System.out.println("b -> Blocks");
        System.out.println("t -> Turnovers");
    }

    // MODIFIES: this
    // EFFECTS: creates an object of type Team based on user's input
    private void createTeam() {
        while (true) {
            System.out.println(" ");
            System.out.println("Add a team (in order of first to last)");
            System.out.println("d -> done");
            String name = input.next();
            if (name.equals("d")) {
                break;
            }
            Team team = new Team(name);
            league.getLeague().add(team);
            int draftNo = team.getPosition();
            System.out.println("Team" + " " + name + " " + "has been added and will draft at position" + " " + draftNo);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes users command based on drafting options
    @SuppressWarnings("methodlength")
    private void draftProcess() {
        for (Team team : league.getLeague()) {
            System.out.println(team.getTeamName() + " " + "turn to draft!");
            while (true) {
                draftMenu();
                String name2 = input.next();
                if (name2.equals("t")) {
                    viewTeam();
                } else if (name2.equals("q")) {
                    System.out.println("See You Later!");
                    System.exit(0);
                } else if (name2.equals("v")) {
                    viewPlayer(team);
                } else if (name2.equals("save")) {
                    if (saveProcedure(league)) {
                        saveLeague();
                        break;
                    }
                    messageStorage("a", "", "");
                } else if (name2.equals("f")) {
                    filter();
                } else if (team.draftPlayer(name2) != null) {
                    team.draftPlayer(name2);
                    messageStorage("c", team.getTeamName(), name2);
                    break;
                } else {
                    messageStorage("b", name2, "");
                }
            }
        }
    }

    // EFFECTS: stores messages that can be used to call
    private void messageStorage(String input, String input2, String input3) {
        if (input.equals("a")) {
            System.out.println("Round is not complete, cannot save");
            System.out.println(" ");
        } else if (input.equals("b")) {
            System.out.println("Unable to draft " + input2 + " " + "please try again");
            System.out.println(" ");
        } else if (input.equals("c")) {
            System.out.println(input2 + " " + "has successfully drafted " + input3);
            System.out.println(" ");
        }


    }

    // EFFECTS: checks if each team in the league has the same size
    public static boolean saveProcedure(League list) {
        int firstSize = list.getLeague().get(0).getRoster().size();
        for (Team team : list.getLeague()) {
            if (team.getRoster().size() != firstSize) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: select an object Player within the draft pool and displays Player's stats
    private void viewPlayer(Team team) {
        System.out.println("Enter Player Name");
        String name3 = input.next();
        Player player = team.getPlayer(name3);
        if (player == null) {
            System.out.println(name3 + " " + "not found");
            System.out.println(" ");
            return;
        }
        displayPlayerStats(player);
    }

    // EFFECTS: displays menu of stats for to user to filter
    public void draftMenu() {
        System.out.println("Players Remaining in the Draft Pool:");
        for (Player player : pool) {
            System.out.println(player.getName());
        }
        System.out.println(" ");
        System.out.println("Select a player to draft");
        System.out.println("v -> View Player Stats (measured in ___ per game)");
        System.out.println("f -> Filter Draft Pool");
        System.out.println("t -> View Teams so far");
        System.out.println("save -> Save League");
        System.out.println("q -> quit");
    }

    // EFFECTS: select an object Player within the draft pool and displays Player drafted to the team
    public void viewTeam() {
        for (Team team2 : league.getLeague()) {
            System.out.println(" ");
            System.out.println(team2.getTeamName() + " " + "Team:");
            for (Player player : team2.getRoster()) {
                System.out.println(player.getName());
            }
        }
        System.out.println(" ");
    }

    // EFFECTS: displays menu of filter options
    public void displayPlayerStats(Player player) {
        System.out.println(" ");
        System.out.println("Name: " + player.getName());
        System.out.println("Position: " + player.getPosition());
        System.out.println("Field Goal %: " + player.getFg());
        System.out.println("Free throw %: " + player.getFt());
        System.out.println("3pt Made: " + player.getThrees());
        System.out.println("Points: " + player.getPoints());
        System.out.println("Rebounds: " + player.getRebounds());
        System.out.println("Assists: " + player.getAssists());
        System.out.println("Steals: " + player.getSteals());
        System.out.println("Blocks: " + player.getBlocks());
        System.out.println("Turnovers: " + player.getTurnovers());
        System.out.println(" ");
    }


    // EFFECTS: determines if all teams in the league are full
    public boolean reachedMaxSize(List<Team> listTeam) {
        for (Team var : listTeam) {
            if (var.getRoster().size() < maxRosterSize) {
                return false;
            }
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: replicates snake draft order by reversing the order everytime the last person drafts
    private void beginDraft() {
        while (!reachedMaxSize(league.getLeague())) {
            draftProcess();
            Collections.reverse(league.getLeague());
            draftProcess();
            Collections.reverse(league.getLeague());
        }
    }

    // EFFECTS: filters player stats based on user's input
    private void filter() {
        displayStatMenu();
        String stat = input.next();

        if (stat.equals("pos")) {
            filterPos();
        } else if (stat.equals("fg")) {
            filterFg();
        } else if (stat.equals("ft")) {
            filterFt();
        } else if (stat.equals("3")) {
            filterThrees();
        } else if (stat.equals("p")) {
            filterPoints();
        } else if (stat.equals("r")) {
            filterRebounds();
        } else if (stat.equals("a")) {
            filterAssists();
        } else if (stat.equals("s")) {
            filterSteals();
        } else if (stat.equals("b")) {
            filterBlocks();
        } else if (stat.equals("t")) {
            filterTurnovers();
        }
    }

    // EFFECTS: filters out draft pool by a certain stat based on user's selection
    private void filterPos() {
        System.out.println("Which Position? (PG, SG, SF, PF, C)");
        String pos = input.next();
        List<Player> validPos = new ArrayList<>();
        for (Player player2 : pool) {
            if (player2.getPosition().equals(pos)) {
                validPos.add(player2);
            }
        }
        System.out.println(" ");
        System.out.println("Filtered out all " + pos + "'s");
        for (Player player : validPos) {
            System.out.println(player.getName());
        }
        System.out.println(" ");
    }

    // EFFECTS: conducts the filter process based on fg
    private void filterFg() {
        List<Player> fgPool = pool.stream().sorted(Comparator.comparingDouble(Player::getFg).reversed())
                .collect(Collectors.toList());
        System.out.println(" ");
        System.out.println("Filtered based on Field Goal % (descending)");
        for (Player player : fgPool) {
            System.out.println(player.getName());
        }
        System.out.println(" ");
    }

    // EFFECTS: conducts the filter process based on ft
    private void filterFt() {
        List<Player> fgPool = pool.stream().sorted(Comparator.comparingDouble(Player::getFt).reversed())
                .collect(Collectors.toList());
        System.out.println(" ");
        System.out.println("Filtered based on Free Throw % (descending)");
        for (Player player : fgPool) {
            System.out.println(player.getName());
        }
        System.out.println(" ");
    }

    // EFFECTS: conducts the filter process based on threes
    private void filterThrees() {
        List<Player> fgPool = pool.stream().sorted(Comparator.comparingDouble(Player::getThrees).reversed())
                .collect(Collectors.toList());
        System.out.println(" ");
        System.out.println("Filtered based on 3pts Made (descending)");
        for (Player player : fgPool) {
            System.out.println(player.getName());
        }
        System.out.println(" ");
    }

    // EFFECTS: conducts the filter process based on points
    private void filterPoints() {
        List<Player> fgPool = pool.stream().sorted(Comparator.comparingDouble(Player::getPoints).reversed())
                .collect(Collectors.toList());
        System.out.println(" ");
        System.out.println("Filtered based on Points (descending)");
        for (Player player : fgPool) {
            System.out.println(player.getName());
        }
        System.out.println(" ");
    }

    // EFFECTS: conducts the filter process based on rebounds
    private void filterRebounds() {
        List<Player> fgPool = pool.stream().sorted(Comparator.comparingDouble(Player::getRebounds).reversed())
                .collect(Collectors.toList());
        System.out.println(" ");
        System.out.println("Filtered based on Rebounds (descending)");
        for (Player player : fgPool) {
            System.out.println(player.getName());
        }
        System.out.println(" ");
    }

    // EFFECTS: conducts the filter process based on assists
    private void filterAssists() {
        List<Player> fgPool = pool.stream().sorted(Comparator.comparingDouble(Player::getAssists).reversed())
                .collect(Collectors.toList());
        System.out.println(" ");
        System.out.println("Filtered based on Assists (descending)");
        for (Player player : fgPool) {
            System.out.println(player.getName());
        }
        System.out.println(" ");
    }

    // EFFECTS: conducts the filter process based on steals
    private void filterSteals() {
        List<Player> fgPool = pool.stream().sorted(Comparator.comparingDouble(Player::getSteals).reversed())
                .collect(Collectors.toList());
        System.out.println(" ");
        System.out.println("Filtered based on Steals (descending)");
        for (Player player : fgPool) {
            System.out.println(player.getName());
        }
        System.out.println(" ");
    }

    // EFFECTS: conducts the filter process based on blocks
    private void filterBlocks() {
        List<Player> fgPool = pool.stream().sorted(Comparator.comparingDouble(Player::getBlocks).reversed())
                .collect(Collectors.toList());
        System.out.println(" ");
        System.out.println("Filtered based on Blocks (descending)");
        for (Player player : fgPool) {
            System.out.println(player.getName());
        }
        System.out.println(" ");
    }

    // EFFECTS: conducts the filter process based on turnovers
    private void filterTurnovers() {
        List<Player> fgPool = pool.stream().sorted(Comparator.comparingDouble(Player::getTurnovers))
                .collect(Collectors.toList());
        System.out.println(" ");
        System.out.println("Filtered based on Turnovers (ascending)");
        for (Player player : fgPool) {
            System.out.println(player.getName());
        }
        System.out.println(" ");
    }


    // sourced from saveLeague method in JsonSerializationDemo
    // EFFECTS: saves the league to file
    private void saveLeague() {
        try {
            jsonWriter.open();
            jsonWriter.write(league);
            jsonWriter.close();
            System.out.println("Saved " + league.getLeagueName() + " to " + JSON_STORE);
            System.out.println(" ");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // sourced from loadLeague method in JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads league from file
    private void loadLeague() {
        try {
            league = jsonReader.read();
            System.out.println("Loaded " + league.getLeagueName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    public static void main(String[] args) {
        new DraftApp(); // change to DraftGUI
    }

}

