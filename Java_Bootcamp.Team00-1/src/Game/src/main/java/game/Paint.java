package game;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import java.util.HashMap;

public class Paint {

    private final ColoredPrinter coloredPrinter;
    private final char enemyChar;
    private final char playerChar;
    private final char wallChar;
    private final char goalChar;
    private final char emptyChar;
    private final Ansi.BColor enemyColor;
    private final Ansi.BColor playerColor;
    private final Ansi.BColor wallColor;
    private final Ansi.BColor goalColor;
    private final Ansi.BColor emptyColor;

    public Paint(String prop, String profile) {
        coloredPrinter = new ColoredPrinter
                .Builder(0, false)
                .foreground(Ansi.FColor.BLACK)
                .build();
        String urlProp = String.valueOf(getClass().getResource(prop)).substring(5);
        HashMap<String, String> properties = Parser.parseFile(urlProp, profile);
        checkPropMain(properties);
        this.enemyChar = properties.get("enemy.char").charAt(0);
        this.playerChar = properties.get("player.char").charAt(0);
        this.wallChar = properties.get("wall.char").charAt(0);
        this.goalChar = properties.get("goal.char").charAt(0);
        this.emptyChar = properties.get("empty.char").charAt(0);
        this.enemyColor = Ansi.BColor.valueOf(properties.get("enemy.color"));
        this.playerColor = Ansi.BColor.valueOf(properties.get("player.color"));
        this.wallColor = Ansi.BColor.valueOf(properties.get("wall.color"));
        this.goalColor = Ansi.BColor.valueOf(properties.get("goal.color"));
        this.emptyColor = Ansi.BColor.valueOf(properties.get("empty.color"));
    }

    private void checkPropMain(HashMap<String, String> properties) {
        checkPropGet(properties, "enemy.char");
        checkPropGet(properties, "player.char");
        checkPropGet(properties, "wall.char");
        checkPropGet(properties, "goal.char");
        checkPropGet(properties, "empty.char");
        checkPropGet(properties, "enemy.color");
        checkPropGet(properties, "player.color");
        checkPropGet(properties, "wall.color");
        checkPropGet(properties, "goal.color");
        checkPropGet(properties, "empty.color");
    }

    private void checkPropGet(HashMap<String, String> properties, String str) {
        String val = properties.get(str);
        if (val == null) {
            System.err.println("Wrong input properties.");
            System.exit(-1);
        }
    }

    public void drawField(char[][] field, boolean isPod) {
        if (isPod) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        for (char[] chars : field) {
            for (char aChar : chars) {
                if (aChar == Field.getEmpty()) {
                    coloredPrinter.setBackgroundColor(emptyColor);
                    coloredPrinter.print(emptyChar);
                } else if (aChar == Field.getPlayer()) {
                    coloredPrinter.setBackgroundColor(playerColor);
                    coloredPrinter.print(playerChar);
                } else if (aChar == Field.getEnemy()) {
                    coloredPrinter.setBackgroundColor(enemyColor);
                    coloredPrinter.print(enemyChar);
                } else if (aChar == Field.getFinish()) {
                    coloredPrinter.setBackgroundColor(goalColor);
                    coloredPrinter.print(goalChar);
                } else if (aChar == Field.getWall()) {
                    coloredPrinter.setBackgroundColor(wallColor);
                    coloredPrinter.print(wallChar);
                }
            }
            System.out.println();
        }
    }
}
