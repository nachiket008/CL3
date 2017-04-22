<%@ page import="java.io.*,java.net.*"%>
<html>
<head>
<title>ODD EVEN SORT</title>

</head>
<body>
<center><h1>ODD EVEN SORT</h1></center>
<br><br>
<center>
<%
try
{
	String str=request.getParameter("numbers");
	String command[]={"/home/nachiket/Cl3/self/oesort/./a.out",str};
	
	ProcessBuilder builder = new ProcessBuilder(command);
	Process proc= builder.start();
	
	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	String line;
	
	while((line=bufferedReader.readLine())!=null)
	{
	
	out.println(line);
	%>
	<br>
	<%
	}
	
        bufferedReader.close();
}
catch(Exception e)
{

out.println(e);
}
%>
</center>
</body>
</html>

