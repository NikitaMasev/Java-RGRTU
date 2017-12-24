package runner;

import view.View;
import controller.Controller;
import model.Model;

public class Runner {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Model model = new Model();
		Controller controller = new Controller(model);
		View view = new View(controller);
		model.start();
	}
}
