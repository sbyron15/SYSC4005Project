import java.util.List;

public class RoundRobin implements SelectionPolicy {

	int lastIndex = 0;

	@Override
	public Queue selectQueue(List<Queue> queues) {
		Queue selected = queues.get(lastIndex);
		lastIndex++;
		
		if (lastIndex > (queues.size() - 1)) {
			lastIndex = 0;
		}
		
		return selected;
	}

}
