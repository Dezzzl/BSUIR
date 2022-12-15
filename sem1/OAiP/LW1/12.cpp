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
double a, z1, z2;
cout<<"Введите a=";
a=ncheck();
if (a==-2){cout<<"на ноль делить нельзя";
return 0;}
else if(a<-2){cout<<"подкоренное выражение меньше 0";
return 0;}
z1=(sqrt(2*a+2*sqrt(a*a-4)))/(sqrt(a*a-4)+a+2);
z2=1/sqrt(a+2);
cout << z1<<endl;
cout << z2 <<endl;
return 0;
}