#include<pthread.h>
#include<stdio.h>
#include<stdlib.h>

pthread_t philosopher[5];
pthread_mutex_t chopstick[5];

void *func(int n)
{
	while(1)
	{
		printf("The philosopher %d is thinking\n",n);
		
		pthread_mutex_lock(&chopstick[n]);
		pthread_mutex_lock(&chopstick[(n+1)%5]);
		printf("The philosopher %d is eating\n",n);
		sleep(2);
		printf("The philosopher %d has finished eating\n",n);
		pthread_mutex_unlock(&chopstick[n]);
		pthread_mutex_unlock(&chopstick[(n+1)%5]);
		sleep(2);
	}
	return (NULL);
}

int main()
{
	int i;
	
	
	for(i=0;i<5;i++)
	pthread_mutex_init(&chopstick[i],NULL);
	
	for(i=0;i<5;i++)
	pthread_create(&philosopher[i],NULL,(void *)func,(void *)i);
	for(i=0;i<5;i++)
	pthread_join(philosopher[i],NULL);

	for(i=0;i<5;i++)
	pthread_mutex_destroy(&chopstick[i]);



return 0;
}
