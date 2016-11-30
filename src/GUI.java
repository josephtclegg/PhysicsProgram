import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class GUI extends Application{
	
	private double mWeight1;
	private double mWeight2;
	private double mAccelG;
	private double mLength;
	private double uFrk;
	private double uFrs;
	private double dAngle;
	private double rAngle;
	private double mAccelH;
	
	private double pAccel;
	private int isKinetic = 0;
	
	private String[] mUnits = {"kg", "N"};
	
	private TextField mass1;
	private TextField mass2;
	private TextField accel;
	private TextField angle;
	private TextField length;
	private TextField friction;
	
	private int massIndex = 0;
	private int massIndex1 = 0;
	private double botLeftx;
	private double botLefty;
	private double topRightx;
	private double topRighty;

	private Polygon massRect1;
	private Polygon massRect2;

	private boolean hasDrawn = false;
	
	private Group group;
	
	private Polygon plane = new Polygon();
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		
		group = new Group();
		group.setStyle("-fx-background-color :#D3D3D3;");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		
		primaryStage.setTitle("Newton's Laws");
		
		mass1 = new TextField();
		
		mass2 = new TextField();
		
		mass1.setPromptText("kg");
		mass2.setPromptText("kg");
		
		Button btn = new Button();
		btn.setText("Animate");
		btn.setOnAction(new EventHandler<ActionEvent>(){
			
			public void handle(ActionEvent event){
				if(startAnimation()){
					drawRamp(primaryStage);
					drawBox();
					if(calculateAcc() != 0){

					}

				}
			}
		});
		
		ObservableList<String> options = FXCollections.observableArrayList ("Mass 1", "Weight 1");
		
		final ComboBox<String> massCombo = new ComboBox<String>(options);
		massCombo.setValue("Mass 1");
		
		massCombo.setOnAction(new EventHandler<ActionEvent>(){
			
				public void handle(ActionEvent event){
					massIndex = massCombo.getSelectionModel().getSelectedIndex();
					mass1.setPromptText(mUnits[massIndex]);
				}
		});
		
		
		ObservableList<String> options1 = FXCollections.observableArrayList ("Mass 2", "Weight 2");
		
		final ComboBox<String> massCombo1 = new ComboBox<String>(options1);
		massCombo1.setValue("Mass 2");
		
		massCombo1.setOnAction(new EventHandler<ActionEvent>(){
			
			public void handle(ActionEvent event){
				massIndex1 = massCombo1.getSelectionModel().getSelectedIndex();
				mass2.setPromptText(mUnits[massIndex1]);
			}
		});	
		
		accel = new TextField();
		accel.setPromptText("m/s\u00b2");
		
		final Label tAccel = new Label();
		tAccel.setText("Acceleration of Gravity");
		
		final Label tAngle = new Label();
		tAngle.setText("Angle");
		
		angle = new TextField();
		angle.setPromptText("Degrees");
		
		final Label tLength = new Label();
		tLength.setText("Length of Incline");
		
		length = new TextField();
		length.setPromptText("meters");
		
		ObservableList<String> optionsFriction = FXCollections.observableArrayList ("Fr\u2096", "Fr\u209B");
		
		final ComboBox<String> frictionCombo = new ComboBox<String>(optionsFriction);
		frictionCombo.setValue("Fr\u2096");
		
		frictionCombo.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event){
				isKinetic = frictionCombo.getSelectionModel().getSelectedIndex();
			}
		});
		
		friction = new TextField();
		
		grid.add(mass1, 1, 0);
		grid.add(massCombo, 0, 0);
		grid.add(mass2, 1, 1);
		grid.add(massCombo1, 0, 1);
		grid.add(accel, 1, 2);
		grid.add(tAccel, 0, 2);
		grid.add(tAngle, 0, 3);
		grid.add(angle, 1, 3);
		grid.add(tLength, 0, 4);
		grid.add(length, 1, 4);
		grid.add(frictionCombo, 0, 5);
		grid.add(friction, 1, 5);
		grid.add(btn, 1, 6);
	
		group.getChildren().add(grid);
		
		primaryStage.setScene(new Scene(group, 1280,1024));
		primaryStage.show();
		
	}
	
	private boolean startAnimation(){
		
		String temp;
		
		temp = accel.getText();
		if(!temp.equals("")){
			mAccelG = Double.parseDouble(temp);
			mAccelG = Math.abs(mAccelG);
			temp = "";
		}
		else
			return false;
		
		temp = mass1.getText();
		if(!temp.equals("")){
			mWeight1 = Double.parseDouble(temp);
			if(massIndex == 0){
				mWeight1 = mWeight1*mAccelG;
			}
			temp = "";
		}
		else
			return false;
		
		temp = mass2.getText();
		if(!temp.equals("")){
			mWeight2 = Double.parseDouble(temp);
			if(massIndex == 0){
				mWeight2 = mWeight2*mAccelG;
			}
			temp = "";
		}
		else
			return false;
		
		temp = friction.getText();
		if(!temp.equals("")){
			if(isKinetic == 0){
				uFrk = Double.parseDouble(temp);
			}
			else if(isKinetic == 1){
				uFrs = Double.parseDouble(temp);
			}
			temp = "";
		}
		else
			return false;
		
		temp = angle.getText();
		if(!temp.equals("")){
			dAngle = Double.parseDouble(temp);
			rAngle = (dAngle*Math.PI)/180;
			temp = "";
		}
		else
			return false;
		
		temp = length.getText();
		if(!temp.equals("")){
			mLength = Double.parseDouble(temp);
			temp = "";
		}
		else
			return false;
		
		System.out.println(mWeight1 + " " + mWeight2 + " " + uFrk + " " + uFrs + " " + mAccelG + " " + mLength + " " + dAngle + " " + rAngle);
		
		return true;
		
	}
	
	private void drawRamp(Stage stage){
		
		int windowH = (int)stage.getHeight();
		int windowW = (int)stage.getWidth();
		
		Image wood = new Image(Main.class.getResourceAsStream("wood_floor.jpg"));
		
		botLeftx = windowW/2;
		botLefty = (int)(windowH*.8);
		
		double pHeight;
		
		if(dAngle < 45){
		
			double mWidth = (int) (mLength*Math.cos(rAngle));
			System.out.println("Width" + mWidth + " Angle" + rAngle);
			double pConvert = 600/mWidth;
				
			double mHeight = (int)(mLength*Math.sin(rAngle));
			pHeight = mHeight*pConvert;

			plane.getPoints().addAll(new Double[]{
					botLeftx,botLefty,
					botLeftx+600,botLefty,
					botLeftx+600,botLefty-pHeight
			});

			topRightx = botLeftx+600;
			topRighty = botLefty-pHeight;
		}
		else{
			int mHeight = (int)(mLength*Math.sin(rAngle));
			double pConvert = (botLefty-200)/mHeight;
			
			double mWidth = (int)(mLength*Math.cos(rAngle));
			double pWidth = mWidth*pConvert;
			
			plane.getPoints().addAll(new Double[]{
					botLeftx+600,200.0,
					botLeftx+600,botLefty,
					(botLeftx+600)-pWidth,botLefty
			});

			topRightx = botLeftx + 600;
			topRighty = 200.0;
			
		}
		
		plane.setStroke(Color.BLACK);
		
		plane.setFill(new ImagePattern(wood,0,0,2,2,true));
		
		System.out.println(hasDrawn);
		
		if(!hasDrawn){
			group.getChildren().add(plane);
			hasDrawn = true;
		}
		else{
			group.getChildren().remove(plane);
			group.getChildren().add(plane);
		}
		
		
	}
	
	private double calculateAcc(){
		mAccelH = (mAccelG*Math.sin(rAngle))-(uFrk*mAccelG*Math.cos(rAngle));
		return mAccelH;
	}

	private void drawBox(){

		double rBotLeftx;
		double rBotLefty;

		double boxHeight = 100;
		double boxWidth = 100;

		massRect1 = new Polygon();
		massRect1.getPoints().addAll(new Double[]{
		/*bot Right*/ topRightx,topRighty,
		/*bot left*/  rBotLeftx = topRightx-(boxWidth*Math.cos(rAngle)),rBotLefty = topRighty + (boxWidth*Math.sin(rAngle)),
		/*top left*/  rBotLeftx - (boxHeight*Math.sin(rAngle)), rBotLefty - (boxHeight*Math.cos(rAngle)),
		/*top right*/ topRightx - (boxHeight*Math.sin(rAngle)), topRighty - (boxHeight*Math.cos(rAngle))
				}
		);

		Image metal = new Image(Main.class.getResourceAsStream("metal-texture.jpg"));

		massRect1.setFill(new ImagePattern(metal));

		massRect1.setStroke(Color.BLACK);

		group.getChildren().add(massRect1);



	}
	
	

}
