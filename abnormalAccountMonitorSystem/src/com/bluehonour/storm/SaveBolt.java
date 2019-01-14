package com.bluehonour.storm;

import java.sql.Connection;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;

import com.bluehonour.util.DBUtils;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;


public class SaveBolt extends BaseRichBolt {

    private Connection conn = null;
    private String QQID_SQL = "insert into abnormalAccount (time, userAccount, qqid) values (?,?,?)";
    private String NATIP_SQL = "insert into abnormalAccount (time, userAccount, natIP) values (?,?,?)";
    private String COOKIEDEVOS_SQL = "insert into abnormalAccount (time, userAccount, cookieValue,devName,osName) values (?,?,?,?,?)	";
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        conn = DBUtils.getConn();
    }

    @Override
    public void execute(Tuple tuple) {
        String type = tuple.getString(0);
        String account = tuple.getString(1);
        String source = tuple.getString(2);
        String time = tuple.getString(3);
        System.out.println("@@@@@@@@@@@@@@@@@@@  save bolt: "+type+"\t"+account+"\t"+source+"\t"+time);



        QueryRunner qr = new QueryRunner();
        Object[] params;

        try{
        	  if(type.equals("qqidCountBolt")){
              	params = new Object[]{time, account, source};
              	qr.update(conn,QQID_SQL, params);
              } else if(type.equals("natIPCountBolt")){
            	  params = new Object[]{time, account, source};
            	  qr.update(conn,NATIP_SQL, params);
              } else if(type.equals("cookieDevOsCountBolt")){
            	  String[] fields = source.split("-");
            	  params = new Object[]{time,account,fields[0],fields[1],fields[2]};
            	  qr.update(conn,COOKIEDEVOS_SQL,params);
              }
        }catch(Exception e){
        	e.printStackTrace();
        }
        
        

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
