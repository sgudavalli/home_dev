package generators;

import java.util.Random;

import utils.Utils;

public class DummyPriceGrid {

	public static String nextMessage(boolean isCorporate) {

		String priceGrid = new String();
		priceGrid = updateJson(Utils.parseReqeustFromFile("dataStore/priceGrid.json"), isCorporate);
		return priceGrid;
	}

	public static String updateJson(String json, boolean isCorporate) {

		String hotelCode = isCorporate ? "" : Utils.randomHotelCode(Utils.numOfHotels);

		Random random = new Random();
		String lastModified = Utils.randomRandomTimeBtwRange();

		String[] currencies = new String[] { "EUR", "CNY", "KRW", "USD", "AUD" };

		String priceGridCode = Utils.randomSalt(Utils.TWO);

		String replaceString = json.replace("${lastModified}", lastModified).replace("${chainCode}", Utils.chainCode)
				.replace("${hotelCode}", hotelCode).replace("${priceGridCode}", priceGridCode)
				.replace("${currency}", Utils.randomFrom(currencies))
				.replace("${isFollowHouseDefaultCurrency}", String.valueOf(random.nextBoolean()));

		return replaceString;
	}

}
