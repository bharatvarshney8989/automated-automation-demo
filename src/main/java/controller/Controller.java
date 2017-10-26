package controller;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javapoet.CodeGenerator;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by kohlih on 26-10-2017.
 */
public class Controller extends AnchorPane implements Initializable {

    @FXML
    private AnchorPane mainwindow;
    @FXML
    private Button btnInspect;
    @FXML
    private TextField txtSwagger;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try {
            mainwindow.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

    public void inspect(Event event) throws Exception
    {
        System.out.println("Reached Inspect");
        System.out.println("Swagger Link - " + txtSwagger.getText());
        CodeGenerator.generate();
    }

    public void btnInspectMouseEntered(Event event)
    {
        btnInspect.setStyle("-fx-background-color:#58D68D");
        Platform.runLater(
                () -> {
                    setupCustomTooltipBehavior(100,10000,100);
                    Tooltip tooltip = new Tooltip();
                    tooltip.setText("Click to check out the Swagger link entered in above Text box");
                    Tooltip.install(btnInspect,tooltip);
                }
        );
    }

    public void btnInspectMouseExited(Event event)
    {
        btnInspect.setStyle(null);
    }

    public void txtSwaggerMouseEntered(Event event)
    {
        Platform.runLater(
                () -> {
                    setupCustomTooltipBehavior(100,10000,100);
                    Tooltip tooltip = new Tooltip();
                    tooltip.setText("Enter Swagger Link for your API in this Text box");
                    Tooltip.install(txtSwagger,tooltip);
                }
        );
    }

    /**
     * <p>
     * Tooltip behavior is controlled by a private class javafx.scene.control.Tooltip$TooltipBehavior.
     * All Tooltips share the same TooltipBehavior instance via a static private member BEHAVIOR, which
     * has default values of 1sec for opening, 5secs visible, and 200 ms close delay (if mouse exits from node before 5secs).
     *
     * The hack below constructs a custom instance of TooltipBehavior and replaces private member BEHAVIOR with
     * this custom instance.
     * </p>
     *
     */
    private static void setupCustomTooltipBehavior(int openDelayInMillis, int visibleDurationInMillis, int closeDelayInMillis) {
        try {

            Class TTBehaviourClass = null;
            Class<?>[] declaredClasses = Tooltip.class.getDeclaredClasses();
            for (Class c:declaredClasses) {
                if (c.getCanonicalName().equals("javafx.scene.control.Tooltip.TooltipBehavior")) {
                    TTBehaviourClass = c;
                    break;
                }
            }
            if (TTBehaviourClass == null) {
                // abort
                return;
            }
            Constructor constructor = TTBehaviourClass.getDeclaredConstructor(
                    Duration.class, Duration.class, Duration.class, boolean.class);
            if (constructor == null) {
                // abort
                return;
            }
            constructor.setAccessible(true);
            Object newTTBehaviour = constructor.newInstance(
                    new Duration(openDelayInMillis), new Duration(visibleDurationInMillis),
                    new Duration(closeDelayInMillis), false);
            if (newTTBehaviour == null) {
                // abort
                return;
            }
            Field ttbehaviourField = Tooltip.class.getDeclaredField("BEHAVIOR");
            if (ttbehaviourField == null) {
                // abort
                return;
            }
            ttbehaviourField.setAccessible(true);

            // Cache the default behavior if needed.
            Object defaultTTBehavior = ttbehaviourField.get(Tooltip.class);
            ttbehaviourField.set(Tooltip.class, newTTBehaviour);

        } catch (Exception e) {
            System.out.println("Aborted setup due to error:" + e.getMessage());
        }
    }

}