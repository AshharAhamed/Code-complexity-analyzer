package com.neo.codecomplexityanalyzer.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.neo.codecomplexityanalyzer.Util.Queue;
import com.neo.codecomplexityanalyzer.service.ICNCService;

public class CNCServiceImpl implements ICNCService {
	private static final int stackSize = 100;
	private static final int queueSize = 100;
	private static final int arraySize = 100;
	private static final String errorMessage1 = "Missing } , not properly ended !";
	private static final String errorMessage2 = "Missing ) , not properly ended !";
	private static final String searchIf = "if";
	private static final String searchFor = "for";
	private static final String searchWhile = "while";
	private static final String searchDo = "do";
	private static final String searchSwitch = "switch";
	private static final String searchCase = "case";

	private static final char openBrace = '(';
	private static final char closeBrace = ')';
	private static final char openSquareBrace = '{';
	private static final char closeSquareBrace = '}';

	private String sourceCode;
	private long sourceCodeLength;
	private GeneralServiceImpl generalService;
	private HashMap<Integer, Integer> lineScore;
	private HashMap<Integer, Integer> lineScoreFor;
	private HashMap<Integer, Integer> lineScoreWhile;
	private HashMap<Integer, Integer> lineScoreDoWhile;
	private static final Logger logger = LogManager.getLogger(CNCServiceImpl.class);

	private ArrayList<Integer> nestedIfCurlyBracesStartIndex = new ArrayList<Integer>();
	private ArrayList<Integer> nestedIfStartIndex = new ArrayList<Integer>();
	private ArrayList<Integer> nestedIfEndIndex = new ArrayList<Integer>();
	private ArrayList<Integer> nestedForStartIndex = new ArrayList<Integer>();
	private ArrayList<Integer> nestedForEndIndex = new ArrayList<Integer>();
	private ArrayList<Integer> nestedWhileStartIndex = new ArrayList<Integer>();
	private ArrayList<Integer> nestedWhileEndIndex = new ArrayList<Integer>();
	private ArrayList<Integer> nestedIfCurlyBracesEndIndex = new ArrayList<Integer>();
	private ArrayList<Integer> lineNumberArr = new ArrayList<Integer>();

	public CNCServiceImpl(String filePath) {
		generalService = new GeneralServiceImpl();
		this.sourceCode = generalService.getSourceCode(filePath);
		this.sourceCodeLength = generalService.getSourceCodeLength();
		this.lineScore = generalService.getLineHashMap();
		this.lineScoreFor = generalService.getLineHashMap();
		this.lineScoreWhile = generalService.getLineHashMap();
		this.lineScoreDoWhile = generalService.getLineHashMap();
	}

	

	public HashMap<Integer, Integer> getNestedIfControlScore() {
		int ifStartIndex, ifEndIndex = 0, ifCount = 0, logicalCount = 0, score = 0;
		int lineNo;
		int tempForBegin = 0,tempOpenSquareBracket = 0,tempCloseSquareBracket,tempForCount = 0;

		Queue queue = new Queue(queueSize);
		char tempCloseSquareBra;
		int ifCountForLineScore = 0;
		do {
			int tempNestedIfStart = sourceCode.indexOf(searchIf, ifEndIndex);
			ifStartIndex = tempNestedIfStart + 3;
			if (ifStartIndex == 2)
				break;
			++ifCount;
			
			if(ifCount>1) {
//				this.nestedIfStartIndex.add(tempNestedForStart);
				tempForBegin = tempNestedIfStart;
				tempForCount = ifCountForLineScore;
			}
			
			System.out.println("code line 102:char at tempNestedIfStart = " + sourceCode.charAt(tempNestedIfStart)
					+ " char at ifStartIndex = " + sourceCode.charAt(ifStartIndex));
			nestedIfStartIndex.add(tempNestedIfStart);

			int indexTemp = ifStartIndex;
			Stack s1 = new Stack(stackSize);
			while (true) {
				if (indexTemp >= sourceCodeLength) {
					System.out.println("LineNumber 108");
					System.out.println(errorMessage2);
					return null;
				}
				char strTemp = sourceCode.charAt(indexTemp);
				tempCloseSquareBra = strTemp;
				// String strTempS = Character.toString(openSquareBrace);

				if (strTemp == openSquareBrace) {
					/*if(ifCount>1) {
						ArrayList<Integer> lineNumberArr1 = new ArrayList<Integer>();
						lineNumberArr1 = this.getLineNumbersBy(tempOpenSquareBracket, tempForBegin);
						for(int Lno:lineNumberArr1) {
							String lineEx = this.getLineAsString(Lno);
							
							if(lineEx.contains(";")) {
								lineScore.put(Lno, (lineScore.get(Lno) + tempForCount)tempForCount);
							}
						}
						tempForCount = tempForCount+1;
					}*/
					
					String subStringIfWithOps = sourceCode.substring(tempNestedIfStart, indexTemp - 1);
					ifCountForLineScore = ifCountForLineScore + 1;
					System.out.println("String which was cut for CNC IF complex calculation " + (indexTemp - 1) + " is "
							+ subStringIfWithOps);
					lineNo = generalService.getFormattedLineByIndex(indexTemp - 1);
					lineScore.put(lineNo, (lineScore.get(lineNo) + ifCountForLineScore));

					this.nestedIfCurlyBracesStartIndex.add(indexTemp);
					queue.enqueue(subStringIfWithOps);
					s1.push(strTemp);
					break;
				} else if (tempCloseSquareBra == closeSquareBrace) {
					
					if(s1.isEmpty()) {
						s1.pop();
						if (s1.isEmpty())
							break;
					}
					if (!queue.isEmpty()) {
						 queue.dequeue();
						this.nestedIfCurlyBracesEndIndex.add(indexTemp);
						ifCountForLineScore = ifCountForLineScore - 1;
						
//						  if (queue.isEmpty()) break;
						 

						if (ifCountForLineScore == 0) {
							break;
						}
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

		} while (true);
		int iter = 0;
		int i, j, h;
		i = 0;
		j = this.nestedIfStartIndex.size() - 1;
		h = this.nestedIfStartIndex.size() - 1;
		while (!queue.isEmpty()) {
			int x = 0, y;
			ArrayList<Integer> lineNumberArr2 = new ArrayList<Integer>();
			// System.out.println("nestedIfStartIndex size = "
			// +this.nestedIfStartIndex.size());
			// System.out.println("Line Number of if start :
			// "+generalService.getFormattedLineByIndex(this.nestedIfCurlyBracesStartIndex.get(j))/*+"and
			// end :
			// "+generalService.getFormattedLineByIndex(this.nestedIfEndIndex.get(i))*/);
			++iter;
			score = score + iter;
			if (!(h <= -1)) {
				x = nestedIfStartIndex.get(h);
			}
			System.out.println("size of array =" + nestedIfStartIndex.size() + " value of j = " + j);
			queue.dequeue();
			++i;
			--j;
			--h;
			if (!(h <= -1)) {
				System.out.println("size of array =" + nestedIfStartIndex.size() + " value of j = " + h);
				y = nestedIfStartIndex.get(h);
				lineNumberArr2 = this.getLineNumbersBy(y, x);
				for (int object : lineNumberArr2) {
					System.out.println("Line 165 code:number of lines of " + j + 1 + "th if = " + object);
				}

			}
		}

		return lineScore;
	}

	@Override
	public /*int*/HashMap<Integer, Integer> getNestedForScore() {
		// TODO Auto-generated method stub
		int forStartIndex, forEndIndex = 0, forCount = 0, score = 0;
		int lineNo;
		int tempForBegin = 0,tempOpenSquareBracket = 0,tempCloseSquareBracket,tempForCount = 0;

		Queue queue = new Queue(queueSize);
		int forCountForLineScore = 0;
		char tempCloseSquareBra;
		do {
			int tempNestedForStart = sourceCode.indexOf(searchFor, forEndIndex);
			forStartIndex = tempNestedForStart + 3;
			if (forStartIndex == 2)
				break;
			++forCount;
			
			
			if(forCount>1) {
//				this.nestedIfStartIndex.add(tempNestedForStart);
				tempForBegin = tempNestedForStart;
				tempForCount = forCountForLineScore;
			}

			int indexTemp = forStartIndex;
			Stack s1 = new Stack(stackSize);
			int lineNo1 = generalService.getFormattedLineByIndex(indexTemp - 1);
			System.out.println("this from line number 294 char at  " + lineNo1 + ":" + forStartIndex);
			System.out.println(sourceCode.charAt(forStartIndex));
			while (true) {
				if (indexTemp >= sourceCodeLength) {
					System.out.println(errorMessage2);
					System.out.println("this from line number 299 indexTemp : " + indexTemp + " sourceCodeLength : "
							+ sourceCodeLength);
//					return -1;
					return null;
				}
				char strTemp = sourceCode.charAt(indexTemp);
				tempCloseSquareBra = strTemp;
				if (strTemp == openSquareBrace) {
					if(forCount>1) {
						ArrayList<Integer> lineNumberArr1 = new ArrayList<Integer>();
						lineNumberArr1 = this.getLineNumbersBy(tempOpenSquareBracket+2, tempForBegin);
						for(int Lno:lineNumberArr1) {
							String lineEx = this.getLineAsString(Lno);
							
							if(lineEx.contains(";")) {
								lineScoreFor.put(Lno, (lineScore.get(Lno) + tempForCount)/*tempForCount*/);
//								lineScore.put(Lno, (lineScore.get(Lno) + tempForCount)/*tempForCount*/);
							}
						}
						tempForCount = tempForCount+1;
					}
					tempOpenSquareBracket = indexTemp;
					String subStringIfWithOps = sourceCode.substring(tempNestedForStart, indexTemp);
					forCountForLineScore = forCountForLineScore + 1;
					queue.enqueue(subStringIfWithOps);
					lineNo = generalService.getFormattedLineByIndex(indexTemp - 1);
					lineScoreFor.put(lineNo, (lineScoreFor.get(lineNo) + forCountForLineScore));
					System.out.println("this from line number 308");
					break;
				} else if (tempCloseSquareBra == closeSquareBrace) {
					if (!queue.isEmpty()) {
						// queue.dequeue();
						if(s1.isEmpty()) {
							s1.pop();
							if (s1.isEmpty())
								break;
						}
						if (!queue.isEmpty()) {
							 queue.dequeue();
							this.nestedIfCurlyBracesEndIndex.add(indexTemp);
							forCountForLineScore = forCountForLineScore - 1;
							
//							  if (queue.isEmpty()) break;
							 

							if (forCountForLineScore == 0) {
								break;
							}
						} else {
							System.out.println(errorMessage1);
							return null;
						}
					} else {
						System.out.println(errorMessage1);
						System.out.println("this from line number 321");
//						return -1;
						return null;
					}
				}
				++indexTemp;
			}
			if (tempCloseSquareBra == closeSquareBrace) {
				System.out.println("this from line number 328");
				break;
			}
			forEndIndex = indexTemp;
			System.out.println(
					"this from line number 332: queue size is = " + queue.size() + " forEndIndex = " + forEndIndex);
		} while (true);
		int iter = 0;
		System.out.println("this from line number 335 ");
		while (!queue.isEmpty()) {
			++iter;
			score = score + iter;
			queue.dequeue();
		}
		return lineScoreFor;
	}

	

	/*@Override
	public int getNestedWhileScore() {
		// TODO Auto-generated method stub
		int whileStartIndex, whileEndIndex = 0, whileCount = 0, score = 0;

		Queue queue = new Queue(queueSize);
		char tempCloseSquareBra;
		do {
			int tempNestedForStart = sourceCode.indexOf(searchWhile, whileEndIndex);
			// System.out.println("tempNestedForStart at
			// :"+sourceCode.charAt(tempNestedForStart));
			whileStartIndex = tempNestedForStart + 5;
			if (whileStartIndex == 4)
				break;
			++whileCount;

			int indexTemp = whileStartIndex;
			int lineNo = generalService.getFormattedLineByIndex(indexTemp - 1);
			System.out.println("this from line number 294 char at  " + lineNo + ":" + whileStartIndex);
			System.out.println(sourceCode.charAt(whileStartIndex));
			while (true) {
				if (indexTemp >= sourceCodeLength) {
					System.out.println(errorMessage2);
					System.out.println("this from line number 299 indexTemp : " + indexTemp + " sourceCodeLength : "
							+ sourceCodeLength);
					return -1;
				}
				char strTemp = sourceCode.charAt(indexTemp);
				tempCloseSquareBra = strTemp;
				String strTempS = Character.toString(openSquareBrace);
				if (strTemp == openSquareBrace) {
					String subStringIfWithOps = sourceCode.substring(tempNestedForStart, indexTemp);
					queue.enqueue(subStringIfWithOps);
					System.out.println("this from line number 308");
					break;
				} else if (tempCloseSquareBra == closeSquareBrace) {
					if (!queue.isEmpty()) {
						// queue.dequeue();
						System.out.println("this from line number 313");
						if (queue.isEmpty()) {
							System.out.println("this from line number 315");
							break;

						}
					} else {
						System.out.println(errorMessage1);
						System.out.println("this from line number 321");
						return -1;
					}
				}
				++indexTemp;
			}
			if (tempCloseSquareBra == closeSquareBrace) {
				System.out.println("this from line number 328");
				break;
			}
			whileEndIndex = indexTemp;
			System.out.println(
					"this from line number 332: queue size is = " + queue.size() + " whileEndIndex = " + whileEndIndex);
		} while (true);
		int iter = 0;
		System.out.println("this from line number 335 ");
		while (!queue.isEmpty()) {
			++iter;
			score = score + iter;
			System.out.println("this from line number 339 : score is = " + score);
			queue.dequeue();
		}
		System.out.println("this from line number 342 : score is = " + score);
		return score;
	}*/
	
	@Override
	public /*int*/HashMap<Integer, Integer> getNestedWhileScore() {
		// TODO Auto-generated method stub
		
		
		int whileStartIndex, whileEndIndex = 0, whileCount = 0, score = 0;
		int lineNo;
		int tempWhileBegin = 0,tempOpenSquareBracket = 0,tempCloseSquareBracket,tempwhileCount = 0;

		Queue queue = new Queue(queueSize);
		int whileCountForLineScore = 0;
		char tempCloseSquareBra;
		do {
			int tempNestedForStart = sourceCode.indexOf(searchWhile, whileEndIndex);
			whileStartIndex = tempNestedForStart + 5;
			if (whileStartIndex == 4)
				break;
			++whileCount;
			
			
			if(whileCount>1) {
//				this.nestedIfStartIndex.add(tempNestedForStart);
				tempWhileBegin = tempNestedForStart;
				tempwhileCount = whileCountForLineScore;
			}

			int indexTemp = whileStartIndex;
			Stack s1 = new Stack(stackSize);
			int lineNo1 = generalService.getFormattedLineByIndex(indexTemp - 1);
			System.out.println("this from line number 294 char at  " + lineNo1 + ":" + whileStartIndex);
			System.out.println(sourceCode.charAt(whileStartIndex));
			while (true) {
				if (indexTemp >= sourceCodeLength) {
					System.out.println(errorMessage2);
					System.out.println("this from line number 299 indexTemp : " + indexTemp + " sourceCodeLength : "
							+ sourceCodeLength);
//					return -1;
					return null;
				}
				char strTemp = sourceCode.charAt(indexTemp);
				tempCloseSquareBra = strTemp;
				if (strTemp == openSquareBrace) {
					if(whileCount>1) {
						ArrayList<Integer> lineNumberArr1 = new ArrayList<Integer>();
						lineNumberArr1 = this.getLineNumbersBy(tempOpenSquareBracket+2, tempWhileBegin);
						for(int Lno:lineNumberArr1) {
							String lineEx = this.getLineAsString(Lno);
							
							if(lineEx.contains(";")) {
								lineScoreWhile.put(Lno, (lineScoreWhile.get(Lno) + tempwhileCount)/*tempwhileCount*/);
//								lineScore.put(Lno, (lineScore.get(Lno) + tempwhileCount)/*tempwhileCount*/);
							}
						}
						tempwhileCount = tempwhileCount+1;
					}
					tempOpenSquareBracket = indexTemp;
					String subStringIfWithOps = sourceCode.substring(tempNestedForStart, indexTemp);
					whileCountForLineScore = whileCountForLineScore + 1;
					queue.enqueue(subStringIfWithOps);
					lineNo = generalService.getFormattedLineByIndex(indexTemp - 1);
					lineScoreWhile.put(lineNo, (lineScoreWhile.get(lineNo) + whileCountForLineScore));
					System.out.println("this from line number 308");
					break;
				} else if (tempCloseSquareBra == closeSquareBrace) {
					if (!queue.isEmpty()) {
						// queue.dequeue();
						if(s1.isEmpty()) {
							s1.pop();
							if (s1.isEmpty())
								break;
						}
						if (!queue.isEmpty()) {
							 queue.dequeue();
							this.nestedIfCurlyBracesEndIndex.add(indexTemp);
							whileCountForLineScore = whileCountForLineScore - 1;
							
//							  if (queue.isEmpty()) break;
							 

							if (whileCountForLineScore == 0) {
								break;
							}
						} else {
							System.out.println(errorMessage1);
							return null;
						}
					} else {
						System.out.println(errorMessage1);
						System.out.println("this from line number 321");
//						return -1;
						return null;
					}
				}
				++indexTemp;
			}
			if (tempCloseSquareBra == closeSquareBrace) {
				System.out.println("this from line number 328");
				break;
			}
			whileEndIndex = indexTemp;
			System.out.println(
					"this from line number 332: queue size is = " + queue.size() + " whileEndIndex = " + whileEndIndex);
		} while (true);
		int iter = 0;
		System.out.println("this from line number 335 ");
		while (!queue.isEmpty()) {
			++iter;
			score = score + iter;
			queue.dequeue();
		}
		
		
		return lineScoreWhile;
	}
	
	@Override
	public HashMap<Integer, Integer> getNestedDoWhileScore() {
		// TODO Auto-generated method stub
		

		int doStartIndex, doEndIndex = 0, doWhileCount = 0, score = 0;
		int lineNo;
		int tempDoWhileBegin = 0,tempOpenSquareBracket = 0,tempCloseSquareBracket,tempDoWhileCount = 0;

		Queue queue = new Queue(queueSize);
		int doWhileCountForLineScore = 0;
		char tempCloseSquareBra;
		do {
			int tempNestedForStart = sourceCode.indexOf(searchDo, doEndIndex);
			doStartIndex = tempNestedForStart + 2;
			if (doStartIndex == 1)
				break;
			++doWhileCount;
			
			
			if(doWhileCount>1) {
//				this.nestedIfStartIndex.add(tempNestedForStart);
				tempDoWhileBegin = tempNestedForStart;
				tempDoWhileCount = doWhileCountForLineScore;
			}

			int indexTemp = doStartIndex;
			Stack s1 = new Stack(stackSize);
			int lineNo1 = generalService.getFormattedLineByIndex(indexTemp - 1);
			System.out.println("this from line number 294 char at  " + lineNo1 + ":" + doStartIndex);
			System.out.println(sourceCode.charAt(doStartIndex));
			while (true) {
				if (indexTemp >= sourceCodeLength) {
					System.out.println(errorMessage2);
					System.out.println("this from line number 299 indexTemp : " + indexTemp + " sourceCodeLength : "
							+ sourceCodeLength);
//					return -1;
					return null;
				}
				char strTemp = sourceCode.charAt(indexTemp);
				tempCloseSquareBra = strTemp;
				if (strTemp == openSquareBrace) {
					if(doWhileCount>1) {
						ArrayList<Integer> lineNumberArr1 = new ArrayList<Integer>();
						lineNumberArr1 = this.getLineNumbersBy(tempOpenSquareBracket+2, tempDoWhileBegin);
						for(int Lno:lineNumberArr1) {
							String lineEx = this.getLineAsString(Lno);
							
							if(lineEx.contains(";")) {
								lineScoreDoWhile.put(Lno, (lineScoreDoWhile.get(Lno) + tempDoWhileCount)/*tempDoWhileCount*/);
//								lineScore.put(Lno, (lineScore.get(Lno) + tempDoWhileCount)/*tempDoWhileCount*/);
							}
						}
						tempDoWhileCount = tempDoWhileCount+1;
					}
					tempOpenSquareBracket = indexTemp;
					String subStringIfWithOps = sourceCode.substring(tempNestedForStart, indexTemp);
					doWhileCountForLineScore = doWhileCountForLineScore + 1;
					queue.enqueue(subStringIfWithOps);
					lineNo = generalService.getFormattedLineByIndex(indexTemp - 1);
					lineScoreDoWhile.put(lineNo, (lineScoreDoWhile.get(lineNo) + doWhileCountForLineScore));
					System.out.println("this from line number 308");
					break;
				} else if (tempCloseSquareBra == closeSquareBrace) {
					if (!queue.isEmpty()) {
						// queue.dequeue();
						if(s1.isEmpty()) {
							s1.pop();
							if (s1.isEmpty())
								break;
						}
						if (!queue.isEmpty()) {
							 queue.dequeue();
							this.nestedIfCurlyBracesEndIndex.add(indexTemp);
							doWhileCountForLineScore = doWhileCountForLineScore - 1;
							
//							  if (queue.isEmpty()) break;
							 

							if (doWhileCountForLineScore == 0) {
								break;
							}
						} else {
							System.out.println(errorMessage1);
							return null;
						}
					} else {
						System.out.println(errorMessage1);
						System.out.println("this from line number 321");
//						return -1;
						return null;
					}
				}
				++indexTemp;
			}
			if (tempCloseSquareBra == closeSquareBrace) {
				System.out.println("this from line number 328");
				break;
			}
			doEndIndex = indexTemp;
			System.out.println(
					"this from line number 332: queue size is = " + queue.size() + " doEndIndex = " + doEndIndex);
		} while (true);
		int iter = 0;
		System.out.println("this from line number 335 ");
		while (!queue.isEmpty()) {
			++iter;
			score = score + iter;
			queue.dequeue();
		}
		return lineScoreDoWhile;
	}

	// returns the index of next for
	private int searchNextFor(int forEndIndex) {
		int forStartIndex = sourceCode.indexOf(searchFor, forEndIndex) + 3;
		if (sourceCode.charAt(forStartIndex) == ' ' || sourceCode.charAt(forStartIndex) == '(')
			return forStartIndex;
		else
			return 2;
	}

	private int getLastRoundClosingBrace(int startIndex) {
		int indexTemp = startIndex;
		char charTemp;
		Stack s1 = new Stack(stackSize);
		while (indexTemp < sourceCodeLength) {
			charTemp = sourceCode.charAt(indexTemp);
			if (charTemp == openBrace)
				s1.push(openBrace);
			else if (charTemp == closeBrace) {
				if (Boolean.FALSE.equals(s1.isEmpty())) {
					s1.pop();
					if (Boolean.TRUE.equals(s1.isEmpty())) {
						return (indexTemp + 1);
					}
				} else {
					return -1;
				}
			}
			++indexTemp;
		}
		return -1;
	}

	public ArrayList<Integer> getLineNumbersBy(int startindex1, int endIndex1) {
		int startIndex, endIndex = 0;
		int startLine, endLine;
		int indexCounter = 0;
		int lineCount = 0;
		ArrayList<Integer> lineNumberArr1 = new ArrayList<Integer>();

		startLine = generalService.getFormattedLineByIndex(startindex1);
		endLine = generalService.getFormattedLineByIndex(endIndex1);
		String line;

		do {
			if (indexCounter >= sourceCode.length())
				break;
			startIndex = sourceCode.indexOf("\n", endIndex) + 1;
			if (startIndex == 0)
				break;
			++lineCount;
			if (lineCount >= startLine && lineCount < endLine) {
				line = sourceCode.substring(endIndex, startIndex);
				if ((line.length() != 2) || !(line.contains("}"))) {
					lineNumberArr1.add(lineCount);
				}
			}

			/*
			 * for(int i = 0 ; i < line.length() ; ++i){ if(index == indexCounter){ return
			 * lineCount; } ++indexCounter; }
			 */
			endIndex = startIndex;
		} while (lineCount < endLine);
		return lineNumberArr1;
	}

	public String getLineAsString(int lineNumber) {
		int startIndex, endIndex = 0;
		int startLine, endLine;
		int indexCounter = 0;
		int lineCount = 0;

		String line;

		do {
			if (indexCounter >= sourceCode.length())
				break;
			startIndex = sourceCode.indexOf("\n", endIndex) + 1;
			if (startIndex == 0)
				break;
			++lineCount;
			if (lineCount == lineNumber) {
				line = sourceCode.substring(endIndex, startIndex);
				return line;

			}
			endIndex = startIndex;
		} while (true);
		return null;
	}



	
}
