package GameLogic;

@FunctionalInterface
public interface IGameRunnable<T extends Entity, R extends String> {
	void run(T params, R params2);
}
