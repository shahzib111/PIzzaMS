package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainScrn extends Application {
	int cod = 0;
	int cuphone;
	int finishCheck = 0;

	@Override
	public void start(Stage primaryStage) throws Exception {
		mainScrn();
	}

	public void mainScrn() {
		Stage primaryStage = new Stage();
		GridPane gp = new GridPane();
		for (int i = 0; i < 5; i++) {
			gp.getColumnConstraints().add(new ColumnConstraints(200)); // column
																		// is
																		// wide
		}

		for (int i = 0; i < 7; i++) {
			gp.getRowConstraints().add(new RowConstraints(50)); // column 1 is
																// // 50 wide
		}
		for (int i = 0; i < 3; i++) {
			gp.getRowConstraints().add(new RowConstraints(100)); // column 1 is
																	// 100 wide
		}

		Text AskEmployeeId = new Text("Enter Employee Id:");
		Text InvalidId = new Text("Invalid Id Try Again");
		TextField EmployeeIdInput = new TextField();
		Button enter = new Button("Enter");

		gp.add(AskEmployeeId, 1, 0);
		gp.add(EmployeeIdInput, 2, 0);
		gp.add(enter, 2, 1);

		enter.setOnMouseClicked(e -> {
			try {
				// 1. Get a connection
				Connection myConn = DriverManager.getConnection(
						"jdbc:mysql://localhost/PizzaMS?verifyServerCertificate=false&useSSL=true", "root", "password");
				// 2. Create a statement
				Statement myStmt = myConn.createStatement();
				// 3. Execute SQL query
				ResultSet myRs = myStmt.executeQuery(
						"select EmployeeID from PizzaMS where EmployeeID = '" + EmployeeIdInput.getText() + "'");
				// 4. process result set
				if (myRs.next()) {
					primaryStage.close();
					// next scene here
					CarryOrDeli();
				} else {
					gp.add(InvalidId, 3, 1);
				}
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		});
		Scene scene = new Scene(gp, 1000, 800);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Pizza Management System");

	}

	public void CarryOrDeli() throws FileNotFoundException {
		Stage option = new Stage();
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(0, 100, 0, 0));

		for (int i = 0; i < 6; i++) {
			gp.getColumnConstraints().add(new ColumnConstraints(165)); // column
																		// is
																		// wide
		}

		for (int i = 0; i < 7; i++) {
			gp.getRowConstraints().add(new RowConstraints(200)); // column 1 is
																	// // 50
																	// wide
		}
		// <------------------------------------------------------->
		// getting images from files.
		FileInputStream one = new FileInputStream("carryout.jpeg");
		FileInputStream two = new FileInputStream("delivery.jpeg");

		// converting to a viewable node image
		Image c = new Image(one);
		Image d = new Image(two);
		ImageView carryout = new ImageView(c);
		ImageView delivery = new ImageView(d);

		Text title = new Text("       Chose Carryout or Delivery");
		title.setFill(Color.BLUE);
		title.setStyle("-fx-font: 24 arial;");

		carryout.setFitWidth(100);
		carryout.setFitHeight(100);

		delivery.setFitWidth(100);
		delivery.setFitHeight(100);

		gp.add(carryout, 2, 1);
		gp.add(delivery, 4, 1);
		gp.add(title, 2, 0, 3, 1);

		Button backToId = new Button("Back");

		backToId.setOnMouseClicked(e -> {
			option.close();
			mainScrn();
		});

		delivery.setOnMouseClicked(e -> {
			option.close();
			onDeliveryClick();
		});

		carryout.setOnMouseClicked(e -> {
			option.close();
			onCarryoutClick();
		});

		gp.add(backToId, 3, 2);

		Scene scene = new Scene(gp, 1000, 1000);
		option.setScene(scene);
		option.show();
		option.setTitle("Pizza Management System");
	}

	public void onDeliveryClick() {

		cod = 1;

		Stage deliveryStage = new Stage();

		BorderPane bp = new BorderPane();
		StackPane sp = new StackPane();
		GridPane gp = new GridPane();

		gp.setPadding(new Insets(10, 10, 10, 10));

		// Top Stack Pane Setup
		Text delivery = new Text("Delivery");
		delivery.setFill(Color.BLACK);
		sp.getChildren().add(delivery);
		sp.setAlignment(Pos.CENTER);

		// Right Grid Pane setup
		for (int i = 0; i < 11; i++) {
			gp.getColumnConstraints().add(new ColumnConstraints(50)); // column
																		// is
																		// wide
		}
		for (int i = 0; i < 50; i++) {
			gp.getColumnConstraints().add(new ColumnConstraints(50)); // column
																		// is
																		// wide
		}
		// label for phone
		Label inphone = new Label("Phone Number: ");
		inphone.setMinWidth(Region.USE_PREF_SIZE);
		inphone.setMaxWidth(Region.USE_PREF_SIZE);

		// textfield for phone
		TextField phone = new TextField();
		phone.setMinWidth(100);
		phone.setMaxWidth(1000);

		// textarea for previous order
		TextArea prevo = new TextArea();
		prevo.setPrefRowCount(100);
		prevo.setPrefColumnCount(20);

		// label for name
		Label inname = new Label("Name: ");
		inname.setMinWidth(Region.USE_PREF_SIZE);
		inname.setMaxWidth(Region.USE_PREF_SIZE);

		// textfield for name
		TextField name = new TextField();
		name.setMinWidth(100);
		name.setMaxWidth(1000);

		// label for Address Field 1
		Label inaf1 = new Label("Address Field 1: ");
		inaf1.setMinWidth(Region.USE_PREF_SIZE);
		inaf1.setMaxWidth(Region.USE_PREF_SIZE);

		// textfield for Address field 1
		TextField af1 = new TextField();
		af1.setMinWidth(100);
		af1.setMaxWidth(1000);

		// label for Address Field 2
		Label inaf2 = new Label("Address Field 2: ");
		inaf2.setMinWidth(Region.USE_PREF_SIZE);
		inaf2.setMaxWidth(Region.USE_PREF_SIZE);

		// textfield for Address field 2
		TextField af2 = new TextField();
		af2.setMinWidth(100);
		af2.setMaxWidth(1000);

		// label for city
		Label incity = new Label("City: ");
		incity.setMinWidth(Region.USE_PREF_SIZE);
		incity.setMaxWidth(Region.USE_PREF_SIZE);

		// textfield for city
		TextField city = new TextField();

		// label for state
		Label instate = new Label("State: ");
		instate.setMinWidth(Region.USE_PREF_SIZE);
		instate.setMaxWidth(Region.USE_PREF_SIZE);

		// textfield for state
		TextField state = new TextField("GA");

		// label for zip code
		Label inzip = new Label("Zip Code:  ");
		inzip.setMinWidth(Region.USE_PREF_SIZE);
		inzip.setMaxWidth(Region.USE_PREF_SIZE);

		// textfield for state
		TextField zip = new TextField();

		Text error = new Text("Please Enter the required feilds");

		// label for Driver instructions
		Label indi = new Label("Driver Instructions:");
		indi.setMinWidth(Region.USE_PREF_SIZE);
		indi.setMaxWidth(Region.USE_PREF_SIZE);

		// textarea for Driver Instructions
		TextArea di = new TextArea();
		di.setPrefRowCount(4);

		// gap between gridpane row and columns
		gp.setHgap(10);
		gp.setVgap(15);

		Button next = new Button("Next");
		Button backToSelect = new Button("Back");

		phone.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.length() == 10) {
					try {
						// 1. Get a connection
						Connection myConn = DriverManager.getConnection(
								"jdbc:mysql://localhost/PizzaMS?verifyServerCertificate=false&useSSL=true", "root",
								"password");
						// 2. Create a statement
						Statement myStmt = myConn.createStatement();
						// 3. Execute SQL query

						ResultSet myRs = myStmt
								.executeQuery("select * from CustomerInfo where Phone = '" + phone.getText() + "'");

						// 4. process result set
						while (myRs.next()) {
							name.setText(myRs.getString(2));
							af1.setText(myRs.getString(3));
							af2.setText(myRs.getString(4));
							city.setText(myRs.getString(5));
							state.setText(myRs.getString(6));
							zip.setText(myRs.getString(7));
							di.setText(myRs.getString(8));
						}
						Statement myStmt1 = myConn.createStatement();
						// 3. Execute SQL query

						ResultSet myRs1 = myStmt1
								.executeQuery("select * from PreviousOrder where Phones = '" + phone.getText() + "'");
						while (myRs1.next()) {
							prevo.setText(myRs1.getString(2));
						}

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});

		next.setOnMouseClicked(e -> {
			if (phone.getText().length() == 10 && name.getText().length() > 3 && af1.getText().length() > 8
					&& city.getText().length() > 2 && state.getText().length() > 0 && zip.getText().length() > 4) {
				try {
					// 1. Get a connection
					Connection myConn = DriverManager.getConnection(
							"jdbc:mysql://localhost/PizzaMS?verifyServerCertificate=false&useSSL=true", "root",
							"password");
					// 2. Create a statement
					Statement myStmt = myConn.createStatement();
					// 3. Execute SQL query
					ResultSet myRs = myStmt
							.executeQuery("select * from CustomerInfo where Phone = '" + phone.getText() + "'");
					// 4. process result set
					if (myRs.next()) {
						deliveryStage.close();
					} else {
						myStmt = myConn.createStatement();

						String sql = "INSERT INTO CustomerInfo " + "VALUES ('" + phone.getText() + "', '"
								+ name.getText() + "', '" + af1.getText() + "','" + af2.getText() + "','"
								+ city.getText() + "','" + state.getText() + "', '" + zip.getText() + "','"
								+ di.getText() + "')";
						myStmt.executeUpdate(sql);

					}

				} catch (Exception exc) {
					exc.printStackTrace();
				}

				deliveryStage.close();
				try {
					chosePizza(phone);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				gp.add(error, 2, 24);
			}
		});

		// back button set
		backToSelect.setOnMouseClicked(e -> {
			deliveryStage.close();
			try {
				CarryOrDeli();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		// adding everything to gridpane
		gp.add(inphone, 0, 0);
		gp.add(phone, 2, 0);
		gp.add(inname, 4, 0);
		gp.add(name, 5, 0, 5, 1);
		gp.add(inaf1, 0, 2);
		gp.add(af1, 2, 2, 6, 2);
		gp.add(inaf2, 0, 4);
		gp.add(af2, 2, 3, 6, 3);
		gp.add(incity, 0, 6);
		gp.add(city, 1, 4, 3, 5);
		gp.add(instate, 0, 8);
		gp.add(state, 1, 8);
		gp.add(inzip, 2, 8);
		gp.add(zip, 3, 8);
		gp.add(indi, 0, 10);
		gp.add(di, 0, 11, 8, 13);
		gp.add(next, 4, 25);
		gp.add(backToSelect, 2, 25);
		gp.add(prevo, 10, 0, 6, 30);

		bp.setRight(gp);
		bp.setTop(sp);

		Scene scene = new Scene(bp, 1000, 1000);
		deliveryStage.setScene(scene);
		deliveryStage.show();
		deliveryStage.setTitle("Pizza Management System");
	}

	public void onCarryoutClick() {
		cod = 2;

		Stage carryoutStage = new Stage();

		BorderPane bp = new BorderPane();
		StackPane sp = new StackPane();
		GridPane gp = new GridPane();

		gp.setPadding(new Insets(10, 10, 10, 10));

		// Top Stack Pane Setup
		Text carout = new Text("CARRY OUT");
		carout.setFill(Color.BLACK);
		sp.getChildren().add(carout);
		sp.setAlignment(Pos.CENTER);

		TextArea prevo = new TextArea();
		prevo.setPrefRowCount(100);
		prevo.setPrefColumnCount(20);

		Text error = new Text("Please Enter the required feilds");

		// Right Grid Pane setup
		for (int i = 0; i < 11; i++) {
			gp.getColumnConstraints().add(new ColumnConstraints(50)); // column
																		// is
																		// wide
		}
		for (int i = 0; i < 11; i++) {
			gp.getColumnConstraints().add(new ColumnConstraints(50)); // column
																		// is
																		// wide
		}
		// label for phone
		Label inphone = new Label("Phone Number: ");
		inphone.setMinWidth(Region.USE_PREF_SIZE);
		inphone.setMaxWidth(Region.USE_PREF_SIZE);

		// textfield for phone
		TextField phone = new TextField();
		phone.setMinWidth(100);
		phone.setMaxWidth(1000);

		// label for name
		Label inname = new Label("Name: ");
		inname.setMinWidth(Region.USE_PREF_SIZE);
		inname.setMaxWidth(Region.USE_PREF_SIZE);

		// textfield for name
		TextField name = new TextField();
		name.setMinWidth(100);
		name.setMaxWidth(1000);

		// label for Address Field 1
		Label inaf1 = new Label("Address Field 1: ");
		inaf1.setMinWidth(Region.USE_PREF_SIZE);
		inaf1.setMaxWidth(Region.USE_PREF_SIZE);

		// textfield for Address field 1
		TextField af1 = new TextField();
		af1.setMinWidth(100);
		af1.setMaxWidth(1000);
		af1.setDisable(true);

		// label for Address Field 2
		Label inaf2 = new Label("Address Field 2: ");
		inaf2.setMinWidth(Region.USE_PREF_SIZE);
		inaf2.setMaxWidth(Region.USE_PREF_SIZE);

		// textfield for Address field 2
		TextField af2 = new TextField();
		af2.setMinWidth(100);
		af2.setMaxWidth(1000);
		af2.setDisable(true);

		// label for city
		Label incity = new Label("City: ");
		incity.setMinWidth(Region.USE_PREF_SIZE);
		incity.setMaxWidth(Region.USE_PREF_SIZE);

		// textfield for city
		TextField city = new TextField();
		city.setDisable(true);

		// label for state
		Label instate = new Label("State: ");
		instate.setMinWidth(Region.USE_PREF_SIZE);
		instate.setMaxWidth(Region.USE_PREF_SIZE);

		// textfield for state
		TextField state = new TextField("GA");
		state.setDisable(true);

		// label for zip code
		Label inzip = new Label("Zip Code:  ");
		inzip.setMinWidth(Region.USE_PREF_SIZE);
		inzip.setMaxWidth(Region.USE_PREF_SIZE);

		// textfield for zip
		TextField zip = new TextField();
		zip.setDisable(true);

		// label for Driver instructions
		Label indi = new Label("Driver Instructions:");
		indi.setMinWidth(Region.USE_PREF_SIZE);
		indi.setMaxWidth(Region.USE_PREF_SIZE);

		// textfield for Driver Instructions
		TextArea di = new TextArea();
		di.setPrefRowCount(4);
		di.setDisable(true);

		// gap between gridpane row and columns
		gp.setHgap(10);
		gp.setVgap(15);

		// back and next buttons
		Button next = new Button("Next");
		Button backToSelect = new Button("Back");

		phone.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.length() == 10) {
					try {
						// 1. Get a connection
						Connection myConn = DriverManager.getConnection(
								"jdbc:mysql://localhost/PizzaMS?verifyServerCertificate=false&useSSL=true", "root",
								"password");
						// 2. Create a statement
						Statement myStmt = myConn.createStatement();
						// 3. Execute SQL query

						ResultSet myRs = myStmt
								.executeQuery("select Name from CustomerInfo where Phone = '" + phone.getText() + "'");
						while (myRs.next()) {
							name.setText(myRs.getString(1));
						}

						Statement myStmt1 = myConn.createStatement();
						// 3. Execute SQL query

						ResultSet myRs1 = myStmt1
								.executeQuery("select * from PreviousOrder where Phones = '" + phone.getText() + "'");
						while (myRs1.next()) {
							prevo.setText(myRs1.getString(2));
						}

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// 4. process result set

				}
			}
		});

		next.setOnMouseClicked(e -> {
			if (phone.getText().length() == 10 && name.getText().length() > 1) {
				try {
					// 1. Get a connection
					Connection myConn = DriverManager.getConnection(
							"jdbc:mysql://localhost/PizzaMS?verifyServerCertificate=false&useSSL=true", "root",
							"password");
					// 2. Create a statement
					Statement myStmt = myConn.createStatement();
					// 3. Execute SQL query
					ResultSet myRs = myStmt
							.executeQuery("select * from CustomerInfo where Phone = '" + phone.getText() + "'");
					// 4. process result set
					if (myRs.next()) {
						carryoutStage.close();
					} else {
						myStmt = myConn.createStatement();

						String sql = "INSERT INTO CustomerInfo " + "VALUES ('" + phone.getText() + "', '"
								+ name.getText() + "', '" + af1.getText() + "','" + af2.getText() + "','"
								+ city.getText() + "','" + state.getText() + "', '" + 30060 + "','" + di.getText()
								+ "')";
						myStmt.executeUpdate(sql);

					}

				} catch (Exception exc) {
					exc.printStackTrace();
				}

				carryoutStage.close();
				try {
					chosePizza(phone);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				gp.add(error, 3, 25);
			}
		});

		// back button set
		backToSelect.setOnMouseClicked(e -> {
			carryoutStage.close();
			try {
				CarryOrDeli();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		// adding everything to gridpane
		gp.add(inphone, 0, 0);
		gp.add(phone, 2, 0);
		gp.add(inname, 4, 0);
		gp.add(name, 5, 0, 5, 1);
		gp.add(inaf1, 0, 2);
		gp.add(af1, 2, 2, 6, 2);
		gp.add(inaf2, 0, 4);
		gp.add(af2, 2, 3, 6, 3);
		gp.add(incity, 0, 6);
		gp.add(city, 1, 4, 3, 5);
		gp.add(instate, 0, 8);
		gp.add(state, 1, 8);
		gp.add(inzip, 2, 8);
		gp.add(zip, 3, 8);
		gp.add(indi, 0, 10);
		gp.add(di, 0, 11, 8, 13);
		gp.add(next, 4, 26);
		gp.add(backToSelect, 2, 26);
		gp.add(prevo, 10, 0, 6, 30);

		bp.setRight(gp);
		bp.setTop(sp);

		Scene scene = new Scene(bp, 1000, 1000);
		carryoutStage.setScene(scene);
		carryoutStage.show();
		carryoutStage.setTitle("Pizza Management System");
	}

	double total = 0;

	public void chosePizza(TextField cphone) throws FileNotFoundException, SQLException {

		// getting images from files.
		FileInputStream inbeef = new FileInputStream("beef.jpg");
		FileInputStream ingp = new FileInputStream("greenpepper.jpeg");
		FileInputStream inonion = new FileInputStream("onion.jpg");
		FileInputStream inpep = new FileInputStream("pepparoni.png");
		FileInputStream inchicken = new FileInputStream("chicken.jpg");
		FileInputStream inham = new FileInputStream("ham.jpg");
		FileInputStream inmush = new FileInputStream("mushrooms.jpg");
		FileInputStream inolives = new FileInputStream("olives.jpg");
		FileInputStream intoma = new FileInputStream("tomatos.jpg");
		FileInputStream inspin = new FileInputStream("spinach.jpg");

		// side images import
		FileInputStream inpepsi = new FileInputStream("pepsi.jpg");
		FileInputStream inmountain = new FileInputStream("mountaindew.jpg");
		FileInputStream incrush = new FileInputStream("crush.png");
		FileInputStream indrpepper = new FileInputStream("drpepper.jpeg");
		FileInputStream inbread = new FileInputStream("breadsticks.jpg");
		FileInputStream incheese = new FileInputStream("cheesesticks.png");
		FileInputStream inbrownie = new FileInputStream("brownie.jpg");
		FileInputStream inbwings = new FileInputStream("bwings.png");
		FileInputStream inwings = new FileInputStream("wings.jpg");

		// converting to a viewable node image
		Image ibeef = new Image(inbeef);
		Image igp = new Image(ingp);
		Image ionion = new Image(inonion);
		Image ipep = new Image(inpep);
		Image ichicken = new Image(inchicken);
		Image iham = new Image(inham);
		Image imush = new Image(inmush);
		Image iolive = new Image(inolives);
		Image itoma = new Image(intoma);
		Image ispin = new Image(inspin);

		// converting to viewable node image
		Image ipepsi = new Image(inpepsi);
		Image imountain = new Image(inmountain);
		Image icrush = new Image(incrush);
		Image idrpepper = new Image(indrpepper);
		Image ibread = new Image(inbread);
		Image icheese = new Image(incheese);
		Image ibrownie = new Image(inbrownie);
		Image iwings = new Image(inwings);
		Image ibwings = new Image(inbwings);

		// crating imageview
		ImageView beef = new ImageView(ibeef);
		ImageView greenpepper = new ImageView(igp);
		ImageView onion = new ImageView(ionion);
		ImageView pep = new ImageView(ipep);
		ImageView chicken = new ImageView(ichicken);
		ImageView ham = new ImageView(iham);
		ImageView mush = new ImageView(imush);
		ImageView olive = new ImageView(iolive);
		ImageView toma = new ImageView(itoma);
		ImageView spin = new ImageView(ispin);

		// crating imageview
		ImageView pepsi = new ImageView(ipepsi);
		ImageView mtndew = new ImageView(imountain);
		ImageView crush = new ImageView(icrush);
		ImageView drpep = new ImageView(idrpepper);
		ImageView bread = new ImageView(ibread);
		ImageView cheese = new ImageView(icheese);
		ImageView brownie = new ImageView(ibrownie);
		ImageView wings = new ImageView(iwings);
		ImageView bwings = new ImageView(ibwings);

		TextArea rcpt = new TextArea("                              Pizza");
		rcpt.setPrefRowCount(100);
		rcpt.setPrefColumnCount(20);

		ResultSet myRs;
		String phone;
		String name;
		String af1;
		String af2;
		String city;
		String state;
		String zip;
		String di;

		// 1. Get a connection
		Connection myConn = DriverManager.getConnection(
				"jdbc:mysql://localhost/PizzaMS?verifyServerCertificate=false&useSSL=true", "root", "password");
		// 2. Create a statement
		Statement myStmt = myConn.createStatement();
		// 3. Execute SQL query
		myRs = myStmt.executeQuery("select * from CustomerInfo where Phone = '" + cphone.getText() + "'");
		// 4. process result set
		if (myRs.next()) {
			phone = myRs.getString(1);
			name = myRs.getString(2);
			af1 = myRs.getString(3);
			af2 = myRs.getString(4);
			city = myRs.getString(5);
			state = myRs.getString(6);
			zip = myRs.getString(7);
			di = myRs.getString(8);

			rcpt.appendText("\n\n");
			rcpt.appendText(name + "\n");
			rcpt.appendText(phone + "\n");
			rcpt.appendText(af1 + "\n");
			if (af2.length() > 0) {
				rcpt.appendText(af2 + "\n");
			}
			rcpt.appendText(city + " ");
			rcpt.appendText(state + " ");
			rcpt.appendText(zip + "\n \n");
			rcpt.appendText(di + "\n\n");
		}

		Button next = new Button("Next");

		next.setOnMouseClicked(s -> {

			try {
				// 1. Get a connection
				Connection myConn1 = DriverManager.getConnection(
						"jdbc:mysql://localhost/PizzaMS?verifyServerCertificate=false&useSSL=true", "root", "password");
				// 2. Create a statement
				Statement myStmt1 = myConn1.createStatement();
				// 3. Execute SQL query

				String sql = "INSERT INTO PreviousOrder " + "VALUES ('" + cphone.getText() + "', '" + rcpt.getText()
						+ "')";
				myStmt.executeUpdate(sql);

			} catch (SQLException b) {
				// TODO Auto-generated catch block
				b.printStackTrace();
			}

		});

		Stage chosePizza = new Stage();
		BorderPane bp = new BorderPane();
		TabPane tb = new TabPane();
		Pane pane = new Pane();

		Tab pizzaSelect = new Tab("Pizza");
		Tab sideSelect = new Tab("Sides");
		Button back = new Button("Back");

		pizzaSelect.setClosable(false);
		sideSelect.setClosable(false);

		pizzaSelect.setContent(new Rectangle(500, 1000, Color.LIGHTSTEELBLUE));

		tb.getTabs().add(pizzaSelect);
		tb.getTabs().add(sideSelect);

		tb.setMaxWidth(500);
		tb.setMaxHeight(1000);

		// toggle group for toggle buttons
		ToggleGroup pzs = new ToggleGroup();
		ToggleGroup pzc = new ToggleGroup();

		Label crustl = new Label("Size: ");

		ToggleButton smallPz = new ToggleButton("Small");
		smallPz.setMinWidth(Region.USE_PREF_SIZE);
		smallPz.setMaxWidth(Region.USE_PREF_SIZE);

		smallPz.setOnMouseClicked(e -> {

			rcpt.appendText("\nSmall Pizza                           $8.00\n");
			total = total + 8;
		});

		ToggleButton medPz = new ToggleButton("Medium");
		medPz.setMinWidth(Region.USE_PREF_SIZE);
		medPz.setMaxWidth(Region.USE_PREF_SIZE);

		medPz.setOnMouseClicked(e -> {
			rcpt.appendText("\nMedium Pizza                       $10.00\n");
			total = total + 10;
		});

		ToggleButton largePz = new ToggleButton("Large");
		largePz.setMinWidth(Region.USE_PREF_SIZE);
		largePz.setMaxWidth(Region.USE_PREF_SIZE);

		largePz.setOnMouseClicked(e -> {
			rcpt.appendText("\nlarge Pizza                            $12.00\n");
			total = total + 12;
		});

		Label saucel = new Label("Sauce: ");

		ToggleButton sauce = new ToggleButton("Classic");
		sauce.setMinWidth(Region.USE_PREF_SIZE);
		sauce.setMaxWidth(Region.USE_PREF_SIZE);

		sauce.setOnMouseClicked(e -> {
			rcpt.appendText("\tClassic sauce                $0.00\n");
		});

		ToggleButton lightSauce = new ToggleButton("Light");
		lightSauce.setMinWidth(Region.USE_PREF_SIZE);
		lightSauce.setMaxWidth(Region.USE_PREF_SIZE);

		lightSauce.setOnMouseClicked(e -> {
			rcpt.appendText("\tlight sauce                    $0.00\n");
		});

		ToggleButton extraSauce = new ToggleButton("Extra");
		extraSauce.setMinWidth(Region.USE_PREF_SIZE);
		extraSauce.setMaxWidth(Region.USE_PREF_SIZE);

		extraSauce.setOnMouseClicked(e -> {
			rcpt.appendText("\tExtra sauce                   $0.00\n");
		});

		GridPane gp = new GridPane();
		GridPane sp = new GridPane();

		for (int i = 0; i < 11; i++) {
			gp.getColumnConstraints().add(new ColumnConstraints(50)); // column
			sp.getColumnConstraints().add(new ColumnConstraints(50)); // is
			// wide
		}
		for (int i = 0; i < 11; i++) {
			gp.getColumnConstraints().add(new ColumnConstraints(50)); // column
			sp.getColumnConstraints().add(new ColumnConstraints(50)); // //
			// wide
		}
		pane.getChildren().add(rcpt);

		beef.setFitWidth(80);
		beef.setFitHeight(80);

		beef.setOnMouseClicked(e -> {
			rcpt.appendText("\tBeef                               $0.25\n");
			total = total + .25;
		});

		greenpepper.setFitWidth(80);
		greenpepper.setFitHeight(80);

		greenpepper.setOnMouseClicked(e -> {
			rcpt.appendText("\tGreen Peppers             $0.25\n");
			total = total + .25;
		});

		onion.setFitWidth(80);
		onion.setFitHeight(80);

		onion.setOnMouseClicked(e -> {
			rcpt.appendText("\tOnion                            $0.25\n");
			total = total + .25;
		});

		pep.setFitWidth(80);
		pep.setFitHeight(80);

		pep.setOnMouseClicked(e -> {
			rcpt.appendText("\tPepperoni                     $0.25\n");
			total = total + .25;
		});

		chicken.setFitWidth(80);
		chicken.setFitHeight(80);

		chicken.setOnMouseClicked(e -> {
			rcpt.appendText("\tChicken                         $0.25\n");
			total = total + .25;
		});

		ham.setFitWidth(80);
		ham.setFitHeight(80);

		ham.setOnMouseClicked(e -> {
			rcpt.appendText("\tHam                               $0.25\n");
			total = total + .25;
		});

		mush.setFitWidth(80);
		mush.setFitHeight(80);

		mush.setOnMouseClicked(e -> {
			rcpt.appendText("\tMushrooms                  $0.25\n");
			total = total + .25;
		});

		olive.setFitWidth(80);
		olive.setFitHeight(80);

		olive.setOnMouseClicked(e -> {
			rcpt.appendText("\tOlives                           $0.25\n");
			total = total + .25;
		});

		toma.setFitWidth(80);
		toma.setFitHeight(80);

		toma.setOnMouseClicked(e -> {
			rcpt.appendText("\tTomatoes                     $0.25\n");
			total = total + .25;
		});

		spin.setFitWidth(80);
		spin.setFitHeight(80);

		spin.setOnMouseClicked(e -> {
			rcpt.appendText("\tSpinich                          $0.25\n");
			total = total + .25;
		});

		pepsi.setFitWidth(80);
		pepsi.setFitHeight(80);

		pepsi.setOnMouseClicked(e -> {
			rcpt.appendText("\nPepsi                                     $2.99\n");
			total = total + 2.99;
		});

		mtndew.setFitWidth(80);
		mtndew.setFitHeight(80);

		mtndew.setOnMouseClicked(e -> {
			rcpt.appendText("\nMountain Dew                      $2.99\n");
			total = total + 2.99;
		});

		crush.setFitWidth(80);
		crush.setFitHeight(80);

		crush.setOnMouseClicked(e -> {
			rcpt.appendText("\nCrush                                    $2.99\n");
			total = total + 2.99;
		});

		drpep.setFitWidth(80);
		drpep.setFitHeight(80);

		drpep.setOnMouseClicked(e -> {
			rcpt.appendText("\nDr Pepper                             $2.99\n");
			total = total + 2.99;
		});

		bread.setFitWidth(80);
		bread.setFitHeight(80);

		bread.setOnMouseClicked(e -> {
			rcpt.appendText("\nBread Sticks                         $5.00\n");
			total = total + 2.99;
		});

		cheese.setFitWidth(80);
		cheese.setFitHeight(80);

		cheese.setOnMouseClicked(e -> {
			rcpt.appendText("\nCheese Sticks                      $6.00\n");
			total = total + 2.99;
		});

		brownie.setFitWidth(80);
		brownie.setFitHeight(80);

		brownie.setOnMouseClicked(e -> {
			rcpt.appendText("\nBrownie                                $6.00\n");
			total = total + 2.99;
		});

		wings.setFitWidth(80);
		wings.setFitHeight(80);

		wings.setOnMouseClicked(e -> {
			rcpt.appendText("\nTraditional Wings                 $5.00\n");
			total = total + 2.99;
		});

		bwings.setFitWidth(80);
		bwings.setFitHeight(80);

		bwings.setOnMouseClicked(e -> {
			rcpt.appendText("\nBone-out Wings                   $5.00\n");
			total = total + 2.99;
		});

		pzs.getToggles().addAll(sauce, lightSauce, extraSauce);
		pzc.getToggles().addAll(smallPz, medPz, largePz);

		gp.setVgap(20);
		gp.setHgap(20);

		sp.setVgap(20);
		sp.setHgap(20);

		// back button
		back.setOnMouseClicked(e -> {
			if (cod == 1) {
				chosePizza.close();
				onDeliveryClick();

			} else if (cod == 2) {
				chosePizza.close();
				onCarryoutClick();
			}
		});

		Button finishOrder = new Button("Finish Order");
		finishOrder.setMinWidth(Region.USE_PREF_SIZE);
		finishOrder.setMaxWidth(Region.USE_PREF_SIZE);

		finishOrder.setOnMouseClicked(e -> {
			if (finishCheck != 1) {
				if (cod == 2) {
					double taxvalue = total * .07;
					Text tax = new Text("Tax: " + taxvalue);
					Text finalttl = new Text("Total:                       $" + (total + taxvalue));
					rcpt.appendText("\nSubtotal:                               $" + total);
					rcpt.appendText("\nTax:                                      $  " + taxvalue);
					rcpt.appendText("\nTotal:                                     $" + (total + taxvalue));
					finishCheck++;
				}
				else {
					double taxvalue = total * .07;
					double dfee = 3.99;

					Text tax = new Text("Tax: " + taxvalue);
					Text finalttl = new Text("Total:                       $" + (total + taxvalue));
					rcpt.appendText("\nSubtotal:                               $" + total);
					rcpt.appendText("\nTax:                                       $  " + taxvalue);
					rcpt.appendText("\nDelivery fee;                          $  " + dfee);
					rcpt.appendText("\nTotal:                                     $" + (total + taxvalue +dfee));
					finishCheck++;
				}
			}
		});

		gp.add(crustl, 0, 1);
		gp.add(saucel, 0, 2);
		gp.add(lightSauce, 5, 2);
		gp.add(extraSauce, 1, 2);
		gp.add(sauce, 3, 2);
		gp.add(smallPz, 1, 1);
		gp.add(medPz, 3, 1);
		gp.add(largePz, 5, 1);
		gp.add(beef, 1, 3);
		gp.add(chicken, 3, 3);
		gp.add(ham, 5, 3);
		gp.add(pep, 1, 4);
		gp.add(greenpepper, 3, 4);
		gp.add(onion, 5, 4);
		gp.add(mush, 1, 5);
		gp.add(olive, 3, 5);
		gp.add(toma, 5, 5);
		gp.add(spin, 1, 6);
		gp.add(back, 1, 8);
		gp.add(next, 5, 8);
		gp.add(finishOrder, 3, 8);

		sp.add(pepsi, 1, 1);
		sp.add(mtndew, 3, 1);
		sp.add(crush, 5, 1);
		sp.add(drpep, 1, 2);
		sp.add(bread, 3, 2);
		sp.add(cheese, 5, 2);
		sp.add(wings, 1, 3);
		sp.add(bwings, 3, 3);
		sp.add(brownie, 5, 3);

		sideSelect.setContent(sp);
		pizzaSelect.setContent(gp);
		bp.setCenter(tb);
		bp.setLeft(pane);

		Scene scene = new Scene(bp, 1000, 1000);
		chosePizza.setScene(scene);
		chosePizza.show();
	}

	public static void main(String[] args) {

		launch(args);

	}
}