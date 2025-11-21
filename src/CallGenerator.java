import java.util.Random;

public class CallGenerator {
double callRate = 10.0;
int id;
Random rnd = new Random();
public CallGenerator(int id) { this.id = id; }
double generateCallTime(double curTime) {
    return curTime + rnd.nextDouble() * callRate;
}
    }