package utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import generators.DummyAuditTrailv2Db;

public class DebuggerToKafka {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Properties props = new Properties();
	    props.put("bootstrap.servers", "localhost:9092");
	    props.put("acks", "all");
	    props.put("retries", 0);
	    props.put("batch.size", 16384);
	    props.put("linger.ms", 1);
	    props.put("buffer.memory", 33554432);
	    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	    
	    Producer<String, String> producer = null;
	    
	    int batchsize =0 ;
	    try {
	        producer = new KafkaProducer<String, String>(props);
	        
	        for(int days=3; days <= 4; days++) {
	        	Date today = new Date();
	        	Date dt = new Date(today.getTime() + TimeUnit.DAYS.toMillis(days));
	        	
	            for (int events=1; events <= 2; events++) {
		        	for (int hotels=1; hotels <= 2; hotels++) {
			    		List<BomAudit> notifications = DummyAuditTrailv2Db.nextMessage(hotels,
			    				new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(dt));
			    		
			    		for (BomAudit audit: notifications) {
			    			producer.send(new ProducerRecord<String, String>("audit", 
			    					audit.getDocument()));
			    			
			    			batchsize++;
				    		if (batchsize % 1000 == 0) {
			    				System.out.println("execute batch for hotel -> " + 
			    						audit.getHotelCode() + ";Record locator @" + Integer.toString(batchsize));
			    			}
			    		}
			    	}
			    }
	        }
	        
	      } catch (Exception e) {
	        e.printStackTrace();
	   
	      } finally {
	        producer.close();
	      }
		
	}

}
