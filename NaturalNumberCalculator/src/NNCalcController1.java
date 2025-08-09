import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Controller class.
 *
 * @author KaylinKerrick
 */
public final class NNCalcController1 implements NNCalcController {

    /**
     * Model object.
     */
    private final NNCalcModel model;

    /**
     * View object.
     */
    private final NNCalcView view;

    /**
     * Useful constants.
     */
    private static final NaturalNumber TWO = new NaturalNumber2(2),
            INT_LIMIT = new NaturalNumber2(Integer.MAX_VALUE);

    /**
     * Updates this.view to display this.model, and to allow only operations
     * that are legal given this.model.
     *
     * @param model
     *            the model
     * @param view
     *            the view
     * @ensures [view has been updated to be consistent with model]
     */
    private static void updateViewToMatchModel(NNCalcModel model, NNCalcView view) {
        //Reference top and bottom
        NaturalNumber top = model.top();
        NaturalNumber bottom = model.bottom();

        //Update the top and bottom display to match the model
        view.updateTopDisplay(top);
        view.updateBottomDisplay(bottom);

        //Top must be >= bottom to subtract
        view.updateSubtractAllowed(top.compareTo(bottom) >= 0);

        //Can't divide by zero
        view.updateDivideAllowed(!bottom.isZero());

        //Bottom must be <= INT_LIMIT to raise the power
        view.updatePowerAllowed(bottom.compareTo(INT_LIMIT) <= 0);

        // Allow root when 2 <= bottom <= INT_LIMIT
        view.updateRootAllowed(
                bottom.compareTo(TWO) >= 0 && bottom.compareTo(INT_LIMIT) <= 0);

    }

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public NNCalcController1(NNCalcModel model, NNCalcView view) {
        this.model = model;
        this.view = view;
        updateViewToMatchModel(model, view);
    }

    @Override
    public void processClearEvent() {
        /*
         * Get alias to bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        bottom.clear();
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSwapEvent() {
        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        NaturalNumber temp = top.newInstance();
        temp.transferFrom(top);
        top.transferFrom(bottom);
        bottom.transferFrom(temp);
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processEnterEvent() {
        //Get top and bottom references
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        //Transfer value from bottom to top
        top.transferFrom(bottom);
        //Call update view method
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        //Add the top value to the bottom
        bottom.add(top);
        //Clear top value after adding
        top.clear();
        //Call update view method
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processSubtractEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        //Subtract top value from bottom
        bottom.subtract(top);
        //Clear top
        top.clear();
        //Update the view
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processMultiplyEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        //Multiply bottom value by the top
        bottom.multiply(top);
        //Clear top and update view
        top.clear();
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processDivideEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        //Divide the top by the bottom, remainder is stored
        NaturalNumber remainder = top.divide(bottom);
        //The top value becomes the remainder value
        top.transferFrom(remainder);
        //Update view
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processPowerEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        //Ensure bottom value is an integer
        int exponent = bottom.toInt();
        //Copy the top value to the bottom
        bottom.copyFrom(top);
        //Raise the value to the previously saved "bottom" or exponent value
        bottom.power(exponent);
        //Clear top
        top.clear();
        //Update view
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processRootEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        //Ensure bottom is an integer, save as "root"
        int root = bottom.toInt();
        //Copy top into the bottom
        bottom.copyFrom(top);
        //Take the root of the value
        bottom.root(root);
        //Clear top and update view
        top.clear();
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddNewDigitEvent(int digit) {
        NaturalNumber bottom = this.model.bottom();
        //Multiply bottom by 10 and add on digit to the end
        bottom.multiplyBy10(digit);
        //Update view
        updateViewToMatchModel(this.model, this.view);

    }

}
