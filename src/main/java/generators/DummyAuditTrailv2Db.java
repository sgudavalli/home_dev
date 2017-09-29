package generators;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utils.BomAudit;
import utils.Utils;

public class DummyAuditTrailv2Db {

	public static ArrayList<String> smallCat = new ArrayList<String>();
	public static ArrayList<String> avgCat = new ArrayList<String>();
	public static ArrayList<String> bigCat = new ArrayList<String>();
	
	public static String[] actionValues = new String[] {"create", "modify", "delete", "markForDeletion", "restore", 
			"duplicate", "archive", "activate", "deactivate", "lock", "unlock", "publish", "sendForPublication", 
			"validate", "upload", "merge", "acknowledge", "statusChange", "synchronize", "start", "stop", "pause", 
			"resume", "purge", "release", "abort", "restart", "completed", "markAsCompleted", "conversion", 
			"disable", "enable", "optimize"};

	public static void populateCatSub() {

		InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream("audit/auditCategory");
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		// ArrayList<String> array = new ArrayList<String>();

		try {
			String line = null;
			while ((line = br.readLine()) != null) {

				String[] lineVal = line.split(",", 2);

				if (lineVal[1].equals("low")) {
					smallCat.add(lineVal[0]);
				} else if (lineVal[1].equals("medium")) {
					avgCat.add(lineVal[0]);
				} else if (lineVal[1].equals("high")) {
					bigCat.add(lineVal[0]);
				}

			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}

	}

	public static List<BomAudit> nextMessage(int hotelNumber, String creationTimeStamp) {
		
		List<BomAudit> auditRecords = new ArrayList<BomAudit>();
		
		populateCatSub();

		String hotelCode = String.format("D%04d", hotelNumber);
		String brandCode = String.format("B%02d", hotelNumber % 10);
		String regionCode = String.format("R%02d", hotelNumber % 50);
		String userId = Utils.randomRatePlanCode(Utils.TWO);
		String organization = userId.substring(0, 2);
		String chainCode = Utils.chainCode;
		String conversationId = Utils.randomSalt(10);
		

		String auditEventTemplate = new String();
		auditEventTemplate = Utils.parseReqeustFromFile("audit/auditMessage.json");

		for (int i = 1; i <= 21; i++) {

			Random rand = new Random();
			String[] parts = null;

			if (i % 19 == 0) {
				int randomIndex = rand.nextInt(smallCat.size());
				parts = smallCat.get(randomIndex).split("\\|", 2);
			} else if (i % 7 == 0) {
				int randomIndex = rand.nextInt(avgCat.size());
				parts = avgCat.get(randomIndex).split("\\|", 2);
			} else {
				int randomIndex = rand.nextInt(bigCat.size());
				parts = bigCat.get(randomIndex).split("\\|", 2);
			}

			String category = parts[0];
			String subCategory = parts[1];
			String action = Utils.randomFrom(actionValues);
			String code1 = Utils.randomSalt(3);
			String code2 = Utils.randomSalt(3);
			String code3 = Utils.randomSalt(3);
			String date1 = Utils.randomRandomDate();
			String date2 = Utils.randomRandomDate();
			
			BomAudit auditRec = new BomAudit();
			auditRec.setAction(action);
			auditRec.setCode1(code1);
			auditRec.setCode2(code2);
			auditRec.setCode3(code3);
			auditRec.setCategory(category);
			auditRec.setSubCategory(subCategory);
			auditRec.setUserId(userId);
			auditRec.setOrganization(organization);
			auditRec.setDate1(date1);
			auditRec.setDate2(date2);
			auditRec.setChainCode(chainCode);
			auditRec.setHotelCode(hotelCode);
			auditRec.setBrandCode(brandCode);
			auditRec.setRegionCode(regionCode);
			auditRec.setLastModified(creationTimeStamp);
			auditRec.setEventId(Integer.toString(i));
			auditRec.setConversationId(conversationId);
			
			
			String audit = auditEventTemplate
					.replace("${eventId}", Integer.toString(i))
					.replace("${action}", action)
					.replace("${code1}", code1)
					.replace("${code2}", code2)
					.replace("${code3}", code3)
					.replace("${category}", category)
					.replace("${subCategory}", subCategory)
					.replace("${date1}", date1)
					.replace("${date2}", date2)
					.replace("${lastModified}", creationTimeStamp)
					.replace("${hotelCode}", hotelCode)
					.replace("${brandCode}", brandCode)
					.replace("${regionCode}", regionCode)
					.replace("${conversationId}", conversationId)
					.replace("${organization}", organization)
					.replace("${userId}", userId)
					.replace("${chainCode}", chainCode);

			auditRec.setDocument(audit);
			
			auditRecords.add(auditRec);
		}
		
		return auditRecords;
	}

}
