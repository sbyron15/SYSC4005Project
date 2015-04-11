
public class Queue {
	
	private boolean[] connected;
	private int length;
	private int id;
	private Float connectionProb;
	
	public Queue(int id) {
		connected = new boolean[Runner.NUM_SERVERS];
		length = 0;
		this.id = id;
	}
	
	public void removePacket() {
		length = length - 1;
		if (length < 0) {
			length = 0;
		}
	}
	
	public void generateArrival() {
		length = length + 1;
	}
	
	public void setConnected(int i, boolean connected) {
		this.connected[i] = connected;
	}
	
	public boolean isConnected(int i) {
		return connected[i];
	}
	
	public int getLength() {
		return length;
	}
	
	public int getId() {
		return id;
	}

	public Float getConnectionProb() {
		return connectionProb;
	}

	public void setConnectionProb(Float connectionProb) {
		this.connectionProb = connectionProb;
	}

}
