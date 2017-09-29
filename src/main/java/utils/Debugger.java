package utils;

import java.util.List;

import generators.DummyAuditTrailv2Db;
//import generators.DummyAmountRule;
//import generators.DummyPriceGrid;
//import generators.DummyRatePlan;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Debugger {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		
		
		String myDriver = "com.mysql.jdbc.Driver";
	    String myUrl = "jdbc:mysql://localhost/testDb";
	    Class.forName(myDriver);
	    Connection conn = DriverManager.getConnection(myUrl, "hiveuser", "hivepassword");
	   
	    String insert = "insert into hst_history_r2 (conversationId, chainCode, hotelCode, "
	    		+ "brandCode, regionCode, categoryCode, subCategoryCode, userId, organization, "
	    		+ "action, creationDate, code1, code2, code3, date1, date2, document) "
	    		+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	    
	    PreparedStatement preparedStatement = conn.prepareStatement(insert);
	    
	    int batchsize =0;
	    
//	    System.out.println(conn.isClosed());
		
	    for (int i=89; i <= 5000; i++) {
	    	for (int j=1; j <= 1500; j++) {
	    		List<BomAudit> notifications = DummyAuditTrailv2Db.nextMessage(i,
	    				new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()));
	    		
	    		for (BomAudit audit: notifications) {
	    			
	    			preparedStatement.setString(1, audit.getConversationId() + 
	    					"|" + audit.getEventId());
	    			preparedStatement.setString(2, audit.getChainCode());
	    			preparedStatement.setString(3, audit.getHotelCode());
	    			preparedStatement.setString(4, audit.getBrandCode());
	    			preparedStatement.setString(5, audit.getRegionCode());
	    			preparedStatement.setString(6, audit.getCategory());
	    			preparedStatement.setString(7, audit.getSubCategory());
	    			preparedStatement.setString(8, audit.getUserId());
	    			preparedStatement.setString(9, audit.getOrganization());
	    			preparedStatement.setString(10, audit.getAction());
	    			preparedStatement.setString(11, audit.getLastModified());
	    			preparedStatement.setString(12, audit.getCode1());
	    			preparedStatement.setString(13, audit.getCode2());
	    			preparedStatement.setString(14, audit.getCode3());
	    			preparedStatement.setString(15, audit.getDate1());
	    			preparedStatement.setString(16, audit.getDate2());
	    			preparedStatement.setString(17, audit.getDocument());
	    			preparedStatement.addBatch();
	    			batchsize++;
	    			
	    			if (batchsize % 1000 == 0) {
	    				System.out.println("execute batch for hotel -> " + 
	    						audit.getHotelCode() + ";Record locator @" + Integer.toString(batchsize));
	    				preparedStatement.executeBatch(); // Execute every 1000 items.
	                }
	    			
	    		}
	    	
	    	}
	    }
	    
		preparedStatement.executeBatch();
		
//		String amountRule = DummyAmountRule.nextMessage();
//		System.out.println(amountRule);
//		
//		String priceGrid = DummyPriceGrid.nextMessage(false);
//		System.out.println(priceGrid);
//		
//		String ratePlan = DummyRatePlan.nextMessage(false);
//		System.out.println(ratePlan);
		
//		Utils.randomHotelCode(1000);

	}

}

