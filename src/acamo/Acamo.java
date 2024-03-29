package acamo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CompletableFuture;

import acamo.ActiveAircrafts;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jsonstream.PlaneDataServer;
import messer.BasicAircraft;
import messer.*;
import senser.Senser;
import de.saring.leafletmap.*;
import de.saring.leafletmap.events.MapClickEventListener;


public class Acamo extends Application implements Observer
{
	private ActiveAircrafts activeAircrafts;
    private TableView<BasicAircraft> table = new TableView<BasicAircraft>();
    private ObservableList<BasicAircraft> aircraftList = FXCollections.observableArrayList();
    private ArrayList<String> fields;
 
    private double latitude = 48.7433425;
    private double longitude = 9.3201122;
    private static boolean haveConnection = true;
    
    public static void main(String[] args) {
		launch(args);
    }
 
    @Override
    public void start(Stage stage) {
		String urlString = "https://public-api.adsbexchange.com/VirtualRadar/AircraftList.json";
		PlaneDataServer server;
		
		if(haveConnection)
			server = new PlaneDataServer(urlString, latitude, longitude, 50);
		else
			server = new PlaneDataServer();
		
		new Thread(server).start();

		Senser senser = new Senser(server);
		new Thread(senser).start();
		
		Messer messer = new Messer();
		senser.addObserver(messer);
		new Thread(messer).start();
		
		// TODO: create activeAircrafts
		activeAircrafts = new ActiveAircrafts();
		
		// TODO: activeAircrafts and Acamo needs to observe messer  
		
        fields = BasicAircraft.getAttributesNames();

        // TODO: Fill column header using the attribute names from BasicAircraft
		for(int i = 0;i < fields.size();i++) {
		}
		table.setItems(aircraftList);

		table.setEditable(false);
        table.autosize();
 
        // TODO: Create layout of table and pane for selected aircraft
        
        // TODO: Add event handler for selected aircraft
 
        
		Scene scene = new Scene(...);
        stage.setScene(scene);
        stage.setTitle("Acamo");
        stage.sizeToScene();
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.show();
    }

    
    // TODO: When messer updates Acamo (and activeAircrafts) the aircraftList must be updated as well
    @Override
    public void update(Observable o, Object arg) {

    }
}
