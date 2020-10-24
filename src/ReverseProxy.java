import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * @author Rajat Garg
 *
 */
public class ReverseProxy {
	public String proxyURL;
	public LinkedHashSet<String> activeMachines;
	public HashMap<String, Machine> machines;
	public HashMap<String, ArrayList<String>> persistentMem;
	public String[] IPlist;
	public Iterator<String> Iterator;

	/**
	 * @param proxyURL: The URL of a reverse proxy object.
	 * @param IPlist: List of IPs of machine object under reverse proxy object.
	 */
	ReverseProxy(String proxyURL, String[] IPlist) {
		this.proxyURL = proxyURL;
		this.activeMachines = new LinkedHashSet<String>();
		this.machines = new HashMap<String, Machine>();
		this.IPlist = IPlist;
		this.persistentMem = new HashMap<String, ArrayList<String>>();
		this.Iterator = this.activeMachines.iterator();
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
			this.activeMachines.add(m.getIP());
		}
	}

	/**
	 * @param url: The URL of the special request (machine_down).
	 */
	public void machine_down(String url) {
		String[] urlarr = url.split("=", 2);
		this.machines.get(urlarr[1]).machine_down();
		this.activeMachines.remove(urlarr[1]);
		this.machines.remove(urlarr[1]);
		String errStr = "[ERR!]: ****" + urlarr[1] + " is down ****";
		this.persistentMem.get(urlarr[1]).add(errStr);
	}

	/**
	 * @param url: The URL of the special request (machine_up).
	 */
	public void machine_up(String url) {
		String[] urlarr = url.split("=", 2);
		this.machines.put(urlarr[1], new Machine(urlarr[1], this.persistentMem.get(urlarr[1])));
		this.activeMachines.add(urlarr[1]);
	}

	/**
	 * @param url: The URL of any request to reverse proxy object.
	 */
	public void request(String url) {
		if (url.contains("machine_down")) {
			this.machine_down(url);
			return;
		} else if (url.contains("machine_up")) {
			this.machine_up(url);
			return;
		} else {
			if (this.activeMachines.isEmpty()) {
				System.out.println("[ERR!]: **** No Resource Available ****");
				return;
			} else if (!this.Iterator.hasNext()) {
				this.Iterator = this.activeMachines.iterator();
			}
			Machine m = this.machines.get(this.Iterator.next());
			m.request(url);
			persistentMem.put(m.getIP(), m.getLogs());
		}
	}
}
