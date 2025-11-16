package FileIO;

public interface Factory<T extends IFile>{
	public T create();
	
}
