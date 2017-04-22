#include<iostream>
#include<omp.h>
#include<stdlib.h>
using namespace std;


void swap(int a,int b,int* arr)
{
	int temp;
	temp=arr[a];
	arr[a]=arr[b];
	arr[b]=temp;
}
	

int partition(int *arr, int low, int high)
{
	int i,j,pivot;
	i=low-1;
	
	pivot=arr[high];
	
	for(int j=low;j<=high-1;j++)
	{
		if(arr[j]<=pivot)
		{
			i++;
			swap(i,j,arr);
		}
		
	}
	swap(i+1,high,arr);
	return (i+1);
}
	
void quicksort(int *arr, int low, int high)
{
	if(low<high)
	{
		int pos=partition(arr,low,high);
		#pragma omp parallel sections
		{
			#pragma omp section
			{
				quicksort(arr,low,pos-1);
			}
			
			#pragma omp section
			{
				quicksort(arr,pos+1,high);
			}
		}
	}
}

int main(int arg, char **argv)
{
	int arr[arg-1];
	for(int i=1;i<arg;i++)
		arr[i-1]=atoi(argv[i]);
		cout<<"Unsorted array";
	for(int i=0;i<arg-1;i++)
		cout<<arr[i]<<" ";
		
	quicksort(arr,0,arg-2);
	cout<<"Sorted array";
	for(int i=0;i<arg-1;i++)
		cout<<arr[i]<<" ";
		
	return 0;
}
