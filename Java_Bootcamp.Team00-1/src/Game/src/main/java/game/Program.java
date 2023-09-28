package game;

import com.beust.jcommander.JCommander;

public class Program {
    public static void main(String[] args) {
        try {
            Arguments jArgs = new Arguments();
            JCommander jCommander = JCommander
                    .newBuilder()
                    .addObject(jArgs)
                    .build();
            jCommander.parse(args);
            String profile = jArgs.getProfile();
            Paint paint = new Paint("/application-" + profile + ".properties", profile);
            Field.createField(jArgs.getEnemiesCount(), jArgs.getWallsCount(), jArgs.getSize());
            App.run(Field.getField(), new Player(), paint, profile.equals("production"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
