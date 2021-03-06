package peertoPeer2;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.math.BigInteger;
import java.net.*;
import java.util.Arrays;
import java.util.Random;
import java.io.*;
/**This program will extend the first package peer to peer 
 * added hamming code (error detecting and error correcting code)
 * 
 * @author Choudhury Iqbal
 *
 */

public class GUIChatServer extends Applet implements ActionListener, Runnable
{
	
	DataInputStream  input;
	DataOutputStream output;
	ServerSocket     server;
	
	TextArea   chatLogTA =   new TextArea("",0,0,TextArea.SCROLLBARS_NONE);
	TextField  messageTF =   new TextField();
	Button     sendBN    =   new Button("send");
	Label      name      =   new Label("Enter Name: ");
	TextField  nameTF    =   new TextField();
	
	
	
	//------------------------------------------------------------------------------------------------
	
	
	 
	public void init()
	{
		try
		{
		server        = new ServerSocket(8080);
		
		Socket socket =server.accept();
		
		input         = new DataInputStream(socket.getInputStream());
		
		output        = new DataOutputStream(socket.getOutputStream());
		
		}
		catch(IOException x){}
		
		
		
		setLayout(null);
		chatLogTA.setBounds(10, 10, 400, 400);
		messageTF.setBounds(10, 420, 400, 20);
		
		name.setBounds(10,440,100,20);
		nameTF.setBounds(120,440,100,20);
		
		
		add(chatLogTA);
		add(messageTF);
		
		add(name);
		add(nameTF);
		
		
		messageTF.addActionListener(this);
		Thread t = new Thread(this);
		t.start();
	}
	
	
	
	//-------------------------------------------------------------------------------
	public void actionPerformed(ActionEvent e)
	{	
		try
		{
			
		    String message   = messageTF.getText();
		
		    chatLogTA.append( nameTF.getText() + " " + messageTF.getText()+ "\n");
		    
		    String binary=new BigInteger(message.getBytes()).toString(2);
		  
		
		    binary.trim();
		    String text=codeBinary(binary);
		    
		   output.writeUTF(text);	
		   messageTF.setText("");
		}
		catch(IOException x){};
	}
	

//this code will take a string and return a 12 bit hamming code with parity bit
public  String codeBinary(String binary)
  {
		
	  int k=-1;
	  String text="";
	  binary="1"+binary;
	  for (int i=0;i<binary.length();i+=8){
    	  text+=findCodeBinary(binary.substring(i,i+8));
    	  text.trim();
    	
    	
      }
		return text.trim();
  }

//This code will take subString of a binary code of string and find the hamming code of that letter
  public String findCodeBinary(String substring)
  {

		int intBinary=Integer.parseInt(substring);
		String[] messageArray=new String[8];
	    messageArray=strToStrArray(substring);
	    
	    String[] hammingMessage=new String[12];
		   
		hammingMessage=hmGenerator(messageArray);
		
		
	    for(int i=0;i<12;i++)
	    {
			   if(hammingMessage[i]==null){
				   hammingMessage[i]="0";
			   }
		}
	    
	    int []intHamming   = new int[12];
		intHamming         = strArrayToIntArray(hammingMessage);
        int []finalHamming = new int[12];
		finalHamming       =fillUpParityBit(intHamming);
		String text        =intArrayToStr(finalHamming);
		
		
		
	return text.trim();
}



	//this method convert a int array to a Strin
	public String intArrayToStr(int[] finalHamming) 
	{
		
		String temp="";
		for(int i=0;i<finalHamming.length;i++){
			temp=temp+finalHamming[i];
			
	}
		temp.trim();
		return temp;
	}
	//done


//hammingCode generator take a  8 bit string array, fill up the position and 
//and left the parity bit position alone
//formula: p1=1,3,5,7,9,11---p2=2,3,6,7,10,11---p3=4,5,6,7,12----p4=8,9,10,11,12
	public String[] hmGenerator(String[] messageArray)
	{
		String []temp=new String[12];
		
		int k=0;
		for(int i=1;i<13;i++){
			switch(i){
				case 3:
					temp[i-1]=messageArray[k++];
				
					break;
				case 5:
					temp[i-1]=messageArray[k++];
					break;
				case 6:
					temp[i-1]=messageArray[k++];
					break;
				case 7:
					temp[i-1]=messageArray[k++];
					break;
				case 9:
					temp[i-1]=messageArray[k++];
					break;
				case 10:
					temp[i-1]=messageArray[k++];
					break;
				case 11:
					temp[i-1]=messageArray[k++];
					break;
				case 12: 
					temp[i-1]=messageArray[k++];
					break;
		}
			
		}
		return temp;
		
	}//end



	//this method convet a string to string array
	public String[] strToStrArray(String binary) {
		
		String [] temp=new String[8];
		
		for (int i=0;i<8;i++)
		{
			temp[i]=binary.substring(i,i+1);
		}
		return temp;
	}//end strToStrArray


//convert a string Array to int Array
	public int[] strArrayToIntArray(String[] toBinary) 
	{

		int[] temp=new int[toBinary.length];
		for(int i=0;i<toBinary.length;i++)
		{
			
			temp[i]=Integer.parseInt(toBinary[i].trim());
			
			
		}
		return temp;
	}//end


	//this method place the parity bit
	public int[] fillUpParityBit(int[] tempInt) {
	
		
		if((tempInt[2]+tempInt[4]+tempInt[6]+tempInt[8]+tempInt[10])%2==0){
			tempInt[0]=1;
		}
		if((tempInt[2]+tempInt[5]+tempInt[6]+tempInt[9]+tempInt[10])%2==0){
			tempInt[1]=1;
		}
		if((tempInt[4]+tempInt[5]+tempInt[6]+tempInt[11])%2==0){
			tempInt[3]=1;
		}
		if(tempInt[8]+tempInt[9]+tempInt[10]+tempInt[11]%2==0){
			tempInt[7]=1;
		}
		//generating random error
				Random randomGenerator = new Random();
				int randomInt = randomGenerator.nextInt(12);
				
				if(tempInt[randomInt]==0){
					tempInt[randomInt]=1;
					
				}
				if(tempInt[randomInt]==0){
					tempInt[randomInt]=1;
					
				}
				return tempInt;
	
	}//end




	public void run()
	{
		while (true)
		{

			try 		
			{			
				String recText=input.readUTF();
				String binary=decodeInput(recText);
				chatLogTA.append("Other:" + " "  + binary + "\n");
			} 
			catch (IOException x) {}
		}

	}
	public String decodeInput(String recText)
	{
		String text="";
		for(int i=0;i<recText.length();i+=12)
		{
			text=text+decodedMessage(recText.substring(i,i+12));
		}
		text.trim();
		return text;
		
	}
	public String decodedMessage(String recText) {
		String text="";
		String [] recArray=new String[12];
		
		recArray=strToStrArrayClient(recText);
		
		int recIntArray[] =new int[12];
		recIntArray       =strArrayToIntArray(recArray);
		
		 String message    =Arrays.toString(recIntArray);
	     message          =message.substring(1,message.length()-1);
	     message.trim();
	    String [] messageArray=message.split(",");
	    int[] retriveMessage=new int[messageArray.length];
	     int k=0;
	    for(int i=0;i<messageArray.length;i++)
	    {
		    retriveMessage[k]=Integer.parseInt(messageArray[i].trim());
		  k++;
		
	    }
	    int[] decodeMessage=new int[12];
	    decodeMessage=hammingDecoder(retriveMessage);
	
	   int [] originalMessage=new int[8];
	   originalMessage=removeParity(decodeMessage);

	   int [] binaryArray=new int[7];
	  for (int i=0;i<7;i++){
		  binaryArray[i]=originalMessage[i+1];
	   }
	
	  String binary=intArrayToStr(binaryArray);
	    text = new String(new BigInteger(binary, 2).toByteArray());
		  return text;
	   }
	

	public String[] strToStrArrayClient(String binary) {
		
		String [] temp=new String[12];
		
		for (int i=0;i<12;i++)
		{
			temp[i]=binary.substring(i,i+1);
		}
		return temp;
	}//end

///this method remove parity bit to see the message
	public int[] removeParity(int[] decodeMessage) {
	
		int [] temp=new int[8];
		int k=0;
		for (int i=0;i<12;i++){
			switch(i){
			
			case 2:
				temp[k++]=decodeMessage[i];
				break;
			case 4:
				temp[k++]=decodeMessage[i];
				break;
			case 5:
				temp[k++]=decodeMessage[i];
				break;
			case 6:
				temp[k++]=decodeMessage[i];
				break;
			case 8:
				temp[k++]=decodeMessage[i];
				break;
			case 9:
				temp[k++]=decodeMessage[i];
				break;
			case 10:
				temp[k++]=decodeMessage[i];
				break;
			case 11:
				temp[k++]=decodeMessage[i];
				break;
			
			}
		}
		return temp;
	}

//this is the error correcting code it will check 
//every hamming code for error and correct it if necessary
	public int[] hammingDecoder(int[] tempInt) {
	
			int p1=-1; int p2=-1; int p3=-1;int p4=-1;
			if((tempInt[0]+tempInt[2]+tempInt[4]+tempInt[6]+tempInt[8]+tempInt[10])%2==0){
				p1=1;
			}
			if((tempInt[1]+tempInt[2]+tempInt[5]+tempInt[6]+tempInt[9]+tempInt[10])%2==0){
				p2=2;
			}
			if((tempInt[3]+tempInt[4]+tempInt[5]+tempInt[6]+tempInt[11])%2==0){
				p3=4;
			}
			if(tempInt[7]+tempInt[8]+tempInt[9]+tempInt[10]+tempInt[11]%2==0){
				p4=8;
			}
			int index=p1+p2+p3+p4;
			if(index>0){
				if(tempInt[index-1]==0){
					tempInt[index-1]=1;
					
				}
				else{
					tempInt[index-1]=0;
				}
			}
			return tempInt;
		
	}

	
	

}