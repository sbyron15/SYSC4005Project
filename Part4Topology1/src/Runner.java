import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Runner {
	public final static int NUM_QUEUES = 5;
	public final static int NUM_SERVERS = 3;

	static List<Queue> queues = new ArrayList<Queue>();
	static List<Server> servers = new ArrayList<Server>();

	static Random rand;

	static int queueLengths = 0;
	
	static Float un = null;

	public static void main(String[] args) {

		 SelectionPolicy policy = new RandomSelection();
		// SelectionPolicy policy = new LeastConnected();
//		SelectionPolicy policy = new LongestConnected();

		rand = new Random();

		for (int k = 1; k < 11; k++) {
			float connectionProb = 0.5f;
			float arrivalProb = (float) (0.02 * k);
			System.out.println("lambda=" + arrivalProb + "\t");

			for (int j = 0; j < 20; j++) {

				queues = new ArrayList<Queue>();
				servers = new ArrayList<Server>();
				queueLengths = 0;

				for (int q = 0; q < NUM_QUEUES; q++) {
					Queue queue = new Queue(q);
					queue.setConnectionProb(connectionProb);
					queues.add(queue);
				}

				for (int s = 0; s < NUM_SERVERS; s++) {
					Server server = new Server(s, policy, false, queues);
					servers.add(server);
				}

				for (int i = 0; i < 50000; i++) {

					for (Server server : servers) {
						server.updateConnectivity();
					}

					servers = policy.sortServers(servers);

					for (Server server : servers) {
						server.selectPacket();
					}

					generateArrivals(arrivalProb);
					collectStatistics();
				}

				printStatistics();

			}

		}

	}

	public static void generateArrivals(float arrivalProb) {
		for (Queue q : queues) {
			float correlation = 0.4f;
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

	public static void collectStatistics() {
		for (Queue q : queues) {
			queueLengths += q.getLength();
		}
	}

	public static void printStatistics() {
		System.out.println((float) queueLengths / 250000);
	}

}
