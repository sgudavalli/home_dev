package org.rbr.amadeus.run

import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.kafka._
import org.apache.spark.storage._

object sscKafkaHelloWorld {
  
  def main(args : Array[String]) {
    
    val conf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("sparkStreamingApp")
      
    val ssc = new StreamingContext(conf, Seconds(2))
    
    val Array(zkQuorum, group, topics, numThreads) = Array("localhost:2181", "sscKafkaTest", "test", "1")
    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
      
    val messages = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap)
    
    messages.foreachRDD(
      rdd => {
        rdd.foreachPartition(
          partitionofRecords => {
            partitionofRecords.foreach (
              row => println(row)
            )
          }
        )
      }
    )
    
    ssc.start()
    ssc.awaitTermination()
    
  }
  
  
}