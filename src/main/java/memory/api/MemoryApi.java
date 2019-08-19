package memory.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import memory.prevayler.Prevayler;
import memory.prevayler.Query;
import memory.prevayler.TransactionWithQuery;

@RestController
public class MemoryApi implements Serializable {

	private static final long serialVersionUID = 1L;

	static Prevayler prevayler;

	static {
		try {
			prevayler = new Prevayler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 		/*
		 * $http.get('rest/example').success(function(data) { //
		 * console.log(data) }).error(function() { console.log('ois2') });
		 * 
		 * 
		 * $http.get('rest/example', { params: { json: { usuario: {
		 *  } } } }).success(function(data) { console.log(data)
		 * }).error(function() { console.log('ois2') });
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Object get(HttpServletRequest request) throws IOException, Exception {
		Object obj = prevayler.execute(new Query() {

			public StringBuilder query(StringBuilder prevalentSystem) throws Exception {

				boolean noParams = request.getParameterMap().size() == 0;

				if (noParams)
					throw new Exception();

				StringBuilder returns = new StringBuilder();

				Entry<String, String[]> entry = request.getParameterMap().entrySet().iterator().next();
				String key = entry.getKey();
				// params is regexp or json
				if ("json".equals(key)) {
					String json = entry.getValue()[0];

					StringBuilder hasg = new StringBuilder(
							json.substring(1, json.indexOf(Constants.DOUBLE_QUOTES.concat(Constants.TWO_POINTS))));
					final Matcher matcher = Pattern.compile("(.+?:(\\[.*?\\](,|$)|[^\\[]*?(,|$)))")
							.matcher(json.toString().replaceFirst(hasg.toString() + ":", "").replace('{', ' ')
									.replace('}', ' ').trim());

					final ArrayList<String> attributes = new ArrayList<String>();

					while (matcher.find())
						attributes.add(matcher.group());

					System.err.println(attributes);
				} else if ("regexp".equals(key)) {
					String regexp = entry.getValue()[0];
					Matcher matcher = Pattern.compile(regexp).matcher(prevalentSystem);
					while (matcher.find()) {
						returns.append(matcher.group());
					}
				} else {
					throw new Exception();
				}

				return returns;
			}
		});

		System.err.println(obj);

		return obj;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody Object put(HttpServletRequest request) throws IOException, Exception {
		// comeca aqui
		final StringBuilder json = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null)
			json.append(line);

		reader = null;
		line = null;

		Object obj = prevayler.execute(new TransactionWithQuery() {

			private static final long serialVersionUID = 1L;

			public final StringBuilder executeAndQuery(final StringBuilder prevalentSystem) throws Exception {

				return null;
			}
		});

		System.err.println(obj);

		return obj;
	}

	/**
	 * 
	 * 		$http.post('rest/example', {
			mercado : {
				nome : 'Angeloni',
				dados : [ {
					abacate : 2.97,
					laranja : 2.15
				}, {
					abacate : 2.95,
					laranja : 2.18,
					mamao : 2.89
				} ]
			}

	 * @param request
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody StringBuilder post(HttpServletRequest request) throws IOException, Exception {
		StringBuilder params = params(request.getInputStream());

		StringBuilder returns = postTransaction(params);

		System.err.println(returns);

		return returns;
	}

	static final StringBuilder params(InputStream inputStream) throws Exception {

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		final StringBuilder params = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null)
			params.append(line);
		inputStream = null;
		reader = null;
		line = null;

		return params;
	}

	static final StringBuilder postTransaction(final StringBuilder json) throws Exception {
		StringBuilder returns = prevayler.execute(new TransactionWithQuery() {

			private static final long serialVersionUID = 1L;

			public final StringBuilder executeAndQuery(final StringBuilder prevalentSystem) throws Exception {

				Matcher matcher = Pattern.compile("(\"[^\"]+)").matcher(json);
				final StringBuffer sb = new StringBuffer();
				while (matcher.find()) {
					matcher.appendReplacement(sb, matcher.group(1) + String.valueOf(json.hashCode()));
				}
				matcher.appendTail(sb);
				System.err.println(sb.toString());

				final StringBuilder record = new StringBuilder();
				String table = json.substring(1, json.indexOf("\" :"));
				StringBuilder b = new StringBuilder(
						new StringBuilder().append(table).append(json.hashCode()).append("\""));
				record.append("{").append(b).append(":").append(json.substring(json.indexOf(":") + 1));

				prevalentSystem.append(prevalentSystem.length() == 0 ? "" : ",").append(record);

				return record;
			}
		});
		return returns;
	}

}