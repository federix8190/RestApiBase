package py.gov.csj.poi.errores;

public abstract class AppException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public AppException(String message) {
		super(message);
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Excepcion que debe ser lanzada cuando ocurre algun error interno en el
	 * servidor.
	 */
	public static class InternalError extends AppException {

		private static final long serialVersionUID = -3139036056656795230L;

		public InternalError(String message, Throwable cause) {
			super(message, cause);
		}

		public InternalError(String message) {
			super(message);
		}

	}

	/**
	 * Excepcion que debe ser lanzada cuando algun parametro requerido no fue
	 * provedio por el invocador del metodo o cuando no se cumple alguna otra
	 * restriccion definida por la logica del negocio.
	 */
	public static class IllegalArgument extends AppException {

		private static final long serialVersionUID = -8765864922699868014L;

		public IllegalArgument(String message) {
			super(message);
		}
	}
	
	/**
	 * Excepcion que debe ser lanzada cuando no se encontro un resultado
	 * adecuado para una invocacion del metodo. 
	 */
	public static class NotFound extends AppException {

		private static final long serialVersionUID = 1149303182428995606L;

		public NotFound(String message) {
			super(message);
		}
	}
}
