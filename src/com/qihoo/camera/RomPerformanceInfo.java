package com.qihoo.camera;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RomPerformanceInfo {
	
	public String usedMem;
	public String freeMem;
	public String iscmCPU;
	public String busyCPU;
	public String iscmVSZMem;
	public int infoType = 1;
	
	public void MemSplit(String topAll)
	{
		String[] eachTop = topAll.split("Mem:");

//    	System.out.println(eachTop[2]);
    	
    	String[] usedMemPart = eachTop[2].split("used");
		usedMem = numberSplit(usedMemPart[0]);
		String[] freeMemPart = usedMemPart[1].split("free");
		freeMem = numberSplit(freeMemPart[0]);
		
		String[] busyCPUPartTmp = freeMemPart[1].split("idle");
		String[] busyCPUPart = busyCPUPartTmp[0].split("nic");
		
		String idleCPU = numberSplit(busyCPUPart[1]);
		DecimalFormat df = new DecimalFormat("#.0");
		double DbusyCPU = 100 - Double.valueOf(idleCPU);
		busyCPU = df.format(DbusyCPU);
		 
		System.out.println("usedMem:" + usedMem);
		System.out.println("freeMem:" + freeMem);
		System.out.println("busyCPU:" + busyCPU);
		
		/*
		if(Double.valueOf(busyCPU)>80)
		{
			System.out.println(eachTop[2]);
			try {
				ContentToTxt ctt_lowIdle = new ContentToTxt("E:\\RomPerformanceData\\","LOW_IDLE.txt");
				SimpleDateFormat dff = new SimpleDateFormat("yyyy,MM,dd,HH,mm,ss");
		    	String formatTime = dff.format(new Date());
				ctt_lowIdle.NormalContentToFile(formatTime);
				ctt_lowIdle.NormalContentToFile(eachTop[2]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}*/
		
		String[] iscmCPUPartTmp = eachTop[2].split("scm /var/log/iscm.log");
		String[] iscmCPUPart = iscmCPUPartTmp[0].split("COMMAND");
		
//		System.out.println(iscmCPUPart[1]);
		splitString(iscmCPUPart[1]);
	}
	
	//字符串按空格分割成子串
	private void splitString(String simString)
	{

		String reSimString = simString.replaceAll(" +","@");
		String tmpSimString[] = reSimString.split("@");
		
		int len = tmpSimString.length;
		
		iscmCPU = tmpSimString[len-2];
		iscmVSZMem = tmpSimString[len-infoType-4];
		
		System.out.println("iscmCPU:" + iscmCPU);
		System.out.println("iscmVSZMem:" + iscmVSZMem);
	}

	
	//字符串中取数字,支持小数点
	private String numberSplit(String Mpart) {
    	String simpleString = null;
    	
		Pattern p = Pattern.compile("\\d+(.\\d+)?");
		Matcher m = p.matcher(Mpart);
		  
		while (m.find()) 
		{
		   simpleString = m.group();
		}
		return simpleString;
	}
	
	// 302/600 type=0  404/606/503 type=1
	public void setInfoType(int newType)
	{
		infoType = newType;
	}

}
