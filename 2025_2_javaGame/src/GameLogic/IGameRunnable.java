package GameLogic;

@FunctionalInterface
public interface IGameRunnable<T extends Entity> {
	void run(T params);
}
