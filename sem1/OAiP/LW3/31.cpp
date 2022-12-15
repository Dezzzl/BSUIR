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
float f(float x){
    const double PI=3.14;
    const double e=2.7182818284590;
    return pow(e, PI*x)*sin(PI*x);
    }
int main(){
int i;
float a, b, h, max, min;
cout<<"Введите a,b,h"<<endl;
a=ncheck();
b=ncheck();
h=ncheck();
max=min=f(a);
for(i=1, a; a<=b; i++, a+=h)
{if(max<f(a))max=f(a);
if(min>f(a))min=f(a);
cout<<i<<" "<<a<<" "<<f(a)<<endl;
}
cout<<"min="<<min<<endl<<"max="<<max;
return 0;
}