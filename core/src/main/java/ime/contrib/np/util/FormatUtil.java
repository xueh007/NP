package ime.contrib.np.util;

import java.math.RoundingMode;
import java.text.NumberFormat;

public class FormatUtil {

	public static String format(double number, int decimalPlace) {
		NumberFormat nf = NumberFormat.getInstance();
		
		nf.setMaximumFractionDigits(decimalPlace);
		nf.setRoundingMode(RoundingMode.HALF_UP);
		nf.setGroupingUsed(false);		
		
		return nf.format(number);
	}
}
