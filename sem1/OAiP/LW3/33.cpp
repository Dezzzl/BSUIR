    #include <iostream>
    #include<math.h>
    using namespace std;
    double ncheck(){
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
    double f(double x){
    return pow(2.7182818284590, 2*x);
    }
    double summ(double x, int n){
double rec=1;
double sum=1;
for(int k=1; k<=n; k++){
    rec*=2*x/k;
    sum+=rec; 
  
}
return sum;
    }
    int main()
    {
    double a, b , h, eps;
    eps=0.001;
    cout<<"Введите а(начальное знач.), b(конечное знач), h(шаг).\n";
    a=ncheck();
    b=ncheck();
    h=ncheck();
    cout<<"номер|"<< "значение f(a)|"<<"значение суммы|"<<"модуль разности суммы и f(a)|"<<"кол-во разложений|\n";
    int i=1;
    for(a; a<=b; a+=h, i++)
    {
int n=2;
   while(abs(summ(a, n)-f(a))>eps){
    n++;
   }
    cout<<"  "<<i<<"     "<<f(a)<<"        "<<summ(a, n)<<"               "<<abs(summ(a, n)-f(a))<<"                "<<n<<"\n";
        }
    }