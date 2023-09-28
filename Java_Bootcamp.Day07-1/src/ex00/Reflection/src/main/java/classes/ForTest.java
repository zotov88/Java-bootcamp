package classes;

public class ForTest {

    private String s;
    private Integer i;
    private Long l;
    private Double d;
    private Boolean b;

    public ForTest() {
    }

    public ForTest(String s, Integer i, Long l, Double d, Boolean b) {
        this.s = s;
        this.i = i;
        this.l = l;
        this.d = d;
        this.b = b;
    }

    public Double fu1(Integer i, Double d) {
        return i / d;
    }

    public Boolean fu2(String s) {
        return s.length() > 5;
    }

    public Boolean fu3(Boolean b) {
        return !b;
    }

    public Long fu4(String s, Integer i) {
        return (long) (s.length() + i);
    }

    public void fu5() {
        System.out.println("message");
    }

    @Override
    public String toString() {
        return "ForTest{" +
                "s='" + s + '\'' +
                ", i=" + i +
                ", l=" + l +
                ", d=" + d +
                ", b=" + b +
                '}';
    }
}
