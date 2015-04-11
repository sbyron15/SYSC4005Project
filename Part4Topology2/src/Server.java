import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server {

	private static final int NUM_QUEUES = 5;
	private List<Queue> queues;
	private SelectionPolicy selectionPolicy;
	private Random rand;
	private Float arrivalProb;
	private boolean debug;
	
	private Float un = null;

	private int queueLengths;

	public Server(SelectionPolicy selectionPolicy, float[] connectionProb,
			Float arrivalProb, boolean debug) {
		queues = new ArrayList<Queue>();

		for (int i = 0; i < NUM_QUEUES; i++) {
			Queue q = new Queue(i);
			q.setConnectionProb(connectionProb[i]);
			queues.add(q);
			
		}

		this.selectionPolicy = selectionPolicy;
		rand = new Random();
		this.arrivalProb = arrivalProb;
		this.debug = debug;
	}

	public void updateConnectivity() {
		for (Queue q : queues) {
			Float r = rand.nextFloat();
			if (r < q.getConnectionProb()) {
				q.setConnected(true);
			} else {
				q.setConnected(false);
			}
		}
	}

	public void selectPacket() {
		Queue selected = selectionPolicy.selectQueue(queues);

		if (selected != null) {
			if (selected.getLength() == 0) {
				if (debug) {
					System.out.println("Packet selected from queue "
							+ selected.getId() + " but queue is empty");
				}
			} else if (!selected.isConnected()) {
				if (debug) {
					System.out.println("Packet selected from queue "
							+ selected.getId() + " but queue is disconnected");
				}
			} else {
				selected.removePacket();
				if (debug) {
					System.out.println("Packet selected from queue "
							+ selected.getId());
				}
			}
		}
	}

	public void generateArrivals() {
		
		for (Queue q : queues) {
			float correlation = 0.1f;
			float v = rand.nextFloat()*2*correlation;
			v = v - correlation;
			
			float u;
			
			if (un == null) {
				u = rand.nextFloat();
			} else {
				u = un;
			}
			
			u = u + v;
			
			if (u>1.0f) {
				u = u - 1.0f;
			} else if (u<0) {
				u = u + 1.0f;
			}
			
			un = u;
			
			if (u < arrivalProb * (q.getId() + 1)) {
				q.generateArrival();
			}
		}
	}

	public void collectStatistics() {
		for (Queue q : queues) {
			queueLengths += q.getLength();
		}
	}

	public void printStatistics() {
		System.out.println((float) queueLengths / 250000);
	}

}
