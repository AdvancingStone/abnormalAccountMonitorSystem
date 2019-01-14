package com.bluehonour.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class TopologyMain {
    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("productDataSpout", new ProductDataSpout(),7);
        builder.setBolt("splitBolt", new SplitBolt(),3).shuffleGrouping("productDataSpout");
        //分流
        builder.setBolt("natIPCountBolt", new NatIPCountBolt(),3).shuffleGrouping("splitBolt");
        builder.setBolt("qqidCountBolt", new QqidCountBolt(),3).shuffleGrouping("splitBolt");
        builder.setBolt("cookieDevOsCountBolt", new CookieDevOsCountBolt(),3).shuffleGrouping("splitBolt");
        //合并
        builder.setBolt("combineBolt", new CombineBolt(),1)
                .shuffleGrouping("natIPCountBolt")
                .shuffleGrouping("qqidCountBolt")
                .shuffleGrouping("cookieDevOsCountBolt");
        builder.setBolt("saveBolt", new SaveBolt(),1).shuffleGrouping("combineBolt");


        Config config = new Config();
        config.setDebug(true);

        StormTopology topology = builder.createTopology();
        if(args!=null && args.length>0){
            //cluster mode
            config.setNumWorkers(4);
            StormSubmitter.submitTopologyWithProgressBar(args[0], config, topology);
        } else{
            // local mode
            config.setNumWorkers(4);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("dd", config, topology);
        }
    }
}
