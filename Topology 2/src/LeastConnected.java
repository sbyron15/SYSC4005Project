import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class LeastConnected implements SelectionPolicy {
	
	private Random rand;
	
	public LeastConnected() {
		rand = new Random();
	}

	@Override
	public Queue selectQueue(List<Queue> queues, int id) {
		List<Queue> nonEmpty = new ArrayList<Queue>();
		
		for (Queue q : queues) {
			if (q.isConnected(id) && q.getLength() > 0) {
				nonEmpty.add(q);
			}
		}
		
		int maxLength = 0;
		List<Queue> largestQueue = new ArrayList<Queue>();
		
		for (Queue q : nonEmpty) {
			if (q.getLength() > maxLength) {
				largestQueue = new ArrayList<Queue>();
				largestQueue.add(q);
				maxLength = q.getLength();
			} else if (q.getLength() == maxLength) {
				largestQueue.add(q);
			}
		}
		
		if (!largestQueue.isEmpty()) {
			int r = rand.nextInt(largestQueue.size());
			return largestQueue.get(r);
		} else {
			return null;
		}
	}
	
	@Override
	public List<Server> sortServers(List<Server> servers) {
		Collections.sort(servers, new ConnectionComparator());
		return servers;
	}
	
	private class ConnectionComparator implements Comparator<Server> {

		@Override
		public int compare(Server o1, Server o2) {
			if (o1.getNumberOfConnectedQueues() > o2.getNumberOfConnectedQueues()) {
				return 1;
			} else if (o1.getNumberOfConnectedQueues() < o2.getNumberOfConnectedQueues()) {
				return 0;
			} else {
				return 0;
			}
		}
		
	}

}
