module task4 {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.media;
	requires java.desktop;
	requires java.sql;
	requires javafx.base;

	opens application to javafx.graphics, javafx.fxml;
}
