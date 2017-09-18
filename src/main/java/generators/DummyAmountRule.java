package generators;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import utils.Utils;

public class DummyAmountRule {

	public static String nextMessage() {

		String amountRule = new String();
		amountRule = updateJson(Utils.parseReqeustFromFile("dataStore/amountRule.json"));
		return amountRule;
	}

	public static String updateJson(String json) {

		String lastModified = Utils.randomRandomTimeBtwRange();
		String[] dowList = new String[] { "1", "12", "123", "1234", "12345", "123456", "1234567", "12456", "24567" };

		String hotelCode = Utils.randomHotelCode(Utils.numOfHotels);

		/**/
		SimpleDateFormat format = new SimpleDateFormat("ddMMMYYYY");
		Calendar calendar = Calendar.getInstance();

		calendar.set(2016, 6, 27);
		long startTime = calendar.getTimeInMillis();

		calendar.set(2017, 6, 27);
		long endTime = calendar.getTimeInMillis();

		long randomStartTime = startTime + (long) (Math.random() * (endTime - startTime));
		long randomEndTime = randomStartTime + (long) (Math.random() * (endTime - randomStartTime));
		calendar.setTimeInMillis(randomStartTime);
		String beginDate = format.format(calendar.getTime());

		calendar.setTimeInMillis(randomEndTime);
		String endDate = format.format(calendar.getTime());
		/**/

		String dow = Utils.randomFrom(dowList);

		String replaceString = json.replace("${lastModified}", lastModified).replace("${chainCode}", Utils.chainCode)
				.replace("${hotelCode}", hotelCode).replace("${beginDate}", beginDate).replace("${endDate}", endDate)
				.replace("${dow}", dow).replace("${priceGridCode}", Utils.randomSalt(2))
				.replace("${productCodePrefix}", Utils.randomSalt(1));

		return replaceString;
	}

}
