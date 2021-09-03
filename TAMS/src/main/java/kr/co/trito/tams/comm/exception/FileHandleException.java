package kr.co.trito.tams.comm.exception;

public class FileHandleException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileHandleException(String msg) {
		super(msg);
	}
	
	public FileHandleException(String msg, Throwable cause) {
		super(msg, cause);
	}
}

