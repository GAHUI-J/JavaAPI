import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


//위도, 경도 추출
public class Project01_D {

	public static void main(String[] args) {
		
//		String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=";
		String client_id="lam0j80e09";
		String client_secret="g63aQMJqOprFtHhLYn1k9zuTJgYeB16z4xxThETH";
		BufferedReader io = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("주소를 입력하세요:");
			//한줄단위로 읽어들임
			String address = io.readLine();
			//공백이 있으면 안됨
			String addr = URLEncoder.encode(address, "UTF-8");
			String reqUrl = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query="+addr;
			
			URL url = new URL(reqUrl);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			//get방식으로 요청
			con.setRequestMethod("GET");
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
			con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);
			BufferedReader br;
			int responseCode = con.getResponseCode();
			
			if(responseCode==200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
			}else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			
			String line;
			StringBuffer response = new StringBuffer(); //JSON
			while((line=br.readLine())!=null) {
				response.append(line);
			}
			
			br.close();
			
			JSONTokener tokener = new JSONTokener(response.toString());
			JSONObject object = new JSONObject(tokener);
			System.out.println(object.toString());
			
			
			JSONArray arr = object.getJSONArray("addresses");
			
			for(int i=0;i<arr.length();i++) {
				JSONObject temp = (JSONObject)arr.get(i);
				System.out.println("address: "+temp.get("roadAddress"));
				System.out.println("jibunAddress: "+temp.get("jibunAddress"));
				System.out.println("경도: "+temp.get("x"));
				System.out.println("위도: "+temp.get("y"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
		

	}

}
