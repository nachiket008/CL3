import json

x=[1,2,3,4,5,6,7,8,9]
n=8
global flag
def Queen(k):
	global flag
	for i in range(1,n+1):
		if Place(k,i)==True:
			x[k]=i
			if(k==n):
				if(flag==0):
					print("\nSolutions that satisfy the given condition are:\n")
				print x[1:n+1]
				flag=1
			else:
				Queen(k+1)
def Place(k,i):
	for j in range(1,k):
		if(x[j]==i or (abs(x[j]-i)==abs(j-k))):
			return False
	return True
with open('input.json') as f:
	data=json.load(f)

if data['start']<1 or data['start']>8:
	print "Invalid json input"
	exit()

x[1]=data['start']
print "The first queen is placed at position",str(x[1])
flag=0
Queen(2)
