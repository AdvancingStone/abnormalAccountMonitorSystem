<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="js/echarts.min.js"></script>
<script src="js/jquery-1.8.3.min.js"></script>

</head>
<body>
	 <div id="chart" style="width:1000px;height:600px;"></div>
	<!-- 3. 在网页加载完毕时, 通过echarts的init函数传入上述的div的dom对象, 完成图表对象的创建 -->
    <script>
        load();
        var int=window.setInterval(load,1000*60*5);
        function load(){
            var div = document.getElementById("chart");
            //传入div , 创建图表对象
            var chart = echarts.init(div);
            //4.    编写图标对象的配置信息(JSON对象)

            var option = {
                title : {
                    text: '运营商网络流量流向异常账号统计',
                    subtext: '真实数据',
                    x:'left'
                },
                color: ['#3398DB'],
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis : [
                    {
                        type : 'category',
                        data : [],
                        axisTick: {
                            alignWithLabel: true
                        }
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'直接访问',
                        type:'bar',
                        barWidth: '50%',
                        data:[]
                    }
                ]
            };
            //5.    将配置的JSON对象, 设置给图标对象
            chart.setOption(option);
            chart.showLoading();


            var dates=[];    //日期数组（实际用来盛放X轴坐标值）
            var nums=[];    //异常账号数量数组（实际用来盛放Y坐标值）

            //6. ajax

            $.ajax({
                type : "post",
                async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url : "abnormalAccountServlet",    //请求发送到TestServlet处
                data : "type=queryAbnormalAccount",
                dataType : "json",        //返回数据形式为json
                success : function(result) {
                    //alert("请求成功");
                    //请求成功时执行该函数内容，result即为服务器返回的json对象
                    if (result) {
                        for(var i=0;i<result.length;i++){
                            dates.push(result[i].time);    //挨个取出类别并填入类别数组
                        }
                        for(var i=0;i<result.length;i++){
                            nums.push(result[i].num);    //挨个取出销量并填入销量数组
                        }
                        chart.hideLoading();    //隐藏加载动画
                        chart.setOption({        //加载数据图表
                            xAxis: {
                                data: dates
                            },
                            series: [{
                                // 根据名字对应到相应的系列
                                name: '异常账号数量',
                                data: nums
                            }]
                        });

                    }

                },
                error : function(errorMsg) {
                    //请求失败时执行该函数
                    alert("图表请求数据失败!");
                    chart.hideLoading();
                }
            })
        }
    </script>
</body>
</html>