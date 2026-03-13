package in.co.rays.proj4.exception;

public class DatabaseServerDownException extends RuntimeException {
	public DatabaseServerDownException(String msg) {
		super(msg);
	}
}
