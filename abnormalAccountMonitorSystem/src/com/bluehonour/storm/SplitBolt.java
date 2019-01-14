package com.bluehonour.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.Map;

/**
 * 对spout的数据进行切分
 *     根据
 *      natIP去重求和数 > 5
 *      qqid去重求和数 > 20
 *      cookieValue + devName + osName去重求和数 > 5
 *     发送下级bolt
 */
public class SplitBolt extends BaseRichBolt {
    private OutputCollector collector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
    	String isOver = tuple.getStringByField("isOver");
    	System.out.println(isOver);
    	if("true".equals(isOver)) {
    		 collector.emit(new Values("", "", "", "","true"));
    		 System.out.println("wo ");
//    		 return;
    	} else {
    		String line = tuple.getStringByField("line");
    		System.out.println("split "+line);
    		if(null!=line && line.length()>0){
    			String[] fields = line.split("\t");
    			if(fields.length==8){
    				String userAccount = fields[1]; //宽带账户
    				String natIP = userAccount + "\t" + fields[4]; //内网IP
    				String qqid = userAccount + "\t" + fields[3]; //qq号
    				String cookieValue = fields[5]; //cookie value
    				String devName = fields[6];// 设备名称
    				String osName = fields[7];// 操作系统名称
    				String cookieDevOs = userAccount + "\t" + cookieValue+"-"+devName+"-"+osName;
    				//发送tuple
    				collector.emit(new Values(natIP, qqid, cookieDevOs, line,"false"));
    			}
    		}
    	}
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("natIP", "qqid", "cookieDevOs", "line","isOver"));
    }
}
