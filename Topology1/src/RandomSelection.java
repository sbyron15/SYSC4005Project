import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RandomSelection implements SelectionPolicy {
	
	private Random rand;
	
	public RandomSelection() {
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
		
		if (!nonEmpty.isEmpty()) {
			int r = rand.nextInt(nonEmpty.size());
			return nonEmpty.get(r);
		} else {
			return null;
		}
	}

}
