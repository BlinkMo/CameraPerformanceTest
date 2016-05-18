package com.qihoo.camera;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RomPerformanceTest {
	
	String FOLDER_PATH = "E:\\RomPerformanceData\\";
	public final static String FILENAME_USEDMEM = "USEDMEM.txt";
	public final static String FILENAME_FREEMEM = "FREEMEM.txt";
	public final static String FILENAME_ISCMCPU = "ISCMCPU.txt";
	public final static String FILENAME_BUSYCPU = "BUSYCPU.txt";
	public final static String FILENAME_ISCM_VSZMEM = "ISCM_VSZMEM.txt";
	public final static String FILENAME_LOW_IDLE = "LOW_IDLE.txt";

	public final static String TELNET_IP_1 = "192.168.0.3";
	public final static String TELNET_IP_2 = "192.168.0.13";
	public final static int TELNET_PORT = 23;
	public final static String TELNET_TYPE_D302 = "D302";
	public final static String TELNET_TYPE_D503 = "D503";
	public final static String TELNET_TYPE_D404 = "D404";
	public final static String TELNET_TYPE_D600 = "D600";
	public final static String TELNET_TYPE_D606 = "D606";
	
	public final static int TELNET_REPEATTIMES = 90000;
	public final static int TELNET_SLEEPTIMES = 30000;
	
	public static void dataCreate(String folderPath , RomPerformanceInfo romInfo) throws IOException
	{
		ContentToTxt ctt_usedMem = new ContentToTxt(folderPath,FILENAME_USEDMEM);
		ContentToTxt ctt_freeMem = new ContentToTxt(folderPath,FILENAME_FREEMEM);
		ContentToTxt ctt_iscmCPU = new ContentToTxt(folderPath,FILENAME_ISCMCPU);
		ContentToTxt ctt_busyCPU = new ContentToTxt(folderPath,FILENAME_BUSYCPU);
		ContentToTxt ctt_iscmVSZMem = new ContentToTxt(folderPath,FILENAME_ISCM_VSZMEM);
//		ContentToTxt ctt_lowIdle = new ContentToTxt(folderPath,FILENAME_LOW_IDLE);
		
		ctt_usedMem.FormatContentToFilePath(romInfo.usedMem);
		ctt_freeMem.FormatContentToFilePath(romInfo.freeMem);
		ctt_iscmCPU.FormatContentToFilePath(romInfo.iscmCPU);
		ctt_busyCPU.FormatContentToFilePath(romInfo.busyCPU);
		ctt_iscmVSZMem.FormatContentToFilePath(romInfo.iscmVSZMem);

	}
	
	
	public void doCameraPerformanceTest(String ip , int port , String type) throws IOException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
    	String formatTime = sdf.format(new Date());
    	String folderPath = FOLDER_PATH + formatTime + "-" + type + "-" + ip;
    	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		RomPerformanceInfo rominfo = new RomPerformanceInfo();
		//µÇÂ¼
    	CameraTelnet ct = new CameraTelnet(ip , port , type);
    	
    	//ÖØ¸´·¢Ö¸Áî
    	for(int i=0;i< TELNET_REPEATTIMES; i++)
    	{	
            System.out.println("------------------------------------"); 
    		System.out.println(type +" Start for No."+i+" : " + df.format(new Date()));
    		
    		String topAll = ct.sendCommandGroup();
    		if(type.equals("D302")==true || type.equals("D600")==true )
    		{
    			rominfo.setInfoType(0);
    		}
    		rominfo.MemSplit(topAll);
        	dataCreate(folderPath, rominfo);
    		
    		try {
    			if(i == TELNET_REPEATTIMES-1) break;
				new Thread();
				Thread.sleep(TELNET_SLEEPTIMES);
        	} 
    		catch (InterruptedException e)
    		{
        		e.printStackTrace();
        	}
    	}
    	
    	ct.disconnect();

	}
	public static void main(String[] args)
	{
//		RomPerformanceThread rpt_600 = new RomPerformanceThread(TELNET_IP_1,TELNET_PORT,TELNET_TYPE_D600);
//		rpt_600.start();
		RomPerformanceThread rpt_606 = new RomPerformanceThread(TELNET_IP_1,TELNET_PORT,TELNET_TYPE_D606);
		rpt_606.start();
//		RomPerformanceThread rpt_503 = new RomPerformanceThread(TELNET_IP_1,TELNET_PORT,TELNET_TYPE_D503);
//		rpt_503.start();
	}

}
