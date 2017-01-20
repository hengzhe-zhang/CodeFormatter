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
		System.out.println("��ӭʹ�ô����ʽ��ϵͳ");
		Date date=new Date();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy��MM��dd��");
		System.out.println("������"+simpleDateFormat.format(date));
		int codeMode=-1;
		System.out.println("����������ʽ��ģʽ:ģʽһΪK&R��ʽ(��������1)��ģʽ��ΪAllman��ʽ(��������2)");
		Scanner scanner=new Scanner(System.in);
		codeMode=scanner.nextInt();
		
		System.out.println("����������ַ:(��ʽ����:d://test.c)");
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
			bufferedWriter.flush();			//ǿ��ˢ�»���������֤����������ȫ�����
			bufferedReader.close();			//�رջ���������
			bufferedWriter.close();			//�رջ��������
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			System.out.println("�����ʽ�����,��ʽ����Ĵ��뱣����D���µ�new.c��");
		}
	}

	public static void addTab(StringBuilder stringBuilder,int tabNum) {		//��StringBuilder���������
		for(int i=0;i<tabNum;i++)
			stringBuilder.insert(0, "	");
	}
	
	private static void codeFormat1(BufferedReader bufferedReader,BufferedWriter bufferedWriter) throws IOException {
		String currentCol="";		//��ǰ���ַ���
		int tabNum=0;
		StringBuilder stringBuilder=new StringBuilder();
		while((currentCol=bufferedReader.readLine())!=null){
			int curPos=0;		//��λ�ñ��
			boolean begin=false;			//��Ǹ��д����Ƿ��Ѿ���ʼ
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
					for(int i=0;i<tabNum;i++)				//�������������
						bufferedWriter.write("	");
					bufferedWriter.write("}");
					bufferedWriter.newLine();
				}else if(currentCol.charAt(curPos)=='{'){		
					addTab(stringBuilder, tabNum);
					bufferedWriter.write(stringBuilder.toString());
					stringBuilder.delete(0, stringBuilder.length());
					bufferedWriter.newLine();
					for(int i=0;i<tabNum;i++)				//�������������
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
						stringBuilder.delete(0, stringBuilder.length());		//��ʽ�����������ʱ����StringBuilder�������
					}	
				}else if((currentCol.charAt(curPos)==' '||currentCol.charAt(curPos)=='	')&&!begin){		//����¼����ǰ�Ŀո�
				}else{
					begin=true;
					stringBuilder.append(currentCol.charAt(curPos));
				}
				curPos++;
			}
		}
	}
	
	private static void codeFormat2(BufferedReader bufferedReader, BufferedWriter bufferedWriter) throws IOException {
		String currentCol="";		//��ǰ���ַ���
		int tabNum=0;
		StringBuilder stringBuilder=new StringBuilder();
		while((currentCol=bufferedReader.readLine())!=null){
			int curPos=0;		//��λ�ñ��
			boolean begin=false;			//��Ǹ��д����Ƿ��Ѿ���ʼ
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
					for(int i=0;i<tabNum;i++)				//�������������
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
						stringBuilder.delete(0, stringBuilder.length());		//��ʽ�����������ʱ����StringBuilder�������
					}	
				}else if((currentCol.charAt(curPos)==' '||currentCol.charAt(curPos)=='	')&&!begin){		//����¼����ǰ�Ŀո�
				}else{
					begin=true;
					stringBuilder.append(currentCol.charAt(curPos));
				}
				curPos++;
			}
		}
	}
}
