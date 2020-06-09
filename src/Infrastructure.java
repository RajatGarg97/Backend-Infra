import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Infrastructure {

	public static void main(String[] args) {

		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		scn.nextLine();
		String ips = scn.nextLine();
		String[] machineURLarray = ips.split(" ");
		HashMap<String, Machine> machineURLs = new HashMap<String, Machine>();

		for (int i = 0; i < n; i++) {
			machineURLs.put(machineURLarray[i], new Machine(machineURLarray[i]));
		}

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
				machineList.add(machineURLs.get(IPlist[j]));
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

		for (String revProxy : revProxyList) {
			revProxyHM.get(revProxy).generateLogs();
		}
	}
}