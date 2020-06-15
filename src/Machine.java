import java.util.ArrayList;

public class Machine {
	public String ip;
	public boolean isActive;
	public ArrayList<String> logFile;
	public int requestCount;

	Machine(String ip) {
		this.ip = ip;
		this.isActive = true;
		this.logFile = new ArrayList<String>();
		this.requestCount = 0;
	}

	Machine(String ip, ArrayList<String> logFile) {
		this.ip = ip;
		this.isActive = true;
		this.logFile = logFile;
		this.requestCount = 0;
	}

	public String getIP() {
		return this.ip;
	}

	public int getRequestCount() {
		return this.requestCount;
	}

	public ArrayList<String> getLogs() {
		return this.logFile;
	}

	public boolean status() {
		return this.isActive;
	}

	public void machine_down() {
		this.isActive = false;
		this.logger("----MACHINE DOWN----");
	}

//	public void request(String url) {
//		if (url.contains("down")) {
//			this.machine_down();
//		} else if (url.contains("up")) {
//			
//		}
//	}

	public void logger(String log) {
		this.logFile.add(log);
	}

	public void printLogs() {
		System.out.println(this.getIP());
		for (int i = 0; i < this.logFile.size(); i++) {
			System.out.println(this.logFile.get(i));
		}
		System.out.println("---------------------------");
	}
}