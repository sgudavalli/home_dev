package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Utils {

	public static String chainCode = "JMT";
	public static Integer numOfHotels = 1000;
	public static Integer THREE = 3;
	public static Integer TWO = 2;

	public static <T> T randomFrom(T... items) {
		return items[new Random().nextInt(items.length)];
	}

	public static String randomHotelCode(int numOfHotels) {
		return String.format("D%04d", new Random().nextInt(numOfHotels));
	}

	public static String randomRatePlanCode(int numOfSaltChars) {

		String[] prefixList = new String[] { "AA", "BB", "CC" };

		String input = randomSalt(numOfSaltChars);
		StringBuffer buf = new StringBuffer(input);

		while (buf.length() < 3) {
			buf.insert(0, '0');
		}

		String ratePlanCode = randomFrom(prefixList) + buf.toString();

		return ratePlanCode;
	}

	public static String randomSalt(int size) {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < size) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		return salt.toString();
	}

	public static String randomRandomDate() {
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
		return randomRandomTimeBtwRange(format);
	}

	public static String randomRandomTimeBtwRange() {
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss.SSS'Z'");
		return randomRandomTimeBtwRange(format);
	}

	public static String randomRandomTimeBtwRange(SimpleDateFormat format) {

		Calendar calendar = Calendar.getInstance();

		calendar.set(2016, 6, 27);
		long startTime = calendar.getTimeInMillis();

		calendar.set(2017, 6, 27);
		long endTime = calendar.getTimeInMillis();

		long randomTime = startTime + (long) (Math.random() * (endTime - startTime));
		calendar.setTimeInMillis(randomTime);

		return format.format(calendar.getTime());
	}

	public static String parseReqeustFromFile(String filePath) {
		// ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		// InputStream inputStream = classLoader.getResourceAsStream(filePath);
		InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream(filePath);

		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		try {
			while ((length = inputStream.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result.toString();

	}

	public static String join(List<String> items, String separator) {
		
		StringBuilder commaSepValueBuilder = new StringBuilder();

		for (int i = 0; i < items.size(); i++) {
			commaSepValueBuilder.append(items.get(i));

			if (i != items.size() - 1) {
				commaSepValueBuilder.append(", ");
			}
		}

		return commaSepValueBuilder.toString();
	}

}
