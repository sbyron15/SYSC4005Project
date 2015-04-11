import java.util.List;


public interface SelectionPolicy {
	
	public Queue selectQueue(List<Queue> queues);

}
