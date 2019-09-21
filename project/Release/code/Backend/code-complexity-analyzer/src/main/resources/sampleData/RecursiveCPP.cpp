using namespace std;

int fibonacci(int n){
  
   if (n <= 1)
        return 1;
   else 
       return n*fibonacci(n-1);
}

int main(){
   int num;
   cout<<"Enter a number: ";
   cin>>num;
   cout<<"Factorial of entered number: "<<f(num);
   return 0;
}