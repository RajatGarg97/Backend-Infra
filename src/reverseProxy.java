import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class reverseProxy {
	public String proxyURL;
	public Queue<Machine> activeMachines;
	public Queue<Machine> readyMachines;
	public HashMap<String, Machine> machines;
	public String[] IPlist;

	reverseProxy(String proxyURL, String[] IPlist) {
		this.proxyURL = proxyURL;
		this.activeMachines = new LinkedList<Machine>();
		this.machines = new HashMap<String, Machine>();
		this.IPlist = IPlist;
		this.readyMachines = new LinkedList<Machine>();
	}

	public String getProxyURL() {
		return this.proxyURL;
	}

	public void construct(ArrayList<Machine> machineList) {
		for (int i = 0; i < machineList.size(); i++) {
			Machine m = machineList.get(i);
			this.machines.put(m.getIP(), m);
			this.activeMachines.add(m);
		}
	}

	public void specialRequest(String url) {
		String[] urlarr = url.split("=", 2);
		if (url.contains("down")) {
			this.machines.get(urlarr[1]).machine_down();
		} else {
			ArrayList<String> inactiveMcLog = this.machines.get(urlarr[1]).getLogs();
			inactiveMcLog.add("----MACHINE UP----");
			this.machines.put(urlarr[1], new Machine(urlarr[1], inactiveMcLog));
			this.readyMachines.add(this.machines.get(urlarr[1]));
		}
	}

	public void request(String url) {
		if (url.contains("machine_down") || url.contains("machine_up")) {
			this.specialRequest(url);
		} else {
			while ((!this.activeMachines.isEmpty()) && (!this.activeMachines.peek().status())) {
				this.activeMachines.poll();
			}
			while (!this.readyMachines.isEmpty()) {
				this.readyMachines.poll();
			}
			Machine m = this.activeMachines.poll();
			m.logger(url);
			this.activeMachines.add(m);
		}
	}

}