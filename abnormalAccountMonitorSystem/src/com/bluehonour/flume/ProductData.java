package com.bluehonour.flume;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bluehonour.flume.entity.Product;
import com.bluehonour.util.UUIDUtils;

/**
 *
 */
public class ProductData {

    Random random = new Random();
    String[] devices = new String[]{"MacBook","MacBook Pro","Thinkpad","iphone8plus","surface","ASUS","iphone7","lenovo","dell","huawei"};
    String[] os = new String[]{"windows7","windows8","windows10","linux"};

    /**
     * 一次性创造多少条数据
     * @param num
     * @return
     */
    public List<Product> createData(int num){
        if(num < 1){
            num = 1;
        }
        List<Product> productList = new ArrayList<>();
        for(int i=0; i<num; i++){
            //数据统计时间
            String time = String.valueOf(System.currentTimeMillis());
            //宽带账户
            String userAccount = "1572345"+((int)(Math.random()*(10000-1000))+10000);
            //用户以太网IP
            String userIP = random.nextInt(254)+"."+random.nextInt(254)+"."+random.nextInt(254)+"."+random.nextInt(254);
            //qq号
            String qqid = ""+(int)((Math.random()*(1000000000-100000000))+100000000);
            //内网Ip C类网
            String natIP = "192.168."+random.nextInt(254)+"."+random.nextInt(254);
            //cookie value(16位)
            String cookieValue = UUIDUtils.generateUUID(16);
            //设备名称
            String devName = devices[random.nextInt(devices.length)];
            //操作系统名称
            String osName = os[random.nextInt(os.length)];
            if(devName.startsWith("iphone")){
                osName = "ios";
            } else if(devName.startsWith("MacBook")){
                osName = "mac";
            }
            //创建对象
            Product product = new Product(time,userAccount,userIP,qqid,natIP,cookieValue,devName,osName);
            //添加进list集合
//            for(int j=0; j<num; j++) {
            	productList.add(product);
//            }
        }
        return productList;
    }

    /**
     * 通过tcp的方式不断生成数据，flume采集
     * @param address IP地址
     * @param port 端口号
     * @throws IOException
     */
    public void tcpSendSocket(String address, int port) throws IOException {

        Socket socket = null;
        PrintWriter pw = null;
        try {
            socket = new Socket(address, port);

            List<Product> data = createData(30);

            //创建字符输出流
            pw = new PrintWriter(socket.getOutputStream());
            if(data!=null && data.size()>0){
                for(int i=0; i<data.size(); i++){
                    System.out.println(data.get(i));
                    pw.println(data.get(i));
                    pw.flush();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
        	if(pw != null){
        		pw.close();
        	}
            if(socket != null){
                socket.close();
            }
        }
    }


    public static void main(String[] args) {
        Thread t1 = new Thread(new TcpSendSocket2("192.168.246.137",5140));
        t1.start();
        Thread t2 = new Thread(new TcpSendSocket2("192.168.246.138",5140));
        t2.start();

    }
}

class TcpSendSocket2 implements Runnable {
    private ProductData data = new ProductData();
    String address;
    int port;
    public TcpSendSocket2(String address, int port){
        this.address = address;
        this.port = port;
    }

    @Override
    public void run() {
        while(true){
            try{
                data.tcpSendSocket(address,port);
                Thread.sleep(1000);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
