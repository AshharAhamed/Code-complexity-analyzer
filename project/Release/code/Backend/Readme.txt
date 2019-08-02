I am analysing the code "FibonacciMain.java" which is inside the "com.neo.ccaServices.resources" package

I have hard coded the path in the "CCA_Utilities" class, in the "getSourceCode" function. (Let's change this later). 
So before you run, you have to change this, and set your path to it. 
Please see if there is a way to set a relative path, since bot hof the files are in the same package.
File file = new File("D:\\02_Mech_Mango\\01_SLIIT\\Semester_06\\SPM\\03_10th_August_Sprint_01\\01_Testing\\01_Based_on_Size\\src\\utilities\\FibonacciMain.java");

Run the "CcaServicesApplication.java" in side the "com.neo.ccaServices" package. The services should start running in port 8080

** NOTE :: This is an initial test, so the stuff I do here is stupdi and inefficient. Lets enhance later!

Resource URLs (Test these on postman)
=====================================
Get the line count of the source code: 
	http://localhost:8080/line-count

Get the source code line by line as an array:
	http://localhost:8080/get-code

