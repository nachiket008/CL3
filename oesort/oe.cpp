#include<iostream>
#include<string>
#include<omp.h>
#include<stdlib.h>
#include<math.h>
using namespace std;
int arr[100];
void swap(int *a,int *b)
{
	int temp=*a;
	*a=*b;
	*b=temp;
	
}
void oddevensort(int size)
{
	int sort=0,i;
	int odd,even;
	if(size%2==0)
	{
		odd=size-1;
		even=size-1;
	}
	else
	{
		odd=size;
		even=size-1;
	}
	while(!sort)
	{
		sort=1;
		#pragma omp parallel for
		for(i=1;i<odd;i=i+2)
		{
			if(arr[i]>arr[i+1])
			{
				swap(&arr[i],&arr[i+1]);
				sort=0;
			}	
		}	
		
		#pragma omp parallel for
		for(i=0;i<even;i=i+2)
		{
			if(arr[i]>arr[i+1])
			{
				swap(&arr[i],&arr[i+1]);
				sort=0;
			}	
		}	
	}
}
int main(int argc,char **argv)
{
	int i,j,k,x,z,y;
	string ip=argv[1];
	string buffer="";
	int cnt=0;
	int len=ip.length();
	for(int i=0;i<len;i++)
	{
		if(ip[i]!=',')
			buffer=buffer+ip[i];
		else
		{
			arr[cnt++]=atoi(buffer.c_str());
			buffer="";
		}
		if(i==len-1)
		{
			arr[cnt++]=atoi(buffer.c_str());
			buffer="";
		}
	}
	cout<<"The original array is :  ";
	for(i=0;i<cnt;i++)
	{
		cout<<arr[i]<<" ";
	}
	cout<<endl;
	cout<<endl;
	oddevensort(cnt);
cout<<"The sorted array is :  ";
for(i=0;i<cnt;i++)
	{
		cout<<arr[i]<<" ";
	}
	cout<<endl;
	

	return 0;
}

