package utils;

public class Debugger {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		String rule = DummyPriceGrid.nextMessage(false);
		System.out.println("Hello");
		
//		Utils.randomHotelCode(1000);
		
		System.out.println(System.getProperty("user.dir"));
		
		System.out.println(Utils.parseReqeustFromFile("dataStore/ratePlan.json"));

	}

}
