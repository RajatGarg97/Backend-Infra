import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class reverseProxy {
		public String proxyURL;
		public Queue<Machine> activeMachines;
		public HashMap<String, Machine> machines;
		public String[] IPlist;

		reverseProxy(String proxyURL, String[] IPlist) {
			this.proxyURL = proxyURL;
			this.activeMachines = new LinkedList<Machine>();
			this.machines = new HashMap<String, Machine>();
			this.IPlist = IPlist;
		}

		public String getURL() {
			return this.proxyURL;
		}

		public void construct(ArrayList<Machine> machineList) {
			for (int i = 0; i < machineList.size(); i++) {
				Machine m = machineList.get(i);
				this.machines.put(m.getIP(), m);
				this.activeMachines.add(m);
			}
		}

		public void request(String url) {
			String[] urlarr = url.split("=", 2);

			if (url.contains("machine_down")) {
				this.machines.get(urlarr[1]).machine_down(url);
			} else if (url.contains("machine_up")) {
//				this.machines.get(urlarr[1]).logger(url);
				ArrayList<String> inactiveMcLog = this.machines.get(urlarr[1]).getLogs();
				this.machines.put(urlarr[1], new Machine(urlarr[1], inactiveMcLog));
				this.activeMachines.add(this.machines.get(urlarr[1]));
			} else {
				while ((!this.activeMachines.isEmpty()) && (!this.activeMachines.peek().status())) {
					this.activeMachines.poll();
				}
				Machine m = this.activeMachines.poll();
				m.logger(url);
				this.activeMachines.add(m);
			}
		}

		public void generateLogs() {

			for (String ip : this.IPlist) {
				this.machines.get(ip).printLogs();
			}
		}
	}