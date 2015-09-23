package peertoPeer;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.net.*;
import java.util.Arrays;
import java.io.*;
/**
 * This program will create peer to peer server client chatbox
 * here message will sent coding using excusive or
 * the client and the server both will be able to decrypt the message
 * @author Choudhury Iqbal
 *
 */

public class GUIChatServer extends Applet implements ActionListener, Runnable
{
	//key which is used to encrypt the message
	byte key= (byte)7780;
	DataInputStream  input;
	DataOutputStream output;
	ServerSocket     server;
	
	TextArea  chatLogTA =   new TextArea("",0,0,TextArea.SCROLLBARS_NONE);
	TextField messageTF =   new TextField();
	Button    sendBN    =   new Button("send");
	Label     name      =   new Label("Enter Name: ");
	TextField sendTF    =   new TextField();
	
	//------------------------------------------------------------------------------------------------
	public void init()
	{
		try
		{
		server        = new ServerSocket(8080);
		
		Socket socket = server.accept();
		
		input         = new DataInputStream(socket.getInputStream());
		
		output        = new DataOutputStream(socket.getOutputStream());
		
		}
		catch(IOException x){}
		
		
		
		setLayout(null);
		chatLogTA.setBounds(10, 10, 400, 400);
		messageTF.setBounds(10, 420, 300, 20);
		sendBN.setBounds(320, 420, 70, 20);
		name.setBounds(10,440,100,20);
		sendTF.setBounds(120,440,100,20);
		
		
		add(chatLogTA);
		add(messageTF);
		add(sendBN);
		add(name);
		add(sendTF);
		
		
		sendBN.addActionListener(this);
		Thread t = new Thread(this);
		t.start();
	}
	
	
	
	//-------------------------------------------------------------------------------
	public void actionPerformed(ActionEvent e)
	{	
		try
		{
			sendBN.setLabel(sendTF.getText());	
		    String message = messageTF.getText();
		
		    chatLogTA.append("You: " + " " + messageTF.getText()+ "\n");
		
		//  creating local variables
		    byte[] bytes    =message.getBytes();
			int exclusiveMessage[] = new int[bytes.length];
			int temp;
			
			
			//Encrypting the message using exclusive or.And save them in codedByte array
			int k=0;
		   for(byte b: bytes)
		   {
			    temp=b^key;
		        exclusiveMessage[k]=temp;
		        k++;			
		  }
		
		  String encryptedMessage=Arrays.toString(exclusiveMessage);
		  output.writeUTF(encryptedMessage);		
		  messageTF.setText("");
		}
		catch(IOException x){};
	}
	
	//------------------------------------------------------------------------------------------
	public void run()
	{
		while (true)
		{

			try 
			{
					String message   = input.readUTF();
					//removing the "[" and "]" from substring
					message=message.substring(1, message.length()-1);
					
					//Trimming the array(removing unnecessary space from the String;
					message.trim();
					
					//Splitting the string to make an string array
					String[] messageArray=message.split(",");
					
					//creating int array to save the the message
					int[] retrieveMessage=new int[messageArray.length];
					
					
					//convert the string arry to interger array.also trimming the blank space
					int s=0;
					for(int i=0;i<messageArray.length;i++)
					{
						
						retrieveMessage[s]=Integer.parseInt(messageArray[i].trim());
						s++;
						
					}
					
					byte retriveByte;
					byte retrivedArray[]=new byte[messageArray.length];
					int l=0;
					
				//casting the int to byte and decrypt the message using exclusive or	
				for(int w: retrieveMessage)
				{
					retriveByte=(byte) ((byte)w^key);
					retrivedArray[l]=retriveByte;
					l++;	
					
				}
				//convert the byte to string again
				String str = new String(retrivedArray, "UTF-8");
				chatLogTA.append("Other:" + " "  + str + "\n");
				

			} 
			catch (IOException x) {}
		}

	}
	

}