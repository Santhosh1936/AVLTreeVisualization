package AVL;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

//import static jdk.internal.org.jline.terminal.Terminal.MouseTracking.Button;

public class AVLTreeGUI extends Application {
    private Pane treePane = new Pane();
    private AVL.Solution avlTree = new AVL.Solution(); // Use your existing AVL Tree logic
    private AVL.BSTNode root = null; // Root of the AVL Tree

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create GUI Components
        TextField inputField = new TextField();
        inputField.setPromptText("Enter a value");

        Button insertButton = new Button("Insert");
        Button deleteButton = new Button("Delete");
        Button searchButton = new Button("Search");

        HBox controls = new HBox(10, inputField, insertButton, deleteButton, searchButton);
        controls.setStyle("-fx-padding: 10; -fx-alignment: center;");

        VBox layout = new VBox(10, treePane, controls);
        layout.setStyle("-fx-padding: 10;");

        // Button Actions
        insertButton.setOnAction(e -> {
            String[] values = inputField.getText().trim().split(","); // Split by comma
            boolean inserted = false;

            for (String val : values) {
                try {
                    int value = Integer.parseInt(val.trim()); // Convert to integer
                    root = avlTree.insertNode(root, value);
                    inserted = true;
                } catch (NumberFormatException ex) {
                    showAlert("Invalid Input", "Please enter valid integers separated by commas.");
                    return; // Stop execution if any input is invalid
                }
            }

            if (inserted) {
                updateTreeVisualization(); // Update the tree if at least one value was inserted
            }
            inputField.clear();
        });

//        insertButton.setOnAction(e -> {
//            try {
//                int value = Integer.parseInt(inputField.getText().trim());
//                root = avlTree.insertNode(root, value);
//                updateTreeVisualization();
//                inputField.clear();
//            } catch (NumberFormatException ex) {
//                showAlert("Invalid Input", "Please enter a valid integer.");
//            }
//        });

        deleteButton.setOnAction(e -> {
            try {
                int value = Integer.parseInt(inputField.getText().trim());
                root = avlTree.deleteNode(root, value);
                updateTreeVisualization();
                inputField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid integer.");
            }
        });

        searchButton.setOnAction(e -> {
            try {
                int value = Integer.parseInt(inputField.getText().trim());
                AVL.BSTNode found = avlTree.searchNode(root, value);
                if (found != null) {
                    showAlert("Search Result", "Node with value " + value + " found!");
                } else {
                    showAlert("Search Result", "Node with value " + value + " not found.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid integer.");
            }
        });

        // Configure Scene and Stage
        primaryStage.setTitle("AVL Tree Visualization");
        primaryStage.setScene(new Scene(layout, 800, 600));
        primaryStage.show();
    }

    // Update the tree visualization
    private void updateTreeVisualization() {
        treePane.getChildren().clear();
        if (root != null) {
            drawTree(root, 400, 50, 200); // Starting position
        }
    }

    private void drawTree(AVL.BSTNode node, double x, double y, double xOffset) {
        if (node == null) return;

        // Draw the node
        Circle circle = new Circle(x, y, 20, Color.LIGHTBLUE);
        circle.setStroke(Color.BLACK);
        Label label = new Label(String.valueOf(node.data));
        label.setLayoutX(x - 10);
        label.setLayoutY(y - 10);

        treePane.getChildren().addAll(circle, label);

        // Draw edges to children
        if (node.left != null) {
            Line line = new Line(x, y, x - xOffset, y + 80);
            treePane.getChildren().add(line);
            drawTree(node.left, x - xOffset, y + 80, xOffset / 2);
        }
        if (node.right != null) {
            Line line = new Line(x, y, x + xOffset, y + 80);
            treePane.getChildren().add(line);
            drawTree(node.right, x + xOffset, y + 80, xOffset / 2);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

