import java.util.List;


public interface SelectionPolicy {
	
	public Queue selectQueue(List<Queue> queues, int id);
	
	public List<Server> sortServers(List<Server> servers);

}
