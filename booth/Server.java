import java.lang.*;
import java.io.*;  
import java.net.*;  
public class Server
{  
	Socket s;
	DataInputStream dis; 
    DataOutputStream diso;
    ServerSocket ss;
   
    int number1,number2,bits;
	void establishConnection()
	{
		try
		{
		ss= new ServerSocket(12345);
		s = ss.accept();
		dis=new DataInputStream(s.getInputStream());
     	diso=new DataOutputStream(s.getOutputStream());
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	int getData()
	{
		try{
		String num1,num2,bit;
		num1=(String)dis.readUTF();
		num2=(String)dis.readUTF();
		bit=(String)dis.readUTF();
		int n1,n2,b;
		n1=Integer.parseInt(num1);
		n2=Integer.parseInt(num2);
		b=Integer.parseInt(bit);
		number1=n1;
		number2=n2;
		bits=b;
		return b;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return bits;
	}

	String perform (int num1, int num2)
	{
		
		String m=Integer.toBinaryString(num1);
		String r=Integer.toBinaryString(num2);
		String mdash=Integer.toBinaryString((-num1));
		System.out.println(m);
		System.out.println(r);
		System.out.println(mdash);
		System.out.println();
		while(m.length()<8)
		{
			m="0"+m;
		}
		while(r.length()<8)
		{
			r="0"+r;
		}
		while(mdash.length()<8)
		{
			mdash="0"+mdash;
		}
		if(m.length()>8)
			m=m.substring(m.length()-8);
		if(r.length()>8)
			r=r.substring(r.length()-8);
		if(mdash.length()>8)
			mdash=mdash.substring(mdash.length()-8);
		System.out.println(m);
		System.out.println(r);
		System.out.println(mdash);
		System.out.println();
		m=m+"000000000";
		mdash=mdash+"000000000";
		r="00000000"+r+"0";
		int cnt=8;
		System.out.println(m);
		System.out.println(r);
		System.out.println();
		
		while(cnt>0)
		{
			switch(r.substring(r.length()-2))
			{
				case "10":
					r=add(r,mdash);
					break;
				case "01":
					r=add(r,m);
					break;
			}
			r=r.charAt(0)+r.substring(0,r.length()-1);
			cnt--;
			System.out.println(r);
		}
		return r.substring(1, r.length()-1);
	}
	String add(String r, String m)
	{
		//System.out.println("IN ADD"+r.length()+r.charAt(r.length()-1));
		String res="";
		int carry=0;
		for(int j=16;j>=0;j--)
		{
			int temp=Integer.parseInt(String.valueOf((r.charAt(j))))+Integer.parseInt(String.valueOf(m.charAt(j)))+carry;
			res=temp%2+res;
			carry=temp/2;
		}
		return res;
	}
	public static void main(String args[])
	{
		Server server = new Server();
		server.establishConnection();
		int b= server.getData();
		String res=server.perform(server.number1, server.number2);
		System.out.println(res);
		int ans;
		if(res.charAt(0)=='1')
		{
			ans=-1*(32768-Integer.parseInt(res, 2));
			System.out.println("-"+(32768-Integer.parseInt(res, 2)));
		}
		else 
		{
			ans=Integer.parseInt(res, 2);
			System.out.println(Integer.parseInt(res, 2));
		}
		
		System.out.println(ans);
		try{
			server.diso.writeUTF(Integer.toString(ans));
			server.diso.flush();
			server.diso.close();
			server.ss.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
			
	}
}