package ex01;

public class Program {
    public static void main(String[] args) {
        if (args.length == 2) {
            System.out.println(new WordsAnalysis().getSimilarity(args[0], args[1]));
        }
    }
}
