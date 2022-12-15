#include <iostream>
#include <cmath>
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
int main()
{
float fun, x, z, a, c, y, m;
int f;
cout<<"Введите z,a,c,f="<<endl;
z=ncheck();
a=ncheck();
c=ncheck();
f=ncheck();
pd:
cin>>f;
do{
switch(f)
{
    case 1: fun=2*x; break;
case 2: fun=x*x; break;
case 3: fun=x/3; break;
}}
while(f!=1 && f!=2 && f!=3);
if (z>=0) {
x=2*z+1;
cout<<"вычиcление происходило по первой ветви"<<endl;
}
else {
x=log(z*z-z);
cout<<"вычиcление происходило по второй ветви"<<endl;
}
y=pow(sin(f),2)+a*pow(cos(pow(x, 3)), 5)+c*log(pow(x, 0./4));
cout<<"y="<<y<<endl;
return 0;
}