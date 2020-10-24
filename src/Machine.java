import java.util.ArrayList;

/**
 * @author Rajat Garg
 *
 */
public class Machine {
	public String ip;
	public boolean isActive;
	public ArrayList<String> logFile;

	/**
	 * This is a constructor method.
	 * 
	 * @param ip: It is the IP address of the machine object.
	 */
	Machine(String ip) {
		this.ip = ip;
		this.logFile = new ArrayList<String>();
		this.machine_up();
	}

	/**
	 * This is another constructor method.
	 * 
	 * @param ip: It is the IP address of the machine object.
	 * @param logFile: An ArrayList in which logs of the machine object are stored.
	 */
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

	/**
	 * This method is used to switch status of machine object to inactive.
	 */
	public void machine_down() {
		this.isActive = false;
	}

	public void request(String url) {
		this.logger(url);
	}

	/**
	 * This method is used to switch status of machine object to active.
	 *201
	 */
	public void machine_up() {
		String str = "[INFO]: **** " + this.getIP() + " is up and running ****";
		this.logger(str);
		this.isActive = true;
	}

	/**
	 * @param log: A string containing log request to be saved in log file.
	 */
	public void logger(String log) {
		if (this.status()) {
			String logstr = "[INFO]: " + log + " [Status Code: 200]";
			this.logFile.add(logstr);
		} else {
			this.logFile.add(log);
		}
	}

	/**
	 * Print logs in the console
	 */
	public void printLogs() {
		System.out.println("Generating Logs for " + this.getIP());
		for (int i = 0; i < this.logFile.size(); i++) {
			System.out.println(this.logFile.get(i));
		}
		System.out.println("--------------------------------------------------");
		System.out.println();
	}
}
