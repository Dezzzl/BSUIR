#include <iostream>
using namespace std;
float ncheck(){
float a;
cin>>a;
   while(cin.fail() || (cin.peek() != '\n' && cin.peek() != ' '))
   {
     cout<< "Попробуй снова"<<endl;
     cin.clear();
     cin.ignore(256,'\n');
     cin>>a;
   } 
   return a;
}
int main(){
int **a,n, m, i, j, summ, min;
cout<<"Введите  n, m"<<endl;
cin>>n>>m;
if(n<2 || m<2)
{cout<<"недопустимй размер";
return 0;}
a= new int* [n];
for(i=0; i<n; i++)a[i]=new int[m];
for(i=0; i<n; i++){
    for(j=0; j<m; j++)
    {
        cout<<"Введи a["<<i+1<<"]["<<j+1<<"]=";
        a[i][j]=ncheck();
    }
}
int *sum = new int[n];
for(i=0; i<n; i++){
sum[i]=0;
for(j=0; j<m; j++){sum[i]+=a[i][j];}
}
for(int raz=0; raz<n; raz++)
for(i=0; i<n-1; i++) if(sum[i]>sum[i+1]){
int stakan = sum[i];
sum[i]=sum[i+1];
sum[i+1]=stakan;
int *buf = a[i];
a[i]=a[i+1];
a[i+1]=buf;    
}
for(i=0; i<n; i++){
    for(j=0; j<m; j++)
    {
        cout<<"a["<<i+1<<"]["<<j+1<<"]="<<a[i][j]<<" ";
    }
    cout<<"сумма["<<i+1<<"]="<<sum[i]<<endl;
}
delete []sum;
delete []a;
for(i=0; i<n; i++){ delete []a[i];}
return 0;
}
