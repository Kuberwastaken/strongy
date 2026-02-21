package strongy.event;

public interface IModifiable<T> {

	ISubscribable<T> whenModified();

}
