package org.pentaho.di.sdk.samples.steps.deciphermentAddress;

import com.google.common.base.Strings;
import org.apache.commons.lang.StringUtils;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowDataUtil;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.*;

public class DeciphermentAddressStep extends BaseStep implements StepInterface {
    private DeciphermentAddressStepData data;
    private DeciphermentAddressStepMeta meta;
    /**
     * This is the base step that forms that basis for all steps. You can derive from this class to implement your own
     * steps.
     *
     * @param stepMeta          The StepMeta object to run.
     * @param stepDataInterface the data object to store temporary data, database connections, caches, result sets,
     *                          hashtables etc.
     * @param copyNr            The copynumber for this step.
     * @param transMeta         The TransInfo of which the step stepMeta is part of.
     * @param trans             The (running) transformation to obtain information shared among the steps.
     */
    public DeciphermentAddressStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta, Trans trans) {
        super(stepMeta, stepDataInterface, copyNr, transMeta, trans);
    }

    @Override
    public boolean init(StepMetaInterface smi, StepDataInterface sdi) {

        DeciphermentAddressStepMeta meta = (DeciphermentAddressStepMeta) smi;
        DeciphermentAddressStepData data = (DeciphermentAddressStepData) sdi;
        if ( !super.init( meta, data ) ) {
            return false;
        }

        // Add any step-specific initialization that may be needed here
        return true;
    }
    /**
     * Once the transformation starts executing, the processRow() method is called repeatedly
     * by PDI for as long as it returns true. To indicate that a step has finished processing rows
     * this method must call setOutputDone() and return false;
     *
     * Steps which process incoming rows typically call getRow() to read a single row from the
     * input stream, change or add row content, call putRow() to pass the changed row on
     * and return true. If getRow() returns null, no more rows are expected to come in,
     * and the processRow() implementation calls setOutputDone() and returns false to
     * indicate that it is done too.
     *
     * Steps which generate rows typically construct a new row Object[] using a call to
     * RowDataUtil.allocateRowData(numberOfFields), add row content, and call putRow() to
     * pass the new row on. Above process may happen in a loop to generate multiple rows,
     * at the end of which processRow() would call setOutputDone() and return false;
     *
     * @param smi the step meta interface containing the step settings
     * @param sdi the step data interface that should be used to store
     *
     * @return true to indicate that the function should be called again, false if the step is done
     */
    public boolean processRow(StepMetaInterface smi, StepDataInterface sdi) throws KettleException {
        meta = (DeciphermentAddressStepMeta) smi;
        data = (DeciphermentAddressStepData) sdi;

        Object[] r = getRow(); // get row, blocks when needed!
        if (r == null) // no more input to be expected...
        {
            setOutputDone();
            return false;
        }

        if (first) {
            first = false;

            data.outputRowMeta = (RowMetaInterface) getInputRowMeta().clone();
            meta.getFields( data.outputRowMeta, getStepname(), null, null, this, null, null );
            data.inputFieldIndex = data.outputRowMeta.indexOfValue(meta.getInputField());

            logBasic("template step initialized successfully");

        }
        String decipherment = "";
        if (r[data.inputFieldIndex]!=null){
            decipherment = r[data.inputFieldIndex].toString().trim();
        }

        if (!Strings.isNullOrEmpty(decipherment)) {
            decipherment = StringUtils.left(decipherment, 3).concat("***").concat(StringUtils.right(decipherment, 3));
        //StringUtils.left(decipherment, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(decipherment, decipherment.length()-11), StringUtils.length(decipherment), "*"), "***"));

        }

        //Object[] outputRow = RowDataUtil.addValueData(r, data.outputRowMeta.size() - 1, decipherment);
        r[data.inputFieldIndex] = decipherment;
        Object[] outputRow = r;
        putRow(data.outputRowMeta, outputRow);
        if (checkFeedback(getLinesRead())) {
            logBasic("Linenr " + getLinesRead()); // Some basic logging
        }

        return true;
    }
    // Run is were the action happens!
    public void run() {
        logBasic("Starting to run...");
        try {
            while (processRow(meta, data) && !isStopped()) {

            }
        } catch (Exception e) {
            logError("Unexpected error : " + e.toString());
            logError(Const.getStackTracker(e));
            setErrors(1);
            stopAll();
        } finally {
            dispose(meta, data);
            logBasic("Finished, processing " + getLinesRead() + " rows");
            markStop();
        }
    }
    /**
     * This method is called by PDI once the step is done processing.
     *
     * The dispose() method is the counterpart to init() and should release any resources
     * acquired for step execution like file handles or database connections.
     *
     * The meta and data implementations passed in can safely be cast
     * to the step's respective implementations.
     *
     * It is mandatory that super.dispose() is called to ensure correct behavior.
     *
     * @param smi   step meta interface implementation, containing the step settings
     * @param sdi  step data interface implementation, used to store runtime information
     */
    public void dispose( StepMetaInterface smi, StepDataInterface sdi ) {

        // Casting to step-specific implementation classes is safe
        DeciphermentAddressStepMeta meta = (DeciphermentAddressStepMeta) smi;
        DeciphermentAddressStepData data = (DeciphermentAddressStepData) sdi;

        // Add any step-specific initialization that may be needed here

        // Call superclass dispose()
        super.dispose( meta, data );
    }


}
