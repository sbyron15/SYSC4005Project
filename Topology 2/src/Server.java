import java.util.List;
import java.util.Random;

public class Server {

	private List<Queue> queues;
	private SelectionPolicy selectionPolicy;
	private Random rand;
	private boolean debug;
	private int id;

	
	public Server(int id, SelectionPolicy selectionPolicy, boolean debug, List<Queue> queues) {
		
		this.queues = queues;
		this.selectionPolicy = selectionPolicy;
		rand = new Random();
		this.debug = debug;
		this.id = id;
	}

	public void updateConnectivity() {
		for (Queue q : queues) {
			Float r = rand.nextFloat();
			if (r < q.getConnectionProb()) {
				q.setConnected(id, true);
			} else {
				q.setConnected(id, false);
			}
		}
	}

	public void selectPacket() {
		Queue selected = selectionPolicy.selectQueue(queues, id);

		if (selected != null) {
			if (selected.getLength() == 0) {
				if (debug) {
					System.out.println("Packet selected from queue "
							+ selected.getId() + " but queue is empty");
				}
			} else if (!selected.isConnected(id)) {
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
	
	public int getNumberOfConnectedQueues() {
		int n = 0;
		for (Queue q : queues) {
			if (q.isConnected(id)) {
				n++;
			}
		}
		return n;
	}

}
