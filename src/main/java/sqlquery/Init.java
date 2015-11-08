package sqlquery;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Init {
	private QueryBean     queryBean;
	private StringPrinter stringPrinter;
	private Logger 		 log = Logger.getLogger(Init.class);

	public Init() throws IOException {
		this.stringPrinter = new StringPrinter();
		init();
	}

	public Init(int lineSize) throws IOException {
		this.stringPrinter = new StringPrinter(lineSize);
		init();
	}

	public void init() throws IOException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(CONTEXT_PATH);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		queryBean = (QueryBean) ctx.getBean("queryBean");

		while (true) {
			System.out.print("> ");
			String command = br.readLine();

			if (command.equalsIgnoreCase("exit")) {
				log.info("Exiting software...");
				break;
			}
			else if (command.length() > 5 &&
				 command.trim().substring(0, 6).compareToIgnoreCase("select") == 0) {
				try {
					String result = stringPrinter.printSelect(queryBean.select(command));
					System.out.println(result);
					log.info("Query `" + command + "` was successfully executed.");
					log.info(result);
				}
				catch (DataAccessException e) {
					System.out.println(e.getMessage());
				}
			}
			else {
				try {
					queryBean.execute(command);
					log.info("Query `" + command + "` was successfully executed.");
					System.out.println("Query executed.");
				}
				catch (DataAccessException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		if (args.length > 0) {
			int lineSize = Integer.parseInt(args[0]);
			new Init(lineSize);
		}
		new Init();
	}


	public static final String CONTEXT_PATH = "context.xml";
}
