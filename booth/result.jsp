<%@ page import="java.io.*, java.net.*" %>

<html>
<head><title>Result</title>
</head>
<body>
<center><h1>RESULT</h1></center>
<%
try
{
	Socket s = new Socket("localhost",12345);
	String str1 = request.getParameter("number1");
            String str2 = request.getParameter("number2");
	    String b = request.getParameter("bits");

	    
	    DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
	    DataInputStream inSocket = new DataInputStream(s.getInputStream());

	    dout.writeUTF(str1);  
	    dout.writeUTF(str2); 
	    dout.writeUTF(b);  
	    dout.flush();  

	    String strr;
	    
	    try
	    {
		    while(inSocket.available() == 0)
		    {
		    
		    	strr=(String)inSocket.readUTF();
		    	out.print("Result of "+str1+" * "+str2+" is: "+strr);
		    	%>
		    	<form action="client.jsp">
        		<br><br>	
			<input type="submit" name="sub" id="sub" value="Back"/>
		    	<%

		    }
            }
            catch(Exception e){}
            

}
catch(Exception e)
{
out.println(e);
}
%>
</body>
</html>