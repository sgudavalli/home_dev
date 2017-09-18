package generators;

import java.util.Random;

import utils.Utils;

public class DummyRatePlan {

    public static String nextMessage(boolean isCorporate) {

        String ratePlan = new String();
        ratePlan = updateJson(Utils.parseReqeustFromFile("dataStore/ratePlan.json"), isCorporate);
        return ratePlan;
    }

    public static String updateJson(String json, boolean isCorporate) {

    	String hotelCode = isCorporate? "" : Utils.randomHotelCode(Utils.numOfHotels);
        String ratePlanCode = Utils.randomRatePlanCode(Utils.THREE);

    	
        Random random = new Random();
        Integer[] isConfidentials = new Integer[] { 0, 1 };
        String[] blckPtcpValues = new String[] { "Blackout", "Participation" };
        String[] marketingDisplay = new String[] { "M", "Y", "A", "B", "N", "E" };
        String lastModified = Utils.randomRandomTimeBtwRange();

        String brandCode = isCorporate? "" : Utils.randomHotelCode(10);
        String regionCode = isCorporate? "" : Utils.randomHotelCode(100);
        String countryCode = isCorporate? "" : Utils.randomHotelCode(1000);

        String replaceString = json.replace("${lastModified}", lastModified)
        		.replace("${chainCode}", Utils.chainCode)
                .replace("${hotelCode}", hotelCode)
                .replace("${brandCode}", brandCode)
                .replace("${regionCode}", regionCode)
                .replace("${countryCode}", countryCode)
                .replace("${ratePlanCode}", ratePlanCode)
                .replace("${blackoutParticipationStatusType}", Utils.randomFrom(blckPtcpValues))
                .replace("${isCommissionable}", String.valueOf(random.nextBoolean()))
                .replace("${isCustomizationAllowed}", String.valueOf(random.nextBoolean()))
                .replace("${isRatePlanAtLeastAtMostAllowed}", String.valueOf(random.nextBoolean()))
                .replace("${name}", Utils.randomSalt(50))
                .replace("${marketingDisplay}", Utils.randomFrom(marketingDisplay))
                .replace("${isCouponRequired}", String.valueOf(random.nextBoolean()))
                .replace("${isCourtesyHoldAllowed}", String.valueOf(random.nextBoolean()))
                .replace("${isToBeDeletedAfterEndDate}", String.valueOf(random.nextBoolean()))
                .replace("${isDisplayOnGDS}", String.valueOf(random.nextBoolean()))
                .replace("${isConfidential}", String.valueOf(Utils.randomFrom(isConfidentials)))
                .replace("${hasExtraPersonCharge}", String.valueOf(random.nextBoolean()))
                .replace("${freeNight}", String.valueOf(random.nextBoolean()))
                .replace("${isGroup}", String.valueOf(random.nextBoolean()))
                .replace("${isID_RequiredAtCheckIn}", String.valueOf(random.nextBoolean()))
                .replace("${isSellStrategyAllowed}", String.valueOf(random.nextBoolean()))
                .replace("${loyaltyAwards}", String.valueOf(random.nextBoolean()))
                .replace("${isLoyaltyNumberRequired}", String.valueOf(random.nextBoolean()))
                .replace("${loyaltyPointsApply}", String.valueOf(random.nextBoolean()))
                .replace("${isPackage}", String.valueOf(random.nextBoolean()))
                .replace("${isDeleteAtHotelAllowed}", String.valueOf(random.nextBoolean()))
                .replace("${isPriceGridOneToOne}", String.valueOf(random.nextBoolean()))
                .replace("${areRatePropertiesAllowed}", String.valueOf(random.nextBoolean()));

        return replaceString;
    }

}
