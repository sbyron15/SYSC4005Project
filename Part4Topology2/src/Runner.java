
public class Runner {

	public static void main(String[] args) {

		SelectionPolicy policy = new RandomSelection();
//		SelectionPolicy policy = new RoundRobin();
//		SelectionPolicy policy = new LongestConnected();

		for (int k = 1; k < 11; k++) {
			float connectionProb[] = {1.0f, 0.8f, 0.6f, 0.4f, 0.2f};
			float arrivalProb = (float) (0.006*k);
			System.out.println("lambda=" + arrivalProb + "\t");

			for (int j = 0; j < 20; j++) {
				Server server = new Server(policy, connectionProb,
						arrivalProb, false);
				for (int i = 0; i < 50000; i++) {
					server.updateConnectivity();
					server.selectPacket();
					server.generateArrivals();
					server.collectStatistics();
				}

				server.printStatistics();
			}

		}

	}

}
