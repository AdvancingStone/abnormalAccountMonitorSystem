package com.bluehonour.storm;


import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

/**
 * 从hdfs上读取数据并发射tuple
 */
public class ProductDataSpout extends BaseRichSpout {

    private SpoutOutputCollector collector = null;
    private FileSystem dirFs = null;
    private FileSystem fs = null;
    private String url = "hdfs://master1:9000/flume/flowData";
    private InputStream is = null;
    private BufferedReader br = null;
    private FileStatus[] fileStatus = null;
    private String targetFile = null;//	5分钟内滚动生成的文件
    private Boolean flag = false;
    private boolean flag2 = false;

//    private static Configuration conf = null;
//
//    static{
//    	conf = new Configuration();
//    	conf.setBoolean("fs.hdfs.impl.disable.cache", true);
//    }
    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
		listDir();
        
    }

    @Override
    public void nextTuple() {
    	try {
    		
    		if(!flag){  //找tmp临时文件，去除.tmp后缀后记录该文件名字
				listDir();
	    	} else{ //hdfs上该文件	滚动生成
	    		if(!flag2 && dirFs.exists(new Path(targetFile))){
	    			fs = FileSystem.newInstance(URI.create(targetFile), new Configuration());
        			is = fs.open(new Path(targetFile));
        			br = new BufferedReader(new InputStreamReader(is));
        			flag2 = !flag2;
	    		}
	    		
	    		if(flag2){
	    			String line = null;
	    			while((line=br.readLine())!=null) {
	    				collector.emit(new Values(line,"false"));
	    			}
	    			if(line==null) {
	    				collector.emit(new Values("","true"));
	    				listDir();
	    				fs = FileSystem.newInstance(URI.create(targetFile),new Configuration());
	    				flag2 = !flag2;
	    				flag = !flag;
	    			}
	    		}
	    	}
    		
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("line","isOver"));
    }

    
    public void listDir(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String curDate = sdf.format(cal.getTime());

		String curPath = url+"/"+curDate;
//		curPath = url+"/"+"2019-01-08";
		try {
			dirFs = FileSystem.get(URI.create(curPath), new Configuration());
			fileStatus = dirFs.listStatus(new Path(curPath));
		}catch (Exception e){
			e.printStackTrace();
		}

		Path path2 = fileStatus[fileStatus.length-1].getPath();
		String pathStr = path2.toString();
		if(pathStr.endsWith("tmp")){
			int endIndex = pathStr.lastIndexOf(".");
			targetFile = pathStr.substring(0,endIndex);
			flag = !flag;

			System.out.println("targetFile:  "+targetFile);
		}
	}
}

