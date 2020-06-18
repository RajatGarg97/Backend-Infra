import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Rajat Garg
 *
 */
public class reverseProxy {
	public String proxyURL;
	public Deque<Machine> activeMachines;
	public HashMap<String, Machine> machines;
	public HashMap<String, ArrayList<String>> persistentMem;
	public String[] IPlist;

	/**
	 * @param proxyURL: The URL of a reverse proxy object.
	 * @param IPlist: List of IPs of machine object under reverse proxy object.
	 */
	reverseProxy(String proxyURL, String[] IPlist) {
		this.proxyURL = proxyURL;
		this.activeMachines = new LinkedList<Machine>();
		this.machines = new HashMap<String, Machine>();
		this.IPlist = IPlist;
		this.persistentMem = new HashMap<String, ArrayList<String>>();
	}

	public String getProxyURL() {
		return this.proxyURL;
	}

	/**
	 * @param machineList: List of machine objects under a reverse proxy object.
	 */
	public void construct(ArrayList<Machine> machineList) {
		for (int i = 0; i < machineList.size(); i++) {
			Machine m = machineList.get(i);
			this.machines.put(m.getIP(), m);
			this.activeMachines.add(m);
		}
	}

	/**
	 * @param url: The URL of the special request (machine_up/machine_down).
	 */
	public void specialRequest(String url) {
		String[] urlarr = url.split("=", 2);
		if (url.contains("down")) {
			this.machines.get(urlarr[1]).machine_down();
		} else {
			this.machines.put(urlarr[1], new Machine(urlarr[1], this.persistentMem.get(urlarr[1])));
			this.activeMachines.addFirst(this.machines.get(urlarr[1]));
		}
	}

	/**
	 * @param url: The URL of any request to reverse proxy object.
	 */
	public void request(String url) {
		if (url.contains("machine_down") || url.contains("machine_up")) {
			this.specialRequest(url);
		} else {

			while ((!this.activeMachines.isEmpty()) && (!this.activeMachines.peek().status())) {
				this.activeMachines.remove();
			}
			if (this.activeMachines.isEmpty()) {
				System.out.println("[ERR!]: No Machine Available");
				return;
			}
			Machine m = this.activeMachines.poll();
			m.logger(url);
			persistentMem.put(m.getIP(), m.getLogs());
			this.activeMachines.add(m);
		}
	}
}