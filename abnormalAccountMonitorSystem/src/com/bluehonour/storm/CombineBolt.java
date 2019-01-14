package com.bluehonour.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 去重合并异常数据类
 */
public class CombineBolt extends BaseRichBolt {
    private OutputCollector collector;
    public static Set<String> set = new HashSet<>();
    public static int count = 0;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
    	//type类型
    	//cookieDevOsCountBolt
    	//natIPCountBolt
    	//qqidCountBolt
    	String type = tuple.getSourceComponent();
        String account = tuple.getString(0);
        String source = tuple.getString(1);
        count += tuple.getIntegerByField("count");
        if(count == 3) {
        	count = 0;
        	System.out.println("&&&&&&&&&&&   CombineBolt count is 3, clear set ");
        	set.clear();
        } else {
        	System.out.println("type: "+type+"\tsource: "+source+"\taccount: "+account);
            if(!set.contains(account)){
                set.add(account);
                String time = ""+ System.currentTimeMillis();
                System.out.println("combineBolt--------------------"+set.size());
                collector.emit(new Values(type, account, source,time));
            }
        }
        
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("type", "account", "source", "time"));
    }
}
