import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.xml.bind.DatatypeConverter;

class ServerEncrypt extends Frame implements ActionListener
{
	ServerSocket ss ;
	Socket s;
	String FKey;
	DataInputStream dis ;
	DataOutputStream dio;
	String Fkey=new String();
	String encInp="",dencInp="";
	char mat[][]=new char[5][5];
	String type,message,d_or_k;
	public static Frame f= new Frame();
	byte[] e_c_d = new byte[10000000];
	Label l_c1,l_c2;
	String d_c,e_c;
	Button b_c;
		
	/********************DES****************************/
	public byte[] decryption(byte[] msg)
	{	
		byte[] decrypted = new byte[10000000];
		try
		{
			byte[] keyBytes = "bc1234567890azertyuiopqs".getBytes("ASCII");
			DESedeKeySpec keySpec = new DESedeKeySpec(keyBytes);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
			SecretKey key = factory.generateSecret(keySpec);
			//byte[] text = msg.getBytes("ASCII");
	
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.DECRYPT_MODE, key);
			decrypted = cipher.doFinal(msg);
		}
		catch(Exception e){}
		return decrypted;
	}
	
	public byte[] encryption(String msg)
	{
		byte[] encrypted=new byte[10000000];
		try
		{
			byte[] keyBytes = "bc1234567890azertyuiopqs".getBytes("ASCII");
			DESedeKeySpec keySpec = new DESedeKeySpec(keyBytes);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
			SecretKey key = factory.generateSecret(keySpec);
			byte[] text = msg.getBytes("ASCII");
	

			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encrypted = cipher.doFinal(text);
			
			System.out.println(encrypted);
		}
		catch(Exception e){}
		return encrypted;
	}
	/********************DES****************************/
	
	/********************RAILFENCE****************************/
	public String  encryption(String plainText, int depth)
	{	
		  int r=depth,len=plainText.length();
		  int c=0;
		  if(len%depth == 0)
		  {
		  	c=len/depth;
		  }
		  else
		  {
		  	c = len/depth +1;	
		  }
		  char mat[][]=new char[r][c];
		  int k=0;
		   
		  String cipherText="";
		   
		  for(int i=0;i< c;i++)
		  {
		   for(int j=0;j< r;j++)
		   {
		    if(k!=len)
		     mat[j][i]=plainText.charAt(k++);
		    else
		     mat[j][i]='~';
		   }
		  }
		  for(int i=0;i< r;i++)
		  {
		   for(int j=0;j< c;j++)
		   {
		   //if(mat[i][j]!='~')
		    cipherText+=mat[i][j];
		   }
		  }
		  return cipherText;
	}
	
	public String  decryption(String cipherText, int depth)
	{
			int r=depth,len=cipherText.length();
			int c=0;

			 if(len%depth == 0)
			  {
			  	c=len/depth;
			  }
			  else
			  {
			  	c = len/depth +1;	
			  }

			  char mat[][]=new char[r][c];
			  int k=0;
			   
			  String plainText="";
			   
			   
			  for(int i=0;i< r;i++)
			  {
			   for(int j=0;j< c;j++)
			   {
			    mat[i][j]=cipherText.charAt(k++);
			    
			   }
			  }
			  for(int i=0;i< c;i++)
			  {
			   for(int j=0;j< r;j++)
			   {
			    plainText+=mat[j][i];
			   }
			  }
			   
			  return plainText;
			 }
	
	/********************RAILFENCE****************************/
	
	/********************PLAYFAIR****************************/
	public String encryption(String input,String key,String type)
	{
		repKey(key);
		formMatrix();
		String newInp=divide(input);
		int i1=0,i2=0,j1=0,j2=0;
		for(int k=0;k<newInp.length()-1;k++)
		{
			for(int i=0;i<5;i++)
			{
				for(int j=0;j<5;j++)
				{
					if(mat[i][j]==newInp.charAt(k))
					{
						i1=i;
						j1=j;
					}
				}
			}
		
			k++;
			for(int i=0;i<5;i++)
			{
				for(int j=0;j<5;j++)
				{
					if(mat[i][j]==newInp.charAt(k))
					{
						i2=i;
						j2=j;
					}
				}
			}
			if(i1==i2)
			{
				//System.out.println("Same row");
				sameRow(k-1,i1,j1,j2);
			}
			else if(j1==j2)
			{
				//System.out.println("Same Column");
				sameColumn(k-1,j1,i1,i2);
			}
			else
			{
				//System.out.println("Rect");
				rect(k-1,i1,j1,i2,j2);	
			}
		
		}
		
		return encInp;
	}
	
	public boolean rep(char c)
	{
		for(int i=0;i<Fkey.length();i++)
		{
			if(c==Fkey.charAt(i))
			{
				return false;
			}
		}
		return true;
	}
	public void formMatrix()
	{
		
		String alpha="abcdefghiklmnopqrstuvwxyz";
		int k=Fkey.length();
		int l=0;
		int m=0;
		for(int i=0;i<5;i++)
		{
			for(int j=0;j<5;j++)
			{
				if(l<k)
				{
					mat[i][j]=Fkey.charAt(l);
					l++;
				}
				else
				{
					while(true)
					{
						if(rep(alpha.charAt(m)))
						{
							mat[i][j]=alpha.charAt(m);
							m++;
							break;
						}
						else
						{
							m++;
						}
					}
				}
			}
		}
		
	}
	
	public void repKey(String key)
	{
		String keyN=new String();
		keyN=keyN+key.charAt(0);
		boolean flag=false;
		for(int i=0;i<key.length();i++)
		{
			for(int j=0;j<keyN.length();j++)
			{
				if(key.charAt(i)==keyN.charAt(j))
				{
					flag=true;
				}
				
			}
			if(flag==false)
			{
				keyN=keyN+key.charAt(i);
			}
			flag=false;
		}
		Fkey=keyN;
	}
	
	public String divide(String input)
	{
		int j=0;
		String newInp=new String();
		newInp=newInp+input.charAt(0);
		for(int i=1;i<=input.length();i++)
		{
			if(i==input.length())
			{
				if(i%2!=0)
					newInp=newInp+'x';
				break;
			}
			if(input.charAt(j)==input.charAt(i))
			{
				newInp=newInp+'x';
			}
			newInp=newInp+input.charAt(i);
			j++;
		}
		System.out.println("\nTest ready to encrypt:");
		for(int i=0;i<newInp.length()-1;i++)
		{
			System.out.print(newInp.charAt(i)+""+newInp.charAt(++i)+"\t");
		}
		return newInp;
		
	}
	public void sameRow(int k,int i,int j1,int j2)
	{
		j1=(j1+1)%5;
		j2=(j2+1)%5;
		encInp=encInp+mat[i][j1]+mat[i][j2];
		
	}
	public void sameColumn(int k,int j,int i1,int i2)
	{
		encInp=encInp+mat[(i1+1)%5][j]+mat[(i2+1)%5][j];
	}
	public void rect(int k,int i1,int j1,int i2,int j2)
	{
		encInp=encInp+mat[i1][j2]+mat[i2][j1];
	}
	
	public String decryption(String input,String key,String type)
	{
		int i1=0,i2=0,j1=0,j2=0;
		for(int k=0;k<input.length();k++)
		{
			for(int i=0;i<5;i++)
			{
				for(int j=0;j<5;j++)
				{
					if(mat[i][j]==input.charAt(k))
					{
						i1=i;
						j1=j;
					}
				}
			}
		
			k++;
			for(int i=0;i<5;i++)
			{
				for(int j=0;j<5;j++)
				{
					if(mat[i][j]==input.charAt(k))
					{
						i2=i;
						j2=j;
					}
				}
			}
			if(i1==i2)
			{
				//System.out.println("Same row");
				if(j1==0)
				{
					j1=5;
				}
				if(j2==0)
				{
					j2=5;
				}
				j1=Math.abs(j1-1)%5;
				j2=Math.abs(j2-1)%5;
				dencInp=dencInp+mat[i1][j1]+mat[i1][j2];
				//sameRow(k-1,i1,j1,j2);
			}
			else if(j1==j2)
			{
				//System.out.println("Same Column");
				//sameColumn(k-1,j1,i1,i2);
				if(i1==0)
				{
					i1=5;
				}
				if(i2==0)
				{
					i2=5;
				}
				dencInp=dencInp+mat[Math.abs(i1-1)%5][j1]+mat[Math.abs(i2-1)%5][j1];
			}
			else
			{
				//System.out.println("Rect");
				dencInp=dencInp+mat[i1][j2]+mat[i2][j1];
				//rect(k-1,i1,j1,i2,j2);	
			}
		
		}
		
		for(int i=0;i<dencInp.length();i++)
		{
			if(dencInp.charAt(i)=='x')
			{
				dencInp=dencInp.substring(0,i)+dencInp.substring(i+1);
			}
		}
		return dencInp;
	}
	
	
	
	/********************PLAYFAIR****************************/
	
	/********************CAESAR****************************/
	public String encryption(String in,String type)
	{
		String output="";
		for(int i=0;i<in.length();i++)
		{
			if((int)in.charAt(i)>=65 && (int)in.charAt(i)<=90)
			{
				output += ecapital(in.charAt(i));
		 	}    	      	
		 	else if((int)in.charAt(i)>=97 && (int)in.charAt(i)<=122)
		 	{
		 		output += esmall(in.charAt(i));
		 	}
		}
		return output;
	}
	
	public static char esmall(char c)
	{
		int x = ((int)c+13) % 127;
		if(x<97 && x!=45)
		{
			x+=97+4;
		}
		if(x>122)
		{
			x-=122;
			x+=96;
		}
		if(x==45)
		{
			x=x-13;
		}
		c = (char)x;
		return c;
	}
		
	public static char ecapital(char c)
	{
		int x = ((int)c+13) % 127;
		if(x<65 && x!=45 && x<97)
		{
			x+=65;
		}
		if(x>90)
		{
			x-=90;
			x+=64;
		}
		if(x==45)
		{
			x=x-13;
		}
		c = (char)(x);
		return c;
	}
	
	public static char dcapital(char c)
	{
		int x;
		x = (((int)c-13) % 127);
			
		if(x<65 && x!=19)
		{
			x+=26;
		}
		if(x==19)
		{
			x=32;
		}
	
		c = (char)x;        	
		return c; 
	}

	public static char dsmall(char c)
	{
		int x;
		x = (((int)c-13) % 127);
		if(x<97 && x!=19)
		{
			x+=26;
		}
		if(x>122)
		{
			x-=122;
		}
		if(x==19)
		{
			x+=13;
		}
		c = (char)x;  
		return c;
	}
	
	public static String decryption(String out,String t)
	{
		String original = "";
		for(int i=0;i<out.length();i++)
		{
			if((int)out.charAt(i)>=65 && (int)out.charAt(i)<=90)
	    		{
	    			original += dcapital(out.charAt(i));
	    		}    	      	
	    		else if((int)out.charAt(i)>=97 && (int)out.charAt(i)<=122)
	    		{
	    			original += dsmall(out.charAt(i));
	    		}
		}
		return original;
	}
	
	
	
	
	
	
	
	/********************CAESAR****************************/
	
	
	public void establishConnection()
	{
		try
		{
		ss= new ServerSocket(8770);
		s = ss.accept();
		dis = new DataInputStream(s.getInputStream());
		dio = new DataOutputStream(s.getOutputStream());  
		}
		catch(Exception e){}
	}
	
	public void display(String title)
	{
		try
		{
			f=new Frame();
			f.setTitle(title);
			f.setSize(300,150);
			f.addWindowListener(new WindowClose());
			f.setLayout(new FlowLayout());
			if(title.equals("DES"))
			{
				l_c1 = new Label(e_c,Label.CENTER);
			}
			l_c1 = new Label(e_c,Label.CENTER);
			b_c = new Button("Decrypt");
			b_c.addActionListener(this);
			b_c.setBounds(150,90,70,40);
		
			f.add(l_c1);
			f.add(b_c);
			f.setVisible(true);
		}
		catch(Exception e){}
	}
	
	public String acceptData()
	{
		try
		{
		
			type = (String)dis.readUTF();
			ServerEncrypt.f.dispose();
			System.out.println(type);
			if(type.equals("PLAYFAIR"))
			{
				message = (String)dis.readUTF();
				d_or_k  = (String)dis.readUTF();
				System.out.println(message);
				System.out.println(d_or_k);
				//encInp = message;
				e_c = encryption(message,d_or_k,"PLAYFAIR");
				d_c = decryption(e_c,d_or_k,"PLAYFAIR");
				display("PLAYFAIR");
			}
			if(type.equals("CAESAR"))
			{
				message = (String)dis.readUTF();
				System.out.println(message);
				e_c = encryption(message,"CAESAR");
				d_c = decryption(e_c,"CAESAR");
				display("CAESAR");
			}
			if(type.equals("DES"))
			{
				message = (String)dis.readUTF();
				System.out.println(message);
				e_c_d =  encryption(message);
				e_c = e_c_d.toString();
				byte[] dc = decryption(e_c_d);
				d_c = new String(dc, "ASCII");
				display("DES");
			}
			if(type.equals("RAILFENCE"))
			{
				message = (String)dis.readUTF();
				d_or_k = (String)dis.readUTF();
				System.out.println(message);
				System.out.println(d_or_k);
				int depth = Integer.parseInt(d_or_k);
				e_c = encryption(message,depth);
				d_c = decryption(e_c,depth);
				display("RAILFENCE");
			}
		}
		catch(Exception e){}
		return type;
	}
	
	
	//ACTION PERFORMED
	public void actionPerformed(ActionEvent ae)
	{
		try
		{
			if(ae.getSource() == b_c)
			{
				l_c2 = new Label(d_c,Label.CENTER);
				f.add(l_c2);
				f.setVisible(true);
			}
		}
		catch(Exception e){}
	}
	
	
	public static void main(String args[])
	{
		ServerEncrypt se = new ServerEncrypt();
		while(true)
		{
		se.establishConnection();
		String type = se.acceptData();		
		}
	}
}

class WindowClose extends WindowAdapter
{
	public void windowClosing(WindowEvent e)
	{
		ServerEncrypt.f.dispose();
		//System.exit(0);
	}
}
