package org.spigotmc.patcher;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.md_5.jbeat.Patcher;

import org.spigotmc.patcher.utils.filechooser;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;

public class Home {
	

	static File input_file;
	static File output_file;
	static File patch_file;
	
	public static void showStage(final Stage stage){
		GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Scene scene = new Scene(grid, 640, 480);
        
        //stage.setScene(new Scene(grid, 640, 480));
        stage.setScene(scene);
        stage.setResizable(false);
        
        Text scenetitle = new Text("Spigot patcher");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 1, 0);
        GridPane.setHalignment(scenetitle, HPos.CENTER);
        
        
        stage.show();
        stage.setScene(scene);
        
        Label copyright = new Label("Made by thijs_a");
        
        grid.add(copyright, 1, 12);
        GridPane.setHalignment(copyright, HPos.CENTER);
        copyright.setOpacity(0.2);
        //GridPane.setValignment(copyright, VPos.BOTTOM);
        
        
        final FileChooser fileChooser = new FileChooser();
        filechooser.configureFileChooser(fileChooser);
        
        final FileChooser patchfilechooser = new FileChooser();
        filechooser.configurepatchFileChooser(patchfilechooser);
        
        Label Ip = new Label("Start jar:");
        grid.add(Ip, 0, 4);

        final TextField ip_textfield = new TextField();
        grid.add(ip_textfield, 1, 4);
        ip_textfield.setEditable(false);
        
        final Button open_start_jar = new Button("Open");
		HBox hbopen_start_jar = new HBox(10);
		
		hbopen_start_jar.setAlignment(Pos.CENTER);
		hbopen_start_jar.getChildren().add(open_start_jar);
		grid.add(hbopen_start_jar, 2, 4);
		
        final TextArea console = new TextArea();
        console.setPrefRowCount(10);
        console.setPrefColumnCount(100);
        console.setWrapText(true);
        console.setPrefWidth(250);
        console.setEditable(false);
        GridPane.setHalignment(console, HPos.CENTER);
        grid.add(console, 1, 10);

		
		
        Label patchfile = new Label("Patch file:");
        grid.add(patchfile, 0, 8);

        final TextField patchfile_textfield = new TextField();
        grid.add(patchfile_textfield, 1, 8);
        patchfile_textfield.setEditable(false);
        
        final Button open_patchfile = new Button("Open");
		HBox hbopen_patchfile = new HBox(10);
		
		hbopen_patchfile.setAlignment(Pos.CENTER);
		hbopen_patchfile.getChildren().add(open_patchfile);
		grid.add(hbopen_patchfile, 2, 8);
        
		

		
        Label userName = new Label("Export jar:");
        grid.add(userName, 0, 6);

        final TextField userTextField = new TextField();
        grid.add(userTextField, 1, 6);
        userTextField.setEditable(false);
        
        final Button save_start_jar = new Button("Save");
		HBox hbsave_start_jar = new HBox(10);
		
		hbsave_start_jar.setAlignment(Pos.CENTER);
		hbsave_start_jar.getChildren().add(save_start_jar);
		grid.add(save_start_jar, 2, 6);
		
        final Button run = new Button("Run");
		HBox hbrun = new HBox(10);
		GridPane.setHalignment(run, HPos.CENTER);
		hbrun.setAlignment(Pos.CENTER);
		hbrun.getChildren().add(run);
		grid.add(run, 1, 11);

        open_start_jar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				
				  File file = fileChooser.showOpenDialog(stage);
                  if (file != null) {
                     ip_textfield.setText(file.getAbsolutePath());
                     input_file = file;
                  }
			}
		});
        
        open_patchfile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				
				  File file = patchfilechooser.showOpenDialog(stage);
                  if (file != null) {
                     patchfile_textfield.setText(file.getAbsolutePath());
                     patch_file = file;
                  }
			}
		});
        
        save_start_jar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
					FileChooser fileChooser_save = new FileChooser();
		            fileChooser.setTitle("Save Jar");
		            File file_save = fileChooser.showSaveDialog(stage);
		            fileChooser_save.getExtensionFilters().addAll(
		            		new FileChooser.ExtensionFilter("Jar", "*.jar"));
		            if (file_save != null) {
		                userTextField.setText(file_save.getAbsolutePath());
		                output_file = file_save;
		            }
			}
		});

        run.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				if(!checkData(ip_textfield.getText(), patchfile_textfield.getText(), userTextField.getText()) == true){
					console.setText("Check your data!");
				}else{
				console.clear();
				run.setDisable(true);
				save_start_jar.setDisable(true);
				open_patchfile.setDisable(true);
				open_start_jar.setDisable(true);
				
				console.setText("Starting patching process, please wait...");
				try {
					console.setText(console.getText() + "\n" + "Input md5 Checksum: " + Files.hash( input_file, Hashing.md5() ) );
					console.setText(console.getText() + "\n \n" + "Patch md5 Checksum: " + Files.hash( patch_file, Hashing.md5() ));
					
				} catch (IOException e1) {
					console.setText(console.getText() + "\n \n" + "Error while patching");
					run.setDisable(false);
					save_start_jar.setDisable(false);
					open_patchfile.setDisable(false);
					open_start_jar.setDisable(false);
				}
				
		        try
		        {
		        	 new Patcher( patch_file, input_file, output_file ).patch();
					 console.setText(console.getText() + "\n \n" + "***** Your file has been patched and verified! We hope you enjoy using Spigot! *****");
					 console.setText(console.getText() + "\n \n" + "Output md5 Checksum: " + Files.hash( output_file, Hashing.md5() ));
					 run.setDisable(false);
					save_start_jar.setDisable(false);
					open_patchfile.setDisable(false);
					open_start_jar.setDisable(false);
					Desktop desktop = Desktop.getDesktop();
					desktop.open(output_file.getParentFile());
		        } catch ( Exception ex )
		        {
		            //System.err.println( "***** Exception occured whilst patching file!" );
		            //ex.printStackTrace();
		        	console.setText(console.getText() + "\n \n" + "Exception occured whilst patching file! Please check your files!");
		            File output = output_file;
		            output.delete();
					run.setDisable(false);
					save_start_jar.setDisable(false);
					open_patchfile.setDisable(false);
					open_start_jar.setDisable(false);
		            
		        }
		        
				
			}
			}
		});
        
       
		
        
	}
	
	public static boolean checkData(String original, String patch, String output){
		boolean check1 = false;
		boolean check2 = false;
		boolean check3 = false;
		
		if(original.length() >1){
			check1 = true;
		}
		if(patch.length() >1){
			check2 = true;
		}
		if(output.length() >1){
			check3 = true;
		}
		
		if(check1 == true && check2 == true && check3 == true){
			return true;
		}else{
			return false;
		}
	}
	
	
	

}
