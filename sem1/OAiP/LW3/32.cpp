    #include <iostream>
    #include<math.h>
    using namespace std;
double ncheck(){
    double a;
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
    double f(double x){
    return pow(2.7182818284590, 2*x);
    }
    int main()
    {
    double a, b , h,  summ, d, rec;
    int i, k, n;
    cout<<"Введите а, b, h, n\n";
    a=ncheck();
    b=ncheck();
    h=ncheck();
    n=ncheck();
    i=1;
    for(a; a<=b; a+=h, i++)
    {
        summ=1;
        rec=1;
        for(k=1; k<=n;k++)
    {
    rec*=2*a/k;
    summ+=rec;
    } 
    d=abs(summ-f(a));
    cout<<i<<"\t"<<f(a)<<"\t"<<rec<<"\t"<<summ<<"\t"<<d<<"\t\n";
    }
return 0;
    }