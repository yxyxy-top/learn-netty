package com.example.nio;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO
 *
 * @author XuYu
 * @date 2021-04-27 17:20
 */
public class BIOClient {

    public void initBioClient(String host, int port) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        // 客户端Socket
        Socket socket = null;
        String inputContent;
        int count = 0;
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            socket = new Socket(host, port);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("clientSocket started: " + stringNowTime());

            while (((inputContent = reader.readLine()) != null) && count < 2) {
                inputContent = stringNowTime() + ": 第" + count + "条消息: " + inputContent + "\n";
                // 将消息发送给服务端
                writer.write(inputContent);
                writer.flush();
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                socket.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String stringNowTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    public static void main(String[] args) {
        BIOClient client = new BIOClient();
        client.initBioClient("127.0.0.1", 9898);
    }

}