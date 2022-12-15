#include <iostream>
#include <math.h>
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
float max(float c, float d){
    if (c<d) return d;
    else return c;

}
int main()
{
float x, y, z, max1, max2, m, b, d, c;
cout<<"Введите x,y,z"<<endl;
x=ncheck();
y=ncheck();
z=ncheck();
if (x==0, y==0, z==0)
{
cout<<"на 0 делить нельзя"<<endl;
return 0;
}
b=x+y+z;
d=x*y*z;
c=x/(y*z);
max1=max(b, d);
max2=max(d, c);
m=max1/max2;
cout<<"max1="<<max1<<endl;
cout<<"max2="<<max2<<endl;
cout<<"m="<<m<<endl;
return 0;
}