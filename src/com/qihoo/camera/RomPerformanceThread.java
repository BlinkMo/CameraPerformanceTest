package com.qihoo.camera;

import java.io.IOException;

//monkeyœﬂ≥Ã

public class RomPerformanceThread extends Thread{
	
	public String rptIP;
	public int rptPORT;
	public String rptTYPE;
	
	public RomPerformanceThread(String ip ,int port ,String type)
	{
		this.rptIP = ip;
		this.rptPORT = port;
		this.rptTYPE = type;
	}
	
	public void run()
	{
		RomPerformanceTest rpt = new RomPerformanceTest();
		try {
			rpt.doCameraPerformanceTest(rptIP,rptPORT,rptTYPE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
