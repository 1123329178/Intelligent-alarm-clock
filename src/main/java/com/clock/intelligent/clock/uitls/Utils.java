package com.clock.intelligent.clock.uitls;

import com.clock.intelligent.clock.model.RequestData;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.ntp.TimeStamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hp
 */
@Component
public class Utils {
	public Utils() {
	}

	private String s;
@Autowired
static Utils utils;
	public static void main(String[] args) {
		utils.fenge("#T,1234567890ABCDFE,1,2,2,0");
	}
	//
	public RequestData fenge(String s) {
		RequestData requestData = new RequestData();
		String[] req = new String[10];
		if (StringUtils.isNotBlank(s)) {
			if (s.length() >= 19) {
			req=s.split(",");
			}
			if (req!=null) {
				requestData.setReqHead(req[0]);
				requestData.setSerialNumber(req[1]);
				requestData.setReqCode(req[2]);
				requestData.setModuleType(req[3]);
				requestData.setWorkMode(req[4]);
				requestData.setKeepCode(req[5]);
			}else{
				return null;
			}

		}return requestData;
	}

	// ���ͻ���,�����ݵķ���
	public void writecilent(SocketChannel channel, String s) throws IOException {
		this.s = s;
		channel.write(ByteBuffer.wrap(s.getBytes()));
	}

	/**����Ӧ�Ŀͻ��˷���ʱ��
	// 获得网络时间
	 * @param diqu
	 * @return
	 * @throws IOException
	 */

	public String getNtptime(String diqu) throws IOException {

		NTPUDPClient timeClient = new NTPUDPClient();
		 timeClient.setDefaultTimeout(5000);
		String ntpip[] = { "ntp.sjtu.edu.cn", "time.buptnet.edu.cn", "s1b.time.edu.cn", "s1c.time.edu.cn",
				"s1d.time.edu.cn", "202.112.31.197", "120.25.108.11" };
		for (int i = 0; i < ntpip.length - 1; i++) {
			try {
				String timeServerUrl = ntpip[i];
				// ��õ�ַ
				System.out.println(i);
				InetAddress timeServerAddress = InetAddress.getByName(timeServerUrl);
				TimeInfo timeInfo = timeClient.getTime(timeServerAddress);
				// TimeStampʱ��� ת��ʱ��ĸ�ʽ��
				TimeStamp timeStamp = timeInfo.getMessage().getTransmitTimeStamp();
				Date date = timeStamp.getDate();
				// System.out.println(date);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy,MM,dd,hh,mm,ss,ss");
				// dateFormat.setTimeZone(TimeZone.getTimeZone(diqu));// �ı�ʱ��

				return dateFormat.format(date);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**��ȡip
	 * @param ip
	 * @return
	 */
	public String[] getIpAddress(String ip) {
		ip.replaceAll("/", "");
		String []ips = ip.split(":");
//		if (ip.length() == 16) {
//			ip = ip.substring(1, ip.length() - 3);
//		} else if (ip.length() == 17) {
//			ip = ip.substring(1, ip.length() - 4);
//		} else if (ip.length() == 18) {
//			ip = ip.substring(1, ip.length() - 5);
//		} else if (ip.length() == 19) {
//			ip = ip.substring(1, ip.length() - 6);
//		} else if (ip.length() == 20) {
//			ip = ip.substring(1, ip.length() - 7);
//		}
		return ips;
	}

	// ����IP��ַ�������ڵ�
	public String getAddressByIP(String strIP) {
		try {
			// URL url = new URL("https://ipapi.ipip.net/?ip=" + "169.235.24.133");
			// 180.166.170.137
			URL url = new URL("http://ip.taobao.com/service/getIpInfo.php?ip=" + "169.235.24.133");
			// URL url = new URL("http://ip.taobao.com/service/getIpInfo.php?ip=" +
			// "169.235.24.133");
			// URL url = new URL(
			// "http://api.ipinfodb.com/v3/ip-country/?key=3fa538d029841c4dfd2105e3d1ff5c5aa30c237dcd1753b6cdf9565ec785b24a&"
			// + "ip=" + strIP);
			// URL url = new URL("
			// http://api.ipinfodb.com/v3/ip-city/?key=<your_api_key>&ip=" +
			// "169.235.24.133");

			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line = null;
			StringBuffer result = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			reader.close();
			// System.out.println(result);
			JSONObject json = JSONObject.fromObject(result.toString());
			//System.out.println("json���ݣ� " + json);
			String country = JSONObject.fromObject(json.get("data")).get("country").toString();
			String region = JSONObject.fromObject(json.get("data")).get("region").toString();
			String city = JSONObject.fromObject(json.get("data")).get("city").toString();
			String county = JSONObject.fromObject(json.get("data")).get("county").toString();
			String isp = JSONObject.fromObject(json.get("data")).get("isp").toString();
			String area = JSONObject.fromObject(json.get("data")).get("area").toString();
			// System.out.println("���ң� " + country);
			// System.out.println("ʡ��: " + region);
			if (country!=null){
				return country + "/" + region;
			}
			return null;
		} catch (IOException e) {
			return "出错了";
		}

	}
	//获得天气
	public String getTianQi(String strIP) {
		TianQi tianQi = new TianQi(strIP);

		s=tianQi.getTianqi();
		return s;

	}
}
