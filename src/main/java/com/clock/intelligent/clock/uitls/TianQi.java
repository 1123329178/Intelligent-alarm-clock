package com.clock.intelligent.clock.uitls;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Component
public class TianQi {
	String ip;
    String tianqi="";

	public TianQi() {
	}

	public TianQi(String s) {
		this.ip=s;
	}

	public String getTianqi() {
		 guiZe g = new guiZe();
		StringBuilder sb = new StringBuilder();
		InputStream is = null;
		BufferedReader br = null;
		PrintWriter out = null;
		try {


		    String url = "https://www.tianqiapi.com/api/?version=v1&appid=36491635&appsecret=L5IZtio9&ip="+ip;
		    URL    uri = new URL(url);
		    System.out.println(url);
		    HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
		    connection.setRequestMethod("GET");
		    connection.setRequestProperty("accept", "*/*");

//		    out = new PrintWriter(connection.getOutputStream());
//		    out.flush();

		    is = connection.getInputStream();
		    br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		    String line;

		    while ( ( line = br.readLine() ) != null ) {
		        sb.append(line);
		    }
			JSONObject json = JSON.parseObject(sb.toString());
			JSONArray data = json.getJSONArray("data");
	        List<WeatherData> datas = JSON.parseObject(data.toJSONString(), new TypeReference<List<WeatherData>>() {
	        });
	            WeatherData a=datas.get(0);
	        	System.out.print(a.getWeek()+" ");
	        	System.out.print(a.getWea()+" ");
	        	System.out.print(a.getWin()[0]+" ");
//	        	System.out.print("����¶�"+a.getTem2()+" ");
//	        	System.out.print("����¶�"+a.getTem1()+" ");
//
//	        	System.out.print("ʪ��"+a.getHumidity()+" ");
//	        	System.out.println("��������"+a.getAir());
//
	        	//tianqi=g.getWeek(a.getWeek());

	        	tianqi=g.getWea(a.getWea())+",";

	        	tianqi=tianqi+g.getWin(a.getWin()[0]);

	        	//System.out.println(a.getWin_speed());
	        	//System.out.println(g.getWin_speed(a.getWin_speed()));
                tianqi=tianqi+g.getWin_speed(a.getWin_speed());
                tianqi=tianqi+g.getTem((a.getTem2()))+",";
	        	tianqi=tianqi+g.getTem(a.getTem1())+",";
	        	tianqi=tianqi+a.getHumidity()+",";
	        	tianqi=tianqi+g.getAir_level(a.getAir());

		       System.out.println(tianqi);
		} catch ( Exception ignored ) {
		} finally {
		    //�ر���
		    try {
		        if(is!=null){
		            is.close();
		        }
		        if(br!=null){
		            br.close();
		        }
		        if (out!=null){
		            out.close();
		        }
		    } catch ( Exception ignored ) {}
		}
		return tianqi;
	}
		public static void main(String[] args) {
			TianQi t = new TianQi("106.75.90.146");
			System.out.println(t.getTianqi());
		}
}

