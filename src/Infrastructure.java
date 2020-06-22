import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Rajat Garg
 *
 */
public class Infrastructure {

	public static void main(String[] args) {

		Scanner scn = new Scanner(System.in);
//		System.out.println("Enter no. of servers: ");
		int n = scn.nextInt();
		scn.nextLine();
//		System.out.println("Enter space separated server IPs");
		String ips = scn.nextLine();
		String[] machineIParray = ips.split(" ");
		if (n != machineIParray.length) {
			System.err.println("Check the input");
			return;
		}
		HashMap<String, Machine> machineIPaddr = new HashMap<String, Machine>();

		for (int i = 0; i < n; i++) {
			machineIPaddr.put(machineIParray[i], new Machine(machineIParray[i]));
		}

//		System.out.println("Enter the number of Reverse Proxies");
		int numProxies = scn.nextInt();
		scn.nextLine();
		ArrayList<String> revProxyList = new ArrayList<String>();
		HashMap<String, reverseProxy> revProxyHM = new HashMap<>();

		for (int i = 0; i < numProxies; i++) {
			String ProxyURL = scn.nextLine();
			revProxyList.add(ProxyURL);
			int numMachines = scn.nextInt();
			scn.nextLine();
			String tempString = scn.nextLine();
			String[] IPlist = tempString.split(" ");
			ArrayList<Machine> machineList = new ArrayList<>();

			for (int j = 0; j < numMachines; j++) {
				machineList.add(machineIPaddr.get(IPlist[j]));
			}
			revProxyHM.put(ProxyURL, new reverseProxy(ProxyURL, IPlist));
			revProxyHM.get(ProxyURL).construct(machineList);
		}

		int queries = scn.nextInt();
		scn.nextLine();
		int q = 0;

		while (q < queries) {
			String inputURL = scn.nextLine();
			String[] inputURLsplit = inputURL.split("/", 2);
			revProxyHM.get(inputURLsplit[0]).request(inputURL);
			q++;
		}

		System.out.println("\nGenerating Machine-wise Logs...\n");

		for (String machineIP : machineIParray) {
			machineIPaddr.get(machineIP).printLogs();
		}
	}
}