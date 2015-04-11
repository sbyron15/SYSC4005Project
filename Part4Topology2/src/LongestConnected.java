import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class LongestConnected implements SelectionPolicy {
	
	private Random rand;
	
	public LongestConnected() {
		rand = new Random();
	}

	@Override
	public Queue selectQueue(List<Queue> queues) {
		List<Queue> nonEmpty = new ArrayList<Queue>();
		
		for (Queue q : queues) {
			if (q.isConnected() && q.getLength() > 0) {
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

}
