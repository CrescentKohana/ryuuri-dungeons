package ryuuri.ui;

import javafx.stage.FileChooser;
import ryuuri.dao.ImageUtil;
import ryuuri.mapgen.CelluralMapHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * A class for the application GUI.
 */

public class GUI extends Application {
    private Slider widthSlider, heightSlider, chanceSlider, stepsSlider, xScaleSlider, yScaleSlider;
    private Button generateBtn, saveImgFile, saveDataBtn, importBtn;
    private ImageUtil imageUtil;

    /**
     * The main window of the application.
     *
     * @param stage Main stage
     */
    private void initUI(Stage stage) {

        // Better antialiasing for the text.
        System.setProperty("prism.lcdtext", "false");

        // Basic UI settings //
        GridPane header = new GridPane();
        BorderPane borderPane = new BorderPane();
        header.setAlignment(Pos.TOP_CENTER);
        header.setPadding(new Insets(10));
        header.setVgap(5);
        header.setHgap(5);

        // Sliders //
        widthSlider = new Slider(1, 10000, 30);
        heightSlider = new Slider(1, 10000, 30);
        chanceSlider = new Slider(1, 100, 45);
        stepsSlider = new Slider(1, 10000, 3);
        xScaleSlider = new Slider(1, 16, 1);
        yScaleSlider = new Slider(1, 16, 1);

        HBox widthFrame = createSlider(
                widthSlider,
                "x (px)",
                "Width of the dungeon in pixels.",
                30,
                10000
        );
        HBox heightFrame = createSlider(
                heightSlider,
                "y (px)",
                "Height of the dungeon in pixels.",
                30,
                10000
        );
        HBox chanceFrame = createSlider(
                chanceSlider,
                "%",
                "Chance for the cells (pixels) to die on each step.",
                45,
                100
        );
        HBox stepsFrame = createSlider(
                stepsSlider,
                "Steps",
                "The amount of simulation steps to run.",
                3,
                10000
        );
        HBox xScaleFrame = createSlider(
                xScaleSlider,
                "x (factor)",
                "Times to scale the x axis of the image.",
                1,
                16
        );
        HBox yScaleFrame = createSlider(
                yScaleSlider,
                "y (factor)",
                "Times to scale the y axis of the image.",
                1,
                16
        );

        // Buttons //
        generateBtn = new Button("Generate");
        saveImgFile = new Button("Save image as");
        saveImgFile.setDisable(true);
        // saveDataBtn = new Button("Save raw data");
        // importBtn = new Button("Import raw data");

        // Actions
        ImageView imageView = new ImageView();
        generateBtn.setOnAction(e -> {
            generate(
                    (int) widthSlider.getValue(),
                    (int) heightSlider.getValue(),
                    (int) chanceSlider.getValue(),
                    (int) stepsSlider.getValue(),
                    (int) xScaleSlider.getValue(),
                    (int) yScaleSlider.getValue()
            );
            imageView.setImage(imageUtil.getImage());
            saveImgFile.setDisable(false);
        });

        saveImgFile.setOnAction(e -> {
            try {
                saveToFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        int row = 0;
        header.add(generateBtn, 0, row);
        header.add(widthFrame,  1, row);
        header.add(heightFrame, 1, ++row);
        header.add(saveImgFile, 0, row);
        header.add(chanceFrame, 1, ++row);
        header.add(stepsFrame,  1, ++row);
        header.add(xScaleFrame, 1, ++row);
        header.add(yScaleFrame, 1, ++row);

        // Close all child windows when exiting the main app
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        borderPane.setCenter(imageView);
        borderPane.setLeft(header);
        var scene = new Scene(borderPane, 854, 480);

        stage.setMinWidth(854);
        stage.setMinHeight(480);
        stage.setTitle("Ryuuri");
        // TODO: Add an icon
        // stage.getIcons().add(new Image(""));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Helper method for creating the dungeon.
     *
     * @param width Width in pixels as integer
     * @param height Height in pixels as integer
     * @param chance The chance for the cells (pixels) to die as integer
     * @param steps Simulation steps to do as integer
     * @param xFactor Times to scale the image in the x axis
     * @param yFactor Times to scale the image in the y axis
     */
    public void generate(int width, int height, int chance, int steps, int xFactor, int yFactor) {
        CelluralMapHandler cells = new CelluralMapHandler(width, height, chance, steps);

        // String output = cells.mapToString();

        imageUtil = new ImageUtil(cells.map);
        imageUtil.scaleData(xFactor, yFactor);

        imageUtil.generateImage();
    }

    /**
     * Helper method for creating the dungeon.
     *
     */
    public void saveToFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("dungeon.png");
        File file = fileChooser.showSaveDialog(null);
        imageUtil.writeFile(file);
    }

    /**
     * Creates a slider with editable box and tooltips.
     *
     * @param slider Slider class
     * @param unit String which shows the unit
     * @param tooltip Hoverable tooltip as String
     * @param initial Initial value for the slider as integer
     * @param max Max value for the slider as integer
     * @return The slider with HBox as its frame
     */
    private HBox createSlider(Slider slider, String unit, String tooltip, int initial, int max) {
        Label label = new Label(unit);
        label.setTooltip(new Tooltip(tooltip));
        label.setPrefWidth(50);

        final IntField intField = new IntField(0, max, initial);
        intField.setTooltip(new Tooltip("Click on me to edit the slider's value!"));
        intField.valueProperty().bindBidirectional(slider.valueProperty());
        intField.setPrefWidth(50);

        HBox sliderFrame = new HBox(10);
        sliderFrame.getChildren().addAll(label, intField, slider);
        sliderFrame.setStyle("-fx-background-color: white; -fx-padding:10; -fx-font-size: 12; -fx-alignment: baseline-left;");

        return sliderFrame;
    }

    @Override
    public void start(Stage stage) {
        initUI(stage);
    }

    /** Starts the GUI.
     *
     * @param args Arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
