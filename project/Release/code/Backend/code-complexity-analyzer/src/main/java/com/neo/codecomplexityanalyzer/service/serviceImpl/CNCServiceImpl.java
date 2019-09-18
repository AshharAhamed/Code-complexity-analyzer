package com.neo.codecomplexityanalyzer.service.serviceImpl;

import java.util.HashMap;

import com.neo.codecomplexityanalyzer.Util.Queue;
import com.neo.codecomplexityanalyzer.service.ICNCService;

public class CNCServiceImpl implements ICNCService {
	private static final int stackSize = 100;
	private static final int queueSize = 100;
	private static final int arraySize = 100;
    private static final String errorMessage1 = "Missing } , not properly ended !";
    private static final String errorMessage2 = "Missing ) , not properly ended !";
    private static final String searchIf    = "if";
    private static final String searchFor   = "for";
    private static final String searchWhile = "while";
    private static final String searchSwitch = "switch";
    private static final String searchCase = "case";


    private static final char openBrace  = '(';
    private static final char closeBrace = ')';
    private static final char openSquareBrace  = '{';
    private static final char closeSquareBrace = '}';

    private String sourceCode;
    private long sourceCodeLength;
    private GeneralServiceImpl generalService;
    private HashMap<Integer, Integer> lineScore;
    
	
    
   public CNCServiceImpl(String filePath) {
	   generalService = new GeneralServiceImpl();
       this.sourceCode = generalService.getSourceCode(filePath);
       this.sourceCodeLength = generalService.getSourceCodeLength();
       this.lineScore = generalService.getLineHashMap();
   }
	
   /*public int getNestedControlScore() {
       int ifStartIndex, ifEndIndex = 0, ifCount = 0, logicalCount = 0;
       do {
           ifStartIndex = sourceCode.indexOf(searchIf, ifEndIndex) + 3;
           if (ifStartIndex == 2)
               break;
           ++ifCount;
           int indexTemp = ifStartIndex;
           Stack s1 = new Stack(stackSize);
           while (true) {
               if (indexTemp >= sourceCodeLength) {
                   System.out.println(errorMessage2);
                   return -1;
               }
               char strTemp = sourceCode.charAt(indexTemp);
               if (strTemp == openBrace)
                   s1. (openBrace);
               else if (strTemp == closeBrace) {
                   if (!s1.isEmpty()) {
                       s1.pop();
                       if (s1.isEmpty())
                           break;
                   } else {
                       System.out.println(errorMessage2);
                       return -1;
                   }
               }
               ++indexTemp;
           }
           ifEndIndex = indexTemp;
           String subString = sourceCode.substring(ifStartIndex, ifEndIndex);
           logicalCount += generalService.getLogicalCount(subString);
       } while (true);
       System.out.println("If Count : " + ifCount + " Logical Operator Count : " + logicalCount);
       return (ifCount + logicalCount);
   }*/
   
   
   public HashMap<Integer, Integer> getNestedIfControlScore() {
       int nestedIfStartIndex[], nestedIfEndIndex[], ifStartIndex, ifEndIndex = 0, ifCount = 0, logicalCount = 0, score=0;
       int lineNo;
       nestedIfStartIndex=new int[arraySize];
       nestedIfEndIndex=new int[arraySize];
       Queue queue = new Queue(queueSize);
       char tempCloseSquareBra;
       int ifCountForLineScore=0;
       do {
    	   int tempNestedIfStart = sourceCode.indexOf(searchIf, ifEndIndex);
    	   ifStartIndex = tempNestedIfStart + 3;
    	   if (ifStartIndex == 2)
               break;
    	   ++ifCount;
    	   
    	   int indexTemp = ifStartIndex;
//    	   ifCountForLineScore = 0;
           while (true) {
               if (indexTemp >= sourceCodeLength) {
                   System.out.println(errorMessage2);
                   return null;
               }
               char strTemp = sourceCode.charAt(indexTemp);
               tempCloseSquareBra = strTemp;
               String strTempS = Character.toString(openSquareBrace);
               if (strTemp == openSquareBrace) {
            	   ifCountForLineScore = ifCountForLineScore + 1;
            	   String subStringIfWithOps = sourceCode.substring(tempNestedIfStart, indexTemp-1);  
            	   System.out.println("String which was cut for CNC IF complex calculation "+(indexTemp-1)+" is "+ subStringIfWithOps);
            	   lineNo = generalService.getFormattedLineByIndex(indexTemp - 1);
            	   lineScore.put(lineNo, (lineScore.get(lineNo) + ifCountForLineScore));
            	   queue.enqueue(subStringIfWithOps);
            	   break;
               }else if (tempCloseSquareBra == closeSquareBrace) {
                   if (!queue.isEmpty()) {
//                	   queue.dequeue();
                       if (queue.isEmpty())
                           break;
                   } else {
                       System.out.println(errorMessage1);
                       return null;
                   }
               }
               ++indexTemp;
           }
           if (tempCloseSquareBra == closeSquareBrace) {
        	   break; 
           }
           ifEndIndex = indexTemp;
           
       }while(true);
       int iter = 0;
       while(!queue.isEmpty()){
    	   ++iter;
    	   score = score + iter;
    	   queue.dequeue();
       }
       
       return lineScore;
   }
   
   
   
   public int getNestedForScore() {
       int nestedForStartIndex[], nestedForEndIndex[], forStartIndex, forEndIndex = 0, forCount = 0, logicalCount = 0, score=0;
       nestedForStartIndex=new int[arraySize];
       nestedForEndIndex=new int[arraySize];
       Queue queue = new Queue(queueSize);
       char tempCloseSquareBra;
       do {
    	   int tempNestedForStart = sourceCode.indexOf(searchFor, forEndIndex);
    	   forStartIndex = tempNestedForStart + 3;
    	   if (forStartIndex == 2)
               break;
    	   ++forCount;
    	   
    	   int indexTemp = forStartIndex;
           
           while (true) {
               if (indexTemp >= sourceCodeLength) {
                   System.out.println(errorMessage2);
                   System.out.println("this from line number 159");
                   return -1;
               }
               char strTemp = sourceCode.charAt(indexTemp);
               tempCloseSquareBra = strTemp;
               String strTempS = Character.toString(openSquareBrace);
               if (strTemp == openSquareBrace) {
            	   String subStringIfWithOps = sourceCode.substring(tempNestedForStart, indexTemp);  
            	   queue.enqueue(subStringIfWithOps);
               }else if (tempCloseSquareBra == closeSquareBrace) {
                   if (!queue.isEmpty()) {
//                	   queue.dequeue();
                       if (queue.isEmpty())
                           break;
                   } else {
                       System.out.println(errorMessage1);
                       System.out.println("this from line number 175");
                       return -1;
                   }
               }
               ++indexTemp;
           }
           if (tempCloseSquareBra == closeSquareBrace) {
        	   break; 
           }
           forEndIndex = indexTemp;
           
       }while(true);
       int iter = 0;
       while(queue.isEmpty()){
    	   ++iter;
    	   score = score + iter;
    	   queue.dequeue();
       }
       
       return score;
   }

   	@Override
	public int getNestedWhileScore() {
		// TODO Auto-generated method stub
		int nestedWhileStartIndex[], nestedWhileEndIndex[], whileStartIndex, whileEndIndex = 0, whileCount = 0, logicalCount = 0, score=0;
		nestedWhileStartIndex=new int[arraySize];
		nestedWhileEndIndex=new int[arraySize];
	    Queue queue = new Queue(queueSize);
	    char tempCloseSquareBra;
	    do {
	 	   int tempNestedForStart = sourceCode.indexOf(searchWhile, whileEndIndex);
	 	  whileStartIndex = tempNestedForStart + 3;
	 	   if (whileStartIndex == 2)
	            break;
	 	   ++whileCount;
	 	   
	 	   int indexTemp = whileStartIndex;
	        
	        while (true) {
	            if (indexTemp >= sourceCodeLength) {
	                System.out.println(errorMessage2);
	                return -1;
	            }
	            char strTemp = sourceCode.charAt(indexTemp);
	            tempCloseSquareBra = strTemp;
	            String strTempS = Character.toString(openSquareBrace);
	            if (strTemp == openSquareBrace) {
	         	   String subStringIfWithOps = sourceCode.substring(tempNestedForStart, indexTemp);  
	         	   queue.enqueue(subStringIfWithOps);
	            }else if (tempCloseSquareBra == closeSquareBrace) {
	                if (!queue.isEmpty()) {
	//             	   queue.dequeue();
	                    if (queue.isEmpty())
	                        break;
	                } else {
	                    System.out.println(errorMessage1);
	                    return -1;
	                }
	            }
	            ++indexTemp;
	        }
	        if (tempCloseSquareBra == closeSquareBrace) {
	     	   break; 
	        }
	        whileEndIndex = indexTemp;
	        
	    }while(true);
	    int iter = 0;
	    while(queue.isEmpty()){
	 	   ++iter;
	 	   score = score + iter;
	 	   queue.dequeue();
	    }
	    
	    return score;
	}
}
   
   
   
