package uieng.businessapplication.presentationmodel;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.model.util.BindingMode;
import com.dlsc.formsfx.model.util.ResourceBundleService;
import com.dlsc.formsfx.model.validators.DoubleRangeValidator;
import com.dlsc.formsfx.model.validators.IntegerRangeValidator;
import com.dlsc.formsfx.model.validators.StringLengthValidator;
import com.dlsc.formsfx.view.util.ColSpan;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.concurrent.Service;
import javafx.scene.control.TextField;

import java.util.Locale;
import java.util.ResourceBundle;

public class FormsPM {

    /**
     * These are the resource bundles for german and english.
     */
    private ResourceBundle rbDE = ResourceBundle.getBundle("demo-locale", Locale.GERMAN);
    private ResourceBundle rbEN = ResourceBundle.getBundle("demo-locale", Locale.ENGLISH);

    /**
     * The default locale is English, thus the {@code ResourceBundleService} is
     * initialised with it.
     */
    private ResourceBundleService rbs = new ResourceBundleService(rbDE);

    // all controls
    private Form formInstance;
    private SongPM model;
    private DoubleField flaecheField;
    private boolean checkIfEinwohnerAndFlaecheIsFilled;

    FormsPM(SongPM model) {
        this.model = model;
    }

    /**
     * Creates or simply returns to form singleton instance.
     *
     * @return Returns the form instance.
     */
    public Form getFormInstance() {

        if (formInstance == null) {
            createForm();

        }
        return formInstance;

    }

    /**
     * Creates a new form instance with the required information.
     */
    private void createForm() {
        formInstance = Form.of(
                Group.of(
                        Field.ofStringType(model.titleProperty())
                                .required("kuerzel_label")
                                .label("canton_label")
                                .span(ColSpan.HALF)
                                .validate(StringLengthValidator.atLeast(1, "error_canton")),
                        Field.ofStringType(model.albumProperty())
                                .required("empty_field")
                                .label("kuerzel_label")
                                .span(ColSpan.HALF)
                                .validate(StringLengthValidator.exactly(2, "error_kuerzel")),
                        Field.ofStringType(model.artistProperty())
                                .required("empty_field")
                                .validate(StringLengthValidator.atLeast(1, "error_canton"))
                                .label("cantonnr_label")
                                .span(ColSpan.HALF),
                        Field.ofDoubleType(model.durationProperty())
                                .label("voice_label")
                                .span(ColSpan.HALF)
                                .validate(DoubleRangeValidator.between(0.5, 1.0, "error_standesstime")),
                        Field.ofStringType(model.genreProperty())
                                .label("accession_label")
                                .span(ColSpan.HALF)
                                .validate(StringLengthValidator.atLeast(1, "error_canton")),
                        Field.ofDoubleType(model.hottnessProperty())
                                .label("city_label")
                                .span(ColSpan.HALF)
                                .validate(DoubleRangeValidator.between(0.5, 1.0, "error_standesstime"))
                        
                )


        ).binding(BindingMode.CONTINUOUS)
                .i18n(rbs);
    }


    /**
     * Sets the locale of the form.
     *
     * @param language The language identifier for the new locale. Either DE or EN.
     */
    public void translate(String language) {
        switch (language) {
            case "EN":
                rbs.changeLocale(rbEN);
                break;
            case "DE":
                rbs.changeLocale(rbDE);
                break;
            default:
                throw new IllegalArgumentException("Not a valid locale");
        }
    }

    public void setupValueChangeListener() {

        flaecheField.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (flaecheField.hasChanged()) {
                checkIfEinwohnerAndFlaecheIsFilled = true;

            }
        });
    }
}

