package com.qihoo.camera;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//д�ļ�

public class ContentToTxt {
	public String folderPath;
	public String fileName;
	public String filePath;
	public File folderF;
	public File fileF;

	public ContentToTxt(String folderpath, String filename) throws IOException
	{
		this.folderPath = folderpath;
		this.fileName = filename;
		this.filePath = folderpath + "\\" + filename;
		this.folderF = new File(folderPath);
		this.fileF = new File(filePath);
		fileCreate();
	}

	public void fileCreate() throws IOException
	{

		if(!folderF.exists() && !folderF.isDirectory())
		{
//		    System.out.println("�ļ���folder������  �Ѵ���" + folderPath);
		    folderF.mkdir();
		}
		if (!fileF.exists())
		{
//			System.out.println("�ļ�file������  �Ѵ���" + filePath);
			fileF.createNewFile();
		}

	}

	public String fileRead() throws IOException
	{
		String line,inContent = new String();
		BufferedReader in = new BufferedReader(new FileReader(fileF));

		while ((line = in.readLine())!= null) {
			inContent += line + "\n" ;
        }
        in.close();
        return inContent;
	}

	public void fileWrite(String inContent,String content) throws IOException
	{
		inContent += content;

        BufferedWriter out = new BufferedWriter(new FileWriter(fileF));
        out.write(inContent);
        out.close();
	}

	public void NormalContentToFile(String content) throws IOException
	{
		String inContent = fileRead();
		fileWrite(inContent,content);
	}

	public void FormatContentToFilePath(String content) throws IOException
	{
		String inContent = fileRead();
		SimpleDateFormat df = new SimpleDateFormat("yyyy,MM,dd,HH,mm,ss");
    	String formatTime = df.format(new Date());
    	String mixContent = "[Date.UTC(" + formatTime +",0), " + content +" ],";

    	//Ϊ��ƥ��highcharts��תʱ������Ҫ�·�-1
    	mixContent = mixContent.replace("(2016,05,", "(2016,04,");
		fileWrite(inContent,mixContent);
	}


}
