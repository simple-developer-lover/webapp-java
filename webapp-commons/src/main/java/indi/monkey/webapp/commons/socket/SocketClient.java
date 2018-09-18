package indi.monkey.webapp.commons.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import lombok.Cleanup;

public class SocketClient {

	private Socket socket;

	public SocketClient(String host, int port) {
		try {
			this.socket = new Socket(host, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String sendMsg(String msg) {
		try {
			@Cleanup
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(msg.getBytes("utf8"));
			@Cleanup
			InputStream inputStream = socket.getInputStream();
			StringBuffer sb = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf8"));
			String s = null;
			while ((s = reader.readLine()) != null) {
				sb.append(s.trim());
			}
			socket.shutdownOutput();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean close() {
		try {
			socket.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/*public static void main(String[] args) {
		SocketClient client = new SocketClient("localhost", 8888);
		String sendMsg = client.sendMsg("https://tieba.baidu.com/p/5771313540");
		JSONObject object = JSON.parseObject(sendMsg);
		JSONObject data = object.getJSONObject("data");
		data.forEach((k, v) -> {
			TiebaUser user = JSON.parseObject(k, TiebaUser.class);
			System.out.println(JSON.toJSONString(user));
		});
		System.out.println(object.getString("picPath"));
	}*/
}
