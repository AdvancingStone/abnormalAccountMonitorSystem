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
 * 根据cookieValue（cookie值） + devName（设备名称） + osName（操作系统）统计5分钟内的宽带异常数据
 */
public class CookieDevOsCountBolt extends BaseRichBolt {
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
    	if("true".equals(isOver)) {
			map.clear();
			System.out.println("CookieDevOsCountBolt: 收到了！！！！！！！！！！！！！");
			System.out.println("CookieDevOsCountBolt: 收到了！！！！！！！！！！！！！");
			System.out.println("CookieDevOsCountBolt: 收到了！！！！！！！！！！！！！");
    		collector.emit(new Values("","",1));
    		System.out.println("aa");
//    		return;
    	} else {
    		String line = tuple.getStringByField("cookieDevOs");
            if(null!=line && line.length()>0){
                String[] fields = line.split("\t");
                
                if(fields.length==2){
                    String userAccount = fields[0];
                    String cookieDevOs = fields[1];

                    Set<String> set = new HashSet<>();
                    if(map.containsKey(userAccount)){ //map中有该账号的纪录
                        set = map.get(userAccount); //map的值：set集合
                    }
                    set.add(cookieDevOs);//set自动去重
                    map.put(userAccount, set);
                    System.out.println("cookieDevOs ******* set size()-----"+set.size() + line);
                    if(set.size()>=5){
                    	collector.emit(new Values(userAccount,cookieDevOs,0));
                    }
                }
            }
    	}
        
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("cookieDevOsAccountBolt","cookieDevOs","count"));
    }
}
