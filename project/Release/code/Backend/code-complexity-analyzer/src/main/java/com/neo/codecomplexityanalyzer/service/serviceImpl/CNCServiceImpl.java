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
	private HashMap<Integer, Integer> lineScoreIf;
	
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
		this.lineScoreIf = generalService.getLineHashMap();
	}

	@Override
	public HashMap<Integer, Integer> getScore(){
		HashMap<Integer, Integer> ifHash,forHash,whileHash,doWhileHash;
		ifHash = this.getNestedIfControlScore();
		forHash = this.getNestedForScore();
		whileHash = this.getNestedWhileScore();
		doWhileHash = this.getNestedDoWhileScore();
		
		for(int i = 1; i<=this.lineScore.size(); i++) {
			this.lineScore.put(i, (this.lineScoreIf.get(i)+this.lineScoreFor.get(i)+this.lineScoreWhile.get(i)+this.lineScoreDoWhile.get(i)));
		}
		
		return lineScore;
	}
	
	@Override
	public HashMap<Integer, Integer> getNestedIfControlScore() {
		// TODO Auto-generated method stub
				int ifStartIndex, ifEndIndex = 0, ifCount  = 0, score = 0;
				int lineNo;
				int tempIfBegin = 0,tempOpenSquareBracket = 0,tempCloseSquareBracket,tempForCount = 0;
				int tempIfBCount =0;

				Queue queue = new Queue(queueSize);
				int ifCountForLineScore = 0;
				char tempCloseSquareBra;
				do {
					int tempNestedIfStart = sourceCode.indexOf(searchIf, ifEndIndex );
					ifStartIndex = tempNestedIfStart + 2;
					if (ifStartIndex == 1)
						break;
					++ifCount;
//					this added on 21.09.2019 11.07 p.m for new changes
					tempIfBCount =ifCount;
					
					if(ifCount>1) {
//						this.nestedIfStartIndex.add(tempNestedIfStart);
						tempIfBegin = tempNestedIfStart;
						tempForCount = ifCountForLineScore;
						
					}
					System.out.println("ongoing if on 82nd row : "+ ifCount+" and index of if is : "+tempNestedIfStart);

					int indexTemp = ifStartIndex;
					Stack s1 = new Stack(stackSize);
					int lineNo1 = generalService.getFormattedLineByIndex(indexTemp - 1);
					System.out.println("this from line number 294 char at  " + lineNo1 + ":" + ifStartIndex);
					System.out.println(sourceCode.charAt(ifStartIndex));
					while (true) {
						
//					this added on 21.09.2019 11.07 p.m for new changes
						
						
						if (indexTemp >= sourceCodeLength) {
							System.out.println(errorMessage2);
							System.out.println("this from line number 299 indexTemp : " + indexTemp + " sourceCodeLength : "
									+ sourceCodeLength);
//							return -1;
//							return lineScoreIf;
							return null;
						}
						char strTemp = sourceCode.charAt(indexTemp);
						tempCloseSquareBra = strTemp;
					
						String tempSquareLine = this.getLineAsString(this.generalService.getFormattedLineByIndex(indexTemp));
						String strTempS = Character.toString(openSquareBrace);
						
						if ((strTemp == openSquareBrace)&&(tempSquareLine.contains("if"))/*&&()*/) {
							
						}
						
						if ((strTemp == openSquareBrace) &&(tempSquareLine.contains("if"))) {
							if(ifCount>1) {
								ArrayList<Integer> lineNumberArr1 = new ArrayList<Integer>();
								lineNumberArr1 = this.getLineNumbersBy(tempOpenSquareBracket+2, tempIfBegin);
								for(int Lno:lineNumberArr1) {
									String lineEx = this.getLineAsString(Lno);
									
									if((lineEx.contains(";"))||(lineEx.contains("for"))||(lineEx.contains("while"))||(lineEx.contains("do"))||(lineEx.contains("switch"))||(lineEx.contains("case"))||(lineEx.contains("try"))||(lineEx.contains("else"))) {
										lineScoreIf.put(Lno, (lineScore.get(Lno) + tempForCount)/*tempForCount*/);
//										lineScore.put(Lno, (lineScore.get(Lno) + tempForCount)/*tempForCount*/);
									}
								}
								tempForCount = tempForCount+1;
							}
							
							s1.push(strTemp);
							queue.enqueue(strTempS);
							
							tempOpenSquareBracket = indexTemp;
							String subStringIfWithOps = sourceCode.substring(tempNestedIfStart, indexTemp);
							ifCountForLineScore = ifCountForLineScore + 1;
							queue.enqueue(subStringIfWithOps);
							lineNo = generalService.getFormattedLineByIndex(indexTemp - 1);
							lineScoreIf.put(lineNo, (lineScoreIf.get(lineNo) + ifCountForLineScore));
							System.out.println("this from line number 308");
							/*if(sourceCode.indexOf("if", indexTemp)>indexTemp) {
								break;
							}*/
							break;
						} else if (tempCloseSquareBra == closeSquareBrace) {
							System.out.println("this is start of close square brackets line number 122");
							if (!queue.isEmpty()) {
									queue.dequeue();
									this.nestedIfCurlyBracesEndIndex.add(indexTemp);
									ifCountForLineScore = ifCountForLineScore - 1;
									
									if (ifCountForLineScore == 0) {
										break;
									}

									if (queue.isEmpty())
										break;
							} else {
								System.out.println(errorMessage1);
								System.out.println("this from line number 321");
//								return -1;
								return null;
							}
						}
						++indexTemp;
					}
					if (tempCloseSquareBra == closeSquareBrace) {
						System.out.println("this from line number 328");
						break;
					}
					ifEndIndex  = indexTemp;
					System.out.println(
							"this from line number 332: queue size is = " + queue.size() + " ifEndIndex  = " + ifEndIndex );
				} while (true);
				int iter = 0;
				System.out.println("this from line number 335 ");
				while (!queue.isEmpty()) {
					++iter;
					score = score + iter;
					queue.dequeue();
				}
				return lineScoreIf;
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
				String tempSquareLine = this.getLineAsString(this.generalService.getFormattedLineByIndex(indexTemp));
				char strTemp = sourceCode.charAt(indexTemp);
				tempCloseSquareBra = strTemp;
				if (strTemp == openSquareBrace &&(tempSquareLine.contains("for"))) {
					if(forCount>1) {
						ArrayList<Integer> lineNumberArr1 = new ArrayList<Integer>();
						lineNumberArr1 = this.getLineNumbersBy(tempOpenSquareBracket+2, tempForBegin);
						for(int Lno:lineNumberArr1) {
							String lineEx = this.getLineAsString(Lno);
							
							if((lineEx.contains(";"))||(lineEx.contains("if"))||(lineEx.contains("while"))||(lineEx.contains("do"))||(lineEx.contains("switch"))||(lineEx.contains("case"))||(lineEx.contains("try"))||(lineEx.contains("else"))) {
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
				String tempSquareLine = this.getLineAsString(this.generalService.getFormattedLineByIndex(indexTemp));
				char strTemp = sourceCode.charAt(indexTemp);
				tempCloseSquareBra = strTemp;
				if (strTemp == openSquareBrace&&(tempSquareLine.contains("while"))) {
					if(whileCount>1) {
						ArrayList<Integer> lineNumberArr1 = new ArrayList<Integer>();
						lineNumberArr1 = this.getLineNumbersBy(tempOpenSquareBracket+2, tempWhileBegin);
						for(int Lno:lineNumberArr1) {
							String lineEx = this.getLineAsString(Lno);
							
							if((lineEx.contains(";"))||(lineEx.contains("for"))||(lineEx.contains("if"))||(lineEx.contains("do"))||(lineEx.contains("switch"))||(lineEx.contains("case"))||(lineEx.contains("try"))||(lineEx.contains("else"))) {
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
				String tempSquareLine = this.getLineAsString(this.generalService.getFormattedLineByIndex(indexTemp));
				char strTemp = sourceCode.charAt(indexTemp);
				tempCloseSquareBra = strTemp;
				if (strTemp == openSquareBrace&&(tempSquareLine.contains("do"))) {
					if(doWhileCount>1) {
						ArrayList<Integer> lineNumberArr1 = new ArrayList<Integer>();
						lineNumberArr1 = this.getLineNumbersBy(tempOpenSquareBracket+2, tempDoWhileBegin);
						for(int Lno:lineNumberArr1) {
							String lineEx = this.getLineAsString(Lno);
							
							if((lineEx.contains(";"))||(lineEx.contains("for"))||(lineEx.contains("while"))||(lineEx.contains("if"))||(lineEx.contains("switch"))||(lineEx.contains("case"))||(lineEx.contains("try"))||(lineEx.contains("else"))) {
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
