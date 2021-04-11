package io.rtx.presentation;

import io.rtx.data.DaoManager;
import io.rtx.data.DaoManagerImpl;

public class Application {

	public static void main(String[] args) {
		DaoManager daoManager = new DaoManagerImpl();
		Controller controller = new Controller(daoManager);
		controller.start();
	}

}
