import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
class Encrypt extends Frame implements ActionListener
{
	Button playfair,railfence,des,caesar,exit;
	Label heading;
	Frame play,Rail,Des,Caesar;
	Button submit_play,submit_rail,submit_des,submit_caesar;
	String m_play,k_play,m_des,m_rail,depth_rail,m_caesar;
	TextField message_play,key_play,message_des,message_rail,dep_rail,message_caesar;
	Socket s;
	DataInputStream inSocket ;
	DataOutputStream dout ;
	
	
	public void paint(Graphics g)
	{
		Font font = new Font("Times New Roman",Font.PLAIN,20);
		g.setFont(font);
		g.drawString("Choose the encryption option from the following",45,100);
	}
	//CONSTRUCTOR
	Encrypt()
	{
		this.setLayout(null);
		
		playfair = new Button("PlayFair");
		des = new Button("DES");
		railfence = new Button("RailFence");
		caesar = new Button("Caesar");
		exit = new Button("Exit");
		heading = new Label("Encryption Techniques",Label.CENTER);
		
		playfair.setBounds(225,150,70,40);
		des.setBounds(225,200,70,40);
		railfence.setBounds(225,250,70,40);
		caesar.setBounds(225,300,70,40);
		exit.setBounds(225,350,70,40);
		
		this.add(heading);
		this.add(playfair);
		this.add(des);
		this.add(railfence);
		this.add(caesar);
		this.add(exit);
		
		playfair.addActionListener(this);
		des.addActionListener(this);
		caesar.addActionListener(this);
		railfence.addActionListener(this);
		exit.addActionListener(this);
		
	}	
	public void framePlayfair()
	{
		System.out.println("PLAYFAIR");
		play = new Frame("Playfair");
		play.setLayout(new FlowLayout());
		play.setSize(300,290);
		submit_play = new Button("Submit");
		submit_play.addActionListener(this);
		submit_play.setBounds(125,200,70,40);
		message_play = new TextField(30);
		key_play = new TextField(30);
		Label msg = new Label("Enter message",Label.LEFT);
		Label k = new Label("Enter key",Label.LEFT);
		play.add(msg);
		play.add(message_play);
		play.add(k);
		play.add(key_play);
		play.add(submit_play);
		
		play.setVisible(true);
	}
	
	public void frameRailfence()
	{
		System.out.println("RAILFENCE");
		Rail = new Frame("RailFence");
		Rail.setLayout(new FlowLayout());
		Rail.setSize(300,290);
		submit_rail = new Button("Submit");
		submit_rail.addActionListener(this);
		submit_rail.setBounds(125,200,70,40);
		message_rail = new TextField(30);
		dep_rail = new TextField(30);
		Label msg = new Label("Enter message",Label.LEFT);
		Label depth = new Label("Enter depth",Label.LEFT);
		Rail.add(msg);
		Rail.add(message_rail);
		Rail.add(depth);
		Rail.add(dep_rail);
		Rail.add(submit_rail);
		
		Rail.setVisible(true);
		//play.addWindowListener(new WindowClose());
	}
	
	public void frameDES()
	{
		System.out.println("DES");
		Des = new Frame("DES");
		Des.setLayout(new FlowLayout());
		Des.setSize(300,150);
		submit_des = new Button("Submit");
		submit_des.addActionListener(this);
		submit_des.setBounds(125,100,70,40);
		message_des = new TextField(30);
		Label msg = new Label("Enter message",Label.LEFT);

		Des.add(msg);
		Des.add(message_des);
		Des.add(submit_des);
		
		Des.setVisible(true);
		//play.addWindowListener(new WindowClose());
		
	}
	
	public void frameCaesar()
	{
		System.out.println("CAESAR");
		Caesar = new Frame("CAESAR");
		Caesar.setLayout(new FlowLayout());
		Caesar.setSize(300,150);
		submit_caesar = new Button("Submit");
		submit_caesar.addActionListener(this);
		submit_caesar.setBounds(125,100,70,40);
		message_caesar = new TextField(30);
		Label msg = new Label("Enter message",Label.LEFT);
		Caesar.add(msg);
		Caesar.add(message_caesar);
		Caesar.add(submit_caesar);
		
		Caesar.setVisible(true);
		
	}
	
	//ACTION PERFORMED
	public void actionPerformed(ActionEvent ae)
	{
		try
		{
		if(ae.getSource() == exit)
		{
			System.exit(0);
		}
		if(ae.getSource() == playfair) 
		{
			framePlayfair();
		}
		
		if(ae.getSource() == railfence) 
		{
			frameRailfence();
		}
		
		if(ae.getSource() == des) 
		{
			frameDES();
		}	
		
		if(ae.getSource() == caesar) 
		{
			frameCaesar();
		}
		if(ae.getSource() == submit_play)
		{
			m_play = message_play.getText();
			k_play= key_play.getText();
		
			dout.writeUTF("PLAYFAIR");
			dout.writeUTF(m_play);
			dout.writeUTF(k_play);
			play.dispose();
		}
		if(ae.getSource() == submit_rail)
		{
			m_rail = message_rail.getText();
			depth_rail = dep_rail.getText();
			
			dout.writeUTF("RAILFENCE");
			dout.writeUTF(m_rail);
			dout.writeUTF(depth_rail);
			Rail.dispose();
		}
		if(ae.getSource() == submit_caesar)
		{
			m_caesar = message_caesar.getText();
			dout.writeUTF("CAESAR");
			dout.writeUTF(m_caesar);
			Caesar.dispose();
		}
		if(ae.getSource() == submit_des)
		{
			m_des = message_des.getText();
			dout.writeUTF("DES");
			dout.writeUTF(m_des);
			Des.dispose();
		}
		}
		catch(Exception e){}
		
	}
	
	public void establishConnection()
	{
		try
		{
			s = new Socket("localhost",8770);
			inSocket = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());   
		}
		catch(Exception e){}
	}
	
	public static void main(String args[])
	{
		Encrypt f = new Encrypt();
		f.establishConnection();
		f.setTitle("Encryption");
		f.setSize(550,500);
		f.setVisible(true);
		f.addWindowListener(new WindowClose());
	}
}


class WindowClose extends WindowAdapter
{
	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
	}
}
