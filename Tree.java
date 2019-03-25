package tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Tree {

	public static ArrayList<Boolean> depthMap = new ArrayList<Boolean>();
	public static int max_depth;

	public static void printTree(String dirName, int depth) {
		if (depthMap.size() == depth) {
			depthMap.add(true);
		} else {
			depthMap.set(depth, true);
		}

		File folder = new File(dirName);
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(folder.listFiles()));
		if(depth == 0) {
			System.out.println(folder.getName());
		}
		files.forEach(file -> {
			
			for (int i = 0; i < depth; i++) {
				if (depthMap.get(i)) {
					System.out.print("\u2502");
				} else {
					System.out.print(" ");
				}
				System.out.print("   ");
			}

			if (files.indexOf(file) == files.size() - 1) {
				depthMap.set(depth, false);
				System.out.print("\u2514");
			} else {
				System.out.print("\u251C");
			}

			System.out.println("\u2500 " + file.getName());

			if (file.listFiles() != null) {
				if(depth + 1 < max_depth || max_depth == 0) {
					printTree(file.toString(), depth + 1);
				}
			}
		});
	}
	
	public static void printHelp() {
		System.out.println(
			"Tree [PATHNAME] -depth=x\n" +
			"   -depth=x : The depth of the tree structure. (default is 0, if x is 0 the depth is infinite)\n"
		);
	}

	public static void main(String[] args) throws IOException {

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			
			max_depth = 0;
			
			System.out.print("> ");
			String command = input.readLine();
			String[] commandArr = command.split(" ");

			if (commandArr[0].equals("tree")) {
				if (commandArr.length == 2) {
					if (commandArr[1].equals("/?") || commandArr[1].equals("help")) {
						printHelp();
					} else {
						printTree(commandArr[1], 0);
					}
				} else if (commandArr.length == 3) {
					if (commandArr[2].matches("^-depth=[0-9]+$")) {
						max_depth = Integer.parseInt(commandArr[2].substring(7));
						printTree(commandArr[1], 0);
					} else {
						System.err.println("Invalid arguments");
					}
				} else {
					System.err.println("Invalid syntax, write 'tree help' or 'tree /?' for help");
				}
			} else {
				System.err.println("Invalid command: " + command);
			}
			
			/* Blev tvungen att lägga till detta, annars började nästa loop innan
			 * ev. errors blivit utskrivna
			*/
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
