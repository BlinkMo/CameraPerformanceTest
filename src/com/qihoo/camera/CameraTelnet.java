package com.qihoo.camera;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.telnet.TelnetClient;

public class CameraTelnet {

	private String telnetIP;
	private int telnetPort;
	private String telnetUserName;
	private String telnetPassword;
	private String cameraType;

	private TelnetClient cameraTelnet = new TelnetClient();
    private InputStream cameraInputStream;
    private PrintStream cameraOutputStream;
    private String prompt = "#";

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public CameraTelnet(String ip , int port , String type)
	{
		this.telnetIP = ip;
		this.telnetPort = port;
		this.cameraType = type;

		if(cameraType.equals("D503")==true || cameraType.equals("D600")==true
				|| cameraType.equals("D606")==true || cameraType.equals("D302")==true )
		{
			this.telnetUserName = "";//此处密码不公开信息！
			this.telnetPassword = "";
		}
		else if(cameraType.equals("D404")==true)
		{
			this.telnetUserName = "";
			this.telnetPassword = "";
		}
		else
		{
			this.telnetUserName = "";//个别情况下需要无密码
			this.telnetPassword = "NONE";
		}

		try {
			cameraTelnet.connect(telnetIP, telnetPort);
			cameraInputStream = cameraTelnet.getInputStream();
			cameraOutputStream = new PrintStream(cameraTelnet.getOutputStream());
	        loginTelnet();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public void loginTelnet()
	{
		readUntil("login:");
        write(telnetUserName);
        if(telnetPassword.equals("NONE")==false)   //����������
        {
        	readUntil("Password:");
        	write(telnetPassword);
        }
        readUntil(prompt);
        System.out.println(df.format(new Date()) + " LOGIN OVER ");
	}

    //д����
    public void write(String value) {
        try {
        	cameraOutputStream.println(value);
        	cameraOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //readUntil
    public String readUntil(String pattern)
    {
        try {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            char ch = (char) cameraInputStream.read();
            while (true)
            {
                sb.append(ch);
                if (ch == lastChar)
                {
                    if (sb.toString().endsWith(pattern))
                    {
                        return sb.toString();
                    }
                }
                ch = (char) cameraInputStream.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //�ر�����
    public void disconnect() {
        try {
        	cameraTelnet.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //��Ŀ�귢�������ַ���
    public String sendCommand(String command) {
        try {
            write(command);
            return readUntil(prompt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //���������ȡtopֵ
    public String sendCommandGroup()
    {
        try {
            sendCommand("echo 1 > /proc/sys/vm/drop_caches");
            sendCommand("top -b -n 2 > /var/log/testMEM.log");
            String commandSimple = sendCommand("cat /var/log/testMEM.log");
            sendCommand("rm -f /var/log/testMem.log");
            return commandSimple;

        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
    }


}
