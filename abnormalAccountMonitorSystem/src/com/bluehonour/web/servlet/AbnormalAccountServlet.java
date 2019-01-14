package com.bluehonour.web.servlet;

import com.bluehonour.web.entity.AbnormalAccount;
import com.bluehonour.web.service.AbnormalAccountServiceDao;
import com.bluehonour.web.service.impl.AbnormalAccountServiceDaoImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/abnormalAccountServlet")
public class AbnormalAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//key: 时间 ，  value: 异常账号数量
	private static Map<String, Integer> map = new HashMap<>();
	private List<AbnormalAccount> list = new ArrayList<>();

	public AbnormalAccountServlet() {
		super();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. 获取用户请求
		String type = request.getParameter("type");
		System.out.println("type: " + type);
		try {
			// 参数一：方法的名称
			// 参数二：方法的参数列表的列的列类型
			Method method = getClass().getMethod(type, HttpServletRequest.class, HttpServletResponse.class);
			// 参数二：调用方法的实参
			// 参数一：当前所属的类的类实例
			method.invoke(this, request, response);

		} catch (Exception e) {
			System.out.println("反射异常了");
			e.printStackTrace();
		}

	}

	/**
	 * 查询5分钟内的异常账户
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public void queryAbnormalAccount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AbnormalAccountServiceDao service = new AbnormalAccountServiceDaoImpl();
		int num = service.getAccountNum();
		System.out.println("num: "+num);

		//获取当前时间
		long currentTimeMillis = System.currentTimeMillis();
		// 转化成 12:12 的时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		String time = sdf.format(currentTimeMillis);

		System.out.println(time);

		//存入list集合中
		list.add(new AbnormalAccount(time,num));

		//发送到ajax的集合
		List<AbnormalAccount> aaList = new ArrayList<>();
		//将一小时内的时间发送到ajax，也就是12个5分钟，不足12个也发
		int beginIndex = 0;
		int endIndex = 0;


		if(list.size()>=12) {
			beginIndex = list.size()-12;
			endIndex = list.size();
		} else {
			beginIndex = 0;
			endIndex = list.size();
		}

		for(int i=beginIndex; i<endIndex; i++) {
			AbnormalAccount account = new AbnormalAccount();
			account.setTime(list.get(i).getTime());
			if(i==0) {
				account.setNum(list.get(i).getNum());
			} else {
				account.setNum(list.get(i).getNum() - list.get(i-1).getNum());
			}
//			System.out.println("赋值： "+account);
			aaList.add(account);
//			System.out.println("aa "+aaList.get(i));
			for(int ii=0; ii<aaList.size(); ii++){
				System.out.println("aaList:size "+aaList.size()+" "+aaList.get(ii).toString());
			}
		}

		ObjectMapper mapper = new ObjectMapper();    //提供java-json相互转换功能的类
		String json = mapper.writeValueAsString(aaList);    //将list中的对象转换为Json格式的数组
//		System.out.println(json);

		//将json数据返回给客户端
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().write(json);
	}

}
