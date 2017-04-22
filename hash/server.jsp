<%@ page import="java.io.*,java.net.*,java.security.*,java.math.*"%>
<html>
<head>
<title>Server</title>
<body>
<center>
<h1>Integrity Check</h1>
<%
try
{
 	ServerSocket server = new ServerSocket(8888);
 	Socket s= server.accept();
 	DataOutputStream dout = new DataOutputStream(s.getOutputStream());  
	DataInputStream din = new DataInputStream(s.getInputStream());
	String str,strmd5,strsha1;
	str=(String)din.readUTF();
	strmd5=(String)din.readUTF();
	strsha1=(String)din.readUTF();
	out.println("DATA RECIEVED IS :");
	%>
	<br>
	<%
	out.println("String :"+str);
	%>
	<br>
	<%
	out.println("MD5 :"+strmd5);
	%>
	<br>
	<%
	out.println("SHA-1 :"+strsha1);
	%>
	<br><br><br>
	<%
	String strmd5calc,strsha1calc;
	MessageDigest md5 = MessageDigest.getInstance("MD5");
	md5.update(str.getBytes(),0,str.length());
	
	strmd5calc=new BigInteger(1,md5.digest()).toString(16);
	
	
	MessageDigest  sha= MessageDigest.getInstance("SHA-1");
	sha.update(str.getBytes(),0,str.length());
	
	strsha1calc=new BigInteger(1,sha.digest()).toString(16);
	out.println("DATA CALCULATED IS :");
	%>
	<br>
	<%
	out.println("String :"+str);
	%>
	<br>
	<%
	out.println("MD5 :"+strmd5calc);
	%>
	<br>
	<%
	out.println("SHA-1 :"+strsha1calc);
	%>
	<br><br><br>
	<%
	if(strmd5.equals(strmd5calc) && strsha1.equals(strsha1calc))
	out.println("Integrity Maintained");
	else
	out.println("Data Tampered");
	s.close();
	server.close();
	
}
catch(Exception e)
{out.println(e);}
%>
</center>
</body>
<head>
</html>
