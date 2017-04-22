import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
public class MillerRabin extends Frame implements ActionListener
{
	Frame main,error,result;
	Button submit,oke,okr,exit,b;
	TextField k_value,number;
	Label accuracy,num,miller,Result,Invalid;
	public MillerRabin()
	{
		this.setLayout(null);
		main = new Frame("Miller Rabin");
		error = new Frame("Invalid");
		result = new Frame("Result");
		//Buttons
		submit = new Button("Submit");
		oke = new Button("OK");
		okr = new Button("OK");
		exit = new Button("Exit");
		b = new Button("");
		//TextFields
		k_value = new TextField(30);
		number = new TextField(30);
		 
		miller = new Label("MILLER RABIN ALGORITHM",Label.CENTER);
		num = new Label("Enter the number",Label.LEFT);
		accuracy = new Label("Accuracy level",Label.LEFT);
		
		main.setSize(500,350);
		error.setSize(250,110);
		result.setSize(250,110);
		
		miller.setBounds(150,50,200,50);
		main.add(miller);
		num.setBounds(100,100,150,40);
		number.setBounds(300,100,100,40);
		accuracy.setBounds(100,200,150,40);
		k_value.setBounds(300,200,100,40);
		
		submit.setBounds(100,300,70,40);
		exit.setBounds(350,300,70,40);
		b.setBounds(450,300,50,50);
		submit.addActionListener(this);
		exit.addActionListener(this);
		
		main.add(miller);
		main.add(num);
		main.add(number);
		main.add(accuracy);
		main.add(k_value);
		main.add(submit);
		main.add(exit);
		main.add(b);
		
		main.setVisible(true);
		
		
	}
	
	public int pow(int a,int b,int m)
	{
		int res=1;
		
		a=a%m;
		
		for(int i=0;i<b;i++)
		{
			res=res*a;
			res=res%m;
		}
		
		return res;
	}
	
	public boolean miller(int d,int n)
	{
		Random rand= new Random();
		int max=n-1;
		int min=1;
		int a = rand.nextInt((max - min) + 1) + min;
		int x= pow(a,d,n);
		
		if(x==1 || x==n-1)
			return true;
		
		while(d!=n-1)
		{
			x=(x*x)%n;
			d=d*2;
			if(x==1)
				return false;
			if(x==n-1)
				return true;
		}
		return false;

	}
	
	public boolean isPrime(int n,int k)
	{
		if (n <= 1 || n == 4)  
   			return false;
		if (n <= 3)
			 return true;
		int d=n-1;
		while(d%2==0)
			d=d/2;
		for(int i=0;i<k;i++)
			if(miller(d,n)==false)
				return false;
		return true;
	}	
	
	public static void main(String args[])
	{
		MillerRabin m= new MillerRabin();
		
	}
	public void displayerror()
	{
		error.setLayout(null);
	oke.setBounds(80,70,50,30);
	error.add(oke);
	Invalid = new Label("Please enter all the fields...");
	Invalid.setBounds(5,25,250,50);
	
	error.add(Invalid);
	error.add(oke);
	oke.addActionListener(this);
	error.setVisible(true);
	}

	public void displayResult(String status)
	{
		//result.dispose();
		result = new Frame("Result");
		result.setLayout(null);
		result.setSize(250,110);
		okr.setBounds(80,70,50,30);
		result.add(okr);
		Result = new Label(status);
		Result.setBounds(5,25,250,50);
		
		result.add(Result);
		result.add(okr);
		okr.addActionListener(this);
		result.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(ae.getSource()==exit)
		{
			System.exit(0);
		}
		if(ae.getSource()==submit)
		{
			String num,accuracy;
			num=number.getText().toString();
			accuracy=k_value.getText().toString();
			if(num.equals("") || accuracy.equals(""))
			{
				displayerror();
			}
			
			int n,k;
			n=Integer.parseInt(num);
			k=Integer.parseInt(accuracy);
			boolean ans=isPrime(n, k);
			String stat;
			if(ans==true)
			{
				stat="The number "+ n + " is probably prime.";
				
			}
			else
				stat="The number "+ n + " is composite.";
			
			displayResult(stat);
		}
		if(ae.getSource()==okr)
		{
			error.setVisible(false);
			number.setText("");
			k_value.setText("");
		}
		if(ae.getSource() == okr)
		{
			result.setVisible(false);
			number.setText("");
			k_value.setText("");
		}
		
	}
}
