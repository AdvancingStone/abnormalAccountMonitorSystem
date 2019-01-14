package com.bluehonour.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 根据QQ号统计5分钟内的异常宽带数据
 */
public class QqidCountBolt extends BaseRichBolt {
    private OutputCollector collector = null;
    public static Map<String, Set<String>> map = new HashMap<>();
    public static int count = 0;
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
    	String isOver = tuple.getStringByField("isOver");
    	System.out.println();
    	if("true".equals(isOver)) {
			map.clear();
			System.out.println("qqidCountBolt: 收到了！！！！！！！！！！！！！");
			System.out.println("qqidCountBolt: 收到了！！！！！！！！！！！！！");
			System.out.println("qqidCountBolt: 收到了！！！！！！！！！！！！！");
    		collector.emit(new Values("","",1));
//    		return;
    		System.out.println("aa");
    	}else {
    		 String line = tuple.getStringByField("qqid");
    	        
    	        if(null!=line && line.length()>0){
    	            String[] fields = line.split("\t");

    	            if(fields.length==2){
    	                String userAccount = fields[0];
    	                String qqid = fields[1];
    	                Set<String> set = new HashSet<>();
    	                if(map.containsKey(userAccount)){ //map中有该账号的纪录
    	                    set = map.get(userAccount); //map的值：set集合
    	                }
    	                set.add(qqid);//set自动去重
    	                map.put(userAccount, set);
    	                System.out.println("qqidCountBOlt********set size()--------"+set.size()+"  "+line);
    	                if(set.size()>=20){
    	                	collector.emit(new Values(userAccount,qqid,0));
    	                }
    	            }
    	        }
    	}
       
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("qqidAccountBolt","qqid","count"));
    }
}
