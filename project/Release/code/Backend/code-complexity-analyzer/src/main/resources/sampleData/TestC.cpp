#include<iostream> 
using namespace std; 
class Person { 
// Data members of person 
public: 
	Person(int x) { cout << "Person::Person(int ) called" << endl; } 
}; 

class Faculty : public Person { 
// data members of Faculty 
public: 
	Faculty(int x):Person(x) { 
	cout<<"Faculty::Faculty(int ) called"<< endl; 
	} 
}; 

class Student : public Person { 
// data members of Student 
public: 
	Student(int x):Person(x) { 
		cout<<"Student::Student(int ) called"<< endl; 
	} 
	
	  for (count = 0; count < prefix && count == 1; ++count) {
            System.out.println(count);
        }

        while (i <= 10 && i == 3) {
            System.out.println(i);
            i++;
        }

        int firstInt = 2;


        for (count = 0; count < prefix; ++count) {
            System.out.println(i);
            --i;
        }

        do {
            System.out.println(i);
            i--;
        } while (i > 1 && i == 10);
}; 

class TA : public Faculty, public Student { 
public: 
	TA(int x):Student(x), Faculty(x) { 
		cout<<"TA::TA(int ) called"<< endl; 
	} 
}; 

int main() { 
	TA ta1(30); 
	
	for ( init; condition; increment ) {
		statement(s);
	}
	
	 if ((time < 10) && (time > 10)) {
            System.out.println("Good morning.");
        } else if (time < 20) {
            System.out.println("Good day.");
        } else {
            System.out.println("Good evening.");
        }

        if (count == 1)
            System.out.println("One");
        if ((count == 2) && (netID != 0))
            System.out.println("Two");
        if ((count == 3) && (netID != 0))
            System.out.println("Three");
        if ((count == 4) && (netID != 0) && (netID != 0) && (netID != 0))
            System.out.println("Four");
		
		        //22

//        if ((time < 10) && (time > 10)) {
//            System.out.println("Good morning.");
//        } else if (time < 20) {
//            System.out.println("Good day.");
//        } else {
//            System.out.println("Good evening.");
//        }
//
//        if (count == 1)
//            System.out.println("One");
//        if ((count == 2) && (netID != 0))
//            System.out.println("Two");
//        if ((count == 3) && (netID != 0))
//            System.out.println("Three");
//        if ((count == 4) && (netID != 0) && (netID != 0) && (netID != 0))
//            System.out.println("Four");
//
//        if(true)
//            System.out.println("Four");


       String ifString = "  if ((time < 10) && (time > 10)) {\n" +
                "            System.out.println(\"Good morning.\");\n" +
                "        } else if (time < 20) {\n" +
                "            System.out.println(\"Good day.\");\n" +
                "        } else {\n" +
                "            System.out.println(\"Good evening.\");\n" +
                "        }\n" +
                "\n" +
                "        if (count == 1)\n" +
                "            System.out.println(\"One\");\n" +
                "        if ((count == 2) && (netID != 0))\n" +
                "            System.out.println(\"Two\");\n" +
                "        if ((count == 3) && (netID != 0))\n" +
                "            System.out.println(\"Three\");\n" +
                "        if ((count == 4) && (netID != 0) && (netID != 0) && (netID != 0))\n" +
                "            System.out.println(\"Four\");\n" +
                "\n" +
                "        if(true)\n" +
                "            System.out.println(\"Four\");";



} 

