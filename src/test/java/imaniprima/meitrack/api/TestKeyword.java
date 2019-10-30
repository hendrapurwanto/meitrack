package imaniprima.meitrack.api;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Deddy Setyawan S
 * @version Jun 2, 2018 3:27:46 PM
 */
public class TestKeyword {
	private TestKeyword () {}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		String time = df.format(new Date((long) ((Double.parseDouble("0.0166161316666667"))*60*60*1000)));
		System.out.println("time>>"+time);


	}


}
