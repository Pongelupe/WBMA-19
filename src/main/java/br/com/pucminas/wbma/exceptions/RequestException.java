package br.com.pucminas.wbma.exceptions;

import java.util.Optional;

import okhttp3.Request;
import okhttp3.Response;

/**
 * This exception was design to deal with http request errors such as Auth, Bad
 * Request and the list goes on
 * 
 * @author pongelupe
 *
 */
public class RequestException extends WBMAException {

	private static final long serialVersionUID = 2702002417385250444L;
	private Request request;
	private Optional<Response> response;

	public RequestException(Request request, Response response) {
		super("Error on the http request to " + request.url());
		this.request = request;
		this.response = Optional.ofNullable(response);
	}

	public Request getRequest() {
		return request;
	}

	public Optional<Response> getResponse() {
		return response;
	}
}
