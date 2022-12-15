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
{float x, y, z, f, z1;
const float e=2.72;
cout <<"Введите x,y,z,f"<<endl;
x=ncheck();
y=ncheck();
z=ncheck();
f=ncheck();
if(y<=0){cout<<"логарифм невозможно вычислить";
return 0;}
cout<<"z1=";
z1=(exp(abs(x-y)))*pow(abs(x-y), x+y)/((atan(x)+atan(z)))+pow((pow (x, 6))+pow(log(y),2),1./3);
cout << z1;
return 0;
}