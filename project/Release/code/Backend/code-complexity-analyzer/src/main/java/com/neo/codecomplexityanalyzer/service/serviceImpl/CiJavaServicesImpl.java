/*
 * -----------------------------------------------------------------------------------------------------
 * 								Created By Wellala S. S> IT17009096
 * -----------------------------------------------------------------------------------------------------
 * 2019.08.04 - Added the function to find the classes in a given code
 * 2019.08.05 - Added the function to find out the relation between classes
 * 2019.08.07 - Added the function to find out the Ancestor classes of a given class
 * 
 * -----------------------------------------------------------------------------------------------------
 * CiServieImpl class contains the services to find out the classes in a given code, Find the 
 * relation between the classes, find the Ancestor classes of a given class and then to count
 * the number of Ancestor classes as well. Plus this gives a representation of the classes inheritance.
 * 
 */

package com.neo.codecomplexityanalyzer.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.KosarajuStrongConnectivityInspector;
import org.jgrapht.alg.interfaces.StrongConnectivityAlgorithm;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;

import com.neo.codecomplexityanalyzer.model.CiResultModel;
import com.neo.codecomplexityanalyzer.service.ICiJavaServices;

public class CiJavaServicesImpl implements ICiJavaServices {

	private String code = "";
	private String filePath = "";

	public CiJavaServicesImpl(String filePath) {
		GeneralServiceImpl gs = new GeneralServiceImpl();
		this.code = gs.getSourceCode(filePath);
		this.filePath = filePath;
	}

	// Loading the Code File that need to be analyzed.

	private static final Logger LOGGER = Logger.getLogger(CiJavaServicesImpl.class.getName());

	private static final String CLASS_KEY_WORD = "class ";
	private static final String EXTENDS_KEYWORD = " extends ";
	private static final String OPENING_BRACE = "{";
	private static final String SINGLE_SPACE_CHARACTOR = " ";
	private static final String CLASS_DOES_NOT_HAVE_INHERITANCE = " class dosen't have inheritance !";
	private static final String CLASSES_FOUND = "Classes Found !";
	private static final String NO_CLASSES_FOUND = "No Classes Found !";
	private static final String CHECKING_FOR_INHERITANCE_FOUND_IN_CODE = "Now Checking for the inheritance in the classes found in the code...";
	private static final String CHECKING_CLASS_HIERARCHY_FROM = "Checking class hierarchy from ";

	public ArrayList<String> getClassNames() {

		int classNameStartIndex = 0, classNameEndIndex = 0, currentIndex1 = 0, initialIndex1 = -1, count1 = 0;

		// This is to store the class names found in the code
		ArrayList<String> classNames = new ArrayList<String>();

		while (initialIndex1 < currentIndex1) {
			// From here it will search for the class keyword in the code.
			classNameStartIndex = code.indexOf(CLASS_KEY_WORD, classNameEndIndex) + 6;

			if (classNameStartIndex > 0 && count1 == 0) {
				count1++;
				initialIndex1 = classNameStartIndex;
			}

			classNameEndIndex = code.indexOf(SINGLE_SPACE_CHARACTOR, classNameStartIndex);

			currentIndex1 = classNameEndIndex;
			if (currentIndex1 < initialIndex1) {
				break;
			}
			if (classNameStartIndex > 0 && classNameEndIndex > 0 && (classNameStartIndex != classNameEndIndex)) {
				classNames.add(code.substring(classNameStartIndex, classNameEndIndex));
			} else {
				break;
			}
		}
		return classNames;
	}

	public HashMap<Integer, CiResultModel> getClassNameIndexByLineNumber() {
		int classNameStartIndex = 0, classNameEndIndex = 0, currentIndex1 = 0, initialIndex1 = -1, count1 = 0;

		// This is to store the class names found in the code
		HashMap<Integer, CiResultModel> classNameWithLineNumber = new HashMap<>();

		while (initialIndex1 < currentIndex1) {
			// From here it will search for the class keyword in the code.
			classNameStartIndex = code.indexOf(CLASS_KEY_WORD, classNameEndIndex) + 6;

			if (classNameStartIndex > 0 && count1 == 0) {
				count1++;
				initialIndex1 = classNameStartIndex;
			}

			classNameEndIndex = code.indexOf(SINGLE_SPACE_CHARACTOR, classNameStartIndex);

			currentIndex1 = classNameEndIndex;
			if (currentIndex1 < initialIndex1) {
				break;
			}
			if (classNameStartIndex > 0 && classNameEndIndex > 0 && (classNameStartIndex != classNameEndIndex)) {
				// classNames.add(code.substring(classNameStartIndex, classNameEndIndex));
				String className = code.substring(classNameStartIndex, classNameEndIndex);
				GeneralServiceImpl gs = new GeneralServiceImpl();
				gs.getSourceCode(this.filePath);

				Integer numOfAncestors = this.getNumberOfAnsestors(className);
				CiResultModel ciModel = new CiResultModel(className, (numOfAncestors + 1), numOfAncestors,
						this.getInheritanceMapOfClass(className));
				classNameWithLineNumber.put(gs.getFormattedLineByIndex(classNameStartIndex), ciModel);
			} else {
				break;
			}
		}
		return classNameWithLineNumber;
	}

	public HashMap<String, String> getClassMapping() {
		ArrayList<String> classNames = new ArrayList<String>();
		Map<String, String> classMap = new HashMap<String, String>();

		classNames = this.getClassNames();

		if (!classNames.isEmpty()) {
			// System.out.println("Info : Classes Found !");
			LOGGER.info(CLASSES_FOUND);
			for (int i = 0; i < classNames.size(); i++) {

				System.out.println("\t# " + classNames.get(i).toString());
			}
		} else {
			// System.out.println("Info : No Classes Found !");
			LOGGER.info(NO_CLASSES_FOUND);
		}

		int inheritanClassNameStartIndex = 0, inheritantClassNameEndIndex = 0;

		if (!classNames.isEmpty()) {

			// System.out.println("Info : Now Checking for the inheritance in the classes
			// found in the code...");
			LOGGER.info(CHECKING_FOR_INHERITANCE_FOUND_IN_CODE);
			for (int i = 0; i < classNames.size(); i++) {

				int initialIndex = -1, currentIndex = 0, count = 0;

				while (initialIndex < currentIndex) {

					System.out.println("\n\t>> Checking in " + classNames.get(i).toString() + " class");
					inheritanClassNameStartIndex = code
							.indexOf(SINGLE_SPACE_CHARACTOR + classNames.get(i).toString() + EXTENDS_KEYWORD);

					if (inheritanClassNameStartIndex > 0 && count == 0) {
						count++;
						initialIndex = inheritanClassNameStartIndex;
					}
					currentIndex = inheritanClassNameStartIndex;

					inheritantClassNameEndIndex = code.indexOf(OPENING_BRACE, inheritanClassNameStartIndex);

					if (inheritanClassNameStartIndex > 0 && inheritantClassNameEndIndex > 0) {
						String classHit = code.substring(inheritanClassNameStartIndex, inheritantClassNameEndIndex);
						System.out.println("\t# " + classHit);
						String val = classHit.trim();
						String[] classArray = val.split("\\s");
						classMap.put(classArray[0], classArray[2]);

					} else {
						System.out.println("Info : " + classNames.get(i).toString() + CLASS_DOES_NOT_HAVE_INHERITANCE);
						classMap.put(classNames.get(i), "");
						break;
					}
				}
			}
		}
		return (HashMap<String, String>) classMap;
	}

	public String getInheritanceMapOfClass(String pClassName) {
		ArrayList<String> classNames = new ArrayList<String>();
		classNames = this.getClassNames();

		Map<String, String> classMap = new HashMap<String, String>();
		classMap = this.getClassMapping();

		Iterator<Entry<String, String>> it = classMap.entrySet().iterator();

		// Creating a Graph
		Graph<String, DefaultEdge> g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

		// Adding the Vertices of the Graph
		for (String clsName : classNames) {
			g.addVertex(clsName);
		}

		// Adding the edges of the Graph
		while (it.hasNext()) {
			Entry<String, String> pair = it.next();
			if (pair.getValue() != "") {
				g.addEdge(pair.getKey(), pair.getValue());
			}
		}

		Iterator<String> iterator = new DepthFirstIterator<>(g, pClassName);
		int count = 0;
		String classInheritanceMapping = new String("");
		while (iterator.hasNext()) {
			String string = iterator.next();
			count++;
			// System.out.print(string);
			classInheritanceMapping = classInheritanceMapping.concat(string);
			if (iterator.hasNext()) {
				// System.out.print(" ---> ");
				classInheritanceMapping = classInheritanceMapping.concat(" ---> ");
			}
		}
		return classInheritanceMapping;
	}

	public void getClassLevelDetails() {
		ArrayList<String> classNames = new ArrayList<String>();
		classNames = this.getClassNames();

		Map<String, String> classMap = new HashMap<String, String>();
		classMap = this.getClassMapping();

		Iterator<Entry<String, String>> it = classMap.entrySet().iterator();

		// Creating a Graph
		Graph<String, DefaultEdge> g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

		// Adding the Vertices of the Graph
		for (String clsName : classNames) {
			g.addVertex(clsName);
		}

		// Adding the edges of the Graph
		while (it.hasNext()) {
			Entry<String, String> pair = it.next();
			if (pair.getValue() != "") {
				g.addEdge(pair.getKey(), pair.getValue());
			}
		}

		String name = "SportsCar";
		Iterator<String> iterator = new DepthFirstIterator<>(g, name);
		int count = 0;
		while (iterator.hasNext()) {
			String string = iterator.next();
			count++;
			System.out.print(string);
			if (iterator.hasNext()) {
				System.out.print(" ---> ");
			}
		}
		System.out.println();
		System.out.println("\nNumber of Ancestor Classes For " + name + " is : " + (count - 1));
	}

	@Override
	public int getNumberOfAnsestors(String nameOftheClass) {
		ArrayList<String> classNames = new ArrayList<String>();
		classNames = this.getClassNames();

		Map<String, String> classMap = new HashMap<String, String>();
		classMap = this.getClassMapping();

		Iterator<Entry<String, String>> it = classMap.entrySet().iterator();

		// Creating a Graph
		Graph<String, DefaultEdge> g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

		// Adding the Vertices of the Graph
		for (String clsName : classNames) {
			g.addVertex(clsName);
		}

		// Adding the edges of the Graph
		while (it.hasNext()) {
			Entry<String, String> pair = it.next();
			if (pair.getValue() != "") {
				g.addEdge(pair.getKey(), pair.getValue());
			}
		}

		Iterator<String> iterator = new DepthFirstIterator<>(g, nameOftheClass);
		int count = 0;

		System.out.println();
		System.out.println("<<< Class Hierarchy >>>");
		System.out.println();

		while (iterator.hasNext()) {
			String string = iterator.next();
			count++;
			System.out.print(string);
			if (iterator.hasNext()) {
				System.out.print(" ---> ");
			}
		}
		System.out.println();
		System.out.println("\nNumber of Ancestor Classes For " + nameOftheClass + " is : " + (count - 1));
		return (count - 1);
	}

	@Override
	public ArrayList<String> getAnsestorClassNames(String nameOfTheClass) {
		ArrayList<String> ansestorList = new ArrayList<String>();
		ArrayList<String> classNames = new ArrayList<String>();
		classNames = this.getClassNames();

		Map<String, String> classMap = new HashMap<String, String>();
		classMap = this.getClassMapping();

		Iterator<Entry<String, String>> it = classMap.entrySet().iterator();

		// Creating a Graph
		Graph<String, DefaultEdge> g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

		// Adding the Vertices of the Graph
		for (String clsName : classNames) {
			g.addVertex(clsName);
		}

		// Adding the edges of the Graph
		while (it.hasNext()) {
			Entry<String, String> pair = it.next();
			if (pair.getValue() != "") {
				g.addEdge(pair.getKey(), pair.getValue());
			}
		}

		Iterator<String> iterator = new DepthFirstIterator<>(g, nameOfTheClass);
		int count = 0;

		System.out.println();
		LOGGER.info(CHECKING_CLASS_HIERARCHY_FROM + nameOfTheClass);
		System.out.println("<<< Class Hierarchy >>>");
		System.out.println();

		while (iterator.hasNext()) {
			String string = iterator.next();
			if (count != 0) {
				ansestorList.add(string);
				System.out.print(string);
				if (iterator.hasNext()) {
					System.out.print(" ---> ");
				}
			}
			count++;

		}
		System.out.println();
		System.out.println("\nNumber of Ancestor Classes For " + nameOfTheClass + " is : " + (count - 1) + "\n");
		return ansestorList;
	}

	public void identifyStronglyConnectedClasses() {

		ArrayList<String> classNames = new ArrayList<String>();
		classNames = this.getClassNames();

		Map<String, String> classMap = new HashMap<String, String>();
		classMap = this.getClassMapping();

		Iterator<Entry<String, String>> it = classMap.entrySet().iterator();

		// Creating a Graph
		Graph<String, DefaultEdge> g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

		// Adding the Vertices of the Graph
		for (String clsName : classNames) {
			g.addVertex(clsName);
		}

		// Adding the edges of the Graph
		while (it.hasNext()) {
			Entry<String, String> pair = it.next();
			if (pair.getValue() != "") {
				g.addEdge(pair.getKey(), pair.getValue());
			}
		}

		// By running this algorithm we can identify the strongly connected classes.
		StrongConnectivityAlgorithm<String, DefaultEdge> scAlg = new KosarajuStrongConnectivityInspector<>(g);
		List<Graph<String, DefaultEdge>> stronglyConnectedSubgraphs = scAlg.getStronglyConnectedComponents();

		System.out.println("Strongly connected components:");

		for (int i = 0; i < stronglyConnectedSubgraphs.size(); i++) {
			System.out.println(stronglyConnectedSubgraphs.get(i));
		}
		System.out.println();
	}

	@Override
	public int calComplexityDueToInheritance(String className) {
		int numOfAnsestors = this.getNumberOfAnsestors(className);
		return (numOfAnsestors + 1);
	}

	@Override
	public int calTotalComplexityDueToInheritance() {
		int complexity = 0;
		ArrayList<String> classList = this.getClassNames();
		for (String clzName : classList) {
			complexity = calComplexityDueToInheritance(clzName);
		}
		return complexity;
	}

	@SuppressWarnings("null")
	@Override
	public HashMap<String, Integer> complexityOfAllClassesDueToInheritance() {
		ArrayList<String> classesOfTheCode = this.getClassNames();
		HashMap<String, Integer> complexityMapWithClassNames = new HashMap<String, Integer>();
		for (String clzName : classesOfTheCode) {
			complexityMapWithClassNames.put(clzName, (this.getNumberOfAnsestors(clzName) + 1));
		}
		return complexityMapWithClassNames;
	}

	@SuppressWarnings("null")
	@Override
	public HashMap<String, Integer> getClassWithTheNumberOfAnsestors() {
		ArrayList<String> classesOfTheCode = this.getClassNames();
		HashMap<String, Integer> numOfAnsestorsWithClassMap = null;
		for (String clzName : classesOfTheCode) {
			numOfAnsestorsWithClassMap.put(clzName, this.getNumberOfAnsestors(clzName));
		}
		return numOfAnsestorsWithClassMap;
	}

}
