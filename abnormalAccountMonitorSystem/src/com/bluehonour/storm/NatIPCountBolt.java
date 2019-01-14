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
 * 根据内网IP统计5分钟内的异常数据
 */
public class NatIPCountBolt extends BaseRichBolt {
    private OutputCollector collector = null;
    // 将数据存入map中，key:账号，value:5分钟内登录该账号的内网IP
    public static Map<String, Set<String>> map = new HashMap<>();

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
    	String isOver = tuple.getStringByField("isOver");
    	if("true".equals(isOver)) {
			map.clear();
			System.out.println("NatIPCountBolt: 收到了！！！！！！！！！！！！！");
			System.out.println("NatIPCountBolt: 收到了！！！！！！！！！！！！！");
			System.out.println("NatIPCountBolt: 收到了！！！！！！！！！！！！！");
    		collector.emit(new Values("","",1));
//    		return;
    	}else {
    		String line = tuple.getStringByField("natIP");
            if(null!=line && line.length()>0){
                String[] fields = line.split("\t");

                if(fields.length==2){
                    String userAccount = fields[0];
                    String natIP = fields[1];
                    Set<String> set = new HashSet<>();
                    if(map.containsKey(userAccount)){ //map中有该账号的纪录
                        set = map.get(userAccount); //map的值：set集合
                    }
                    set.add(natIP);//set自动去重
                    map.put(userAccount, set);
                    System.out.println("natIP() ****** set size()-------"+set.size()+"*****  "+line);
                    if(set.size()>=5){
                    	collector.emit(new Values(userAccount,natIP,0));
                    }
                }
            }
    	}
    	
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("natIPAccountBolt","natIP","count"));
    }
}
