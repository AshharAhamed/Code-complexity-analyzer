package com.neo.codecomplexityanalyzer.service.serviceImpl;

import com.neo.codecomplexityanalyzer.Util.Queue;
import com.neo.codecomplexityanalyzer.service.CNCService;

public class CNCServiceImpl implements CNCService {
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
	
    
   public CNCServiceImpl(String filePath) {
	   generalService = new GeneralServiceImpl();
       this.sourceCode = generalService.getSourceCode(filePath);
       this.sourceCodeLength = generalService.getSourceCodeLength();
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
   
   
   public int getNestedControlScore() {
       int nestedIfStartIndex[], nestedIfEndIndex[], ifStartIndex, ifEndIndex = 0, ifCount = 0, logicalCount = 0, score=0;
       nestedIfStartIndex=new int[arraySize];
       nestedIfEndIndex=new int[arraySize];
       do {
    	   int tempNestedIfStart = sourceCode.indexOf(searchIf, ifEndIndex);
    	   ifStartIndex = tempNestedIfStart + 3;
    	   if (ifStartIndex == 2)
               break;
    	   
    	   int indexTemp = ifStartIndex;
           Queue queue = new Queue(queueSize);
           
           while (true) {
               if (indexTemp >= sourceCodeLength) {
                   System.out.println(errorMessage2);
                   return -1;
               }
               char strTemp = sourceCode.charAt(indexTemp);
               String strTempS = Character.toString(openBrace);
               if (strTemp == openBrace)
                   queue.enqueue(strTempS);
               else if (strTemp == closeBrace) {
                   if (!queue.isEmpty()) {
                	   queue.dequeue();
                       if (queue.isEmpty())
                           break;
                   } else {
                       System.out.println(errorMessage2);
                       return -1;
                   }
               }
               ++indexTemp;
           }
           
       }while(true);
       
       return score;
       
   }
}
   
   
   
