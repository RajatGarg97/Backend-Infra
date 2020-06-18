import java.util.ArrayList;

public class Machine {
	public String ip;
	public boolean isActive;
	public ArrayList<String> logFile;

	Machine(String ip) {
		this.ip = ip;
		this.isActive = true;
		this.logFile = new ArrayList<String>();
	}

	Machine(String ip, ArrayList<String> logFile) {
		this.ip = ip;
		this.logFile = logFile;
		this.machine_up();
	}

	public String getIP() {
		return this.ip;
	}

	public ArrayList<String> getLogs() {
		return this.logFile;
	}

	public boolean status() {
		return this.isActive;
	}

	public void machine_down() {
		this.isActive = false;
		String errStr = "[ERR!]: " + this.getIP() + " is down";
		this.logger(errStr);
//		System.out.println(errStr);
	}
	
	public void machine_up() {
		String str = "[INFO]: " + this.getIP() + " is up and running";
		this.logger(str);
//		System.out.println(str);
		this.isActive = true;
	}

	public void logger(String log) {
		if(this.status()) {
		String logstr = "[INFO]: " + log + " [response: 200]";
		this.logFile.add(logstr);
		} else {
			this.logFile.add(log);
		}
		
//		if(this.status()) {
//			System.out.println("[INFO]: " + this.getIP() + " returned 200");
//		}
	}

	public void printLogs() {
		System.out.println("Generating Logs for " + this.getIP());
		for (int i = 0; i < this.logFile.size(); i++) {
			System.out.println(this.logFile.get(i));
		}
		System.out.println("---------------------------");
		System.out.println();
	}
}