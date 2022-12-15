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
float a, b, z1, z2;
const float PI = 3.1415;
cout<<"Введите число а"<<endl;
a=ncheck();
cout<<"Введите число b"<<endl;
b=ncheck();
z1=pow(cos(PI*a)-cos(PI*b),2)-pow(sin(PI*a)-sin(PI*b),2);
cout<<"z1="<<z1<<endl;
z2=((-4)*pow(sin((PI*a-PI*b)/2),2)*cos(PI*a+PI*b));
cout<<"z2="<<z2<<endl;
return 0;
}