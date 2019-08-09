package com.neo.codecomplexityanalyzer.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;

public class CiCppServicesImpl implements ICiCppServices {

	private String code = "";
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

	public CiCppServicesImpl(String filePath) {
		GeneralServiceImpl gs = new GeneralServiceImpl();
		this.code = gs.getSourceCode(filePath);
	}

	@Override
	public ArrayList<String> getAllClassNames() {
		int classNameStartIndex = 0, classNameEndIndex = 0, currentIndex1 = 0, initialIndex1 = -1, count1 = 0;

		// This is to store the class names found in the code
		ArrayList<String> classNamesList = new ArrayList<String>();

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
				classNamesList.add(code.substring(classNameStartIndex, classNameEndIndex));
			} else {
				break;
			}
		}
		return classNamesList;
	}

	@Override
	public HashMap<String, String> getClassMapping() {
		ArrayList<String> classNames = new ArrayList<String>();
		Map<String, String> classMap = new HashMap<String, String>();

		classNames = this.getAllClassNames();

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
					inheritanClassNameStartIndex = code.indexOf("class " + classNames.get(i).toString() + " : ");

					if (inheritanClassNameStartIndex > 0 && count == 0) {
						count++;
						initialIndex = inheritanClassNameStartIndex;
					}
					currentIndex = inheritanClassNameStartIndex;

					inheritantClassNameEndIndex = code.indexOf(OPENING_BRACE, inheritanClassNameStartIndex);

					if (inheritanClassNameStartIndex > 0 && inheritantClassNameEndIndex > 0) {
						String classHit = code.substring(inheritanClassNameStartIndex + 6, inheritantClassNameEndIndex);
						System.out.println("\t# " + classHit);
						String val = classHit.trim();
						String[] classArray = val.split(":");
						classArray[0] = classArray[0].trim();
						classArray[1] = classArray[1].replace("public", "");

						classMap.put(classArray[0], classArray[1]);

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

	@Override
	public ArrayList<String> getAncestorClasses(String childClass) {
		ArrayList<String> ansestorList = new ArrayList<String>();
		ArrayList<String> classNames = new ArrayList<String>();
		classNames = this.getAllClassNames();

		Map<String, String> classMap = new HashMap<String, String>();
		classMap = this.getClassMapping();

		Iterator<Entry<String, String>> it = classMap.entrySet().iterator();

		// Creating a Graph
		Graph<String, DefaultEdge> g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

		// Adding the Vertices of the Graph
		for (String clsName : classNames) {
			if (clsName.contains(",")) {
				String clsArr[] = clsName.split(",");
				for (int q = 0; q < clsArr.length; q++) {
					clsArr[q] = clsArr[q].trim();
				}
				for (String cls : clsArr) {
					System.out.println("Vertex : " + cls);
					g.addVertex(cls);
				}
			} else {
				g.addVertex(clsName);
			}
		}

		// Adding the edges of the Graph
		while (it.hasNext()) {
			Entry<String, String> pair = it.next();
			if (pair.getValue() != "") {
				String key = pair.getKey().trim(), value = pair.getValue().trim();
				if (value.contains(",")) {
					String tempArr[] = value.split(",");
					for (int i = 0; i < tempArr.length; i++) {
						tempArr[i] = tempArr[i].trim();
						System.out.println("Key : " + key + " Value : " + tempArr[i]);
						g.addEdge(key, tempArr[i]);
					}
				} else {
					System.out.println("Key : " + key + " Value : " + value);
					g.addEdge(key, value);
				}
			}
		}

		Iterator<String> iterator = new DepthFirstIterator<>(g, childClass);
		int count = 0;

		System.out.println();
		LOGGER.info(CHECKING_CLASS_HIERARCHY_FROM + childClass);
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
		System.out.println("\nNumber of Ancestor Classes For " + childClass + " is : " + (count - 1) + "\n");
		return ansestorList;

	}

	@Override
	public int getNumberOfAncestorClasses(String childClass) {
		return 0;

	}

}
