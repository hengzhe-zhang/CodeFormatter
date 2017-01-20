import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class CodeTab {
	public static void main(String[] args) {
		System.out.println("欢迎使用代码格式化系统");
		Date date=new Date();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日");
		System.out.println("今天是"+simpleDateFormat.format(date));
		int codeMode=-1;
		System.out.println("请输入代码格式化模式:模式一为K&R格式(输入数字1)，模式二为Allman格式(输入数字2)");
		Scanner scanner=new Scanner(System.in);
		codeMode=scanner.nextInt();
		
		System.out.println("请输入代码地址:(格式样例:d://test.c)");
		String inPath=scanner.next();
		String outPath="d://new.c";
		File inFile=new File(inPath);
		File outFile=new File(outPath);
		
		try {
			BufferedReader bufferedReader=new BufferedReader(new FileReader(inFile));
			BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(outFile));
			if(codeMode==1){
				codeFormat1(bufferedReader, bufferedWriter);
			}else if(codeMode==2){
				codeFormat2(bufferedReader, bufferedWriter);
			}
			bufferedWriter.flush();			//强制刷新缓冲区，保证缓冲区内容全部输出
			bufferedReader.close();			//关闭缓冲输入流
			bufferedWriter.close();			//关闭缓冲输出流
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			System.out.println("代码格式化完成,格式化后的代码保存在D盘下的new.c中");
		}
	}

	public static void addTab(StringBuilder stringBuilder,int tabNum) {		//向StringBuilder中添加缩进
		for(int i=0;i<tabNum;i++)
			stringBuilder.insert(0, "	");
	}
	
	private static void codeFormat1(BufferedReader bufferedReader,BufferedWriter bufferedWriter) throws IOException {
		String currentCol="";		//当前行字符串
		int tabNum=0;
		StringBuilder stringBuilder=new StringBuilder();
		while((currentCol=bufferedReader.readLine())!=null){
			int curPos=0;		//行位置标记
			boolean begin=false;			//标记该行代码是否已经开始
			while(curPos<currentCol.length()){
				if(currentCol.charAt(curPos)=='#'){
					while(currentCol.charAt(curPos)!='>'){
						stringBuilder.append(currentCol.charAt(curPos));
						curPos++;
					}
					curPos++;
					bufferedWriter.write(stringBuilder.toString()+">");
					bufferedWriter.newLine();
					stringBuilder.delete(0, stringBuilder.length());
				}else if(currentCol.charAt(curPos)=='}'){
					tabNum--;
					for(int i=0;i<tabNum;i++)				//补括号输出缩进
						bufferedWriter.write("	");
					bufferedWriter.write("}");
					bufferedWriter.newLine();
				}else if(currentCol.charAt(curPos)=='{'){		
					addTab(stringBuilder, tabNum);
					bufferedWriter.write(stringBuilder.toString());
					stringBuilder.delete(0, stringBuilder.length());
					bufferedWriter.newLine();
					for(int i=0;i<tabNum;i++)				//补括号输出缩进
						bufferedWriter.write("	");
					bufferedWriter.write("{");
					bufferedWriter.newLine();
					tabNum++;
				}else if(currentCol.charAt(curPos)==';'){
					if(stringBuilder.indexOf("for")>=0){
						stringBuilder.append(";");
					}else{
						stringBuilder.append(";");
						addTab(stringBuilder, tabNum);
						bufferedWriter.write(stringBuilder.toString());
						bufferedWriter.newLine();
						stringBuilder.delete(0, stringBuilder.length());		//格式化代码输出后及时清理StringBuilder里的内容
					}	
				}else if((currentCol.charAt(curPos)==' '||currentCol.charAt(curPos)=='	')&&!begin){		//不记录代码前的空格
				}else{
					begin=true;
					stringBuilder.append(currentCol.charAt(curPos));
				}
				curPos++;
			}
		}
	}
	
	private static void codeFormat2(BufferedReader bufferedReader, BufferedWriter bufferedWriter) throws IOException {
		String currentCol="";		//当前行字符串
		int tabNum=0;
		StringBuilder stringBuilder=new StringBuilder();
		while((currentCol=bufferedReader.readLine())!=null){
			int curPos=0;		//行位置标记
			boolean begin=false;			//标记该行代码是否已经开始
			while(curPos<currentCol.length()){
				if(currentCol.charAt(curPos)=='#'){
					while(currentCol.charAt(curPos)!='>'){
						stringBuilder.append(currentCol.charAt(curPos));
						curPos++;
					}
					curPos++;
					bufferedWriter.write(stringBuilder.toString()+">");
					bufferedWriter.newLine();
					stringBuilder.delete(0, stringBuilder.length());
				}else if(currentCol.charAt(curPos)=='}'){
					tabNum--;
					for(int i=0;i<tabNum;i++)				//补括号输出缩进
						bufferedWriter.write("	");
					bufferedWriter.write("}");
					bufferedWriter.newLine();
				}else if(currentCol.charAt(curPos)=='{'){		
					addTab(stringBuilder, tabNum);
					bufferedWriter.write(stringBuilder.toString());
					stringBuilder.delete(0, stringBuilder.length());
					bufferedWriter.write("{");
					bufferedWriter.newLine();
					tabNum++;
				}else if(currentCol.charAt(curPos)==';'){
					if(stringBuilder.indexOf("for")>=0){
						stringBuilder.append(";");
					}else{
						stringBuilder.append(";");
						addTab(stringBuilder, tabNum);
						bufferedWriter.write(stringBuilder.toString());
						bufferedWriter.newLine();
						stringBuilder.delete(0, stringBuilder.length());		//格式化代码输出后及时清理StringBuilder里的内容
					}	
				}else if((currentCol.charAt(curPos)==' '||currentCol.charAt(curPos)=='	')&&!begin){		//不记录代码前的空格
				}else{
					begin=true;
					stringBuilder.append(currentCol.charAt(curPos));
				}
				curPos++;
			}
		}
	}
}
