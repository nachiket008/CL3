<%@ page import="java.io.*,java.net.*,java.security.*,java.math.*"%>
<html>
<head>
<title>
Result
</title>

<body>
<h1>RESULT</h1>
<%
try
{
	String strmd5,strsha1;
	String str= request.getParameter("string");
	Socket s= new Socket("localhost",8888);
	DataInputStream din = new DataInputStream(s.getInputStream());
	DataOutputStream dout = new DataOutputStream(s.getOutputStream()); 
	
	
	MessageDigest md5 = MessageDigest.getInstance("MD5");
	md5.update(str.getBytes(),0,str.length());
	
	strmd5=new BigInteger(1,md5.digest()).toString(16);
	
	
	MessageDigest  sha= MessageDigest.getInstance("SHA-1");
	sha.update(str.getBytes(),0,str.length());
	
	strsha1=new BigInteger(1,sha.digest()).toString(16);
	
	%>
	<br><br>
	<%
	out.println("MD5 value is : "+strmd5);
	%><br><br>
	<%
	out.println("SHA-1 value is : "+strsha1);
	dout.writeUTF(str);
	dout.writeUTF(strmd5);
	dout.writeUTF(strsha1);
	dout.flush();
	dout.close();
	s.close();
	
}
catch(Exception e)
{out.println(e);}
%>

</body>

</head>
</html>
