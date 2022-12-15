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
double x, y, z;
cout<<"Введите z=(ecли z>=0, то вычисление произойдет по 1-ой ветви,\nиначе - по второй ветви\n";
z=ncheck();
if (z>=0) {
x=2*z+1;
cout<<"вычиcление происходило по первой ветви"<<endl;
}
else {
x=log(z*z-z);
cout<<"вычиcление происходило по второй ветви"<<endl;
}
if(x<=0)
{
cout<<"ошибка";
return 0;
}
y=pow(sin(x),2)+pow(cos(pow(x, 3)), 5)+log(pow(x, 0./4));
cout<<"y="<<y<<endl;
return 0;}