package ime.contrib.np.util;

import java.util.Random;

public class RandomUtil {
    public static int randomInt(int i) {
        Random r = new Random();
        return r.nextInt(i);
    }
}
