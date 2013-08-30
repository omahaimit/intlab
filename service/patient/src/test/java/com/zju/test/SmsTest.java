package com.zju.test;

public class SmsTest {

	//private static final String nativeUrl = "http://write.blog.csdn.net/postlist";
	//private static final String sinaShortUrl = "http://api.t.sina.com.cn/short_url/shorten.json?source=2702428363&url_long=";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		String exam = "血常规（五分类）+学期起";
		exam = exam.split("[+]")[0].split("（")[0].trim();
		System.out.println(exam);
		/*HttpClient client = new HttpClient();
		
		GetMethod get = new GetMethod(sinaShortUrl + nativeUrl + "?id=" + "9021843782758485949");
		
		if  (client.executeMethod(get) == HttpStatus.SC_OK) {
			System.out.println(1);
			String result = get.getResponseBodyAsString();
			JSONArray array = new JSONArray(result);
			JSONObject object = array.getJSONObject(0);
			String shortUrl = object.get("url_short").toString();
			System.out.println(shortUrl);
		}
		System.out.println(2);
		get.releaseConnection();*/
		
		/*PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn");
		post.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码
		NameValuePair[] data = { new NameValuePair("Uid", "winstar"),
				new NameValuePair("Key", "b58bee5a7f931e3b6d18"),
				new NameValuePair("smsMob", "15088682576"),
				new NameValuePair("smsText", "您于7月4日在...的...已出，请登录www.imwjp.com查看具体信息。") };
		post.setRequestBody(data);

		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:" + statusCode);
		for (Header h : headers) {
			System.out.println(h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes(
				"gbk"));
		System.out.println(result);
		post.releaseConnection();*/
	}

}
